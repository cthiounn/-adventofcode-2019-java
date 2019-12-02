package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day2-input.file"));
		// part1(input, 12, 2, false);
		part2(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines, int noun, int verb, boolean search) {
		String myChain = lines.get(0);
		String[] values = myChain.split(",");
		values[1] = noun + "";
		values[2] = verb + "";
		int i = 0;
		while (!calculate(i, i + 1, i + 2, i + 3, values)) {
			i += 4;
		}

		if (!search) {
			for (String s : values) {
				System.out.print(s + ",");
			}
			System.out.println("");
			System.out.println(values[0]);
		} else if ("19690720".equals(values[0])) {
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

	private static boolean calculate(int n1, int n2, int n3, int n4, String[] values) {
		if (Integer.parseInt(values[n1]) == 1) {
			values[Integer.parseInt(values[n4])] = (Integer.parseInt(values[Integer.parseInt(values[n2])])
					+ Integer.parseInt(values[Integer.parseInt(values[n3])])) + "";
		} else if (Integer.parseInt(values[n1]) == 2)

		{
			values[Integer.parseInt(values[n4])] = (Integer.parseInt(values[Integer.parseInt(values[n2])])
					* Integer.parseInt(values[Integer.parseInt(values[n3])])) + "";
		} else if (Integer.parseInt(values[n1]) == 99) {
			return true;
		}
		return false;
	}
}
