package com.zeml.rotp_zthm.client.particle;

import com.github.standobyte.jojo.client.particle.custom.HamonAuraParticleRenderType;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class SolarStaticParticle extends SpriteTexturedParticle {


    public SolarStaticParticle(ClientWorld p_i232447_1_, double p_i232447_2_, double p_i232447_4_, double p_i232447_6_) {
        super(p_i232447_1_, p_i232447_2_, p_i232447_4_, p_i232447_6_);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return HamonAuraParticleRenderType.HAMON_AURA;
    }
    @Override
    protected int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @Override
    public void setSpriteFromAge(IAnimatedSprite pSprite) {
        setSprite(pSprite.get((age ) % lifetime, lifetime));
    }


    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }else {
            float red = .5F+.5F* MathHelper.sin((float) (Math.PI*2*this.age/this.lifetime));
            float green = .5F + .5F * MathHelper.cos((float) (Math.PI*2*this.age/this.lifetime));
            float blue = .5F - .5F * MathHelper.cos((float) (Math.PI*2*this.age/this.lifetime));
            this.setColor(red,green,blue);
            this.move(this.xd,this.yd,this.zd);
        }

    }


    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SolarStaticParticle particle = new SolarStaticParticle(world, x, y, z);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
