package readingManagement;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Detailed /*extends AnchorPane*/ implements Initializable {

	/*public Detailed() {

		loadFXML();
	}*/

	/*private void loadFXML() {

		FXMLLoader fxmlLoader = new FXMLLoader(
				Main.class.getResource("detailed.fxml"));
		fxmlLoader.setRoot(this);

		// 自分自身をコントロールとして設定
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}*/

	@FXML
	private Pane root;

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

	public void setStates(String title,String genre,String writer,String publisher,String start,String end,String text){
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

	@FXML
	public void close(){
		root.getScene().getWindow().hide();
	}
}