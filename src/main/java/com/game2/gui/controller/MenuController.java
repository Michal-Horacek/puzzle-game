package com.game2.gui.controller;

import com.game2.gui.graphics.DrawingTool;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by horacekm on 8.10.2017.
 */
public class MenuController {

    @FXML
    VBox root;

    @FXML
    private Button resumeButton;

    private GuiController guiController;

    public void init(GuiController guiController) {
        this.guiController = guiController;
    }

    public void startNewGame() {

        guiController.getSoundPlayer().playSound(SoundType.BUTTON);
        guiController.setWindow(GuiWindow.NEWGAME);
    }

    public void quit() {
        guiController.getSoundPlayer().playSound(SoundType.BUTTON);
        Platform.exit();
        System.exit(0);
    }

    public void showSettings() {
//        guiController.getSoundPlayer().playSound(SoundType.BUTTON);
//        guiController.setWindow(GuiWindow.SETTINGS);
    }

    public void showEditor() {
        guiController.getSoundPlayer().playSound(SoundType.BUTTON);
        guiController.setWindow(GuiWindow.EDITOR);
    }

    public void resume() {
        guiController.getSoundPlayer().playSound(SoundType.BUTTON);
        guiController.setWindow(GuiWindow.PLAYGROUND);
    }

    public boolean isResumeButtonVisible() {
        return resumeButton.isVisible();
    }

    public void setResumeButtonVisible(boolean resumeButtonVisible) {
        resumeButton.setVisible(resumeButtonVisible);
    }
}
