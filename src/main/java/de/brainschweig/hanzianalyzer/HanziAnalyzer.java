package de.brainschweig.hanzianalyzer;

import java.io.IOException;
import java.util.ArrayList;

public class HanziAnalyzer {

	public static void main(String[] args) throws IOException {

		final String connectionString = System.getenv("DB_CONNECTION_STRING");

		System.out.println("DB_CONNECTION_STRING: " + connectionString);

		if (connectionString == null || connectionString.isBlank()) {
			System.out.println("ERROR: Environement Variable DB_CONNECTION_STRING is empty.");
			System.exit(-1);
		}

		try {
			Database.connect(connectionString);
			ArrayList<String> values = Database.fetchResults();

			values.forEach((n) -> {
				char[] chars = n.toCharArray();
				for (char ch : chars) {
					Database.insertHanzi(Character.toString(ch));
				}
			}

			);

		} catch (Exception ex) {
			System.out.println("Found Unhandled exception" + ex);
		}
	}
}
