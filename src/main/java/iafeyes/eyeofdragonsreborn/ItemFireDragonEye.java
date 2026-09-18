package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.FireDragonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemFireDragonEye extends ItemEyeBase {
    public ItemFireDragonEye(Properties properties) {
        super(properties);
    }

    @Override
    protected EyeOfDragonsRebornConfig.EyeConfig getEyeConfig() {
        return EyeOfDragonsRebornConfig.FIRE_DRAGON_EYE;
    }

    @Override
    protected List<Entity> getNearbyEntities(Level level, Player player) {
        int radius = getEyeConfig().getSearchRadiusForDimension(level.dimension().location().toString());
        AABB bb = new AABB(player.blockPosition()).inflate(radius);
        return level.getEntitiesOfClass(FireDragonEntity.class, bb).stream().map(e -> (Entity) e).toList();
    }
}