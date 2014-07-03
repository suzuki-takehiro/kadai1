package readingManagement;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import readingManagement.DialogController.DialogType;

public class Detailed extends AnchorPane implements Initializable {

	@FXML
	private Pane root;

	private String id;

	@FXML
	private TextField titleField;

	@FXML
	private ComboBox<String> genreField;
	String genreText = "";

	@FXML
	private TextField writerField;

	@FXML
	private TextField publisherField;

	@FXML
	private DatePicker startField;

	@FXML
	private DatePicker endField;

	@FXML
	private TextArea textField;

	/**
	 * Detailedに表示レコードデータをセット
	 * @param setid
	 * @param title
	 * @param genre
	 * @param writer
	 * @param publisher
	 * @param start
	 * @param end
	 * @param text
	 */
	public void setStates(String setid, String title, String genre,
			String writer, String publisher, LocalDate start, LocalDate end,
			String text) {
		id = setid;
		titleField.setText(title);
		genreField.setValue(genre);
		writerField.setText(writer);
		publisherField.setText(publisher);
		startField.setValue(start);
		endField.setValue(end);
		textField.setText(text);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ComboBoxController.setCBDate(genreField);

	}

	/**
	 * ウィンドウを閉じる
	 */
	@FXML
	public void close() {
		root.getScene().getWindow().hide();
	}

	/**
	 * Detailed表示元判別フラグ
	 * 表示元Search = true /
	 * 表示元Register = false
	 */
	static boolean ParentType;

	public static void setParentType(boolean type) {
		ParentType = type;
	};

	@FXML
	public void onEdit() {
		DialogController.showDialog(DialogType.confirmationEdit);
		if (Dialog.buttonFlag) {
			if (edit()) {
				if (ParentType) {
					Search.getInstance().search();

				} else {
					Register.getInstance().registerView();

				}
				DialogController.showDialog(DialogType.edit);
			}
		}
	}

	@FXML
	public void onDelete() {
		DialogController.showDialog(DialogType.confirmationDelete);
		if (Dialog.buttonFlag) {
			DBAccess.delete(id);

			if (ParentType) {
				Search.getInstance().search();

			} else {
				Register.getInstance().registerView();

			}
			DialogController.showDialog(DialogType.delete);
			root.getScene().getWindow().hide();
		}
	}

	/**
	 * DBレコード更新中継
	 * DBAccess.upDateのbooleanを返す
	 * 更新に成功 = true /
	 * 更新に失敗 = false
	 */
	public boolean edit() {
		String getTitle = titleField.getText();
		String getGenre = genreField.getValue();
		String getWriter = writerField.getText();
		String getPublisher = publisherField.getText();
		String getStart = "";
		if (!(startField.getValue() == null)) {
			getStart = startField.getValue().toString();
		}
		String getEnd = "";
		if (!(endField.getValue() == null)) {
			getEnd = endField.getValue().toString();
		}
		String getText = textField.getText();
		return DBAccess.upDate(id, getTitle, getGenre, getWriter, getPublisher,
				getStart, getEnd, getText);
	}
}