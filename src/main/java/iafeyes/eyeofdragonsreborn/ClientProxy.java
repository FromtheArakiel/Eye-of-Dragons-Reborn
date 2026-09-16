package iafeyes.eyeofdragonsreborn;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = EyeOfDragonsRebornMod.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(
                ItemInit.EYE_OF_FIREDRAGON, 0,
                new ModelResourceLocation(ItemInit.EYE_OF_FIREDRAGON.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(
                ItemInit.EYE_OF_ICEDRAGON, 0,
                new ModelResourceLocation(ItemInit.EYE_OF_ICEDRAGON.getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void render() {
        RenderingRegistry.registerEntityRenderingHandler(
                EntityFireDragonEye.class,
                new RenderSnowball<>(
                        Minecraft.getMinecraft().getRenderManager(),
                        ItemInit.EYE_OF_FIREDRAGON,
                        Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(
                EntityIceDragonEye.class,
                new RenderSnowball<>(
                        Minecraft.getMinecraft().getRenderManager(),
                        ItemInit.EYE_OF_ICEDRAGON,
                        Minecraft.getMinecraft().getRenderItem()));
    }
}