package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day8 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day8-input.file"));
		doPart1(input, 25, 6);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	private static void doPart1(List<String> input, int wide, int tall) {
		List<String> lines = Arrays.asList(input.get(0).split("(?<=\\G.{" + (wide * tall) + "})"));
		String line = lines.stream().min(Comparator.comparing(Day8::numberZero)).get();
		System.out.println(numberChar(line, '1') * numberChar(line, '2'));

		String pattern = lines.stream().reduce((a, b) -> xor(a, b)).get();
		String[] linesPattern = pattern.split("(?<=\\G.{" + wide + "})");
		for (String s : linesPattern) {
			System.out.println(s.replace("1", "O").replace("0", " "));
		}
	}

	private static int numberZero(String s) {
		return (int) s.chars().filter(e -> '0' == e).count();
	}

	private static int numberChar(String s, char a) {
		return (int) s.chars().filter(e -> a == e).count();
	}

	private static String xor(String s, String s2) {
		String ret = "";
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '0':
				ret += "0";
				break;
			case '1':
				ret += "1";
				break;
			default:
				ret += s2.charAt(i);
				break;
			}
		}
		return ret;
	}
}
