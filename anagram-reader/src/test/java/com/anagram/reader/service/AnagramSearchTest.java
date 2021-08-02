package com.anagram.reader.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AnagramSearchTest {

	@Test
	public void shouldCollectAnagramsInGroups_whenArrayHasAnagrams() {
		String[] testWords = { "Tree", "Race", "Bee", "Care" };

		Map<String, List<String>> anagrams = AnagramSearch
				.collectGroupsOfAnagrams(testWords);
		assertThat(anagrams.values())
				.anyMatch(a -> a.containsAll(Arrays.asList("race", "care")));
		assertThat(anagrams.values()).size().isEqualTo(3);
	}

	@Test
	public void shouldCollectAnagramsInSingleGroup_whenWordsAreInDiffCases() {
		String[] testWords = { "TREE", "Tree", "eeRT", "ReeT" };

		Map<String, List<String>> anagrams = AnagramSearch
				.collectGroupsOfAnagrams(testWords);
		assertThat(anagrams.values()).size().isEqualTo(1);

	}

}
