package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

/**
 * 特定のパス配下のフォルダをランダムで開くPG
 *
 */
public class Test {

	public static void main(String[] args) {

		// 対象のパスを入力するためのポップアップを表示
		String targetPath = JOptionPane.showInputDialog("対象のパスを入力してください。");

		// 対象のキーワードを入力するためのポップアップを表示
		String targetKeyword = JOptionPane.showInputDialog("検索したいキーワードがある場合、入力してください。");

		// 選択されたフォルダを表示
		openFolder(targetPath, targetKeyword);
	}

	/**
	 * フォルダ表示処理
	 * @param targetPath 検索対象のパス
	 * @param targetKeyword 検索対象のキーワード
	 */
	private static void openFolder(String targetPath, String targetKeyword) {

		File selectFolder = null;
		File folder = new File(targetPath);

		// 検索対象のキーワードが入力された場合
		if (!targetKeyword.isEmpty()) {

			List<File> fileList = new ArrayList<File>();
			Path dirpath = Paths.get(targetPath);

			// 検索対象のキーワードを含むファイルのパスを取得
			try (Stream<Path> stream = Files.walk(dirpath, 3)) {
				stream.forEach(path -> {
					if (!path.toFile().isDirectory() && path.toString().contains(targetKeyword)) {
						// ファイルが含まれるフォルダのパスをリストに格納
						fileList.add(path.getParent().toFile());
					}
				});
			} catch (IOException e) {
				System.out.println(e);
			}

			// 対象のファイルが格納されたフォルダをランダムで選択
			Collections.shuffle(fileList);
			selectFolder = fileList.get(0);

			//検索対象のキーワードが入力されなかった場合
		} else {

			//対象パス内の最下層フォルダをランダムで選択
			selectFolder = selectFolder(folder);
		}

		// 選択された最下層フォルダを開く
		Desktop desktop = Desktop.getDesktop();

		try {
			desktop.open(selectFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最下層フォルダ選択処理
	 * @param folder 検索対象のフォルダ
	 * @return 選択された最下層フォルダ
	 */
	private static File selectFolder(File folder) {

		// 最下層フォルダに到達するまでループ
		while (folder.isDirectory()) {

			File[] subFolder = folder.listFiles();
			shuffleList(subFolder);

			for (File f : subFolder) {

				if (f.isDirectory()) {
					folder = f;
					break;

				} else {
					return folder;
				}
			}
		}
		return folder;
	}

	/**
	 * 対象パス配下フォルダランダム並び替え処理
	 * @param list 対象パスは以下のフォルダ
	 */
	private static void shuffleList(File[] list) {

		int index;
		File temp;
		Random random = new Random();

		for (int i = list.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = list[index];
			list[index] = list[i];
			list[i] = temp;
		}
	}
}
