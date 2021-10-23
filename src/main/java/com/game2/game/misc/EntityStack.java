package com.game2.game.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.EntityComparator;

public class EntityStack {
	
	private List<GameEntity> stack;
	
	public EntityStack() {
		
		stack = new ArrayList<>();
	}
	
	public void add(GameEntity gameEntity) {
		stack.add(gameEntity);
		Collections.sort(stack, new EntityComparator());
	}
	
	public List<GameEntity> getStack() {
		return stack;
	}
}
