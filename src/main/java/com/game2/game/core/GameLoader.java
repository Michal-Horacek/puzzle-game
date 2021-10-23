package com.game2.game.core;

import com.game2.game.entity.*;
import com.game2.game.misc.*;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by horacekm on 2.10.2017.
 */
public class GameLoader {

    private GameModel model;

    public GameLoader(GameModel model) {
        this.model = model;
    }

    public void loadFromFile(String filePath) {

        model.getMapInfo().setWidth(31);
        model.getMapInfo().setHeight(19);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {

            String line;
            int counter = 0;
            String background = in.readLine();
            String freeMove = in.readLine();
            model.getMapInfo().setFreeMove(freeMove.equals("1"));
            in.readLine();
            while ((line = in.readLine()) != null) {
                if(line.equals("")) {
                    counter++;
                    continue;
                }
                String[] item = line.split("#");
                for (String s : item) {
                    addToStack(s, counter);
                }
                counter++;
            }

            if(model.getMapInfo().getStarsCount() == 0) {
                for(GameEntity entity : model.getEntityStack().getStack()) {
                    if(entity.getEntityType() == EntityType.FINISH) {
                        entity.setPassable(true);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int countStarsInStack(EntityStack entityStack) {

        int result = 0;

        Iterator<GameEntity> itr = entityStack.getStack().iterator();
        while(itr.hasNext()) {
            GameEntity gameEntity = (GameEntity) itr.next();
            if(gameEntity.getEntityType() == EntityType.STAR)
                result++;
        }

        return result;
    }

    public void loadDefinedMap(int width, int height, EntityStack entityStack, E_Player activePlayer) {

        model.getMapInfo().setWidth(width);
        model.getMapInfo().setHeight(height);
        model.setEntityStack(entityStack);
        model.setActivePlayer(activePlayer);
        model.getMapInfo().setStarsCount(countStarsInStack(entityStack));

        Collections.sort(model.getEntityStack().getStack(), new EntityComparator());
    }

    public void createEmptyMap(int width, int height) {

        model.getMapInfo().setWidth(width);
        model.getMapInfo().setHeight(height);
    }

    private void addToStack(String s, int posY) {

        GameEntity entity = null;
        String entityType = s.substring(0, 2);
        int skin = Integer.parseInt(s.substring(2, 5));
        int posX = Integer.parseInt(s.substring(5, s.length())) - 1;

        if(entityType.equals("AA")) {
            entity = new E_Player(new Point2D(posX, posY));
            model.setActivePlayer((E_Player) entity);
        } else if(entityType.equals("AB")) {
            entity = new E_Finish(new Point2D(posX, posY));
        } else if(entityType.equals("AC")) {
            entity = new E_Star(new Point2D(posX, posY));
            model.getMapInfo().setStarsCount(model.getMapInfo().getStarsCount() + 1);
        } else if(entityType.equals("AD")) {
            entity = new E_Wall(new Point2D(posX, posY));
        } else if(entityType.equals("AE")) {
            entity = new E_Floor(new Point2D(posX, posY));
        } else if(entityType.equals("AF")) {
            if(skin == 1) {
                entity = new E_Laser(new Point2D(posX, posY), Direction.RIGHT);
            } else {
                entity = new E_Laser(new Point2D(posX, posY), Direction.DOWN);
            }
        } else if(entityType.equals("AG")) {
            entity = new E_Wood(new Point2D(posX, posY));
        } else if(entityType.equals("AH")) {
            entity = new E_Dynamite(new Point2D(posX, posY));
        } else if(entityType.equals("AI")) {
            entity = new E_Lock(new Point2D(posX, posY));
        } else if(entityType.equals("AJ")) {
            entity = new E_Key(new Point2D(posX, posY));
        } else if(entityType.equals("AK")) {
            entity = new E_Stone(new Point2D(posX, posY));
        } else if(entityType.equals("AL")) {
            entity = new E_Drill(new Point2D(posX, posY));
        } else if(entityType.equals("AM")) {
            entity = new E_Trigger(new Point2D(posX, posY), skin);
        } else if(entityType.equals("AN")) {
            entity = new E_Bomb(new Point2D(posX, posY), skin);
        } else if(entityType.equals("AO")) {
            entity = new E_TriggerWall(new Point2D(posX, posY), skin);
         }else if(entityType.equals("AP")) {
            entity = new E_Teleport(new Point2D(posX, posY), skin);
        } else if(entityType.equals("ZA")) {
            return;
        } else if(entityType.equals("ZB")) {
            entity = new E_Decoration(new Point2D(posX, posY));
        }

        if(entity == null) {
            return;
        }

        entity.setSkin(skin);
        model.getEntityStack().add(entity);
    }
}
