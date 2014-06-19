package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Search extends AnchorPane implements Initializable {

	public Search() {

		loadFXML();
	}

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
	private TextField titleField;

	@FXML
	private TextField genreField;

	@FXML
	private TextField writerField;

	@FXML
	private TextField publisherField;

	@FXML
	private TextField startField;

	@FXML
	private TextField endField;

	@FXML
	private TextArea textField;

	@FXML
	private TableView<View> table;

	@FXML
	private TableColumn<View, String> idColumn;

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

		// ViewクラスとSearchクラスの対応付け
		idColumn.setCellValueFactory(new PropertyValueFactory<View, String>(
				"id"));
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
	public void search() {

		table.getItems().clear();

		buttonColumn.setCellFactory(new OpenerFactory());

		String getTitle = titleField.getText();

		String getGenre = genreField.getText();

		String getWriter = writerField.getText();

		String getPublisher = publisherField.getText();

		String getStart = startField.getText();

		String getEnd = endField.getText();

		try {

			// JDBCドライバーの指定
			Class.forName("org.sqlite.JDBC");

			// データベースに接続する なければ作成される
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

			// Statementオブジェクト作成
			Statement stmt = con.createStatement();

			// 問合せ文
			String sql = "select * from test where title like '%" + getTitle
					+ "%' and genre like '%" + getGenre
					+ "%' and writer like '%" + getWriter
					+ "%' and publisher like '%" + getPublisher
					+ "%' and start like '%" + getStart + "%' and end like '%"
					+ getEnd + "%'";
			ResultSet rs = stmt.executeQuery(sql);

			// データ表示
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				String writer = rs.getString("writer");
				String publisher = rs.getString("publisher");
				String start = rs.getString("start");
				String end = rs.getString("end");
				String text = rs.getString("text");
				// System.out.println(id + title + genre + writer + publisher +
				// start + end + text);
				table.getItems().add(
						new View(id, title, genre, writer, publisher, start,
								end, text));
			}

			rs.close();
			stmt.close();

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}

		/*// テーブルレコードのクリックイベント
		TableView.TableViewSelectionModel<View> selectionModel = table
				.getSelectionModel();
		selectionModel.selectedItemProperty().addListener(
				new ChangeListener<View>() {

					public void changed(ObservableValue<? extends View> value,
							View old, View next) {
						String title = next.getTitle();
						String genre = next.getGenre();
						String writer = next.getWriter();
						String publisher = next.getPublisher();
						String start = next.getStart();
						String end = next.getEnd();
						String text = next.getText();
						System.out.println(title + genre + writer + publisher
								+ start + end + text);

						// Detailedウィンドウ表示
						FXMLLoader loader = new FXMLLoader(getClass()
								.getResource("detailed.fxml"));
						try {
							loader.load();
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						Parent root = loader.getRoot();
						Detailed controller = loader.getController();
						controller.setStates(title, genre, writer, publisher,
								start, end, text);
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setTitle("confirmation");
						stage.setScene(scene);
						stage.setWidth(540);
						stage.setHeight(480);
						stage.showAndWait();

					}
				});*/
	}
}