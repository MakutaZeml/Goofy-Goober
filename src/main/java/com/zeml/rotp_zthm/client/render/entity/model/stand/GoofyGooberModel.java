package com.zeml.rotp_zthm.client.render.entity.model.stand;

import com.zeml.rotp_zthm.entity.stands.GoofyGooberEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;

public class GoofyGooberModel extends HumanoidStandModel<GoofyGooberEntity> {


	public GoofyGooberModel() {
		super();

		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;

		leftArm.texOffs(71, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, false);
		leftForeArm.texOffs(71, 10).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, false);

		rightArm.texOffs(39, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, false);
		rightForeArm.texOffs(39, 10).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, false);

	}


	@Override
	protected ModelPose initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(leftArm,-14.57f, 54.31f, -1.94f),
				RotationAngle.fromDegrees(rightArm, -177.48f, -75.03f, 140.03f)

		});
	}


}