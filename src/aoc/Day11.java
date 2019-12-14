package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * --- Day 11: Space Police ---
 * Intcode / Roboto
 * 1883
 * APUGURFH
 */

public class Day11 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day11-input.file"));
		part1(input, 0L);
		part1(input, 1L);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static long g(long i, Map<Long, Long> m) {
		return m.containsKey(i) ? m.get(i) : 0L;
	}

	public static void part1(List<String> lines, long beginInput) {
		long[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToLong(Long::parseLong).toArray();
		Map<Long, Long> m = new HashMap<>();
		for (int i = 0; i < n.length; i++) {
			m.put((long)i, n[i]);
		}
		long i = 0L;
		long r2 = 0L;
		long r3 = 0L;
		long r4 = 0L;
		long p1 = 0L;
		long p2 = 0L;
		long p3 = 0L;
		String c = "";
		String pp1 = "";
		String pp2 = "";
		String pp3 = "";

		long rb = 0L;
		String s = "";
		int l = 0;
		boolean stop = false;
		LinkedList<Long> inps = new LinkedList<>();
		LinkedList<Long> outs = new LinkedList<>();
		long color = 0L;
		long steer = 0L;
		int direction = 0;
		int x = 0;
		int y = 0;
		SimpleEntry<Integer, Integer> coord = new SimpleEntry<>(x, y);
		Map<SimpleEntry<Integer, Integer>, Integer> m2 = new HashMap<>();
		inps.add(beginInput);
		boolean globalStop = false;
		int countFirstPaint = 0; 
		while (!globalStop) {
			stop=false;
			while (!stop) {
				s = pad(g(i, m) + "");
				l = s.length();
				r2 = g(i+1, m);
				r3 = g(i+2, m);
				r4 = g(i+3, m);
				c = s.substring(l - 2, l);
				pp1 = s.substring(l - 3, l - 2);
				pp2 = s.substring(l - 4, l - 3);
				pp3 = s.substring(l - 5, l - 4); 
				switch (pp1) {
				case "1":
					p1 = r2;
					break;
				case "2":
					p1 = g(r2+rb, m);
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
					p2 = g(r3+rb, m);
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
					p3 = r4+rb;
					break;
				default:
					p3 = r4;
					break;
				}
				if ("01".equals(c)) {
					m.put(p3, p1+p2);
					i = i+4;
				} else if ("02".equals(c)) {
					m.put(p3, p1*p2);
					i = i+4;
				} else if ("03".equals(c)) {
					p1 = "2".equals(pp1) ? r2+rb : r2;
					m.put(p1, inps.pop());
					i = i+2;
				} else if ("04".equals(c)) {
					outs.add(p1);
					i = i+2;
					if (outs.size() == 2) {
						break;
					}
				} else if ("05".equals(c)) {
					if (p1 != 0L) {
						i = p2;
					} else {
						i = i+3;
					}
				} else if ("06".equals(c)) {
					if (p1 == 0L) {
						i = p2;
					} else {
						i = i+3;
					}
				} else if ("07".equals(c)) {
					m.put(p3, p1<p2   ? 1L : 0L);
					i = i+4;
				} else if ("08".equals(c)) {
					m.put(p3, p1==p2 ? 1L : 0L);
					i = i+4;
				} else if ("99".equals(c)) {
					globalStop = true;
					break;
				} else if ("09".equals(c)) {
					rb = rb+p1;
					i = i+2;
				} else {
					System.out.println("? ");
					globalStop = true;
					break;
				}
			}
			if (outs.size() < 2) {
				globalStop = true;
				break;
			}
			color = outs.poll();
			steer = outs.poll();
			
			if (!m2.containsKey(coord)) {
				countFirstPaint++;
			}
			m2.put(coord, Integer.parseInt(color + ""));

			int increment = steer == 1L ? 1 : -1;
			direction = Math.floorMod(direction + increment,4);
			
			if (direction == 0) {
				y--;
			} else if (direction == 1) {
				x++;
			} else if (direction == 2) {
				y++;
			} else if (direction == 3) {
				x--;
			}
			
			coord = new SimpleEntry<>(x, y);
			Long next = m2.containsKey(coord) && m2.get(coord) == 1 ? 1L : 0L;
			inps.add(next);
		}

		if (beginInput == 0L) {
			System.out.println(countFirstPaint);
		} else {
			printmap(m2);
		}

	}

	private static void printmap(Map<SimpleEntry<Integer, Integer>, Integer> m2) {
		Set<SimpleEntry<Integer, Integer>> keys = m2.keySet();
		int xmax = keys.stream().map(e -> e.getKey()).max(Comparator.comparing(Integer::valueOf)).get();
		int xmin = keys.stream().map(e -> e.getKey()).min(Comparator.comparing(Integer::valueOf)).get();
		int ymax = keys.stream().map(e -> e.getValue()).max(Comparator.comparing(Integer::valueOf)).get();
		int ymin = keys.stream().map(e -> e.getValue()).min(Comparator.comparing(Integer::valueOf)).get();

		SimpleEntry<Integer, Integer> se;
		for (int j = ymin - 1; j < ymax + 2; j++) {
			String c = "";
			for (int i = xmin - 1; i < xmax + 2; i++) {
				se = new SimpleEntry<>(i, j);
				c += m2.containsKey(se) && m2.get(se) == 1 ? "O" : " ";

			}
			System.out.println(c);

		}
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