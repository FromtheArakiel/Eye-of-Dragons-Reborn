package iafeyes.eyeofdragonsreborn;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.registry.IafSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemEyeBase extends Item {
    public ItemEyeBase(Settings settings) {
        super(settings);
    }

    protected abstract EyeOfDragonsRebornConfig.EyeConfig getEyeConfig();

    protected boolean isDragonEntity(Entity entity) {
        return entity instanceof DragonBaseEntity;
    }

    protected boolean isDragonDead(Entity entity) {
        return entity instanceof DragonBaseEntity dragon && dragon.isMobDead();
    }

    protected boolean isDragonTamed(Entity entity) {
        return entity instanceof DragonBaseEntity dragon && dragon.isTamed();
    }

    protected int getDragonStage(Entity entity) {
        return entity instanceof DragonBaseEntity dragon ? dragon.getDragonStage() : 0;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient()) {
            String dimId = world.getRegistryKey().getValue().toString();
            EyeOfDragonsRebornConfig.EyeConfig config = getEyeConfig();
            if (!config.isDimensionAllowed(dimId)) {
                player.sendMessage(
                        Text.translatable("item.eyeofdragonsreborn.dragon_eye.wrong_dimension"),
                        true);
                return TypedActionResult.success(stack);
            }
            findDragonAndShoot(world, player, stack);
        }

        return TypedActionResult.success(stack);
    }

    protected abstract List<Entity> getNearbyEntities(World world, PlayerEntity player);

    private void findDragonAndShoot(World world, PlayerEntity player, ItemStack stack) {
        List<Entity> entities = getNearbyEntities(world, player);

        EyeOfDragonsRebornConfig.EyeConfig config = getEyeConfig();
        boolean filterDead = config.filterDeadDragons;
        boolean filterTamed = config.filterTamedDragons;

        entities = entities.stream()
                .filter(e -> {
                    if (!isDragonEntity(e)) {
                        return true;
                    }
                    if (filterDead && isDragonDead(e)) {
                        return false;
                    }
                    if (filterTamed && isDragonTamed(e)) {
                        return false;
                    }
                    return config.isStageAllowed(getDragonStage(e));
                })
                .toList();

        if (entities.isEmpty()) {
            player.sendMessage(
                    Text.translatable("item.eyeofdragonsreborn.dragon_eye.nonfound"),
                    true);
            return;
        }

        double nearestDistance = Double.MAX_VALUE;
        Entity nearest = null;
        for (Entity entity : entities) {
            double distance = entity.distanceTo(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = entity;
            }
        }

        EyeOfEnderEntity finder = new EyeOfEnderEntity(world,
                player.getX(), player.getEyeY(), player.getZ());
        finder.setItem(stack);
        finder.initTargetPos(nearest.getBlockPos());
        world.spawnEntity(finder);

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                IafSounds.DRAGON_FLIGHT.get(), SoundCategory.NEUTRAL,
                1F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
        world.syncWorldEvent(1003, player.getBlockPos(), 0);

        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }
}