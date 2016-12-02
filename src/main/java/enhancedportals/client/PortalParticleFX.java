package enhancedportals.client;

import enhancedportals.network.ClientProxy;
import enhancedportals.network.ClientProxy.ParticleSet;
import enhancedportals.tile.TileController;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.awt.*;

public class PortalParticleFX extends Particle
{
    private float portalParticleScale;
    private double portalPosX, portalPosY, portalPosZ;

    private ParticleSet particle;
    private int nextFrame = 1;

    public PortalParticleFX(World par1World, TileController controller, double portalX, double portalY, double portalZ, double xMotion, double yMotion, double zMotion)
    {
        super(par1World, portalX, portalY, portalZ, xMotion, yMotion, zMotion);
        motionX = xMotion;
        motionY = yMotion;
        motionZ = zMotion;
        portalPosX = posX = portalX;
        portalPosY = posY = portalY;
        portalPosZ = posZ = portalZ;

        portalParticleScale = particleScale = rand.nextFloat() * 0.2F + 0.5F;
        particleMaxAge = (int) (Math.random() * 10.0D) + 40;
        //noClip = true;

        if (controller == null || ClientProxy.particleSets.size() <= controller.activeTextureData.getParticleType())
        {
            setExpired();
        }
        else
        {
            Color c = new Color(controller.activeTextureData.getParticleColour());
            particleRed = c.getRed() / 255f;
            particleGreen = c.getGreen() / 255f;
            particleBlue = c.getBlue() / 255f;

            particle = ClientProxy.particleSets.get(controller.activeTextureData.getParticleType());
            setParticleTextureIndex(particle.type == 0 ? particle.frames[ClientProxy.random.nextInt(particle.frames.length)] : particle.frames[0]);
        }
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float var2 = super.getBrightnessForRender(par1);
        float var3 = (float) particleAge / (float) particleMaxAge;
        var3 = var3 * var3 * var3 * var3;
        return var2 * (1.0F - var3) + var3;
    }

    @Override
    public int getBrightnessForRender(float par1)
    {
        int var2 = super.getBrightnessForRender(par1);
        float var3 = (float) particleAge / (float) particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        int var4 = var2 & 255;
        int var5 = var2 >> 16 & 255;
        var5 += (int) (var3 * 15.0F * 16.0F);

        if (var5 > 240)
        {
            var5 = 240;
        }

        return var4 | var5 << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (particle == null) // Kill it off as soon as possible
        {
            if (!isExpired)
            {
                setExpired();
            }

            return;
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        float var1 = (float) particleAge / (float) particleMaxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * 2.0F;
        var1 = 1.0F - var1;
        posX = portalPosX + motionX * var1;
        posY = portalPosY + motionY * var1 + (1.0F - var2);
        posZ = portalPosZ + motionZ * var1;

        if (particle.type > 0 && particleAge % 5 == 1)
        {
            if (nextFrame >= particle.frames.length)
            {
                if (particle.type == 2)
                {
                    nextFrame = 0;
                }
                else
                {
                    setExpired();
                    return;
                }
            }

            setParticleTextureIndex(particle.frames[nextFrame]);
            nextFrame++;
        }

        if (particleAge++ >= particleMaxAge)
        {
            setExpired();
        }
    }

    @Override
    public void renderParticle(VertexBuffer worldRender, Entity entity,float particleTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float var8 = (particleAge + particleTicks) / particleMaxAge;
        var8 = 1.0F - var8;
        var8 *= var8;
        var8 = 1.0F - var8;
        particleScale = portalParticleScale * var8;
        super.renderParticle(worldRender, entity, particleTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }
}
