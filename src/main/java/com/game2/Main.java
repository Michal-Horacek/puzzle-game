package com.game2;

import com.game2.gui.controller.GuiController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		GuiController guiController = new GuiController(primaryStage);
		guiController.init();
	}
}
