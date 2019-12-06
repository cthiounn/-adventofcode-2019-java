package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		int i = 0;
		for (String s : mapOrbit.keySet()) {
			String previous = s;
			while (true) {
				if ("COM".equals(previous)) {
					break;
				}
				previous = mapOrbit.get(previous);
				i++;
			}

		}
		System.out.println(i);
	}

	public static void part2(Map<String, String> mapOrbit) {
		List<String> previousOrb = new ArrayList<>();
		String previousY = mapOrbit.get("YOU");
		String previousS = mapOrbit.get("SAN");
		List<String> previousOrbY = new ArrayList<>();
		List<String> previousOrbS = new ArrayList<>();
		String commonNode = "";
		while (true) {
			if (previousOrb.contains(previousS)) {
				previousOrbS.add(previousS);
				commonNode = previousS;
				break;
			} else {
				previousOrb.add(previousS);
				previousOrbS.add(previousS);
				previousS = mapOrbit.get(previousS);
			}
			if (previousOrb.contains(previousY)) {
				previousOrbY.add(previousY);
				commonNode = previousY;
				break;
			} else {
				previousOrb.add(previousY);
				previousOrbY.add(previousY);
				previousY = mapOrbit.get(previousY);
			}
		}
		System.out.println(previousOrbS.indexOf(commonNode) + previousOrbY.indexOf(commonNode));
	}
}
