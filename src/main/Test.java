package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

public class Test {

	// 余力あれば以下も実装
	// ・キーワード検索
	// ・認証機能追加（DB接続）
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		// 対象のパスを入力するためのポップアップを表示
		String targetPath = JOptionPane.showInputDialog("対象のパスを入力してください");

		//対象パス内の最下層フォルダをランダムで選択
		File folder = new File(targetPath);
		File selectFolder = selectFolder(folder);
		System.out.println(selectFolder.toString());

		// ランダムで選択された最下層フォルダを開く
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(selectFolder);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// 最下層フォルダをランダムで選択
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

	// 対象パス内のフォルダをランダムに並び替え
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
