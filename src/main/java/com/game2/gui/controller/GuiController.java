package com.game2.gui.controller;

import com.game2.game.core.Game;
import com.game2.gui.graphics.DrawingTool;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

/**
 * Created by horacekm on 8.10.2017.
 */
public class GuiController {

    private Stage primaryStage;
    private Scene menuScene;
    private Scene newGameScene;
    private Scene playgroundScene;
    private Scene editorScene;
    private Scene settingsScene;

//    private GuiWindow activeWindow;
    private PlaygroundController playgroundController;
    private MenuController menuController;
    private NewGameController newGameController;
    private EditorController editorController;
    private SettingsController settingsController;
    private SoundPlayer soundPlayer;
    private Game game;

    public static final int width = 31;
    public static final int height = 19;
    public static int squareSize = 40;

    public GuiController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {

        soundPlayer = new SoundPlayer();
        // soundPlayer.setSoundOn(true);

        primaryStage.setResizable(false);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            menuScene = new Scene(loader.load());
            menuController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/view/playground.fxml"));
            playgroundScene = new Scene(loader.load());
            playgroundController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/view/newGame.fxml"));
            newGameScene = new Scene(loader.load());
            newGameController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/view/editor.fxml"));
            editorScene = new Scene(loader.load());
            editorController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/view/settings.fxml"));
            settingsScene = new Scene(loader.load());
            settingsController = loader.getController();

            soundPlayer.loadSounds();
            soundPlayer.loadGuiSounds();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        squareSize = (int) (primScreenBounds.getWidth() / 1.8 / width);

        menuController.init(this);
        playgroundController.init(this);
        newGameController.init(this);
        settingsController.init(this);
        editorController.init(this);
        editorController.prepareController();

        setWindow(GuiWindow.MENU);
        primaryStage.show();


        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public void setWindow(GuiWindow window) {
        switch (window) {
            case MENU:
                primaryStage.setScene(menuScene);
                break;
            case PLAYGROUND:
                primaryStage.setScene(playgroundScene);
                break;
            case NEWGAME:
                primaryStage.setScene(newGameScene);
                break;
            case EDITOR:
                primaryStage.setScene(editorScene);
                break;
            case SETTINGS:
                primaryStage.setScene(settingsScene);
                break;
        }
//        activeWindow = window;
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlaygroundController getPlaygroundController() {
        return playgroundController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public NewGameController getNewGameController() {
        return newGameController;
    }

    public EditorController getEditorController() {
        return editorController;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public Scene getPlaygroundScene() {
        return playgroundScene;
    }
}
