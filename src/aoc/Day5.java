package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day5 {
	static int input = 1;

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day5-input.file"));

//		part1(input, 1);
//		System.out.println(input);
		part1(input, 5);
		System.out.println(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines, int beginInput) {
		int[] numbers = Arrays.asList(lines.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		input = beginInput;
		int i = 0;
		int value2 = 0;
		int value3 = 0;
		boolean stop = false;
		while (!stop) {
			System.out.println("---------------");
			System.out.println("i=" + i);
			System.out.println(
					"c=" + numbers[i] + ";" + numbers[i + 1] + ";" + numbers[i + 2] + ";" + numbers[i + 3] + ";");
			String command = pad(numbers[i]);
			String param1 = command.substring(2, 3);
			if ("1".equals(param1)) {
				value2 = numbers[i + 1];

				System.out.println("direct p1=" + value2);
			} else if ("0".equals(param1) && command.endsWith("03")) {

				value2 = numbers[i + 1];
			} else {

				value2 = numbers[numbers[i + 1]];
				System.out.println("passive p1=" + value2);
			}
			String param2 = command.substring(1, 2);
			if ("1".equals(param2)) {
				value3 = numbers[i + 2];
				System.out.println("direct p2=" + value3);
			} else if ("0".equals(param1) && (command.endsWith("03") || command.endsWith("04"))) {
				value3 = 0;

				System.out.println("ignore p2=" + value3);
			} else {

				value3 = numbers[numbers[i + 2]];
				System.out.println("passive p2=" + value2);
			}
			String param3 = command.substring(0, 1);
			if (command.endsWith("01")) {
				System.out.println("add");
				System.out.println("value2=" + value2);
				System.out.println("value3=" + value3);

				System.out.println("b=" + numbers[numbers[i + 3]]);
				calculate(1, value2, value3, numbers[i + 3], numbers);

				System.out.println("a=" + numbers[numbers[i + 3]]);
				i += 4;
			} else if (command.endsWith("02")) {

				System.out.println("mult");

				System.out.println("value2=" + value2);
				System.out.println("value3=" + value3);

				System.out.println("b=" + numbers[numbers[i + 3]]);
				calculate(2, value2, value3, numbers[i + 3], numbers);

				System.out.println("a=" + numbers[numbers[i + 3]]);
				i += 4;
			} else if (command.endsWith("03")) {

				System.out.println("write input");
				calculate(3, value2, 0, 0, numbers);
				i += 2;
			} else if (command.endsWith("04")) {

				System.out.println("store input");
				calculate(4, value2, 0, 0, null);
				i += 2;
			} else if (command.endsWith("05")) {
				if (calculate(5, value2, 0, 0, null)) {
					i = value3;
				} else {
					i += 3;
				}
			} else if (command.endsWith("06")) {

				if (calculate(6, value2, 0, 0, null)) {
					i = value3;
				} else {
					i += 3;
				}
			} else if (command.endsWith("07")) {
				calculate(7, value2, value3, numbers[i + 3], numbers);
				i += 4;
			} else if (command.endsWith("08")) {
				calculate(8, value2, value3, numbers[i + 3], numbers);
				i += 4;
			} else if (command.endsWith("99")) {
				stop = true;
			} else {
				System.out.println("? " + command);
				stop = true;
			}
			System.out.println(input);

		}

	}

	private static String pad(int i) {
		String st = i + "";
		String ret = st;
		for (int j = 0; j < 5 - st.length(); j++) {
			ret = "0" + ret;
		}
		System.out.println("ret=" + ret);
		return ret;
	}

	private static boolean calculate(int value1, int value2, int value3, int value4, int[] numbers) {
		if (value1 == 1) {
			numbers[value4] = value2 + value3;
		} else if (value1 == 2) {
			numbers[value4] = value2 * value3;
		} else if (value1 == 3) {
			numbers[value2] = input;
		} else if (value1 == 4) {
			input = value2;
		} else if (value1 == 7) {
			numbers[value4] = value2 < value3 ? 1 : 0;
		} else if (value1 == 8) {
			numbers[value4] = value2 == value3 ? 1 : 0;
		} else if (value1 == 99) {
			return true;
		} else if (value1 == 5) {
			return value2 != 0;
		} else if (value1 == 6) {
			return value2 == 0;
		}
		return false;
	}
}
