package iafeyes.eyeofdragonsreborn;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class ItemFireDragonEye extends ItemEyeBase {

    public ItemFireDragonEye() {
        super("eye_of_firedragon");
    }

    @Override
    protected List<Entity> getNearbyEntities(World world, EntityPlayer player) {
        AxisAlignedBB bb = new AxisAlignedBB(player.getPosition()).grow(EyeOfDragonsRebornConfig.SEARCH_RADIUS);
        return world.getEntitiesWithinAABB(EntityFireDragon.class, bb).stream()
                .filter(d -> !d.isMobDead() && !d.isTamed())
                .map(e -> (Entity) e)
                .collect(Collectors.toList());
    }

    @Override
    protected EntityEyeBase createEntity(World world, EntityPlayer player, ItemStack itemstack) {
        return new EntityFireDragonEye(world,
                player.posX, player.posY + player.height, player.posZ);
    }
}