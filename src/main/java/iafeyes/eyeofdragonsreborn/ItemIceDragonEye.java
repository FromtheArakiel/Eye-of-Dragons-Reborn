package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.IceDragonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemIceDragonEye extends ItemEyeBase {
    public ItemIceDragonEye(Properties properties) {
        super(properties);
    }

    @Override
    protected EyeOfDragonsRebornConfig.EyeConfig getEyeConfig() {
        return EyeOfDragonsRebornConfig.ICE_DRAGON_EYE;
    }

    @Override
    protected List<Entity> getNearbyEntities(Level level, Player player) {
        int radius = getEyeConfig().getSearchRadiusForDimension(level.dimension().location().toString());
        AABB bb = new AABB(player.blockPosition()).inflate(radius);
        return level.getEntitiesOfClass(IceDragonEntity.class, bb).stream().map(e -> (Entity) e).toList();
    }
}