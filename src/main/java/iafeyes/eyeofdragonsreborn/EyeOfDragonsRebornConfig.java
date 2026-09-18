package iafeyes.eyeofdragonsreborn;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class EyeOfDragonsRebornConfig {
    public static final ModConfigSpec SPEC;

    public static final EyeConfig FIRE_DRAGON_EYE;
    public static final EyeConfig ICE_DRAGON_EYE;
    public static final EyeConfig LIGHTNING_DRAGON_EYE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        FIRE_DRAGON_EYE = new EyeConfig(builder, "fire_dragon_eye", "Fire Dragon Eye");
        ICE_DRAGON_EYE = new EyeConfig(builder, "ice_dragon_eye", "Ice Dragon Eye");
        LIGHTNING_DRAGON_EYE = new EyeConfig(builder, "lightning_dragon_eye", "Lightning Dragon Eye");

        SPEC = builder.build();
    }

    public static class EyeConfig {
        public final ModConfigSpec.IntValue searchRadius;
        public final ModConfigSpec.ConfigValue<List<? extends String>> dimensionRadiusOverrides;
        public final ModConfigSpec.BooleanValue useDimensionWhitelist;
        public final ModConfigSpec.ConfigValue<List<? extends String>> dimensionList;
        public final ModConfigSpec.BooleanValue filterDeadDragons;
        public final ModConfigSpec.BooleanValue filterTamedDragons;
        public final ModConfigSpec.BooleanValue useStageWhitelist;
        public final ModConfigSpec.ConfigValue<List<? extends Integer>> stageList;

        public EyeConfig(ModConfigSpec.Builder builder, String path, String name) {
            builder.comment("Settings for " + name).push(path);

            searchRadius = builder
                    .comment("Default search radius (in blocks) when using this eye.",
                            "Default: 1000")
                    .defineInRange("searchRadius", 1000, 1, 10000);

            dimensionRadiusOverrides = builder
                    .comment("Per-dimension search radius overrides.",
                            "Format: \"<dimension_id>:<radius>\"",
                            "Example: [\"minecraft:the_nether:500\", \"minecraft:the_end:200\"]",
                            "Note: Dimension-specific radius takes priority over the default search radius.",
                            "Default: []")
                    .defineListAllowEmpty(
                            "dimensionRadiusOverrides",
                            List.of(),
                            o -> o instanceof String s && s.contains(":")
                    );

            useDimensionWhitelist = builder
                    .comment("If true, dimensionList is a whitelist (only listed dimensions allow searching).",
                            "If false, it's a blacklist (listed dimensions are blocked).",
                            "Default: false (blacklist mode)")
                    .define("useDimensionWhitelist", false);

            dimensionList = builder
                    .comment("List of dimension IDs, e.g. \"minecraft:overworld\", \"minecraft:the_nether\", \"minecraft:the_end\".",
                            "Behavior depends on useDimensionWhitelist.",
                            "Default: [] (empty)",
                            "With blacklist mode + empty list, all dimensions are allowed.",
                            "With whitelist mode + empty list, all dimensions are blocked.")
                    .defineListAllowEmpty(
                            "dimensionList",
                            List.of(),
                            o -> o instanceof String s && !s.isEmpty()
                    );

            filterDeadDragons = builder
                    .comment("If true, dead dragons are excluded from search results.",
                            "Default: true")
                    .define("filterDeadDragons", true);

            filterTamedDragons = builder
                    .comment("If true, tamed dragons are excluded from search results.",
                            "Default: true")
                    .define("filterTamedDragons", true);

            useStageWhitelist = builder
                    .comment("If true, stageList is a whitelist (only listed stages are searched).",
                            "If false, stageList is a blacklist (listed stages are excluded).",
                            "Default: false (blacklist mode)")
                    .define("useStageWhitelist", false);

            stageList = builder
                    .comment("List of dragon stages (1-5).",
                            "Behavior depends on useStageWhitelist.",
                            "Default: [1, 2] (exclude stage 1 and 2)",
                            "With blacklist mode + [1, 2], only stage 3, 4, 5 are searched.",
                            "With whitelist mode + empty list, all stages are blocked.")
                    .defineListAllowEmpty(
                            "stageList",
                            List.of(1, 2),
                            o -> o instanceof Integer i && i >= 1 && i <= 5
                    );

            builder.pop();
        }

        public int getSearchRadiusForDimension(String dimensionId) {
            List<? extends String> overrides = dimensionRadiusOverrides.get();
            for (String entry : overrides) {
                int idx = entry.lastIndexOf(':');
                if (idx > 0) {
                    String dim = entry.substring(0, idx);
                    if (dim.equals(dimensionId)) {
                        try {
                            return Integer.parseInt(entry.substring(idx + 1));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
            return searchRadius.get();
        }

        public boolean isDimensionAllowed(String dimensionId) {
            List<? extends String> list = dimensionList.get();
            boolean useWhitelist = useDimensionWhitelist.get();
            boolean inList = list.contains(dimensionId);
            return useWhitelist == inList;
        }

        public boolean isStageAllowed(int stage) {
            List<? extends Integer> list = stageList.get();
            boolean inList = list.contains(stage);
            boolean useWhitelist = useStageWhitelist.get();
            return useWhitelist == inList;
        }
    }
}