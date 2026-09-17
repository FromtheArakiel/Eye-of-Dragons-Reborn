package iafeyes.eyeofdragonsreborn;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public static RegistryObject<Item> EYE_OF_POISONDRAGON;

    static {
        if (ModList.get().isLoaded("poison_dragons")) {
            EYE_OF_POISONDRAGON = ITEMS.register("eye_of_poisondragon", () ->
                    new ItemPoisonDragonEye(
                            new Item.Properties()
                                    .stacksTo(16)));
        }
    }
}