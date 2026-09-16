package iafeyes.eyeofdragonsreborn;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityFireDragonEye extends EntityEyeBase {
    public EntityFireDragonEye(World world) {
        super(world);
    }

    public EntityFireDragonEye(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected Item getDropItem() {
        return ItemInit.EYE_OF_FIREDRAGON;
    }
}