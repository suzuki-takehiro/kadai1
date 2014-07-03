package readingManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javafx.scene.control.ComboBox;

public class ComboBoxController {

	/**
	 * ComboBoxプルダウンリスト表示項目追加
	 * List<String> dfaultGenreDate に登録されている基本項目　＋　DBに登録されている項目
	 * @param genreField
	 */

	static void setCBDate(ComboBox<String> genreField) {

		List<String> dfaultGenreDate = Arrays.asList("コミック", "文庫", "教養", "趣味",
				"ビジネス", "コンピュータ");
		List<String> dbGenreDate = DBAccess.getDBGenre();
		ArrayList<String> list = new ArrayList<String>();

		list.addAll(dfaultGenreDate);
		list.addAll(dbGenreDate);
		Arrays.sort(list.toArray());

		TreeSet<String> treeSet = new TreeSet<String>();
		treeSet.addAll(list);

		genreField.setEditable(true);
		genreField.getItems().addAll(treeSet);

	}

}
