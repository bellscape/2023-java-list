package demo;

import experiment.ListX;

@SuppressWarnings("Convert2MethodRef")
public class WordCount {
	public static void main(String[] args) {

		String sentence = "The quick brown fox jumps over the lazy dog.";
		String[] words = sentence.split("\\W+");

		ListX.fromArray(words)
				.filterNot(w -> w.isEmpty())
				.map(w -> w.toLowerCase())
				.groupBy(w -> w)
				.mapValues(list -> list.size())
				.forEach((k, v) -> System.out.println(v + "\t" + k));

	}
}
