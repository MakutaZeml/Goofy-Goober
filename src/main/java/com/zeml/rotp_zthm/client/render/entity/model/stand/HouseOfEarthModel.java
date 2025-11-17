package com.zeml.rotp_zthm.client.render.entity.model.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.*;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.stands.SilverChariotEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zthm.action.HOESolarStormAction;
import com.zeml.rotp_zthm.entity.stands.HouseOfEarthEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HouseOfEarthModel extends HumanoidStandModel<HouseOfEarthEntity> {
    private final ModelRenderer hair;
    private final ModelRenderer hair2;
    private final ModelRenderer torso_r1;
    private final ModelRenderer skirt;
    private final ModelRenderer heart3;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer heartCube7;
    private final ModelRenderer heartCube8;
    private final ModelRenderer heartCube9;
    private final ModelRenderer heart2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer heartCube2;
    private final ModelRenderer heartCube3;
    private final ModelRenderer heartCube4;
    private final ModelRenderer heart5;
    private final ModelRenderer heartCube11;
    private final ModelRenderer heartCube12;
    private final ModelRenderer heartCube13;
    private final ModelRenderer scarf;
    private final ModelRenderer scarf2;
    private final ModelRenderer scarf3;
    private final ModelRenderer scarf4;
    private final ModelRenderer scarf5;

    public HouseOfEarthModel(){
        super();
        texWidth = 128;
        texHeight = 128;


        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);

        hair = new ModelRenderer(this);
        hair.setPos(0.0F, 0.2F, 4.2F);
        head.addChild(hair);
        hair.texOffs(0, 16).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);

        hair2 = new ModelRenderer(this);
        hair2.setPos(0.0F, 4.0F, 0.0F);
        hair.addChild(hair2);
        hair2.texOffs(0, 20).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 0.0F, 0.0F);


        upperPart = new ModelRenderer(this);
        upperPart.setPos(0.0F, 12.0F, 0.0F);
        body.addChild(upperPart);


        torso = new ModelRenderer(this);
        torso.setPos(0.0F, -12.0F, 0.0F);
        upperPart.addChild(torso);
        torso.texOffs(0, 64).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        torso.texOffs(0, 0).addBox(-1.0F, 10.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        torso_r1 = new ModelRenderer(this);
        torso_r1.setPos(0.0F, 18.5F, 14.0F);
        torso.addChild(torso_r1);
        setRotationAngle(torso_r1, 0.7854F, 0.0F, 0.0F);
        torso_r1.texOffs(0, 56).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);

        skirt = new ModelRenderer(this);
        skirt.setPos(0.0F, 10.0F, 0.0F);
        torso.addChild(skirt);
        skirt.texOffs(45, 46).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.1F, false);

        heart3 = new ModelRenderer(this);
        heart3.setPos(0.0F, 11.75F, -2.3F);
        torso.addChild(heart3);
        heart3.texOffs(0, 80).addBox(-4.0F, -2.15F, 0.3F, 8.0F, 1.0F, 4.0F, 0.11F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(2.0F, -1.0F, 1.0F);
        heart3.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.3927F);
        cube_r1.texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-2.0F, -1.0F, 1.0F);
        heart3.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3927F);
        cube_r2.texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        heartCube7 = new ModelRenderer(this);
        heartCube7.setPos(0.0F, 0.05F, 0.0F);
        heart3.addChild(heartCube7);
        setRotationAngle(heartCube7, 0.0F, 0.0F, -0.7854F);
        heartCube7.texOffs(40, 77).addBox(0.0F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube8 = new ModelRenderer(this);
        heartCube8.setPos(0.5F, -0.45F, 0.0F);
        heart3.addChild(heartCube8);
        setRotationAngle(heartCube8, 0.0F, 0.0F, -0.7854F);
        heartCube8.texOffs(44, 77).addBox(-0.1F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube9 = new ModelRenderer(this);
        heartCube9.setPos(-0.5F, -0.45F, 0.0F);
        heart3.addChild(heartCube9);
        setRotationAngle(heartCube9, 0.0F, 0.0F, -0.7854F);
        heartCube9.texOffs(36, 77).addBox(0.0F, -0.95F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heart2 = new ModelRenderer(this);
        heart2.setPos(0.0F, 0.0F, 0.0F);
        heart3.addChild(heart2);
        heart2.texOffs(0, 80).addBox(-4.0F, -2.15F, 0.3F, 8.0F, 1.0F, 4.0F, 0.11F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(2.0F, -1.0F, 1.0F);
        heart2.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -0.3927F);
        cube_r3.texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(-2.0F, -1.0F, 1.0F);
        heart2.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, 0.3927F);
        cube_r4.texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        heartCube2 = new ModelRenderer(this);
        heartCube2.setPos(0.0F, 0.05F, 0.0F);
        heart2.addChild(heartCube2);
        setRotationAngle(heartCube2, 0.0F, 0.0F, -0.7854F);
        heartCube2.texOffs(40, 77).addBox(0.0F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube3 = new ModelRenderer(this);
        heartCube3.setPos(0.5F, -0.45F, 0.0F);
        heart2.addChild(heartCube3);
        setRotationAngle(heartCube3, 0.0F, 0.0F, -0.7854F);
        heartCube3.texOffs(44, 77).addBox(-0.1F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube4 = new ModelRenderer(this);
        heartCube4.setPos(-0.5F, -0.45F, 0.0F);
        heart2.addChild(heartCube4);
        setRotationAngle(heartCube4, 0.0F, 0.0F, -0.7854F);
        heartCube4.texOffs(36, 77).addBox(0.0F, -0.95F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heart5 = new ModelRenderer(this);
        heart5.setPos(0.0F, 1.75F, -3.3F);
        torso.addChild(heart5);
        setRotationAngle(heart5, -0.7854F, 0.0F, 0.0F);


        heartCube11 = new ModelRenderer(this);
        heartCube11.setPos(0.0F, 0.05F, 0.0F);
        heart5.addChild(heartCube11);
        setRotationAngle(heartCube11, 0.0F, 0.0F, -0.7854F);
        heartCube11.texOffs(40, 81).addBox(0.0F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube12 = new ModelRenderer(this);
        heartCube12.setPos(0.5F, -0.45F, 0.0F);
        heart5.addChild(heartCube12);
        setRotationAngle(heartCube12, 0.0F, 0.0F, -0.7854F);
        heartCube12.texOffs(44, 81).addBox(-0.1F, -1.05F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        heartCube13 = new ModelRenderer(this);
        heartCube13.setPos(-0.5F, -0.45F, 0.0F);
        heart5.addChild(heartCube13);
        setRotationAngle(heartCube13, 0.0F, 0.0F, -0.7854F);
        heartCube13.texOffs(36, 81).addBox(0.0F, -0.95F, -0.5F, 1.0F, 1.0F, 1.0F, -0.05F, false);

        scarf = new ModelRenderer(this);
        scarf.setPos(0.0F, 0.0F, 2.0F);
        torso.addChild(scarf);
        setRotationAngle(scarf, 0.1745F, 0.0F, 0.0F);
        scarf.texOffs(66, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);

        scarf2 = new ModelRenderer(this);
        scarf2.setPos(0.0F, 3.0F, 0.0F);
        scarf.addChild(scarf2);
        scarf2.texOffs(66, 3).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);

        scarf3 = new ModelRenderer(this);
        scarf3.setPos(0.0F, 3.0F, 0.0F);
        scarf2.addChild(scarf3);
        scarf3.texOffs(66, 6).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);

        scarf4 = new ModelRenderer(this);
        scarf4.setPos(0.0F, 3.0F, 0.0F);
        scarf3.addChild(scarf4);
        scarf4.texOffs(66, 9).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);

        scarf5 = new ModelRenderer(this);
        scarf5.setPos(0.0F, 3.0F, 0.0F);
        scarf4.addChild(scarf5);
        scarf5.texOffs(66, 12).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);


        leftArm = convertLimb(new ModelRenderer(this));
        leftArm.setPos(6.0F, -10.0F, 0.0F);
        upperPart.addChild(leftArm);
        leftArm.texOffs(48, 108).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 4.0F, 0.0F, false);
        leftArm.texOffs(44, 97).addBox(-2.0F, 0.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.1F, false);

        leftArmJoint = new ModelRenderer(this);
        leftArmJoint.setPos(0.0F, 4.0F, 0.0F);
        leftArm.addChild(leftArmJoint);
        leftArmJoint.texOffs(44, 102).addBox(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, -0.1F, true);

        leftForeArm = new ModelRenderer(this);
        leftForeArm.setPos(0.0F, 4.0F, 0.0F);
        leftArm.addChild(leftForeArm);
        leftForeArm.texOffs(48, 118).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, 0.0F, false);
        leftForeArm.texOffs(44, 97).addBox(-2.0F, 3.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.1F, false);

        rightArm = convertLimb(new ModelRenderer(this));
        rightArm.setPos(-6.0F, -10.0F, 0.0F);
        upperPart.addChild(rightArm);
        rightArm.texOffs(16, 108).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 6.0F, 4.0F, 0.0F, false);
        rightArm.texOffs(12, 97).addBox(-1.0F, 0.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.1F, false);

        rightArmJoint = new ModelRenderer(this);
        rightArmJoint.setPos(0.0F, 4.0F, 0.0F);
        rightArm.addChild(rightArmJoint);
        rightArmJoint.texOffs(12, 102).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, -0.1F, false);

        rightForeArm = new ModelRenderer(this);
        rightForeArm.setPos(0.0F, 4.0F, 0.0F);
        rightArm.addChild(rightForeArm);
        rightForeArm.texOffs(16, 118).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, 0.0F, false);
        rightForeArm.texOffs(12, 97).addBox(-1.0F, 3.5F, -2.0F, 3.0F, 1.0F, 4.0F, 0.1F, false);

        leftLeg = convertLimb(new ModelRenderer(this));
        leftLeg.setPos(1.9F, 12.0F, 0.0F);
        body.addChild(leftLeg);
        leftLeg.texOffs(96, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        leftLegJoint = new ModelRenderer(this);
        leftLegJoint.setPos(0.0F, 6.0F, 0.0F);
        leftLeg.addChild(leftLegJoint);
        leftLegJoint.texOffs(96, 102).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, true);

        leftLowerLeg = new ModelRenderer(this);
        leftLowerLeg.setPos(0.0F, 6.0F, 0.0F);
        leftLeg.addChild(leftLowerLeg);
        leftLowerLeg.texOffs(96, 118).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        rightLeg = convertLimb(new ModelRenderer(this));
        rightLeg.setPos(-1.9F, 12.0F, 0.0F);
        body.addChild(rightLeg);
        rightLeg.texOffs(64, 108).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

        rightLegJoint = new ModelRenderer(this);
        rightLegJoint.setPos(0.0F, 6.0F, 0.0F);
        rightLeg.addChild(rightLegJoint);
        rightLegJoint.texOffs(64, 102).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

        rightLowerLeg = new ModelRenderer(this);
        rightLowerLeg.setPos(0.0F, 6.0F, 0.0F);
        rightLeg.addChild(rightLowerLeg);
        rightLowerLeg.texOffs(64, 118).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(HouseOfEarthEntity entity,float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation){
        super.setupAnim(entity, walkAnimPos, walkAnimSpeed, ticks, yRotationOffset, xRotation);

    }


    @Override
    protected void initActionPoses(){
        super.initActionPoses();
        ModelPose<HouseOfEarthEntity> blow_1 = new ModelPose<>(new RotationAngle[]{
                RotationAngle.fromDegrees(body,20, 0, 0),
                RotationAngle.fromDegrees(leftArm,0, 0, -27.5),
                RotationAngle.fromDegrees(rightArm,0, 0, 27.5),
                RotationAngle.fromDegrees(leftLeg,-26.39218, -18.05776, 8.74472),
                RotationAngle.fromDegrees(leftLowerLeg,25, 0, 0),
                RotationAngle.fromDegrees(rightLeg,-34.13554, 16.76559, -11.06517),
                RotationAngle.fromDegrees(rightLowerLeg,25, 0, 0)
        });
        ModelPose<HouseOfEarthEntity> blow_2 = new ModelPose<>(new RotationAngle[]{
                RotationAngle.fromDegrees(head,-40, 0, 0),
                RotationAngle.fromDegrees(body, -7.5, 0, 0),
                RotationAngle.fromDegrees(leftArm, -7.5, 0, 0),
                RotationAngle.fromDegrees(leftForeArm,-122.5, 0, 0),
                RotationAngle.fromDegrees(rightArm, -35, 0, 0),
                RotationAngle.fromDegrees(rightForeArm,-135, 0, 0),
                RotationAngle.fromDegrees(leftLeg, 12.5, 0, 0),
                RotationAngle.fromDegrees(leftLowerLeg, 12.5, 0, 0),
                RotationAngle.fromDegrees(rightLeg, 7.5, 0, 12.5),
                RotationAngle.fromDegrees(rightLowerLeg, 17.5, 0, 0)
        });
        ModelPose<HouseOfEarthEntity> blow_3 = new ModelPose<>(new RotationAngle[]{
                RotationAngle.fromDegrees(head,10, 0, 0),
                RotationAngle.fromDegrees(hair,25, 0, 0),
                RotationAngle.fromDegrees(hair2,35, 0, 0),
                RotationAngle.fromDegrees(body, 2.5, 0, 0),
                RotationAngle.fromDegrees(scarf,128.60405, 26.94622, 52.9338),
                RotationAngle.fromDegrees(scarf2,-22.5, 0, 0),
                RotationAngle.fromDegrees(scarf3,-5, 0, 0),
                RotationAngle.fromDegrees(scarf4,-30, 0, 0),
                RotationAngle.fromDegrees(scarf5,-27.5, 0, 0),
                RotationAngle.fromDegrees(leftArm, 61.0146, 17.66046, -9.54013),
                RotationAngle.fromDegrees(leftForeArm,-42.5, 0, 0),
                RotationAngle.fromDegrees(rightArm, 48.56862, -29.91193, 23.75529),
                RotationAngle.fromDegrees(rightForeArm,-40, 0, 0),
                RotationAngle.fromDegrees(leftLeg, 12.5, 0, 0),
                RotationAngle.fromDegrees(leftLowerLeg, 12.5, 0, 0),
                RotationAngle.fromDegrees(rightLeg, 7.5, 0, 12.5),
                RotationAngle.fromDegrees(rightLowerLeg, 17.5, 0, 0)
        });


        actionAnim.put(HOESolarStormAction.BLOW, new PosedActionAnimation.Builder<HouseOfEarthEntity>()
                .addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(blow_1)
                        .addPose(.5F,blow_2)
                        .build(blow_3))
                .addPose(StandEntityAction.Phase.PERFORM, new ModelPoseTransitionMultiple.Builder<>(blow_3)
                        .build(blow_3))
                .addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(blow_3)
                        .build(idlePose))
                .build(idlePose));


    }

    @Override
    protected RotationAngle[][] initSummonPoseRotations() {
        return new RotationAngle[][]{
                new RotationAngle[]{
                        RotationAngle.fromDegrees(hair,10f, 0f, 0f),
                        RotationAngle.fromDegrees(hair2,7.5f, 0f, 0f),
                        RotationAngle.fromDegrees(body,0f, 0f, 7.5f),
                        RotationAngle.fromDegrees(scarf,27.5f, 0f, -70f),
                        RotationAngle.fromDegrees(scarf2,15f, 0f, 0f),
                        RotationAngle.fromDegrees(scarf3,-30f, 0f, 0f),
                        RotationAngle.fromDegrees(scarf4,-70f, 0f, 0f),
                        RotationAngle.fromDegrees(leftArm,-159.4838498304f, 1.5215995538, 37.4736533473),
                        RotationAngle.fromDegrees(leftForeArm,0f, 0f, 87.5f),
                        RotationAngle.fromDegrees(rightArm,0f, 0f, 22.5f),
                        RotationAngle.fromDegrees(rightForeArm,0f, 0f, -60f),
                        RotationAngle.fromDegrees(leftLeg,-18.62f, -4.43f, -9.65f),
                        RotationAngle.fromDegrees(leftLowerLeg,47.5f, 0f, 0f),
                        RotationAngle.fromDegrees(rightLeg,0f, 0f, 10f),
                        RotationAngle.fromDegrees(rightLowerLeg,0f, 0f, -10f),
                        RotationAngle.fromDegrees(scarf5,-25f, 0f, 0f)
                },
                new RotationAngle[]{
                        RotationAngle.fromDegrees(hair,55f, 0f, 0f),
                        RotationAngle.fromDegrees(hair2,40f, 0f, 0f),
                        RotationAngle.fromDegrees(body,-15f, 52.5f, 0f),
                        RotationAngle.fromDegrees(skirt,25f, 0f, 0f),
                        RotationAngle.fromDegrees(scarf,29.9f, -28.29f, -62.95f),
                        RotationAngle.fromDegrees(scarf2,-27.57f, 34.22f, -16.36f),
                        RotationAngle.fromDegrees(scarf3,-30f, 0f, 0f),
                        RotationAngle.fromDegrees(scarf4,-27.5f, 0f, 0f),
                        RotationAngle.fromDegrees(leftArm,0f, 0f, -95f),
                        RotationAngle.fromDegrees(leftForeArm,0f, 0f, -107.5f),
                        RotationAngle.fromDegrees(rightArm,0f, 0f, 120f),
                        RotationAngle.fromDegrees(rightForeArm,0f, 0f, 100f),
                        RotationAngle.fromDegrees(leftLeg,30f, 0f, 0f),
                        RotationAngle.fromDegrees(leftLowerLeg,17.5f, 0f, 0f),
                        RotationAngle.fromDegrees(rightLeg,-35f, 0f, 0f),
                        RotationAngle.fromDegrees(rightLowerLeg,62.5f, 0f, 0f),
                        RotationAngle.fromDegrees(scarf5,-42.5f, 0f, 0f)
                }
        };
    }


    @Override
    protected ModelPose<HouseOfEarthEntity> initIdlePose() {
        return new ModelPose<>(new RotationAngle[]{
                RotationAngle.fromDegrees(hair, 7.5f, 0f, 0f),
                RotationAngle.fromDegrees(hair2,-10f, 0f, 0f),
                RotationAngle.fromDegrees(scarf,0f, 0f, -67.5f),
                RotationAngle.fromDegrees(scarf2,10.15f, -9.85f, -1.75f),
                RotationAngle.fromDegrees(scarf3, -12.5f, 0f, 0f),
                RotationAngle.fromDegrees(scarf4,-15f, 0f, 0f),
                RotationAngle.fromDegrees(scarf5,-12.5f, 0f, 0f),
                RotationAngle.fromDegrees(leftArm, 9.92f, 1.3f, -7.39f),
                RotationAngle.fromDegrees(leftForeArm, -37.5f, 0f, 0f),
                RotationAngle.fromDegrees(rightArm, -23.66f, 8.31f, 30.76f),
                RotationAngle.fromDegrees(rightForeArm, 0f, 0f, -65f),
                RotationAngle.fromDegrees(leftLeg, -5f, 0f, -7.5f),
                RotationAngle.fromDegrees(leftLowerLeg, 12.5f, 0f, 0f),
                RotationAngle.fromDegrees(rightLeg, 0f, 0f, 10f)
        });
    }

    @Override
    protected ModelPose<HouseOfEarthEntity> initIdlePose2Loop() {
        return new ModelPose<>(new RotationAngle[] {
                RotationAngle.fromDegrees(scarf2,0.5F,0,0),
                RotationAngle.fromDegrees(scarf3, -.75F, 0,0),
                RotationAngle.fromDegrees(scarf5, 1.0,0,0),
                RotationAngle.fromDegrees(leftArm, 0F, 0F, -2F),
        });
    }

}
