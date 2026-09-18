package iafeyes.eyeofdragonsreborn;

import com.menoxd.poisondragons.entity.PoisonDragonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ItemPoisonDragonEye extends ItemEyeBase {
    public ItemPoisonDragonEye(Properties properties) {
        super(properties);
    }

    @Override
    protected EyeOfDragonsRebornConfig.EyeConfig getEyeConfig() {
        return EyeOfDragonsRebornConfig.POISON_DRAGON_EYE;
    }

    @Override
    protected boolean isDragonEntity(Entity entity) {
        return entity instanceof PoisonDragonEntity;
    }

    @Override
    protected boolean isDragonDead(Entity entity) {
        return entity instanceof PoisonDragonEntity dragon && dragon.isModelDead();
    }

    @Override
    protected boolean isDragonTamed(Entity entity) {
        return entity instanceof PoisonDragonEntity dragon && dragon.isTame();
    }

    @Override
    protected int getDragonStage(Entity entity) {
        return entity instanceof PoisonDragonEntity dragon ? dragon.getDragonStage() : 0;
    }

    @Override
    protected List<Entity> getNearbyEntities(Level level, Player player) {
        ResourceLocation dimId = level.dimension().location();
        int radius = getEyeConfig().getSearchRadiusForDimension(dimId.toString());
        AABB bb = new AABB(player.blockPosition()).inflate(radius);
        return level.getEntitiesOfClass(PoisonDragonEntity.class, bb).stream().map(e -> (Entity) e).toList();
    }
}