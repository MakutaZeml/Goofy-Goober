package com.zeml.rotp_zthm.init;

import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.zeml.rotp_zthm.power.impl.stand.type.HamonStandType;

public class AddonStands {

    public static final EntityStandSupplier<HamonStandType<StandStats>, StandEntityType<GoofyGooberEntity>>
            GOOFY_GOOBER = new EntityStandSupplier<>(InitStands.GOOFY_GOOBER);

}