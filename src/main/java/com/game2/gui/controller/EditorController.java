package com.game2.gui.controller;

import com.game2.game.core.Game;
import com.game2.game.entity.*;
import com.game2.game.misc.*;
import com.game2.gui.graphics.DrawingTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;
import java.util.List;
import java.net.*;

/**
 * Created by horacekm on 8.10.2017.
 */
public class EditorController {

    @FXML
    Canvas canvas;
    @FXML
    Canvas entityPanel;
    @FXML
    Canvas skinPanel;

//    @FXML
//    public Label info;

    private GuiController guiController;
    private DrawingTool drawingTool;
    private GraphicsContext gc;
    private GraphicsContext entityPanelGC;
    private GraphicsContext skinPanelGC;
    private Game game;
    private EntityType currentEntityType;
    private int currentSkin;
    private int currentSkinPage;
    private GameEntity draggedEntity;

    public void init(GuiController guiController) {
        this.guiController = guiController;
    }

    public void initialize() {

        gc = canvas.getGraphicsContext2D();
        entityPanelGC = entityPanel.getGraphicsContext2D();
        skinPanelGC = skinPanel.getGraphicsContext2D();
    }

    public void prepareController() {
        canvas.setWidth(GuiController.width*GuiController.squareSize);
        canvas.setHeight(GuiController.height*GuiController.squareSize);
        game = new Game();
        game.createEmptyMap(31, 19);
        drawingTool = new DrawingTool(gc, game, GuiController.squareSize);

        buildEntityPanel();
        buildSkinPanel();

        draw();
    }

    public void draw() {
        drawingTool.drawGridBackground();
        drawingTool.drawImageEntities();
    }

    public void onKeyPressed(KeyEvent e) {

        switch(e.getCode()) {

            case ESCAPE:
                guiController.setWindow(GuiWindow.MENU);
            default: break;
        }
    }

    public void onMouseMoved(MouseEvent mouseEvent) {

        if(currentEntityType != null) {

            int posX = (int) mouseEvent.getX() / GuiController.squareSize;
            int posY = (int) mouseEvent.getY() / GuiController.squareSize;

            GameEntity entity = createEntity(currentEntityType, currentSkin);
            if (entity != null) {
                entity.getPosition2D().setXY(posX, posY);
                if (isNotOccupied(entity.getLayerType(), entity.getPosition2D())) {
                    draw();
                    drawingTool.drawEntity(entity);
                }
            }
        }
    }

