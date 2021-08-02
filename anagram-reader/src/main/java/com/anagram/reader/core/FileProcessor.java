package com.anagram.reader.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public class FileProcessor {

	private String fileContent;

	private static final String OUTPUT_FILENAME = "anagram.txt";

	public String[] getFileContent(String filePath)
			throws FileNotFoundException, IOException {

		File file = new File(filePath);

		try (FileReader fileReader = new FileReader(file)) {
			fileContent = readAllCharactersOneByOne(fileReader);
		}

		String[] fileContentArray = fileContent.split("\n");
		return fileContentArray;
	}

	private String readAllCharactersOneByOne(Reader reader) throws IOException {
		StringBuilder content = new StringBuilder();
		int nextChar;
		while ((nextChar = reader.read()) != -1) {
			content.append((char) nextChar);
		}
		return String.valueOf(content);
	}

	public void writeToFile(Map<String, List<String>> anagrams,
			String outputPath) throws IOException {

		File myObj = new File(outputPath + OUTPUT_FILENAME);
		FileWriter myWriter = new FileWriter(myObj);
		anagrams.forEach((a, b) -> {
			try {
				myWriter.write(b.toString() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		myWriter.close();

	}

}
