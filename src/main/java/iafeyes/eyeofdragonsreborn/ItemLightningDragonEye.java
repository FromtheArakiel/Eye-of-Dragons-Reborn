package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.EntityLightningDragon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemLightningDragonEye extends ItemEyeBase {
    public ItemLightningDragonEye(Properties properties) {
        super(properties);
    }

    @Override
    protected List<Entity> getNearbyEntities(Level level, Player player) {
        ResourceLocation dimId = level.dimension().location();
        int radius = EyeOfDragonsRebornConfig.getSearchRadiusForDimension(dimId.toString());
        AABB bb = new AABB(player.blockPosition()).inflate(radius);
        return level.getEntitiesOfClass(EntityLightningDragon.class, bb).stream().map(e -> (Entity) e).toList();
    }
}