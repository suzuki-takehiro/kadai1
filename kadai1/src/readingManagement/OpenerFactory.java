package readingManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

class OpenerFactory implements
Callback<TableColumn<View, String>, TableCell<View, String>> {

@Override
public TableCell<View, String> call(TableColumn<View, String> param) {
TableCell<View, String> tableCell = new TableCell<View, String>() {

	private Pane pane = createPane();

	private String id;

	private Pane createPane() {
		HBox pane = new HBox();
		pane.setAlignment(Pos.CENTER);

		Button button = new Button("詳細を開く");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					// JDBCドライバーの指定
					Class.forName("org.sqlite.JDBC");

					// データベースに接続する なければ作成される
					Connection con = DriverManager
							.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

					// Statementオブジェクト作成
					Statement stmt = con.createStatement();

					// sql文作成
					String sql = "select * from test where id = " + id
							+ "";

					// sql問合せ
					ResultSet rs = stmt.executeQuery(sql);

					// データ表示
					// while (rs.next()) {
					String title = rs.getString("title");
					String genre = rs.getString("genre");
					String writer = rs.getString("writer");
					String publisher = rs.getString("publisher");
					String start = rs.getString("start");
					String end = rs.getString("end");
					String text = rs.getString("text");
					/*System.out.println(id + title + genre + writer
							+ publisher + start + end + text);*/
					// }

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
					controller.setStates(title, genre, writer,
							publisher, start, end, text);
					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setTitle("confirmation");
					stage.setScene(scene);
					stage.setWidth(540);
					stage.setHeight(480);
					stage.showAndWait();
					//System.out.println(id);

					rs.close();
					stmt.close();

				} catch (ClassNotFoundException e) {
					System.out.println("ClassNotFoundException:"
							+ e.getMessage());
				} catch (SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
				} catch (Exception e) {
					System.out.println("Exception:" + e.getMessage());
				}

			}
		});
		pane.getChildren().add(button);
		return pane;
	}

	@Override
	protected void updateItem(String id, boolean empty) {
		super.updateItem(id, empty);
		if (empty == false) {
			this.id = id;
			setGraphic(pane);
		}
	}
};
return tableCell;
}
}