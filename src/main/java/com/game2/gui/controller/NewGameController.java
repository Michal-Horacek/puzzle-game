package com.game2.gui.controller;

import com.game2.game.core.Game;
import com.game2.gui.graphics.DrawingTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.*;


/**
 * Created by horacekm on 9.10.2017.
 */
public class NewGameController {

    @FXML
    private ListView levelList;

    private GuiController guiController;

    public void init(GuiController guiController) {
        this.guiController = guiController;
    }

    public void initialize() {

        try {
            ObservableList<String> items = FXCollections.observableArrayList();
            File levelDir = null;
            levelDir = new File(getClass().getClassLoader().getResource("levels").toURI());
            File[] fileList = levelDir.listFiles();
            for(File file : fileList) {
                items.add(file.getName());
            }
            levelList.setItems(items);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {

        if(levelList.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        System.out.println("Initializing Game ...");
        guiController.getSoundPlayer().playSound(SoundType.ACT);
        guiController.setGame(new Game());
        guiController.getGame().setSoundOn(false);        
        String fileName = "/levels/" + levelList.getSelectionModel().getSelectedItem();
        System.out.println(fileName);
        guiController.getGame().loadMapFromFile(fileName);
        guiController.getGame().start();

        System.out.println("Game is ready.");
        guiController.getPlaygroundController().prepareController();
        guiController.getPlaygroundController().draw();
        guiController.getMenuController().setResumeButtonVisible(true);
        guiController.setWindow(GuiWindow.PLAYGROUND);

    }

    public void onKeyPressed(KeyEvent e) {

        switch(e.getCode()) {
            case ESCAPE:
                guiController.setWindow(GuiWindow.MENU);
                break;
            case SPACE:
            case ENTER:
                if(levelList.getSelectionModel().getSelectedIndex() != -1) {
                    startGame();
                }
                break;
            default:
                break;
        }
    }

    public void onMenuBtn(ActionEvent actionEvent) {

        guiController.setWindow(GuiWindow.MENU);
    }
}
