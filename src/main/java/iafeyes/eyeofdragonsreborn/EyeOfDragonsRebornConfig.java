package iafeyes.eyeofdragonsreborn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EyeOfDragonsRebornConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("eyeofdragonsreborn")
            .resolve("eyeofdragonsreborn.json");

    public static final EyeConfig FIRE_DRAGON_EYE = new EyeConfig();
    public static final EyeConfig ICE_DRAGON_EYE = new EyeConfig();
    public static final EyeConfig LIGHTNING_DRAGON_EYE = new EyeConfig();

    public static void load() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
        } catch (IOException e) {
            EyeOfDragonsRebornMod.LOGGER.error("Failed to create config directory", e);
            return;
        }

        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                RootConfig root = GSON.fromJson(json, RootConfig.class);
                if (root != null) {
                    root.apply();
                }
            } catch (IOException e) {
                EyeOfDragonsRebornMod.LOGGER.error("Failed to load config", e);
            }
        }
        save();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            RootConfig root = new RootConfig();
            root.capture();
            Files.writeString(CONFIG_PATH, GSON.toJson(root));
        } catch (IOException e) {
            EyeOfDragonsRebornMod.LOGGER.error("Failed to save config", e);
        }
    }

    public static class EyeConfig {
        public int searchRadius = 1000;
        public List<String> dimensionRadiusOverrides = new ArrayList<>();
        public boolean useDimensionWhitelist = false;
        public List<String> dimensionList = new ArrayList<>();
        public boolean filterDeadDragons = true;
        public boolean filterTamedDragons = true;
        public boolean useStageWhitelist = false;
        public List<Integer> stageList = new ArrayList<>(List.of(1, 2));

        public int getSearchRadiusForDimension(String dimensionId) {
            for (String entry : dimensionRadiusOverrides) {
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
            return searchRadius;
        }

        public boolean isDimensionAllowed(String dimensionId) {
            boolean inList = dimensionList.contains(dimensionId);
            return useDimensionWhitelist == inList;
        }

        public boolean isStageAllowed(int stage) {
            boolean inList = stageList.contains(stage);
            return useStageWhitelist == inList;
        }
    }

    public static class RootConfig {
        public EyeConfig fireDragonEye;
        public EyeConfig iceDragonEye;
        public EyeConfig lightningDragonEye;

        public void capture() {
            fireDragonEye = FIRE_DRAGON_EYE;
            iceDragonEye = ICE_DRAGON_EYE;
            lightningDragonEye = LIGHTNING_DRAGON_EYE;
        }

        public void apply() {
            if (fireDragonEye != null) copy(fireDragonEye, FIRE_DRAGON_EYE);
            if (iceDragonEye != null) copy(iceDragonEye, ICE_DRAGON_EYE);
            if (lightningDragonEye != null) copy(lightningDragonEye, LIGHTNING_DRAGON_EYE);
        }

        private void copy(EyeConfig from, EyeConfig to) {
            to.searchRadius = from.searchRadius;
            to.dimensionRadiusOverrides = from.dimensionRadiusOverrides != null
                    ? from.dimensionRadiusOverrides : new ArrayList<>();
            to.useDimensionWhitelist = from.useDimensionWhitelist;
            to.dimensionList = from.dimensionList != null
                    ? from.dimensionList : new ArrayList<>();
            to.filterDeadDragons = from.filterDeadDragons;
            to.filterTamedDragons = from.filterTamedDragons;
            to.useStageWhitelist = from.useStageWhitelist;
            to.stageList = from.stageList != null
                    ? from.stageList : new ArrayList<>(List.of(1, 2));
        }
    }
}