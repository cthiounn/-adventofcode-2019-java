package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --- Day 16: Flawed Frequency Transmission --- repeated pattern // triangular
 * matrix 25131128 53201602
 */

public class Day16 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day16-input.file"));
		part1(input.get(0));
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(String line) {
		int[] pattern = new int[] { 0, 1, 0, -1 };
		Map<Integer, int[]> mapOfPatterns = new HashMap<>();
		for (int i = 0; i < line.length(); i++) {
			mapOfPatterns.put(i, grow(i + 1, pattern));
		}
		int[] l = line.chars().map(e -> Integer.parseInt((char) e + "")).toArray();

		int phases = 0;
		while (phases < 100) {
			int[] m = new int[l.length];
			for (int i = 0; i < l.length; i++) {
				int total = 0;
				int[] patternToApply = mapOfPatterns.get(i);
				for (int j = 0; j < l.length; j++) {
					total += (l[j] * patternToApply[(j + 1) % patternToApply.length]);
				}

				m[i] = Math.abs(total) % 10;
			}
			l = m.clone();
			phases++;
		}
		System.out.println(l[0] + "" + l[1] + "" + l[2] + "" + l[3] + "" + l[4] + "" + l[5] + "" + l[6] + "" + l[7]);

		l = line.chars().map(e -> Integer.parseInt((char) e + "")).toArray();
		int[] l2 = new int[l.length * 10000];
		for (int i = 0; i < 10000; i++) {
			for (int k = 0; k < l.length; k++) {
				l2[k + i * l.length] = l[k];
			}
		}
		int offset = Integer.parseInt(l[0] + "" + l[1] + "" + l[2] + "" + l[3] + "" + l[4] + "" + l[5] + "" + l[6]);

		phases = 0;
		while (phases < 100) {
			long total = Arrays.stream(Arrays.copyOfRange(l2, offset, l2.length)).sum();
			for (int j = offset; j < l2.length; j++) {
				long totforj = total;
				total -= l2[j];
				String t = (totforj + "");
				l2[j] = Integer.parseInt(t.substring(t.length() - 1));
			}
			phases++;
		}

		System.out.println(l2[offset + 0] + "" + l2[offset + 1] + "" + l2[offset + 2] + "" + l2[offset + 3] + ""
				+ l2[offset + 4] + "" + l2[offset + 5] + "" + l2[offset + 6] + "" + l2[offset + 7]);

	}

	private static int[] grow(int i, int[] pattern) {
		if (i == 1) {
			return pattern;
		} else {
			int[] n = new int[i * pattern.length];
			for (int j = 0; j < pattern.length; j++) {
				for (int j2 = 0; j2 < i; j2++) {
					n[j * i + j2] = pattern[j];
				}
			}
			return n;
		}

	}
}