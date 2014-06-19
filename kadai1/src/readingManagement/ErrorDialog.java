package readingManagement;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ErrorDialog implements Initializable {

	@FXML
	private Pane root;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	public Label label;

	public void setMessage(String message){
		label.setText(message + "を入力してください。");
	}

	@FXML
	public void close(){
		root.getScene().getWindow().hide();
	}

}
