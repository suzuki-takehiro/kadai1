package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Search extends AnchorPane implements Initializable {

	public Search() {
		loadFXML();
	}

	/**
	 * Searchクラスインスタンス
	 */
	private static Search instance;

	private void loadFXML() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				Main.class.getResource("search.fxml"));
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
	private Pane root;

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

		ComboBoxController.setCBDate(genreField);

		/**
		 * ComboBoxで選択したテキストを取得
		 * gnreTextに代入
		 */
		genreField.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov,
							String old_val, String new_val) {
						genreText = new_val;
					}
				});

		/**
		 * インスタンス
		 */
		instance = this;

		// ViewクラスとSearchクラスの対応付け
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
	//ウィンドウを閉じる
	protected void close() {
		root.getScene().getWindow().hide();
	}

	@FXML
	public void search() {

		String getTitle = titleField.getText();

		String getGenre = genreText;

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

		DBAccess.search(table, buttonColumn, getTitle, getGenre, getWriter,
				getPublisher, getStart, getEnd);
	}

	/**
	 * Searchインスタンス取得
	 *
	 * @return:instance
	 */
	public static Search getInstance() {
		return instance;
	}

}