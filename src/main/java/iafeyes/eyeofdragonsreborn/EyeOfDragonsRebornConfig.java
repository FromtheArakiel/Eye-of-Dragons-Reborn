package iafeyes.eyeofdragonsreborn;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class EyeOfDragonsRebornConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue SEARCH_RADIUS;
    public static final ForgeConfigSpec.BooleanValue USE_DIMENSION_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DIMENSION_LIST;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Eye of Dragons Reborn settings").push("general");

        SEARCH_RADIUS = builder
                .comment("The search radius (in blocks) when using a dragon eye.",
                        "Default: 1000")
                .defineInRange("searchRadius", 1000, 1, 10000);

        builder.pop();

        builder.comment("Dimension restrictions for dragon eye usage").push("dimensions");

        USE_DIMENSION_WHITELIST = builder
                .comment("If true, the dimension list is a whitelist (only listed dimensions allow searching).",
                        "If false, the dimension list is a blacklist (listed dimensions are blocked).",
                        "Default: false (blacklist mode)")
                .define("useWhitelist", false);

        DIMENSION_LIST = builder
                .comment("List of dimension IDs, e.g. \"minecraft:overworld\", \"minecraft:the_nether\", \"minecraft:the_end\".",
                        "Behavior depends on useWhitelist.",
                        "Default: [\"minecraft:overworld\"]")
                .defineListAllowEmpty(
                        "dimensionList",
                        List.of("minecraft:overworld"),
                        o -> o instanceof String s && !s.isEmpty()
                );

        builder.pop();

        SPEC = builder.build();
    }
}