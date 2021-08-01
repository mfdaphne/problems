package com.anagram.reader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.anagram.reader.core.FileProcessor;
import com.anagram.reader.service.AnagramSearch;

public class AnagramProcessor {

	public static void main(String[] args) throws IOException {

		FileProcessor fileProcessor = new FileProcessor();

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter file path : ");
			String filePath = scanner.next();

			String[] words = fileProcessor.getFileContent(filePath);

			Map<String, List<String>> anagrams = AnagramSearch
					.collectGroupsOfAnagrams(words);

			System.out.println("Enter the file Path to write the result : ");
			String outputPath = scanner.next();

			fileProcessor.writeToFile(anagrams, outputPath);

			System.out
					.println("---------OUTPUT HAS BEEN WRITTEN SUCCESSFULLY AT "
							+ outputPath + "anagram.txt" + "----------");

		}

	}

}
