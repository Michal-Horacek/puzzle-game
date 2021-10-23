package com.game2.game.entity;

import com.game2.game.misc.EntityType;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.LayerType;
import com.game2.game.misc.Point2D;

public final class E_Player extends GameEntity {

	public E_Player(Point2D location2d) {
		super(
				location2d, // position on map
				EntityType.PLAYER, // entity type
				LayerType.TOP, // layer type
				0, // skin
				true, // alive
				true, // visible
				true // passable
				);
	} 

}
