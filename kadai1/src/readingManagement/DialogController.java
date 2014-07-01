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
		titleError, periodError, shortageError, edit, delete, regist, confirmationEdit, confirmationDelete;
	}

	/**
	 * Dialog表示元判別フラグ 表示元Regiter = true / 表示元Detailed = false
	 */
	static boolean ParentType;

	public static void setParentType(boolean type) {
		ParentType = type;
	};

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	/**
	 * ダイアログ表示 引数 itleError, periodError, shortageError, edit, delete, regist,
	 * confirmationEdit, confirmationDelete;
	 */
	public static void showDialog(DialogType type) {

		FXMLLoader loader = new FXMLLoader(
				Register.class.getResource("dialog.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		Stage stage2 = new Stage();

		switch (type) {
		case titleError:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("タイトルが正しくありません。");
			Dialog.getInstance().visibleButton();
			break;

		case periodError:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("期間が正しくありません。");
			Dialog.getInstance().visibleButton();
			break;

		case shortageError:
			stage2.setTitle("Error");
			Dialog.getInstance().setLabelText("必須項目が入力されていません。");
			Dialog.getInstance().visibleButton();
			break;

		case regist:
			stage2.setTitle("confirmation");
			Dialog.getInstance().setLabelText("登録しました。");
			Dialog.getInstance().visibleButton();
			break;

		case edit:
			stage2.setTitle("confirmation");
			Dialog.getInstance().setLabelText("変更しました。");
			Dialog.getInstance().visibleButton();
			break;

		case delete:
			stage2.setTitle("confirmation");
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

		stage2.setWidth(270);
		stage2.setHeight(90);

		// ダイアログ表示位置調整
		if (ParentType) {
			stage2.setX(Main.stage.getX()
					+ (Main.stage.getWidth() - stage2.getWidth()) / 2);
			stage2.setY(Main.stage.getY()
					+ (Main.stage.getHeight() - stage2.getHeight()) / 2);
		} else {
			stage2.setX(OpenerFactory.stage.getX()
					+ (OpenerFactory.stage.getWidth() - stage2.getWidth()) / 2);
			stage2.setY(OpenerFactory.stage.getY()
					+ (OpenerFactory.stage.getHeight() - stage2.getHeight())
					/ 2);
		}

		stage2.initOwner(Main.stage);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.getIcons().addAll(Main.icon);
		stage2.setScene(scene);
		stage2.setResizable(false);
		stage2.showAndWait();
	}
}