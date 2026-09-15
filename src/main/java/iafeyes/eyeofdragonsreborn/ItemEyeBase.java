package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.EntityDragonBase;
import com.iafenvoy.iceandfire.registry.IafSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class ItemEyeBase extends Item {
    public ItemEyeBase(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.startUsingItem(hand);

        if (!level.isClientSide() && level.dimension() == Level.OVERWORLD) {
            findDragonAndShoot(level, player, itemstack);
        }

        return InteractionResultHolder.success(itemstack);
    }

    protected abstract List<Entity> getNearbyEntities(Level level, Player player);

    private void findDragonAndShoot(Level level, Player player, ItemStack itemstack) {
        List<Entity> entities = getNearbyEntities(level, player);

        entities = entities.stream()
                .filter(e -> {
                    if (e instanceof EntityDragonBase dragon) {
                        return !dragon.isMobDead() && !dragon.isTame();
                    }
                    return true;
                })
                .toList();

        if (entities.isEmpty()) {
            player.displayClientMessage(Component.translatable("item.eyeofdragonsreborn.dragon_eye.nonfound"), true);
            return;
        }

        double nearestDistance = Integer.MAX_VALUE;
        Entity nearestEntity = null;
        for (Entity entity : entities) {
            double distance = entity.distanceTo(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEntity = entity;
            }
        }

        EyeOfEnder finderEntity = new EyeOfEnder(level, player.getX(), player.getEyeY(), player.getZ());
        finderEntity.setItem(itemstack);
        finderEntity.signalTo(nearestEntity.blockPosition());
        level.addFreshEntity(finderEntity);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                IafSounds.DRAGON_FLIGHT.get(), SoundSource.NEUTRAL,
                1F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        level.levelEvent(null, 1003, player.blockPosition(), 0);

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
    }
}