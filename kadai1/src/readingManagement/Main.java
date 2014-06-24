package readingManagement;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * Main class instance
	 */
	private static Main instance;

	/**
	 * ステージ
	 */
	public static Stage stage;

	static// ウィンドウアイコン設定
	Image icon = new Image(Main.class.getResourceAsStream("windowIcon.gif"));

	public void start(Stage primaryStage) throws Exception {
		/*
		 * AnchorPane main =
		 * FXMLLoader.load(Main.class.getResource("search.fxml"));
		 *
		 * stage.setTitle("ReadingManagement"); stage.setScene(new Scene(main,
		 * 480, 360)); stage.show();
		 */

		// インスタンス
		instance = this;

		// ステージの設定
		stage = primaryStage;
		stage.getIcons().addAll(icon);
		stage.setTitle("ReadingManagement");
		stage.setWidth(480);
		stage.setHeight(480);

		// Searchに遷移
		sendSearch();

		// ステージの設定
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// ページ遷移Register
	public void sendRegister() {

		Register controller = new Register();
		Detailed.setParentType("register");
		this.replaceSceneContent(controller);
	}

	// ページ遷移Search
	public void sendSearch() {

		Search controller = new Search();
		Detailed.setParentType("search");
		this.replaceSceneContent(controller);
	}

	// ページ遷移Detailed
	/*
	 * public void sendDetailed() {
	 *
	 * Detailed controller = new Detailed();
	 * this.replaceSceneContent(controller); }
	 */

	/**
	 * シーンの変更
	 */
	private void replaceSceneContent(Parent controller) {
		Scene scene = stage.getScene();
		if (scene == null) {
			scene = new Scene(controller);
			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(controller);
		}
	}

	/**
	 * Get Instance
	 */
	public static Main getInstance() {
		return instance;
	}
}
