package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day5 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day5-input.file"));

		part1(input, 1);
		part1(input, 5);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines, int beginInput) {
		int[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		int i = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int p1 = 0;
		int p2 = 0;
		int c = 0;
		boolean stop = false;
		while (!stop) {
			r2 = i + 1 < n.length ? n[i + 1] : 0;
			r3 = i + 2 < n.length ? n[i + 2] : 0;
			r4 = i + 3 < n.length ? n[i + 3] : 0;
			c = n[i] % 10;
			p1 = n[i] % 1000 >= 100 || c == 3 || c == 9 ? r2 : n[r2];
			p2 = n[i] % 10000 >= 1000 ? r3 : c == 3 || c == 4 || c == 9 ? 0 : n[r3];
			if (c == 1) {
				n[r4] = p1 + p2;
				i += 4;
			} else if (c == 2) {
				n[r4] = p1 * p2;
				i += 4;
			} else if (c == 3) {
				n[r2] = beginInput;
				i += 2;
			} else if (c == 4) {
				beginInput = p1;
				i += 2;
			} else if (c == 5) {
				if (p1 != 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 6) {
				if (p1 == 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 7) {
				n[r4] = p1 < p2 ? 1 : 0;
				i += 4;
			} else if (c == 8) {
				n[r4] = p1 == p2 ? 1 : 0;
				i += 4;
			} else if (c == 9) {
				stop = true;
			} else {
				System.out.println("? " + n[i]);
				stop = true;
			}
		}
		System.out.println(beginInput);
	}
}
