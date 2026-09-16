package iafeyes.eyeofdragonsreborn;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = EyeOfDragonsRebornMod.MODID)
public class CommonProxy {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ItemInit.EYE_OF_FIREDRAGON);
        event.getRegistry().register(ItemInit.EYE_OF_ICEDRAGON);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        registerUnspawnable(event, EntityFireDragonEye.class, "eye_of_firedragon", 47);
        registerUnspawnable(event, EntityIceDragonEye.class, "eye_of_icedragon", 48);
    }

    private static void registerUnspawnable(RegistryEvent.Register<EntityEntry> event,
                                            Class<? extends Entity> entityClass,
                                            String name, int id) {
        event.getRegistry().register(
                EntityEntryBuilder.<Entity>create()
                        .entity(entityClass)
                        .id(new ResourceLocation(EyeOfDragonsRebornMod.MODID, name), id + 1500)
                        .name(name)
                        .tracker(64, 1, true)
                        .build()
        );
    }

    public void render() {}
}