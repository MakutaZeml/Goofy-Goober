package com.zeml.rotp_zthm.entity.stands;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.zeml.rotp_zthm.init.InitParticles;
import net.minecraft.world.World;

public class HouseOfEarthEntity extends StandEntity {

    public HouseOfEarthEntity(StandEntityType<HouseOfEarthEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isPickable(){ return false;}

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            if(ClientUtil.canSeeStands()){
                for (int i=0;i<3;i++){
                    float x = (float) (this.getX() + (this.getBoundingBox().getXsize()/2-Math.random()*this.getBoundingBox().getXsize()));
                    float y = (float) (this.getY()+Math.random()*this.getBoundingBox().getYsize());
                    float z = (float) (this.getZ() + (this.getBoundingBox().getXsize()/2-Math.random()*this.getBoundingBox().getZsize()));
                    level.addParticle(InitParticles.SOLAR.get(),x,y,z,0,0,0 );
                }
            }

        }
    }
}
