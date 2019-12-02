package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day1-input.file"));
		part1And2(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1And2(List<String> lines) {
		System.out.println("" + lines.stream().mapToInt(x -> Integer.parseInt(x) / 3 - 2).sum());
		int total = 0;
		for (String line : lines) {
			total += calculateFuel(Integer.parseInt(line));
		}
		System.out.println(total);
	}

	private static int calculateFuel(int parseInt) {
		int total = 0;
		int fuelNumb = parseInt / 3 - 2;
		if (fuelNumb <= 0) {
			return 0;
		}
		total += fuelNumb + calculateFuel(fuelNumb);
		return total;
	}
}
