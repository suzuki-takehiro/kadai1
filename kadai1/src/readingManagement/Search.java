package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Search extends AnchorPane implements Initializable {

    public Search() {

        loadFXML();
    }

    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("search.fxml"));
        fxmlLoader.setRoot(this);

        // 自分自身をコントロールとして設定
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void goSearch(MouseEvent event){
    	Main.getInstance().sendSearch();
    }

    @FXML
    protected void goSearch() {
        Main.getInstance().sendSearch();
    }

    @FXML
    protected void goRegister() {
        Main.getInstance().sendRegister();
    }
}