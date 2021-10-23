package com.game2.test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestRunner {

	public static void main(String[] args) {
		TestRunner.run();
	}

	public static void run() {

		List<Test> list = new ArrayList<>();
		list.add(new PlayerTest());
		list.add(new WallTest());
		list.add(new WoodTest());
		list.add(new DynamiteTest());
		list.add(new FloorAndDecorationTest());
		list.add(new StarTest());
		list.add(new FinishTest());
		list.add(new LaserTest());
		list.add(new StoneTest());
		list.add(new KeyTest());
		list.add(new LockTest());
		list.add(new TeleportTest());
		list.add(new MultiplayerTest());
		list.add(new BombTest());
		list.add(new TriggerWallTest());
		list.add(new TriggerTest());
		list.add(new Trigger2Test());
		list.add(new StopTest());

		Iterator<Test> itr = list.iterator();
		while(itr.hasNext()) {
			Test test = itr.next();
			test.run();
		}
	}

}
