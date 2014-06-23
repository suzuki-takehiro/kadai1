package readingManagement;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewCon implements Initializable {

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

	@Override
	public void initialize(URL url, ResourceBundle rb){

		   //ViewクラスとViewConクラスの対応付け
			idColumn.setCellValueFactory(new PropertyValueFactory<View, String>("id"));
			titleColumn.setCellValueFactory(new PropertyValueFactory<View, String>("title"));
			genreColumn.setCellValueFactory(new PropertyValueFactory<View, String>("genre"));
			writerColumn.setCellValueFactory(new PropertyValueFactory<View, String>("writer"));
			publisherColumn.setCellValueFactory(new PropertyValueFactory<View, String>("publisher"));
			startColumn.setCellValueFactory(new PropertyValueFactory<View, String>("start"));
			endColumn.setCellValueFactory(new PropertyValueFactory<View, String>("end"));
			textColumn.setCellValueFactory(new PropertyValueFactory<View, String>("text"));

		try {
			   // JDBCドライバーの指定
			   Class.forName("org.sqlite.JDBC");

			   // データベースに接続する なければ作成される
			   Connection con = DriverManager.getConnection("jdbc:sqlite:C:/SQLiteDB/test");

			   // Statementオブジェクト作成
			   Statement stmt = con.createStatement();

			   //sql文作成
			   String sql = "select * from test";

			   //sql問合せ
			   ResultSet rs = stmt.executeQuery(sql);

			   //データ表示
			   while(rs.next()){
				   String id = rs.getString("id");
				   String title = rs.getString("tetle");
				   String genre = rs.getString("genre");
				   String writer = rs.getString("writer");
				   String publisher = rs.getString("publisher");
				   String start = rs.getString("start");
				   String end = rs.getString("end");
				   String text = rs.getString("text");
				   System.out.println( id + title + genre + writer + publisher + start + end + text );
				   table.getItems().add(new View (id,title,genre,writer,publisher,start,end,text));

			   }

			      rs.close();
			      stmt.close();

			  } catch (ClassNotFoundException e){
				  System.out.println("ClassNotFoundException:" + e.getMessage());
			    }catch (SQLException e){
			    	System.out.println("SQLException:" + e.getMessage());
			    }catch (Exception e){
			    	System.out.println("Exception:" + e.getMessage());
			  }



	}

}
