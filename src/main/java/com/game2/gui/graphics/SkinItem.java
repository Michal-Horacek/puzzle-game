package com.game2.gui.graphics;

import com.game2.game.misc.EntityType;
import javafx.scene.image.Image;

/**
 * Created by horacekm on 9.10.2017.
 */
public class SkinItem {

    private final EntityType entityType;
    private final int skin;
    private final Image image;

    public SkinItem(EntityType entityType, int skin, Image image) {
        this.entityType = entityType;
        this.skin = skin;
        this.image = image;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getSkin() {
        return skin;
    }

    public Image getImage() {
        return image;
    }
}
