package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Detailed extends AnchorPane implements Initializable {

	@FXML
	private Pane root;

	// 現在表示している詳細情報元のID
	private String id;

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

	// テキストフィールド変更不可 editable="false"

	public void setStates(String setid, String title, String genre,
			String writer, String publisher, LocalDate start, LocalDate end,
			String text) {
		id = setid;
		titleField.setText(title);
		genreField.setText(genre);
		writerField.setText(writer);
		publisherField.setText(publisher);
		startField.setValue(start);
		endField.setValue(end);
		textField.setText(text);

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	public void close() {
		root.getScene().getWindow().hide();
	}

	// Detailedウィンドウでの操作フラグ(edit/delete)
	public String processType = "";

	// ConfirmationDialogウィンドウでの操作フラグ取得(yes/"")
	static String ButtonType = "";

	public static void setButtonType(String type) {
		ButtonType = type;
	};

	// 表示元ウィンドウの種類フラグ取得(search/regist)
	static String ParentType = "";

	public static void setParentType(String ans) {
		ParentType = ans;
	};

	@FXML
	public void onEdit() {
		processType = "edit";
		openDialog();
		if (ButtonType.equals("yes")) {
			regist();

			if (ParentType.equals("search")) {
				Search.getInstance().search();

			} else if (ParentType.equals("register")) {
				Register.getInstance().ViewTable();

			}
			processType = "";
		}
	}

	@FXML
	public void onDelete() {
		processType = "delete";
		openDialog();
		if (ButtonType.equals("yes")) {
			delete();

			if (ParentType.equals("search")) {
				Search.getInstance().search();

			} else if (ParentType.equals("register")) {
				Register.getInstance().ViewTable();

			}
			processType = "";
			root.getScene().getWindow().hide();
		}
	}

	// 確認ダイアログ表示
	public void openDialog() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"confirmationDialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ConfirmationDialog controller = loader.getController();
		controller.setProcessType(processType);

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();

		stage2.getIcons().addAll(Main.icon);
		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.setTitle("Confirmation");
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
	}

	// データ登録
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

		//開始期間と終了期間比較
		int diff = getStart.compareTo(getEnd);

		if (titleField.getText().matches(".+")
				&& !(startField.getValue() == null)
				&& !(endField.getValue() == null)) {
			if (diff > 0) {
				showDialog("periodErrer");

			} else {
				try {
					// JDBCドライバーの指定
					Class.forName("org.sqlite.JDBC");

					// データベースに接続する なければ作成される
					Connection con = DriverManager
							.getConnection("jdbc:sqlite:src/SQLite/DB");

					// Statementオブジェクト作成
					Statement stmt = con.createStatement();

					// 登録SQL
					String sqlins = "update test set title = '" + getTitle
							+ "' , genre = '" + getGenre + "' , writer = '"
							+ getWriter + "' , publisher = '" + getPublisher
							+ "' , start = '" + getStart + "' , end = '"
							+ getEnd + "' , text = '" + getText
							+ "' where id = " + id + "";
					stmt.executeUpdate(sqlins);
					stmt.close();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				showDialog("edit");
			}

		} else {

			if (!(titleField.getText().matches(".+"))) {
				setText("タイトル ");}
			if ((startField.getValue() == null)) {
				setText("開始期間 ");}
			if ((endField.getValue() == null)) {
				setText("終了期間 ");}

			ButtonType = "";

			showDialog("shortageError");

		}
	}

	public void delete() {
		try {
			// JDBCドライバーの指定
			Class.forName("org.sqlite.JDBC");

			// データベースに接続する なければ作成される
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:src/SQLite/DB");

			// Statementオブジェクト作成
			Statement stmt = con.createStatement();

			// 登録SQL
			String sqlins = "delete from test where id = " + id + "";
			stmt.executeUpdate(sqlins);
			stmt.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		showDialog("delete");
	}

	// エラーダイアログ表示メッセージ
	public static String error = "";

	public void setText(String koumoku) {
		error = error + koumoku;}

	// メッセージダイアログ表示
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

		if (messageType.equals("shortageError")) {
			stage2.setTitle("Error");
			error = error + "を入力してください。";

		} else if (messageType.equals("edit")) {
			stage2.setTitle("Confirmation");
			error = "変更しました。";

		} else if (messageType.equals("delete")) {
			stage2.setTitle("Confirmation");
			error = "削除しました。";

		} else if (messageType.equals("periodErrer")) {
			stage2.setTitle("Error");
			error = "期間が正しくありません。";

		}

		ErrorDialog controller = loader.getController();
		controller.setMessage(error);

		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.getIcons().addAll(Main.icon);
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
		error = "";
	}
}