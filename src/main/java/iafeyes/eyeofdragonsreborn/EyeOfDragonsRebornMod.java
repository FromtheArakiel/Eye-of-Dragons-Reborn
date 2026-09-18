package iafeyes.eyeofdragonsreborn;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(EyeOfDragonsRebornMod.MODID)
public class EyeOfDragonsRebornMod {
    public static final String MODID = "eyeofdragonsreborn";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    static {
        CREATIVE_TABS.register("eyeofdragonsreborn_tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.eyeofdragonsreborn"))
                .icon(() -> ItemInit.EYE_OF_FIREDRAGON.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(ItemInit.EYE_OF_FIREDRAGON.get());
                    output.accept(ItemInit.EYE_OF_ICEDRAGON.get());
                    output.accept(ItemInit.EYE_OF_LIGHTNINGDRAGON.get());
                })
                .build());
    }

    public EyeOfDragonsRebornMod(IEventBus bus, ModContainer container) {
        ItemInit.ITEMS.register(bus);
        CREATIVE_TABS.register(bus);

        container.registerConfig(ModConfig.Type.COMMON, EyeOfDragonsRebornConfig.SPEC);
    }
}