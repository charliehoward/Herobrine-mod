// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.professorshroom.herobrine.entity.client;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.professorshroom.herobrine.entity.custom.HerobrineEntity;

public class HerobrineModel<T extends HerobrineEntity> extends BipedEntityModel<T> {
    public HerobrineModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData rootData = BipedEntityModel.getModelData(new Dilation(0), 0.0f);

        return TexturedModelData.of(rootData, 64, 64);
    }
}