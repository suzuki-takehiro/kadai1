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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Register extends AnchorPane implements Initializable {

	public Register() {
		loadFXML();
	}

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

		// ViewクラスとRegisterクラスの対応付け
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

		Detailed.setRorSFlag("r");

		ViewTable();

	}

	public void ViewTable() {
		try {
			buttonColumn.setCellFactory(new OpenerFactory());
			// JDBCドライバーの指定
			Class.forName("org.sqlite.JDBC");

			// データベースに接続する なければ作成される
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

			// Statementオブジェクト作成
			Statement stmt = con.createStatement();

			// sql文作成
			String sql = "select * from test order by id desc limit 10";

			// sql問合せ
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

		int diff = getStart.compareTo(getEnd);

		if (titleField.getText().matches(".+")
				&& !(startField.getValue() == null)
				&& !(endField.getValue() == null)) {
			if (diff > 0) {
				showDialog("eb");
			} else {
				try {
					buttonColumn.setCellFactory(new OpenerFactory());

					// JDBCドライバーの指定
					Class.forName("org.sqlite.JDBC");

					// データベースに接続する なければ作成される
					Connection con = DriverManager
							.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

					// Statementオブジェクト作成
					Statement stmt = con.createStatement();

					// 最大ID取得SQL
					String sqlcount = "select max(id) from test";
					ResultSet num = stmt.executeQuery(sqlcount);
					String numId = Integer.toString(num.getInt("max(id)") + 1);

					// 登録SQL
					String sqlins = "insert into test values(" + numId + ",'"
							+ getTitle + "','" + getGenre + "','" + getWriter
							+ "','" + getPublisher + "','" + getStart + "','"
							+ getEnd + "','" + getText + "')";
					stmt.executeUpdate(sqlins);

					// テーブルの中身を削除
					table.getItems().clear();

					num.close();
					stmt.close();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ViewTable();
			}

		} else {

			if (!(titleField.getText().matches(".+"))) {
				setText("タイトル ");
			}
			if ((startField.getValue() == null)) {
				setText("開始期間 ");
			}
			if ((endField.getValue() == null)) {
				setText("終了期間 ");
			}

			showDialog("ea");

		}
	}

	// エラーダイアログ表示メッセージ
	public static String error = "";

	public void setText(String koumoku) {
		error = error + koumoku;
	}

	// エラーダイアログ表示
	public static void showDialog(String type) {

		String messageType = type;

		FXMLLoader loader = new FXMLLoader(
				Register.class.getResource("errorDialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();
		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);

		if (messageType.equals("ea")) {
			error = error + "を入力してください。";
		} else if (messageType.equals("eb")) {
			error = "期間が正しくありません。";
		}

		ErrorDialog controller = loader.getController();
		controller.setMessage(error);

		stage2.setTitle("Error");
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
		error = "";
	}
}