package com.game2.gui.controller;

import com.game2.game.misc.Direction;
import com.game2.game.misc.EntityType;
import com.game2.gui.graphics.DrawingTool;
import com.game2.gui.graphics.SkinItem;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by horacekm on 8.10.2017.
 */
public class PlaygroundController {

    @FXML VBox root;
    @FXML Canvas canvas;
    @FXML Canvas keyCanvas;
    @FXML Canvas drillCanvas;
    @FXML Canvas starCanvas;
    @FXML Label keyLabel;
    @FXML Label drillLabel;
    @FXML Label starLabel;
    @FXML Label movesLabel;

    private GraphicsContext gc;
    private DrawingTool drawingTool;
    private GuiController guiController;

    public void init(GuiController guiController) {

        this.guiController = guiController;

        gc = canvas.getGraphicsContext2D();
        guiController.getPlaygroundScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch(e.getCode()) {
                    case LEFT:
                        guiController.getGame().movePlayer(Direction.LEFT);
                        e.consume();
                        break;
                    case RIGHT:
                        guiController.getGame().movePlayer(Direction.RIGHT);
                        e.consume();
                        break;
                    case UP:
                        guiController.getGame().movePlayer(Direction.UP);
                        e.consume();
                        break;
                    case DOWN:
                        guiController.getGame().movePlayer(Direction.DOWN);
                        e.consume();
                        break;
                    case ALT:
                        guiController.getGame().movePlayer(Direction.STAY);
                        e.consume();
                        break;
                    case TAB:
                        guiController.getGame().switchPlayer();
                        e.consume();
                        break;
                    case BACK_SPACE:
                        guiController.getGame().undoMove();
                        e.consume();
                        break;
                    case ESCAPE:
                        guiController.setWindow(GuiWindow.MENU);
                        e.consume();
                        break;
                    default:
                        break;
                }
                draw();
            }
        });
    }

    public void prepareController() {
        canvas.setWidth(GuiController.width*GuiController.squareSize);
        canvas.setHeight(GuiController.height*GuiController.squareSize);
        drawingTool = new DrawingTool(gc, guiController.getGame(), GuiController.squareSize);
        initStateView();
    }

    public void showMenu() {
        guiController.setWindow(GuiWindow.MENU);
    }

    public void reset() {
        guiController.getNewGameController().startGame();
    }

    public void draw() {
        keyLabel.setText(String.valueOf(guiController.getGame().getGameViewer().getKeysTaken()));
        drillLabel.setText(String.valueOf(guiController.getGame().getGameViewer().getDrillsTaken()));
        starLabel.setText(String.valueOf(guiController.getGame().getGameViewer().getStarsTaken()) + " / "
                + String.valueOf(guiController.getGame().getGameViewer().getStarsCount()));
        movesLabel.setText("Moves: " + String.valueOf(guiController.getGame().getGameViewer().getTime()));
        drawingTool.drawSimpleBackground(Color.DARKBLUE);
        drawingTool.drawImageEntities();
    }

    private void initStateView() {

        // init KEY
        keyCanvas.setWidth(GuiController.squareSize);
        keyCanvas.setHeight(GuiController.squareSize);
        for(SkinItem skinItem : drawingTool.getSkinStack().getStack()) {
            if(skinItem.getEntityType() == EntityType.KEY) {
                keyCanvas.getGraphicsContext2D().drawImage(skinItem.getImage(), 0, 0, GuiController.squareSize, GuiController.squareSize);
                break;
            }
        }

        // init DRILL
        drillCanvas.setWidth(GuiController.squareSize);
        drillCanvas.setHeight(GuiController.squareSize);
        for(SkinItem skinItem : drawingTool.getSkinStack().getStack()) {
            if(skinItem.getEntityType() == EntityType.DRILL) {
                drillCanvas.getGraphicsContext2D().drawImage(skinItem.getImage(), 0, 0, GuiController.squareSize, GuiController.squareSize);
                break;
            }
        }

        // init STAR
        starCanvas.setWidth(GuiController.squareSize);
        starCanvas.setHeight(GuiController.squareSize);
        for(SkinItem skinItem : drawingTool.getSkinStack().getStack()) {
            if(skinItem.getEntityType() == EntityType.STAR) {
                starCanvas.getGraphicsContext2D().drawImage(skinItem.getImage(), 0, 0, GuiController.squareSize, GuiController.squareSize);
                break;
            }
        }

    }
}
