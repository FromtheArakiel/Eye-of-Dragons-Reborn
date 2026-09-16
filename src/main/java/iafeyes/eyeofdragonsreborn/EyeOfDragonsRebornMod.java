package iafeyes.eyeofdragonsreborn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = EyeOfDragonsRebornMod.MODID,
        name = EyeOfDragonsRebornMod.NAME,
        version = EyeOfDragonsRebornMod.VERSION,
        dependencies = "required-after:iceandfire;required-after:llibrary"
)
public class EyeOfDragonsRebornMod {
    public static final String MODID = "eyeofdragonsreborn";
    public static final String NAME = "Eye of Dragons Reborn";
    public static final String VERSION = "1.2.0";

    public static Logger logger;

    public static final CreativeTabs TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.EYE_OF_FIREDRAGON);
        }
    };

    @SidedProxy(
            clientSide = "iafeyes.eyeofdragonsreborn.ClientProxy",
            serverSide = "iafeyes.eyeofdragonsreborn.CommonProxy"
    )
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        EyeOfDragonsRebornConfig.load(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.render();
    }
}