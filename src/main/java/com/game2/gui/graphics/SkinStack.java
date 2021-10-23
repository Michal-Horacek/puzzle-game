package com.game2.gui.graphics;

import com.game2.game.misc.EntityType;
import com.game2.gui.graphics.SkinItem;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horacekm on 9.10.2017.
 */
public class SkinStack {

    private List<SkinItem> stack;

    public SkinStack() {
        stack = new ArrayList<>();
    }

    public void add(SkinItem item) {
        stack.add(item);
    }

    public List<SkinItem> getStack() {
        return stack;
    }

    public Image getImage(EntityType entityType, int skin) {

        for (SkinItem skinItem: stack) {
            if(skinItem.getEntityType() == entityType && skinItem.getSkin() == skin) {
                return skinItem.getImage();
            }
        }

        return null;
    }
}
