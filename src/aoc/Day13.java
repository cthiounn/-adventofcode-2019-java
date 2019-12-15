package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * --- Day 13: Care Package ---
 * intCode // ball-brick game 
 * 284 
 * 13581
 */

public class Day13 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day13-input.file"));
		part1(input, 0L, false);
		part1(input, 2L, true);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static long g(long i, Map<Long, Long> m) {
		return m.containsKey(i) ? m.get(i) : 0L;
	}

	public static void part1(List<String> lines, long beginInput, boolean part2) {
		long[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToLong(Long::parseLong).toArray();
		Map<Long, Long> m = new HashMap<>();
		for (int i = 0; i < n.length; i++) {
			m.put((long) i, n[i]);
		}
		if (part2) {
			m.put(0L, beginInput); 
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

		List<Long> scores = new ArrayList<Long>();
		long rb = 0L;
		String s = "";
		int l = 0;
		boolean stop = false;
		LinkedList<Long> inps = new LinkedList<>();
		LinkedList<Long> outs = new LinkedList<>();
		long x = 0L;
		long y = 0L;
		long tileid = 0L;
		long posball = 0L;
		long pospaddle = 0L;
		Map<SimpleEntry<Long, Long>, Long> m2 = new HashMap<>();
		boolean globalStop = false;
		int step = 0;
		while (!globalStop) {
			step++;
			stop = false;
			while (!stop) {
				s = pad(g(i, m) + "");
				l = s.length();
				r2 = g(i + 1, m);
				r3 = g(i + 2, m);
				r4 = g(i + 3, m);
				c = s.substring(l - 2, l);
				pp1 = s.substring(l - 3, l - 2);
				pp2 = s.substring(l - 4, l - 3);
				pp3 = s.substring(l - 5, l - 4);


				switch (pp1) {
				case "1":
					p1 = r2;
					break;
				case "2":
					p1 = g(r2 + rb, m);
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
					p2 = g(r3 + rb, m);
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
					p3 = r4 + rb;
					break;
				default:
					p3 = r4;
					break;
				}
				 
				if ("01".equals(c)) {
					m.put(p3, p1 + p2);
					i = i + 4;
				} else if ("02".equals(c)) {
					m.put(p3, p1 * p2);
					i = i + 4;
				} else if ("03".equals(c)) {
					p1 = "2".equals(pp1) ? r2 + rb : r2; 
					m.put(p1, inps.pollLast()); 
					i = i + 2;
				} else if ("04".equals(c)) {
					outs.add(p1);
					i = i + 2;
					if (outs.size() == 3) {
						break;
					}
				} else if ("05".equals(c)) {
					if (p1 != 0L) {
						i = p2;
					} else {
						i = i + 3;
					}
				} else if ("06".equals(c)) {
					if (p1 == 0L) {
						i = p2;
					} else {
						i = i + 3;
					}
				} else if ("07".equals(c)) {
					m.put(p3, p1 < p2 ? 1L : 0L);
					i = i + 4;
				} else if ("08".equals(c)) {
					m.put(p3, p1 == p2 ? 1L : 0L);
					i = i + 4;
				} else if ("99".equals(c)) {
					globalStop = true;
					break;
				} else if ("09".equals(c)) {
					rb = rb + p1;
					i = i + 2;
				} else {
					System.out.println("? ");
					globalStop = true;
					break;
				}
			}
			if (outs.size() < 3) {
				globalStop = true;
				break;
			}
			x = outs.poll();
			y = outs.poll();
			tileid = outs.poll(); 
			if (x == -1L && y == 0L) {
				scores.add(tileid);
			} else {
				m2.put(new SimpleEntry<>(x, y), tileid);
			}

			if (tileid == 3L) {
				pospaddle = x; 
			} else if (tileid == 4L) {
				posball = x; 
			}

			if (posball < pospaddle) {
				inps.add(-1L);
			} else if (posball == pospaddle) {
				inps.add(0L);
			} else {
				inps.add(1L);
			} 
		}
		if (!part2) {
			System.out.println(m2.entrySet().stream().filter(e -> e.getValue() == 2L).count());
		} else {
			System.out.println(scores.stream().max(Comparator.comparing(Long::valueOf)).orElse(0L));
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