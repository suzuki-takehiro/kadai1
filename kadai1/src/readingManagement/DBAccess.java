package readingManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import readingManagement.DialogController.DialogType;

public class DBAccess {

	/**
	 * 登録、更新入力値チェック
	 *
	 * @return true/false
	 */
	static int diff = 0;

	static boolean comparison(String title, String start, String end) {
		if (!(title.matches(".+")) || start == "" || end == "") {
			DialogController.showDialog(DialogType.shortageError);
			return false;

		} else if (title.matches("^\\s*\\s$")) {
			DialogController.showDialog(DialogType.titleError);
			return false;

		} else if (diff > 0) {
			DialogController.showDialog(DialogType.periodError);
			return false;

		} else {
			return true;

		}

	}

	/**
	 * DB登録
	 */
	public static void regist(String title, String genre, String writer,
			String publisher, String start, String end, String text) {

		diff = start.compareTo(end);

		if (comparison(title, start, end)) {
			try {
				// Statementオブジェクト作成
				Statement stmt = createStatement();

				// 最大ID取得SQL
				String sqlcount = "select max(id) from test";
				ResultSet rs = stmt.executeQuery(sqlcount);
				String numId = Integer.toString(rs.getInt("max(id)") + 1);

				// 登録SQL
				String sqlins = "insert into test values(" + numId + ",'"
						+ title + "','" + genre + "','" + writer + "','"
						+ publisher + "','" + start + "','" + end + "','"
						+ text + "')";

				stmt.executeUpdate(sqlins);

				rs.close();
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			DialogController.showDialog(DialogType.regist);
		}
	}

	/**
	 * DB検索
	 */
	public static void search(TableView<View> table,
			TableColumn<View, String> buttonColumn, String title, String genre,
			String writer, String publisher, String start, String end) {

		table.getItems().clear();

		buttonColumn.setCellFactory(new OpenerFactory());

		try {

			// Statementオブジェクト作成
			Statement stmt = createStatement();

			// 問合せ文
			String sql = "select * from test where title like '%" + title
					+ "%' and genre like '%" + genre + "%' and writer like '%"
					+ writer + "%' and publisher like '%" + publisher
					+ "%' and start like '%" + start + "%' and end like '%"
					+ end + "%'";

			ResultSet rs = stmt.executeQuery(sql);

			// データ表示
			while (rs.next()) {
				String getId = rs.getString("id");
				String getTitle = rs.getString("title");
				String getGenre = rs.getString("genre");
				String getWriter = rs.getString("writer");
				String getPublisher = rs.getString("publisher");
				String getStart = rs.getString("start");
				String getEnd = rs.getString("end");
				String getText = rs.getString("text");

				table.getItems().add(
						new View(getId, getTitle, getGenre, getWriter,
								getPublisher, getStart, getEnd, getText));
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}

	/**
	 * DBレコード更新
	 * 更新に成功 = true /
	 * 更新に失敗 = false
	 */
	public static boolean upDate(String id, String title, String genre,
			String writer, String publisher, String start, String end,
			String text) {

		diff = start.compareTo(end);

		if (comparison(title, start, end)) {
			try {
				// Statementオブジェクト作成
				Statement stmt = createStatement();

				// 更新SQL
				String sqlins = "update test set title = '" + title
						+ "' , genre = '" + genre + "' , writer = '" + writer
						+ "' , publisher = '" + publisher + "' , start = '"
						+ start + "' , end = '" + end + "' , text = '" + text
						+ "' where id = " + id + "";

				stmt.executeUpdate(sqlins);
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * DBレコード削除
	 */
	public static void delete(String id) {
		try {
			// Statementオブジェクト作成
			Statement stmt = createStatement();

			// 登録SQL
			String sqlins = "delete from test where id = " + id + "";

			stmt.executeUpdate(sqlins);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * TableView表示内容更新
	 */
	public static void ViewTable(TableView<View> table,
			TableColumn<View, String> buttonColumn) {

		// テーブルの中身を削除
		table.getItems().clear();

		try {
			buttonColumn.setCellFactory(new OpenerFactory());

			// Statementオブジェクト作成
			Statement stmt = createStatement();

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

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
	}

	/**
	 * DBからid指定でレコードデータ取得
	 *
	 * @return view
	 */
	static View getData(String id) {

		View view = null;

		try {
			// Statementオブジェクト作成
			Statement stmt = createStatement();

			// sql文作成
			String sql = "select * from test where id = " + id + "";

			// sql問合せ
			ResultSet rs = stmt.executeQuery(sql);

			// Detailed表示データ取得
			String title = rs.getString("title");
			String genre = rs.getString("genre");
			String writer = rs.getString("writer");
			String publisher = rs.getString("publisher");
			String start = rs.getString("start");
			String end = rs.getString("end");
			String text = rs.getString("text");

			view = new View(id, title, genre, writer, publisher, start, end,
					text);

			rs.close();
			stmt.close();

			return view;

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}
		return view;
	}

	/**
	 * Statementオブジェクト作成
	 *
	 * @return stmt
	 */
	static Statement createStatement() {
		try {
			// JDBCドライバーの指定
			Class.forName("org.sqlite.JDBC");

			// データベースに接続する なければ作成される
			Connection con = DriverManager
					.getConnection("jdbc:sqlite:src/SQLite/DB");

			// Statementオブジェクト作成
			Statement stmt = con.createStatement();

			return stmt;

		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
			return null;

		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			return null;

		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
			return null;
		}
	}
}
