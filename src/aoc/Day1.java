package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day1-input.file"));
		part1And2(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1And2(List<String> lines) {
		System.out.println("" + lines.stream().map(x -> (Math.floor(Double.parseDouble(x) / 3) - 2))
				.collect(Collectors.summingDouble(Double::valueOf)));
		double total = 0.;
		for (String line : lines) {
			total += calculateFuel(Double.parseDouble(line));
		}
		System.out.println(total);
	}

	private static double calculateFuel(double parseInt) {
		double total = 0.;
		if (parseInt <= 0.) {
			return 0.;
		} else {
			double fuelNumb = Math.floor(parseInt / 3) - 2;
			if (fuelNumb <= 0.) {
				return 0.;
			}
			total += fuelNumb + calculateFuel(fuelNumb);
		}

		return total;
	}
}
