package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * --- Day 6: Universal Orbit Map ---
 * ChainedNodes
 * 358244
 * 517
 */
public class Day6 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day6-input.file"));
		Map<String, String> mapOrbit = input.stream().map(x -> x.split("\\)"))
				.collect(Collectors.toMap(l -> l[1], l -> l[0]));
		part1(mapOrbit);
		part2(mapOrbit);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(Map<String, String> mapOrbit) {

		System.out.println(mapOrbit.entrySet().stream().mapToInt(x -> calculate(x.getValue(), mapOrbit)).sum());
	}

	private static int calculate(String value, Map<String, String> mapOrbit) {
		return mapOrbit.containsKey(value) ? 1 + calculate(mapOrbit.get(value), mapOrbit) : 1;
	}

	private static String calculatePrevNodes(String value, Map<String, String> mapOrbit) {
		return mapOrbit.containsKey(value) ? calculatePrevNodes(mapOrbit.get(value), mapOrbit) + "," + value : "";
	}

	public static void part2(Map<String, String> mapOrbit) {
		List<String> you = new ArrayList<>(Arrays.asList(calculatePrevNodes("YOU", mapOrbit).substring(1).split(",")));
		List<String> san = new ArrayList<>(Arrays.asList(calculatePrevNodes("SAN", mapOrbit).substring(1).split(",")));
		List<String> diff = new ArrayList<>();
		diff.addAll(you);
		diff.addAll(san);
		you.retainAll(san);
		diff.removeAll(you);
		System.out.println(diff.size() - 2);

	}
}
