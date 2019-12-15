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
import java.util.Map.Entry;
import java.util.Set;

/**
 * --- Day 15: Oxygen System --- 
 *  IntCode / DFS
 *  336
 *  360
 */

public class Day15 {

	public static void main(String[] args) throws IOException {
		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day15-input.file"));
		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static long g(long i, Map<Long, Long> m) {
		return m.containsKey(i) ? m.get(i) : 0L;
	}

	public static void part1(List<String> lines) {
		long[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToLong(Long::parseLong).toArray();
		Map<Long, Long> m = new HashMap<>();
		for (int i = 0; i < n.length; i++) {
			m.put((long) i, n[i]);
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
		boolean globalStop = false;

		long x = 0;
		long y = 0;
		long depth = 0;
		SimpleEntry<Long, Long> startingPoint = new SimpleEntry<Long, Long>(x, y);
		Map<SimpleEntry<Long, Long>, List<Object>> tileToExplore = new HashMap<>();
		List<Object> values = new ArrayList<Object>();
		values.add(0);
		values.add(0);
		Map<Long, Long> mcopy = new HashMap<Long, Long>();
		mcopy.putAll(m);
		values.add(mcopy);
		values.add(0);
		tileToExplore.put(startingPoint, values);
		Map<SimpleEntry<Long, Long>, SimpleEntry<Long, Long>> knownTiles = new HashMap<>();
		boolean foundOxygen = false;
		long response = 0L;

		while (!tileToExplore.isEmpty()) {
			Map<SimpleEntry<Long, Long>, List<Object>> tileToExploreCopy = new HashMap<>();
			tileToExploreCopy.putAll(tileToExplore);
			tileToExplore.clear();
			boolean superbreak = false;
			for (Entry<SimpleEntry<Long, Long>, List<Object>> se : tileToExploreCopy.entrySet()) {
				for (int j = 1; j < 5 && !superbreak; j++) {
					x = se.getKey().getKey();
					y = se.getKey().getValue();
					depth = Long.parseLong(se.getValue().get(0) + "") + 1;
					i = Long.parseLong(se.getValue().get(1) + "");
					m.clear();
					m.putAll((HashMap<Long, Long>) se.getValue().get(2));
					rb = Long.parseLong(se.getValue().get(3) + "");
					inps.clear();
					outs.clear();
					switch (j) {
					case 1:
						x++;
						inps.add(1L);
						break;
					case 2:
						x--;
						inps.add(2L);
						break;
					case 3:
						y--;
						inps.add(3L);
						break;
					case 4:
						y++;
						inps.add(4L);
						break;

					default:
						break;
					}
					globalStop = false;

					while (!globalStop) {
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
								if (outs.size() == 1) {
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
						if (outs.size() < 1) {
							globalStop = true;
							break;
						}
						response = outs.poll();

						SimpleEntry<Long, Long> se2 = new SimpleEntry<Long, Long>(x, y);
						SimpleEntry<Long, Long> ve2 = new SimpleEntry<Long, Long>(depth, response);

						if (!knownTiles.containsKey(se2)) {
							knownTiles.put(se2, ve2);
						} else {
							long depth2 = knownTiles.get(se2).getKey();
							if (depth >= depth2) {
								break;
							} else {
								knownTiles.put(se2, ve2);
							}
						}
						if (response == 2L && !foundOxygen) {
							foundOxygen = true;
							System.out.println(depth);
							depth = 0L;
							tileToExplore.clear();
							knownTiles.clear();
							values = new ArrayList<Object>();
							values.add(depth);
							values.add(i);
							mcopy = new HashMap<Long, Long>();
							mcopy.putAll(m);
							values.add(mcopy);
							values.add(rb);
							startingPoint = new SimpleEntry<Long, Long>(x, y);
							tileToExplore.put(startingPoint, values);
							superbreak = true;
							break;
						} else if (response == 2L) {
							break;
						} else if (response == 1L) {
							values = new ArrayList<Object>();
							values.add(depth);
							values.add(i);
							mcopy = new HashMap<Long, Long>();
							mcopy.putAll(m);
							values.add(mcopy);
							values.add(rb);
							startingPoint = new SimpleEntry<Long, Long>(x, y);
							tileToExplore.put(startingPoint, values);
							break;
						} else if (response == 0L) {
							break;
						}
					}
				}
				if (superbreak) {
					break;
				}
			}
		}

		System.out.println(knownTiles.entrySet().stream().filter(e -> e.getValue().getValue() == 1L)
				.mapToLong(e -> e.getValue().getKey()).max().orElse(0));
	}

	private static void printmap(Map<SimpleEntry<Long, Long>, SimpleEntry<Long, Long>> knownTiles) {
		Set<SimpleEntry<Long, Long>> keys = knownTiles.keySet();
		long xmax = keys.stream().map(e -> e.getKey()).max(Comparator.comparing(Long::valueOf)).get();
		long xmin = keys.stream().map(e -> e.getKey()).min(Comparator.comparing(Long::valueOf)).get();
		long ymax = keys.stream().map(e -> e.getValue()).max(Comparator.comparing(Long::valueOf)).get();
		long ymin = keys.stream().map(e -> e.getValue()).min(Comparator.comparing(Long::valueOf)).get();

		SimpleEntry<Long, Long> se;
		for (long j = ymin - 1; j < ymax + 2; j++) {
			String c = "";
			for (long i = xmin - 1; i < xmax + 2; i++) {
				se = new SimpleEntry<>(i, j);
				c += knownTiles.containsKey(se) && knownTiles.get(se).getValue() == 1 ? "O" : " ";

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