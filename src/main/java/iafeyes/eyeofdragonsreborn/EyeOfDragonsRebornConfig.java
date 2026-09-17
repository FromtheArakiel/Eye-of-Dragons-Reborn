package iafeyes.eyeofdragonsreborn;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class EyeOfDragonsRebornConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue SEARCH_RADIUS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_RADIUS_OVERRIDES;

    public static final ForgeConfigSpec.BooleanValue USE_DIMENSION_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_LIST;

    public static final ForgeConfigSpec.BooleanValue FILTER_DEAD_DRAGONS;
    public static final ForgeConfigSpec.BooleanValue FILTER_TAMED_DRAGONS;

    public static final ForgeConfigSpec.BooleanValue USE_STAGE_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> STAGE_LIST;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Eye of Dragons Reborn general settings").push("general");

        SEARCH_RADIUS = builder
                .comment("Default search radius (in blocks) when using a dragon eye.",
                        "Default: 1000")
                .defineInRange("searchRadius", 1000, 1, 10000);

        builder.pop();

        builder.comment("Dimension restrictions and per-dimension radius overrides").push("dimensions");

        USE_DIMENSION_WHITELIST = builder
                .comment("If true, dimensionList is a whitelist (only listed dimensions allow searching).",
                        "If false, it's a blacklist (listed dimensions are blocked).",
                        "Default: false (blacklist mode)")
                .define("useWhitelist", false);

        DIMENSION_LIST = builder
                .comment("List of dimension IDs, e.g. \"minecraft:overworld\", \"minecraft:the_nether\", \"minecraft:the_end\".",
                        "Behavior depends on useWhitelist.",
                        "Default: [] (empty)",
                        "With blacklist mode + empty list, all dimensions are allowed.",
                        "With whitelist mode + empty list, all dimensions are blocked.")
                .defineListAllowEmpty(
                        "dimensionList",
                        List.of(),
                        o -> o instanceof String s && !s.isEmpty()
                );

        DIMENSION_RADIUS_OVERRIDES = builder
                .comment("Per-dimension search radius overrides.",
                        "Format: \"<dimension_id>:<radius>\"",
                        "Example: [\"minecraft:the_nether:500\", \"minecraft:the_end:200\"]",
                        "If a dimension is not listed here, the default searchRadius is used.",
                        "Default: []")
                .defineListAllowEmpty(
                        "dimensionRadiusOverrides",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );

        builder.pop();

        builder.comment("Dragon filter settings").push("dragon_filter");

        FILTER_DEAD_DRAGONS = builder
                .comment("If true, dead dragons are excluded from search results.",
                        "Default: true")
                .define("filterDeadDragons", true);

        FILTER_TAMED_DRAGONS = builder
                .comment("If true, tamed dragons are excluded from search results.",
                        "Default: true")
                .define("filterTamedDragons", true);

        builder.pop();

        builder.comment("Dragon stage filter (stages 1-5)").push("stage_filter");

        USE_STAGE_WHITELIST = builder
                .comment("If true, stageList is a whitelist (only listed stages are searched).",
                        "If false, stageList is a blacklist (listed stages are excluded).",
                        "Default: false (blacklist mode)")
                .define("useStageWhitelist", false);

        STAGE_LIST = builder
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

        SPEC = builder.build();
    }

    public static int getSearchRadiusForDimension(String dimensionId) {
        List<? extends String> overrides = DIMENSION_RADIUS_OVERRIDES.get();
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
        return SEARCH_RADIUS.get();
    }

    public static boolean isStageAllowed(int stage) {
        List<? extends Integer> list = STAGE_LIST.get();
        boolean inList = list.contains(stage);
        boolean useWhitelist = USE_STAGE_WHITELIST.get();
        return useWhitelist == inList;
    }
}