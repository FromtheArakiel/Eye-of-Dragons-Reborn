package iafeyes.eyeofdragonsreborn;

import net.minecraftforge.common.ForgeConfigSpec;

public class EyeOfDragonsRebornConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue SEARCH_RADIUS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Eye of Dragons Reborn settings").push("general");

        SEARCH_RADIUS = builder
                .comment("The search radius (in blocks) when using a dragon eye.",
                        "Default: 1000")
                .defineInRange("searchRadius", 1000, 1, 10000);

        builder.pop();

        SPEC = builder.build();
    }
}