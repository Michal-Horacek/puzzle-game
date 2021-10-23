package com.game2.gui.controller;

import javafx.scene.input.KeyEvent;

/**
 * Created by horacekm on 8.10.2017.
 */
public class SettingsController {

    private GuiController guiController;

    public void init(GuiController guiController) {
        this.guiController = guiController;
    }

    public void onKeyPressed(KeyEvent e) {

        switch(e.getCode()) {

            case ESCAPE:
                guiController.setWindow(GuiWindow.MENU);
            default: break;
        }
    }
}
