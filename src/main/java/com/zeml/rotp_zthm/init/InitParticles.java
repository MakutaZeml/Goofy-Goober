package com.zeml.rotp_zthm.init;

import com.zeml.rotp_zthm.ExtraHamonStandsAddon;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ExtraHamonStandsAddon.MOD_ID);

    public static final RegistryObject<BasicParticleType> SOLAR = PARTICLES.register("solar_dust", () -> new BasicParticleType(false));

    public static final RegistryObject<BasicParticleType> SOLAR_STATIC =  PARTICLES.register("solar", () -> new BasicParticleType(false));


}
