package iafeyes.eyeofdragonsreborn;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EyeOfDragonsRebornMod implements ModInitializer {
    public static final String MODID = "eyeofdragonsreborn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        EyeOfDragonsRebornConfig.load();

        ItemInit.registerItems();
        ItemInit.registerItemGroup();

        LOGGER.info("Eye of Dragons Reborn initialized.");
    }
}