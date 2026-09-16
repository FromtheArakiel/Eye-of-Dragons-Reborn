package iafeyes.eyeofdragonsreborn;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class ItemIceDragonEye extends ItemEyeBase {

    public ItemIceDragonEye() {
        super("eye_of_icedragon");
    }

    @Override
    protected List<Entity> getNearbyEntities(World world, EntityPlayer player) {
        AxisAlignedBB bb = new AxisAlignedBB(player.getPosition()).grow(EyeOfDragonsRebornConfig.SEARCH_RADIUS);
        return world.getEntitiesWithinAABB(EntityIceDragon.class, bb).stream()
                .filter(d -> !d.isMobDead() && !d.isTamed())
                .map(e -> (Entity) e)
                .collect(Collectors.toList());
    }

    @Override
    protected EntityEyeBase createEntity(World world, EntityPlayer player, ItemStack itemstack) {
        return new EntityIceDragonEye(world,
                player.posX, player.posY + player.height, player.posZ);
    }
}