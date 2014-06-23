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

	public String flag;

	public String id;

	public void setId(String setid) {
		id = setid;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void setFlag(String setflag) {
		flag = setflag;
		if (flag.equals("edit")) {
			label.setText("変更してもよろしいですか？");
			System.out.println(id);
		} else if (flag.equals("delete")) {
			label.setText("削除してもよろしいですか？");
		} else {
			label.setText("エラーです。");
		}
	}

	@FXML
	public void yes() {
		Detailed.setYNFlag("yes");
		root.getScene().getWindow().hide();
	}

	@FXML
	public void close() {
		Detailed.setYNFlag("");
		root.getScene().getWindow().hide();
	}

}
