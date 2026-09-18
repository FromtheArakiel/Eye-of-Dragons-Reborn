package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.LightningDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ItemLightningDragonEye extends ItemEyeBase {
    public ItemLightningDragonEye(Settings settings) {
        super(settings);
    }

    @Override
    protected EyeOfDragonsRebornConfig.EyeConfig getEyeConfig() {
        return EyeOfDragonsRebornConfig.LIGHTNING_DRAGON_EYE;
    }

    @Override
    protected List<Entity> getNearbyEntities(World world, PlayerEntity player) {
        String dimId = world.getRegistryKey().getValue().toString();
        int radius = getEyeConfig().getSearchRadiusForDimension(dimId);
        Box box = new Box(player.getBlockPos()).expand(radius);
        return world.getEntitiesByClass(LightningDragonEntity.class, box, e -> true)
                .stream().map(e -> (Entity) e).toList();
    }
}