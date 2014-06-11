package readingManagement2;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import readingManagement.Detailed;
import readingManagement.Edit;

public class Main extends Application {

    /**
     * Main class instance
     */
    private static Main instance;

    /**
     * ステージ
     */
    private Stage stage;

    public void start(Stage primaryStage) throws Exception {
        /*AnchorPane main = FXMLLoader.load(Main.class.getResource("search.fxml"));

        stage.setTitle("ReadingManagement");
        stage.setScene(new Scene(main, 480, 360));
        stage.show();*/

        // インスタンス
        instance = this;

        // ステージの設定
        stage = primaryStage;
        stage.setTitle("ReadingManagement");
        stage.setWidth(480);
        stage.setHeight(360);

        // Searchに遷移
        sendSearch();

        // ステージの設定
        stage.show();
    }

    public static void main(String[] args) {
    	launch(args);
    }

    //ページ遷移Search
    public void sendSearch() {

    	Search2 controller = new Search2();
        this.replaceSceneContent(controller);
    }

    //ページ遷移Edit
    public void sendEdit() {

    	Edit controller = new Edit();
        this.replaceSceneContent(controller);
    }

    //ページ遷移Detailed
    public void sendDetailed() {

    	Detailed controller = new Detailed();
        this.replaceSceneContent(controller);
    }

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