package readingManagement;

import java.io.IOException;
import java.time.LocalDate;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

class OpenerFactory implements
		Callback<TableColumn<View, String>, TableCell<View, String>> {

	public static Stage stage;

	private Button button;

	@Override
	public TableCell<View, String> call(TableColumn<View, String> param) {
		TableCell<View, String> tableCell = new TableCell<View, String>() {

			private Pane pane = createPane();

			private String id;

			private Pane createPane() {
				HBox pane = new HBox();
				pane.setAlignment(Pos.CENTER);

				button = new Button("詳細を開く");
				button.setDisable(false);
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						openDialog();
					}
				});
				pane.getChildren().add(button);
				return pane;
			}

			//public void onBttonDisable(){button.setDisable(false);}
			//public void offButtonDisable(){button.setDisable(true);}

			@Override
			protected void updateItem(String id, boolean empty) {
				super.updateItem(id, empty);
				if (empty == false) {
					this.id = id;
					setGraphic(pane);
				}
			}

			// Detailedウィンドウ表示
			public void openDialog() {
				DialogController.setParentType(false);
				try {

					View view = DBAccess.getData(id);

					// Detailed表示データ取得
					String title = view.getTitle();
					String genre = view.getGenre();
					String writer = view.getWriter();
					String publisher = view.getPublisher();
					LocalDate start = LocalDate.parse(view.getStart());
					LocalDate end = LocalDate.parse(view.getEnd());
					String text = view.getText();

					FXMLLoader loader = new FXMLLoader(getClass().getResource(
							"detailed.fxml"));
					try {
						loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					Parent root = loader.getRoot();
					Detailed controller = loader.getController();
					controller.setStates(id, title, genre, writer, publisher,
							start, end, text);
					Scene scene = new Scene(root);
					stage = new Stage();

					stage.setTitle("Detailed");
					stage.getIcons().addAll(Main.icon);
					stage.setScene(scene);
					stage.setWidth(518);
					stage.setHeight(280);

					// ダイアログ表示位置調整
					stage.setX(Main.stage.getX()
							+ (Main.stage.getWidth() - stage.getWidth()) / 2);
					stage.setY(Main.stage.getY()
							+ (Main.stage.getHeight() - stage.getHeight()) / 2);

					stage.initOwner(Main.stage);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.showAndWait();

				} catch (Exception e) {
					System.out.println("Exception:" + e.getMessage());
				}
			}

		};
		return tableCell;
	}
}