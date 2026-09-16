package iafeyes.eyeofdragonsreborn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityEyeBase extends Entity {
    private static final double MAX_DISTANCE = 20D;
    private static final int LIFETIME_TICKS = 80;

    private double targetX, targetY, targetZ;
    private int ticksLived;
    private final boolean shatter;

    public EntityEyeBase(World world) {
        super(world);
        setSize(0.25F, 0.25F);
        this.shatter = rand.nextInt(100) > 59;
    }

    public EntityEyeBase(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }
        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    public void moveTowards(double x, double y, double z) {
        double deltaX = x - posX;
        double deltaZ = z - posZ;

        double distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        if (distance > MAX_DISTANCE) {
            targetX = posX + deltaX / distance * MAX_DISTANCE;
            targetZ = posZ + deltaZ / distance * MAX_DISTANCE;
            targetY = posY + 8.0D;
        } else {
            targetX = x;
            targetY = y;
            targetZ = z;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void setVelocity(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float) (Math.atan2(y,
                    Math.sqrt(x * x + z * z)) * 180.0D / Math.PI);
        }
    }

    @Override
    public void onUpdate() {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();

        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        float distanceMoved = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

        World world = getEntityWorld();

        rotationPitch = (float) (Math.atan2(motionY, distanceMoved) * 180.0D / Math.PI);
        while (rotationPitch - prevRotationPitch < -180.0F) prevRotationPitch -= 360.0F;
        while (rotationPitch - prevRotationPitch >= 180.0F) prevRotationPitch += 360.0F;
        while (rotationYaw - prevRotationYaw < -180.0F) prevRotationYaw -= 360.0F;
        while (rotationYaw - prevRotationYaw >= 180.0F) prevRotationYaw += 360.0F;

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

        if (!world.isRemote) {
            double dx = targetX - posX;
            double dz = targetZ - posZ;
            float distAway = (float) Math.sqrt(dx * dx + dz * dz);
            float f2 = (float) Math.atan2(dz, dx);
            double d2 = (double) distanceMoved + (double) (distAway - distanceMoved) * 0.0025D;

            if (distAway < 1.0F) {
                d2 *= 0.8D;
                motionY *= 0.8D;
            }

            motionX = Math.cos(f2) * d2;
            motionZ = Math.sin(f2) * d2;

            if (posY < targetY) {
                motionY += (1.0D - motionY) * 0.014999999664723873D;
            } else {
                motionY += (-1.0D - motionY) * 0.014999999664723873D;
            }
        }

        float f3 = 0.25F;
        if (isInWater()) {
            for (int i = 0; i < 4; ++i) {
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                        posX - motionX * (double) f3,
                        posY - motionY * (double) f3,
                        posZ - motionZ * (double) f3,
                        motionX, motionY, motionZ);
            }
        } else {
            world.spawnParticle(EnumParticleTypes.PORTAL,
                    posX - motionX * (double) f3 + rand.nextDouble() * 0.6D - 0.3D,
                    posY - motionY * (double) f3 - 0.5D,
                    posZ - motionZ * (double) f3 + rand.nextDouble() * 0.6D - 0.3D,
                    motionX, motionY, motionZ);
        }

        if (!world.isRemote) {
            setPosition(posX, posY, posZ);
            ++ticksLived;
            if (ticksLived > LIFETIME_TICKS) {
                setDead();
                if (shatter) {
                    world.spawnEntity(new EntityItem(world, posX, posY, posZ,
                            new ItemStack(getDropItem(), 1)));
                } else {
                    world.playEvent(2003, new BlockPos(this), 0);
                }
            }
        }
    }

    protected abstract Item getDropItem();

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
    }
}