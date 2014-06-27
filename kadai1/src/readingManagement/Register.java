package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Register extends AnchorPane implements Initializable {

	public Register() {
		loadFXML();
	}

	/**
	 * Registerクラスインスタンス
	 */
	private static Register instance;

	private void loadFXML() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"register.fxml"));
		fxmlLoader.setRoot(this);

		// 自分自身をコントロールとして設定
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	private TextField titleField;

	@FXML
	private TextField genreField;

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

	@FXML
	private TableView<View> table;

	@FXML
	private TableColumn<View, String> titleColumn;

	@FXML
	private TableColumn<View, String> genreColumn;

	@FXML
	private TableColumn<View, String> writerColumn;

	@FXML
	private TableColumn<View, String> publisherColumn;

	@FXML
	private TableColumn<View, String> startColumn;

	@FXML
	private TableColumn<View, String> endColumn;

	@FXML
	private TableColumn<View, String> textColumn;

	@FXML
	private TableColumn<View, String> buttonColumn;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		/**
		 * インスタンス設定
		 */
		instance = this;

		titleColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"title"));
		genreColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"genre"));
		writerColumn
				.setCellValueFactory(new PropertyValueFactory<View, String>(
						"writer"));
		publisherColumn
				.setCellValueFactory(new PropertyValueFactory<View, String>(
						"publisher"));
		startColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"start"));
		endColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"end"));
		textColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"text"));
		buttonColumn
				.setCellValueFactory(new PropertyValueFactory<View, String>(
						"id"));
		DBAccess.ViewTable(table, buttonColumn);
	}

	@FXML
	protected void goSearch() {
		Main.getInstance().sendSearch();
	}

	@FXML
	protected void goRegister() {
		Main.getInstance().sendRegister();
	}

	@FXML
	public void regist() {

		String getTitle = titleField.getText();
		String getGenre = genreField.getText();
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
		DBAccess.regist(getTitle, getGenre, getWriter, getPublisher, getStart,
				getEnd, getText);
		registerView();
	}

	/**
	 * RegisterのTableViewを更新
	 */
	public void registerView() {
		DBAccess.ViewTable(table, buttonColumn);
	}

	/**
	 * Registerインスタンス取得
	 *
	 * @return:instance
	 */
	public static Register getInstance() {
		return instance;
	}

}