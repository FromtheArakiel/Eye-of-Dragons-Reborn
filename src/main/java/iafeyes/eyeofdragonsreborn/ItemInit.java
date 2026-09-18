package iafeyes.eyeofdragonsreborn;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static final Item EYE_OF_FIREDRAGON = new ItemFireDragonEye(
            new Item.Settings().maxCount(16));
    public static final Item EYE_OF_ICEDRAGON = new ItemIceDragonEye(
            new Item.Settings().maxCount(16));
    public static final Item EYE_OF_LIGHTNINGDRAGON = new ItemLightningDragonEye(
            new Item.Settings().maxCount(16));

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            Identifier.of(EyeOfDragonsRebornMod.MODID, "eyeofdragonsreborn_tab"));

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(EYE_OF_FIREDRAGON))
            .displayName(Text.translatable("itemGroup.eyeofdragonsreborn"))
            .build();

    public static void registerItems() {
        registerItem(EYE_OF_FIREDRAGON, "eye_of_firedragon");
        registerItem(EYE_OF_ICEDRAGON, "eye_of_icedragon");
        registerItem(EYE_OF_LIGHTNINGDRAGON, "eye_of_lightningdragon");
    }

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY, ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(entries -> {
            entries.add(EYE_OF_FIREDRAGON);
            entries.add(EYE_OF_ICEDRAGON);
            entries.add(EYE_OF_LIGHTNINGDRAGON);
        });
    }

    private static void registerItem(Item item, String name) {
        Registry.register(Registries.ITEM,
                Identifier.of(EyeOfDragonsRebornMod.MODID, name), item);
    }
}