package iafeyes.eyeofdragonsreborn;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EyeOfDragonsRebornMod.MODID);

    public static final RegistryObject<Item> EYE_OF_FIREDRAGON = ITEMS.register("eye_of_firedragon", () ->
            new ItemFireDragonEye(
                    new Item.Properties()
                            .stacksTo(16)));

    public static final RegistryObject<Item> EYE_OF_ICEDRAGON = ITEMS.register("eye_of_icedragon", () ->
            new ItemIceDragonEye(
                    new Item.Properties()
                            .stacksTo(16)));

    public static final RegistryObject<Item> EYE_OF_LIGHTNINGDRAGON = ITEMS.register("eye_of_lightningdragon", () ->
            new ItemLightningDragonEye(
                    new Item.Properties()
                            .stacksTo(16)));
}