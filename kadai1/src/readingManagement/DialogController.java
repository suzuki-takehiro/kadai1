package readingManagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogController implements Initializable {

	enum DialogType {
		periodError, shortageError, edit, delete, confirmationEdit, confirmationDelete;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	/**
	 * ダイアログ表示 引数 periodError, shortageError, edit, delete, confirmationEdit,
	 * confirmationDelete;
	 */
	public static void showDialog(DialogType type) {

		FXMLLoader loader = new FXMLLoader(
				Register.class.getResource("Dialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();

		switch (type) {
		case periodError:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("期間が正しくありません。");
			Dialog.getInstance().visibleButton();
			break;

		case shortageError:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("入力項目が正しくありません。");
			Dialog.getInstance().visibleButton();
			break;

		case edit:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("変更しました。");
			Dialog.getInstance().visibleButton();
			break;

		case delete:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("削除しました。");
			Dialog.getInstance().visibleButton();
			break;

		case confirmationEdit:
			stage2.setTitle("confirmation");
			Dialog.getInstance().setLabelText("変更してもよろしいですか？");
			break;

		case confirmationDelete:
			stage2.setTitle("confirmation");
			Dialog.getInstance().setLabelText("削除してもよろしいですか？");
			break;

		default:
		}

		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.getIcons().addAll(Main.icon);
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
	}
}
