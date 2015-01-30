/*
 * Calculate the rank of user, that mean the interactive of user with the post
 */
package vnu.uet.data.rank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserRank {
	public void rankCalculator(Map<String, Integer> map, String typeData)
			throws IOException {
		int number;
		int coefficient = 1;
		if (typeData == "commented") {
			coefficient = 2;
		}
		if (typeData == "shared") {
			coefficient = 3;
		}
		for (number = 1; number <= 60; number++) {
			FileReader fread = new FileReader("data/" + typeData + "_"
					+ "user_" + number + ".txt");
			BufferedReader read = new BufferedReader(fread);
			while (true) {
				String userName = read.readLine();
				if (userName == null || userName == "")
					break;
				if (!map.containsKey(userName)) {
					map.put(userName, coefficient);
				} else {
					int newValue = map.get(userName) + coefficient;
					map.put(userName, newValue);
				}
			}
			read.close();
			fread.close();
		}

	}

	public static void main(String args[]) throws IOException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		UserRank userRank = new UserRank();
		userRank.rankCalculator(map, "like");
		userRank.rankCalculator(map, "commented");
		userRank.rankCalculator(map, "shared");
		// System.out.println(map);

		File dir = new File("rank");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File rank = new File("rank/user_rank.txt");
		if (!rank.exists()) {
			rank.createNewFile();
		}
		FileWriter fwriter = new FileWriter(rank);
		BufferedWriter writer = new BufferedWriter(fwriter);
		writer.write(map.toString());
		writer.close();
		fwriter.close();
	}
}
