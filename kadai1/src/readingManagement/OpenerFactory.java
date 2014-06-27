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
						openDialog();
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

			public void openDialog() {
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

					// Detailedウィンドウ表示
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
					Stage stage = new Stage();

					stage.setTitle("Detailed");
					stage.getIcons().addAll(Main.icon);
					stage.setScene(scene);
					stage.setWidth(540);
					stage.setHeight(280);
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