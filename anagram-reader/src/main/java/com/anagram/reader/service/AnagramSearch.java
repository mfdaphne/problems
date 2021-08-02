package com.anagram.reader.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnagramSearch {

	public static Map<String, List<String>> collectGroupsOfAnagrams(
			String[] input) {

		List<String> words = new ArrayList<>(Arrays.asList(input));

		Map<String, List<String>> anagrams = words.stream()
				.map(a -> a.toLowerCase())
				.collect(Collectors.groupingBy(word -> sortWord(word)));

		return anagrams;
	}

	private static String sortWord(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		return new String(chars);
	}

}
