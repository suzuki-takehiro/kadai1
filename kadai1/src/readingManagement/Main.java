package readingManagement;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * Mainクラスインスタンス
	 */
	private static Main instance;

	public static Stage stage;

	/**
	 * ウィンドウアイコン
	 */
	static Image icon = new Image(
			Main.class.getResourceAsStream("windowIcon.gif"));

	public void start(Stage primaryStage) throws Exception {

		/**
		 * インスタンス設定
		 */
		instance = this;

		// ステージの設定
		stage = primaryStage;
		stage.getIcons().addAll(icon);
		stage.setTitle("ReadingManagement");
		stage.setWidth(508);
		stage.setHeight(480);

		// Searchに遷移
		sendSearch();

		// ステージの設定
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * ページ遷移Register
	 */
	public void sendRegister() {

		Register controller = new Register();
		Detailed.setParentType(false);
		this.replaceSceneContent(controller);
	}

	/**
	 * ページ遷移Search
	 */
	public void sendSearch() {

		Search controller = new Search();
		Detailed.setParentType(true);
		this.replaceSceneContent(controller);
	}

	/**
	 * シーン変更
	 *
	 * @param controller
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
	 * Mainインスタンス取得
	 *
	 * @return:instance
	 */
	public static Main getInstance() {

		return instance;
	}
}
