package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day2 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day2-input.file"));
		part1(input, 12, 2, false);
		part2(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines, int noun, int verb, boolean search) {
		int[] numbers = Arrays.asList(lines.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		numbers[1] = noun;
		numbers[2] = verb;
		int i = 0;
		while (!calculate(i, numbers)) {
			i += 4;
		}

		if (!search) {
			System.out.println(numbers[0]);
		} else if (19690720 == (numbers[0])) {
			System.out.println("" + (100 * noun + verb));
		}
	}

	public static void part2(List<String> lines) {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				part1(lines, i, j, true);
			}
		}
	}

	private static boolean calculate(int i, int[] values) {
		if (values[i] == 1) {
			values[values[i + 3]] = values[values[i + 1]] + values[values[i + 2]];
		} else if (values[i] == 2) {
			values[values[i + 3]] = values[values[i + 1]] * values[values[i + 2]];
		} else if (values[i] == 99) {
			return true;
		}
		return false;
	}
}
