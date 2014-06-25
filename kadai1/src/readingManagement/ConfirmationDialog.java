package readingManagement;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ConfirmationDialog implements Initializable {

	@FXML
	private Pane root;

	@FXML
	public Label label;

	// Detailedウィンドウでの操作フラグ(edit/delete)
	public String processType;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void setProcessType(String type) {

		processType = type;
		if (processType.equals("edit")) {
			label.setText("変更してもよろしいですか？");

		} else if (processType.equals("delete")) {
			label.setText("削除してもよろしいですか？");

		} else {

			label.setText("エラーです。");
		}
	}

	@FXML
	public void yes() {

		Detailed.setButtonType("yes");
		root.getScene().getWindow().hide();
	}

	@FXML
	public void close() {

		Detailed.setButtonType("");
		root.getScene().getWindow().hide();
	}

}
