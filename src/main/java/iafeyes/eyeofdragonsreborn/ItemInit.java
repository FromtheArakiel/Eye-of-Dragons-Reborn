package iafeyes.eyeofdragonsreborn;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(EyeOfDragonsRebornMod.MODID);

    public static final DeferredItem<Item> EYE_OF_FIREDRAGON = ITEMS.register("eye_of_firedragon", () ->
            new ItemFireDragonEye(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> EYE_OF_ICEDRAGON = ITEMS.register("eye_of_icedragon", () ->
            new ItemIceDragonEye(new Item.Properties().stacksTo(16)));

    public static final DeferredItem<Item> EYE_OF_LIGHTNINGDRAGON = ITEMS.register("eye_of_lightningdragon", () ->
            new ItemLightningDragonEye(new Item.Properties().stacksTo(16)));
}