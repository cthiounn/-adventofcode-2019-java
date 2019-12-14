package aoc;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --- Day 9: Sensor Boost ---
 * IntCode++ - relative base
 * 3280416268
 * 80210
 */
public class Day9 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day9-input.file"));

		part1(input, BigInteger.valueOf(1));
		part1(input, BigInteger.valueOf(2));
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static BigInteger g(BigInteger i, Map<BigInteger, BigInteger> m) {
		return m.containsKey(i) ? m.get(i) : BigInteger.ZERO;
	}

	public static void part1(List<String> lines, BigInteger beginInput) {
		int[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		Map<BigInteger, BigInteger> m = new HashMap<>();
		for (int i = 0; i < n.length; i++) {
			m.put(BigInteger.valueOf(i), BigInteger.valueOf(n[i]));
		}
		BigInteger i = BigInteger.ZERO;
		BigInteger r2 = BigInteger.ZERO;
		BigInteger r3 = BigInteger.ZERO;
		BigInteger r4 = BigInteger.ZERO;
		BigInteger p1 = BigInteger.ZERO;
		BigInteger p2 = BigInteger.ZERO;
		BigInteger p3 = BigInteger.ZERO;
		String c = "";
		String pp1 = "";
		String pp2 = "";
		String pp3 = "";

		BigInteger rb = BigInteger.ZERO;
		String s = "";
		int l = 0;
		boolean stop = false;
		while (!stop) {
			s = pad(g(i, m) + "");
			l = s.length();
			r2 = g(i.add(BigInteger.ONE), m);
			r3 = g(i.add(BigInteger.ONE).add(BigInteger.ONE), m);
			r4 = g(i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE), m);
			c = s.substring(l - 2, l);
			pp1 = s.substring(l - 3, l - 2);
			pp2 = s.substring(l - 4, l - 3);
			pp3 = s.substring(l - 5, l - 4);
			switch (pp1) {
			case "1":
				p1 = r2;
				break;
			case "2":
				p1 = g(r2.add(rb), m);
				break;
			default:
				p1 = g(r2, m);
				break;
			}
			switch (pp2) {
			case "1":
				p2 = r3;
				break;
			case "2":
				p2 = g(r3.add(rb), m);
				break;
			default:
				p2 = g(r3, m);
				break;
			}
			switch (pp3) {
			case "1":
				p3 = r4;
				break;
			case "2":
				p3 = r4.add(rb);
				break;
			default:
				p3 = r4;
				break;
			}
			if ("01".equals(c)) {
				m.put(p3, p1.add(p2));
				i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("02".equals(c)) {
				m.put(p3, p1.multiply(p2));
				i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("03".equals(c)) {
				p1 = "2".equals(pp1) ? r2.add(rb) : r2;
				m.put(p1, beginInput);
				i = i.add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("04".equals(c)) {
				beginInput = p1;
				i = i.add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("05".equals(c)) {
				if (p1 != BigInteger.ZERO) {
					i = p2;
				} else {
					i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
				}
			} else if ("06".equals(c)) {
				if (p1 == BigInteger.ZERO) {
					i = p2;
				} else {
					i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
				}
			} else if ("07".equals(c)) {
				m.put(p3, p1.compareTo(p2) == -1 ? BigInteger.ONE : BigInteger.ZERO);
				i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("08".equals(c)) {
				m.put(p3, p1.compareTo(p2) == 0 ? BigInteger.ONE : BigInteger.ZERO);
				i = i.add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE).add(BigInteger.ONE);
			} else if ("99".equals(c)) {
				stop = true;
			} else if ("09".equals(c)) {
				rb = rb.add(p1);
				i = i.add(BigInteger.ONE).add(BigInteger.ONE);
			} else {
				System.out.println("? ");
				stop = true;
			}
		}
		System.out.println(beginInput);
	}

	private static String pad(String s) {
		if (s.length() >= 5) {
		} else {
			int l = s.length();
			for (int i = 0; i < 5 - l; i++) {
				s = "0" + s;
			}
		}
		return s;
	}
}
