package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Detailed extends AnchorPane implements Initializable {

	/*
	 * public Detailed() {
	 *
	 * loadFXML(); }
	 *
	 * private void loadFXML() {
	 *
	 * FXMLLoader fxmlLoader = new FXMLLoader(
	 * SubMain.class.getResource("detailed.fxml")); fxmlLoader.setRoot(this);
	 *
	 * // 自分自身をコントロールとして設定 fxmlLoader.setController(this);
	 *
	 * try { fxmlLoader.load(); } catch (IOException exception) { throw new
	 * RuntimeException(exception); } }
	 */

	@FXML
	private Pane root;

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
	private TextField startField;

	@FXML
	private TextField endField;

	@FXML
	private TextArea textField;

	// テキストフィールド変更不可 editable="false"

	public void setStates(String setid, String title, String genre,
			String writer, String publisher, String start, String end,
			String text) {
		id = setid;
		titleField.setText(title);
		genreField.setText(genre);
		writerField.setText(writer);
		publisherField.setText(publisher);
		startField.setText(start);
		endField.setText(end);
		textField.setText(text);

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	/*
	 * @FXML protected void goEdit() { OpenerFactory.sendEdit(); }
	 */

	@FXML
	public void close() {
		root.getScene().getWindow().hide();
	}

	public String flag = "";

	static String ynFlag = "";

	public static void setYNFlag(String ans) {
		ynFlag = ans;
	};

	@FXML
	public void onEdit() {
		flag = "edit";
		openDialog();
		if (ynFlag.equals("yes")) {
			regist();
			showDialog("r");
		}
	}

	@FXML
	public void onDelete() {
		flag = "delete";
		openDialog();
		if (ynFlag.equals("yes")) {
			delete();
			showDialog("d");
			root.getScene().getWindow().hide();
		}
	}

	public void openDialog() {

		// 確認ダイアログ表示
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"confirmationDialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ConfirmationDialog controller = loader.getController();
		controller.setId(id);
		controller.setFlag(flag);
		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();

		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.setTitle("Confirmation");
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
		flag = "";
	}

	public void regist() {

		// table.setItems(null);

		String getTitle = titleField.getText();

		String getGenre = genreField.getText();

		String getWriter = writerField.getText();

		String getPublisher = publisherField.getText();

		String getStart = startField.getText();

		String getEnd = endField.getText();

		String getText = textField.getText();

		// System.out.println(getTitle);

		if (titleField.getText().matches(".+")
				&& startField.getText().matches(".+")
				&& endField.getText().matches(".+")) {
			try {
				// JDBCドライバーの指定
				Class.forName("org.sqlite.JDBC");

				// データベースに接続する なければ作成される
				Connection con = DriverManager
						.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

				// Statementオブジェクト作成
				Statement stmt = con.createStatement();

				// 問合せ文
				// 登録SQL
				String sqlins = "update test set title = '" + getTitle
						+ "' , genre = '" + getGenre + "' , writer = '"
						+ getWriter + "' , publisher = '" + getPublisher
						+ "' , start = '" + getStart + "' , end = '" + getEnd
						+ "' , text = '" + getText + "' where id = " + id + "";
				stmt.executeUpdate(sqlins);

				stmt.close();

			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		} else {

			if (!(titleField.getText().matches(".+"))) {
				setText("タイトル ");
			}
			if (!(startField.getText().matches(".+"))) {
				setText("開始期間 ");
			}
			if (!(endField.getText().matches(".+"))) {
				setText("終了期間 ");
			}

			ynFlag = "";

			showDialog("e");

		}
	}

	public void delete() {
		try {
			// JDBCドライバーの指定
			Class.forName("org.sqlite.JDBC");

			// データベースに接続する なければ作成される
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

			// Statementオブジェクト作成
			Statement stmt = con.createStatement();

			// 問合せ文
			// 登録SQL
			String sqlins = "delete from test where id = " + id + "";
			stmt.executeUpdate(sqlins);

			stmt.close();

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static String error = "";

	public void setText(String koumoku) {
		error = error + koumoku;
	}

	public static void showDialog(String type) {
		String messageType = type;
		// メッセージダイアログ表示
		FXMLLoader loader = new FXMLLoader(
				Register.class.getResource("errorDialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();

		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		if(messageType.equals("e")){
			stage2.setTitle("Error");
			error = error + "を入力してください。";
		}else if(messageType.equals("r")){
			stage2.setTitle("Confirmation");
			error = "登録しました。";
		}else if(messageType.equals("d")){
			stage2.setTitle("Confirmation");
			error = "削除しました。";
		}
		ErrorDialog controller = loader.getController();
		controller.setMessage(error);
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
		error = "";
	}
}