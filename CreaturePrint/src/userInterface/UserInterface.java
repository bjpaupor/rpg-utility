package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserInterface extends Application {
	//private Image icon;
	@Override
	public void start(Stage stage) throws Exception {
		//icon = new Image(getClass().getResourceAsStream("/Icon.png"));
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
		Scene scene = new Scene(root, 300, 275);
		stage.setTitle("Creature Print");
		//stage.getIcons().add(icon);
//		stage.setMinWidth(root.getMinWidth());
//		stage.setMinHeight(root.getMinHeight());
		
		stage.setScene(scene);
		stage.setHeight(400);
		stage.setWidth(500);
//		stage.setMaximized(true);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}