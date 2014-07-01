package readingManagement;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Dialog implements Initializable {

	private static Dialog instance;

	@FXML
	private Pane root;

	@FXML
	public Label label;

	@FXML
	private Button yesButton;

	@FXML
	private Button noButton;

	/**
	 * 確認ダイアログで押したボタンの判別フラグ
	 * on yesButton = true /
	 * on noButton = false
	 */
	public static boolean buttonFlag;

	public void setLabelText(String labelText) {
		label.setText(labelText);
	}

	public void setNoButtonText(String buttonText) {
		noButton.setText(buttonText);
	}

	/**
	 * yesボタン非表示 noボタンのテキストを"閉じる"に変更
	 */
	public void visibleButton() {
		setNoButtonText("閉じる");
		yesButton.setVisible(false);
	}

	@FXML
	public void yesButton() {
		buttonFlag = true;
		root.getScene().getWindow().hide();
	}

	@FXML
	public void noButton() {
		buttonFlag = false;
		root.getScene().getWindow().hide();
		setNoButtonText("いいえ");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
	}

	/**
	 * Dialogインスタンス取得
	 *
	 * @return:instance
	 */
	public static Dialog getInstance() {
		return instance;
	}

}
