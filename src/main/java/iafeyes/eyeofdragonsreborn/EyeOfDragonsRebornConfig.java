package iafeyes.eyeofdragonsreborn;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class EyeOfDragonsRebornConfig {
    public static int SEARCH_RADIUS = 1000;
    public static boolean USE_DIMENSION_WHITELIST = false;
    public static String[] DIMENSION_LIST = new String[]{};

    public static void load(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        SEARCH_RADIUS = config.getInt(
                "searchRadius", "general", 1000, 1, 10000,
                "The search radius (in blocks) when using a dragon eye.");

        USE_DIMENSION_WHITELIST = config.getBoolean(
                "useWhitelist", "dimensions", false,
                "If true, dimensionList is a whitelist. If false, it's a blacklist.");

        DIMENSION_LIST = config.getStringList(
                "dimensionList", "dimensions", new String[]{},
                "List of dimension IDs.");

        if (config.hasChanged()) {
            config.save();
        }
    }
}