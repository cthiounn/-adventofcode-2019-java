package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day3 {
	static int i = 0;
	static int j = 0;

	static int sstep = 0;

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day3-input.file"));
		Map<String, String> myMap = new HashMap<String, String>();

		part1(input, myMap);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines, Map<String, String> myMap) {
		for (String st : lines) {
			i = 0;
			j = 0;
			sstep = 0;
			for (String s : st.split(",")) {
				moveAndMark(s, myMap, st.hashCode() + "");
			}

		}

		System.out.println(myMap.entrySet().stream().filter(e -> e.getValue().contains("C"))
				.map(e -> calculateManhattanDistance(e.getKey())).min(Comparator.comparing(Integer::valueOf)).get());

		System.out.println(myMap.entrySet().stream().filter(e -> e.getValue().contains("C"))
				.map(e -> calculateStep(e.getValue().split("!")[1])).min(Comparator.comparing(Integer::valueOf)).get());
	}

	private static int calculateStep(String st) {
		return Integer.parseInt(st.split("C")[0]) + Integer.parseInt(st.split("C")[1]);
	}

	private static void moveAndMark(String s, Map<String, String> myMap, String myLetter) {
		int step = Integer.parseInt(s.substring(1));

		for (int k = 1; k <= step; k++) {
			if (s.startsWith("R")) {
				i += 1;
			} else if (s.startsWith("L")) {
				i -= 1;
			} else if (s.startsWith("U")) {
				j += 1;
			} else if (s.startsWith("D")) {
				j -= 1;
			}

			sstep++;

			if (myMap.containsKey(i + "!" + j) && !myLetter.equals(myMap.get(i + "!" + j).split("!")[0])) {
				myMap.put(i + "!" + j, myMap.get(i + "!" + j) + "C" + sstep);

			} else {
				myMap.put(i + "!" + j, myLetter + "!" + sstep);
			}
		}
	}

	private static int calculateManhattanDistance(String st) {
		String[] coords = st.split("!");
		return Math.abs(Integer.parseInt(coords[0])) + Math.abs(Integer.parseInt(coords[1]));
	}

}
