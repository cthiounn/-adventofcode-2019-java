package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * --- Day 4: Secure Container ---
 * countDouble/Triple/Sorted
 * 1694
 * 1148
 */
public class Day4 {
	static int i = 0;
	static int j = 0;

	static int sstep = 0;

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day4-input.file"));

		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines) {
		String[] ranges = lines.get(0).split("-");
		int rangeMin = Integer.parseInt(ranges[0]);
		int rangeMax = Integer.parseInt(ranges[1]);
		int count = 0;
		int count2 = 0;
		for (int i = rangeMin; i <= rangeMax; i++) {
			if (test(i, false)) {
				count++;
			}
			if (test(i, true)) {
				count2++;
			}
		}
		System.out.println(count);
		System.out.println(count2);
	}

	private static boolean test(int i, boolean parttwo) {
		boolean increasingNumber = true;
		boolean hasConsecutiveDouble = false;

		String si = i + "";
		for (int j = 0; j < si.length() - 1; j++) {
			if (Integer.parseInt(si.charAt(j) + "") > Integer.parseInt(si.charAt(j + 1) + "")) {
				increasingNumber = false;
				break;
			} else if (Integer.parseInt(si.charAt(j) + "") == Integer.parseInt(si.charAt(j + 1) + "")) {
				if (!parttwo) {
					hasConsecutiveDouble = true;
				} else {
					// further test
					final int jj = j;
					if (si.chars().filter(e -> e == si.charAt(jj)).count() == 2) {
						hasConsecutiveDouble = true;

					}
				}
			}
		}
		return increasingNumber && hasConsecutiveDouble;
	}

}