    public void onMousePressed(MouseEvent mouseEvent) {

        int posX = (int) mouseEvent.getX() / GuiController.squareSize;
        int posY = (int) mouseEvent.getY() / GuiController.squareSize;

        if(currentEntityType != null && draggedEntity == null) {

            if (mouseEvent.isPrimaryButtonDown()) {
                GameEntity entity = createEntity(currentEntityType, currentSkin);
                if (entity != null) {
                    entity.getPosition2D().setXY(posX, posY);
                    if (isNotOccupied(entity.getLayerType(), entity.getPosition2D())) {
                        game.getGameViewer().getEntityStack().add(entity);
                    }
                }
            } else if (mouseEvent.isSecondaryButtonDown()) {

                currentEntityType = null;
                currentSkinPage = 0;
                currentSkin = 0;
                draw();
                buildSkinPanel();
                buildEntityPanel();
            }

        } else if(currentEntityType == null) {

            if(mouseEvent.isPrimaryButtonDown()) {

                if(draggedEntity == null) {
                    Iterator<GameEntity> it = game.getGameViewer().getEntityStack().getStack().iterator();
                    while (it.hasNext()) {
                        GameEntity entity = it.next();
                        if (entity.getPosition2D().getX() == posX && entity.getPosition2D().getY() == posY) {
                            draggedEntity = entity;
                            break;
                        }
                    }
                }

                else {
                    Point2D newLocation2D = new Point2D(posX, posY);
                    if(isNotOccupied(draggedEntity.getLayerType(), newLocation2D)) {
                        draggedEntity.setPosition2D(newLocation2D);
                        draw();
                    }
                }

            } else if(mouseEvent.isSecondaryButtonDown()) {

                Iterator<GameEntity> it = game.getGameViewer().getEntityStack().getStack().iterator();
                while (it.hasNext()) {
                    GameEntity entity = it.next();
                    if (entity.getPosition2D().getX() == posX && entity.getPosition2D().getY() == posY) {
                        it.remove();
                        break;
                    }
                }
            }
        }



        draw();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {

        if(mouseEvent.isPrimaryButtonDown()) {
            onMousePressed(mouseEvent);
        }
    }

    GameEntity createEntity(EntityType entityType, int skin) {

        GameEntity entity = null;

        switch(entityType) {

            case BOMB:
                entity = new E_Bomb(new Point2D(0, 0), skin);
                entity.setSkin(skin);
                break;
            case DECORATION:
                entity = new E_Decoration(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case DRILL:
                entity = new E_Drill(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case DYNAMITE:
                entity = new E_Dynamite(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case FINISH:
                entity = new E_Finish(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case FLOOR:
                entity = new E_Floor(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case KEY:
                entity = new E_Key(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case LASER:
                entity = new E_Laser(new Point2D(0, 0), Direction.RIGHT);
                entity.setSkin(skin);
                break;
            case LOCK:
                entity = new E_Lock(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case PLAYER:
                entity = new E_Player(new Point2D(0 ,0));
                entity.setSkin(skin);
                break;
            case STAR:
                entity = new E_Star(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case STONE:
                entity = new E_Stone(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case TELEPORT:
                entity = new E_Teleport(new Point2D(0, 0), skin);
                entity.setSkin(skin);
                break;
            case TRIGGER:
                entity = new E_Trigger(new Point2D(0, 0), skin);
                entity.setSkin(skin);
                break;
            case TRIGGERWALL:
                entity = new E_TriggerWall(new Point2D(0, 0), skin);
                entity.setSkin(skin);
                break;
            case WALL:
                entity = new E_Wall(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
            case WOOD:
                entity = new E_Wood(new Point2D(0, 0));
                entity.setSkin(skin);
                break;
        }

        return entity;
    }

    private boolean isNotOccupied(LayerType layerType, Point2D location2D) {

        List<GameEntity> entityList = game.getGameViewer().getEntityStack().getStack();
        long count = entityList
                .stream()
                .filter(p -> p.getPosition2D().getX() == location2D.getX())
                .filter(p -> p.getPosition2D().getY() == location2D.getY())
                .filter(p -> p.getLayerType() == layerType)
                .count();
        if(count == 0) {
            return true;
        }

        return false;
    }

    public void onClearMapBtn(ActionEvent actionEvent) {

        game.getGameViewer().getEntityStack().getStack().clear();
        currentEntityType = null;
        currentSkinPage = 0;
        currentSkin = 0;
        draw();
        buildEntityPanel();
        buildSkinPanel();
//        Stage stage = new Stage();
//        stage.initOwner(guiController.getPrimaryStage());
//        Scene scene = guiController.getNewGameScene();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene);
//        stage.show();
    }

    private void buildEntityPanel() {

        int lw = 2;
        int sw = 6;

        entityPanel.setWidth(EntityType.values().length * (GuiController.squareSize + 2*sw) + lw);
        entityPanel.setHeight(GuiController.squareSize + 2*sw + 2*lw);

        entityPanelGC.setStroke(Color.GOLD);

        entityPanelGC.setLineWidth(lw);

        for(EntityType entityType : EntityType.values()) {

            if(entityType == currentEntityType) {
                entityPanelGC.setFill(Color.CYAN);
            } else {
                entityPanelGC.setFill(Color.DARKBLUE);
            }

            entityPanelGC.fillRect(
                    (GuiController.squareSize + 2*sw) * entityType.ordinal(),
                    0,
                    GuiController.squareSize + 2*sw,
                    GuiController.squareSize + 2*sw);

            entityPanelGC.strokeRect(
                    (GuiController.squareSize + 2*sw) * entityType.ordinal() + 1,
                    1,
                    GuiController.squareSize + 2*sw,
                    GuiController.squareSize + 2*sw);

            entityPanelGC.drawImage(
                    drawingTool.getSkinStack().getImage(entityType, 1),
                    (GuiController.squareSize + 2*sw) * entityType.ordinal() + sw + lw/2,
                    sw + lw/2,
                    GuiController.squareSize,
                    GuiController.squareSize);
        }

    }

    public void onEntityPanelPressed(MouseEvent mouseEvent) {

        int posX = (int) (mouseEvent.getX() / entityPanel.getWidth() * EntityType.values().length);
        if(currentEntityType != EntityType.values()[posX]) {
            currentEntityType = EntityType.values()[posX];
            currentSkinPage = 0;
            currentSkin = 1;
            buildEntityPanel();
            buildSkinPanel();
        }
    }

    private void buildSkinPanel() {

        int lw = 2;
        int sw = 6;

        skinPanel.setWidth(EntityType.values().length * (GuiController.squareSize + 2*sw) + lw);
        skinPanel.setHeight(GuiController.squareSize + 2*sw + 2*lw);

        skinPanelGC.setStroke(Color.GOLD);
        skinPanelGC.setLineWidth(lw);

        for(int i=0; i < EntityType.values().length; i++) {

            if((i+1) == currentSkin % EntityType.values().length) {
                skinPanelGC.setFill(Color.CYAN);
            } else {
                skinPanelGC.setFill(Color.DARKBLUE);
            }

            skinPanelGC.fillRect(
                    (GuiController.squareSize + 2*sw) * i,
                    0,
                    GuiController.squareSize + 2*sw,
                    GuiController.squareSize + 2*sw);

            skinPanelGC.strokeRect(
                    (GuiController.squareSize + 2*sw) * i + 1,
                    1,
                    GuiController.squareSize + 2*sw,
                    GuiController.squareSize + 2*sw);

            skinPanelGC.drawImage(
                    drawingTool.getSkinStack().getImage(currentEntityType, i + EntityType.values().length*currentSkinPage + 1),
                    (GuiController.squareSize + 2*sw) * i + sw + lw/2,
                    sw + lw/2,
                    GuiController.squareSize,
                    GuiController.squareSize);
        }
    }

    public void onSkinPanelPressed(MouseEvent mouseEvent) {

        int posX = (int) (mouseEvent.getX() / entityPanel.getWidth() * EntityType.values().length);
        int skin = EntityType.values().length*currentSkinPage + posX + 1;
        Image image = drawingTool.getSkinStack().getImage(currentEntityType, skin);
        if(image != null) {
            currentSkin = skin;
            buildSkinPanel();
        }
    }

    public void onMouseExited(MouseEvent mouseEvent) {

        draw();
    }

    public void onMouseReleased(MouseEvent mouseEvent) {

        if(draggedEntity != null) {
            if (draggedEntity.getPosition2D().getX() < 0) draggedEntity.getPosition2D().setX(0);
            if (draggedEntity.getPosition2D().getX() > GuiController.width)
                draggedEntity.getPosition2D().setX(GuiController.width - 1);
            if (draggedEntity.getPosition2D().getY() < 0) draggedEntity.getPosition2D().setY(0);
            if (draggedEntity.getPosition2D().getY() > GuiController.height)
                draggedEntity.getPosition2D().setY(GuiController.height - 1);

            draggedEntity = null;
            draw();
        }
    }

    public void onSkinLeftClicked(MouseEvent mouseEvent) {
        if(currentSkinPage > 0) {
            currentSkinPage--;
            currentSkin = EntityType.values().length * currentSkinPage + 1;
            buildSkinPanel();
        }


    }

    public void onSkinRightClicked(MouseEvent mouseEvent) {
        Image image = drawingTool.getSkinStack().getImage(currentEntityType, EntityType.values().length*(currentSkinPage+1) + 1);
        if(image != null) {
            currentSkinPage++;
            currentSkin = EntityType.values().length * currentSkinPage + 1;
            buildSkinPanel();
        }
    }

    public void onLoadMapBtn(ActionEvent actionEvent) {

        game.getGameViewer().getEntityStack().getStack().clear();
        game.loadMapFromFile("/levels/Default.usx");
        draw();
    }

    public void onSaveMapBtn(ActionEvent actionEvent) {

        List entityList = game.getGameViewer().getEntityStack().getStack();

        if(entityList == null) return;
        if(entityList.size() == 0) return;

        String filePath = "";
        try {
          filePath = getClass().getResource("/levels/Default.usx").toURI().getPath();
        } catch(URISyntaxException e) {
          e.printStackTrace();
        }

        try(Writer writer = new FileWriter(new File(filePath))) {

            Collections.sort(game.getGameViewer().getEntityStack().getStack(), new EntityEditorComparator());

            writer.write("0#\n");
            writer.write("1\n");
            writer.write("~~~\n");

            ListIterator<GameEntity> li = entityList.listIterator();

            int currentLine = 0;
            while(li.hasNext()) {

                GameEntity entity = li.next();
                if(entity.getPosition2D().getY() > currentLine) {
                    for(int i = 1; i<=entity.getPosition2D().getY()-currentLine; i++) {
                        writer.write("\n");
                    }
                    currentLine = entity.getPosition2D().getY();
                }
                String entityWord = "";
                entityWord += resolveType(entity);
                entityWord += String.format("%03d", entity.getSkin());
                entityWord += (entity.getPosition2D().getX() + 1);
                entityWord += "#";
                writer.write(entityWord);
            }

            Collections.sort(game.getGameViewer().getEntityStack().getStack(), new EntityComparator());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String resolveType(GameEntity entity) {

        if(entity instanceof E_Player)
            return "AA";
        else if(entity instanceof E_Finish)
            return "AB";
        else if(entity instanceof E_Star)
            return "AC";
        else if(entity instanceof E_Wall)
            return "AD";
        else if(entity instanceof E_Floor)
            return "AE";
        else if(entity instanceof E_Laser)
            return "AF";
        else if(entity instanceof E_Wood)
            return "AG";
        else if(entity instanceof E_Dynamite)
            return "AH";
        else if(entity instanceof E_Lock)
            return "AI";
        else if(entity instanceof E_Key)
            return "AJ";
        else if(entity instanceof E_Stone)
            return "AK";
        else if(entity instanceof E_Drill)
            return "AL";
        else if(entity instanceof E_Trigger)
            return "AM";
        else if(entity instanceof E_Bomb)
            return "AN";
        else if(entity instanceof E_TriggerWall)
            return "AO";
        else if(entity instanceof E_Teleport)
            return "AP";
        else if(entity instanceof E_Decoration)
            return "ZB";

        return null;
    }

    public void onMenuBtn(ActionEvent actionEvent) {

        guiController.setWindow(GuiWindow.MENU);
    }
}
