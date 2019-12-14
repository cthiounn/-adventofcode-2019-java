package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 {

	static Map<SimpleEntry<String, Long>, Map<String, Long>> dict = new HashMap<>();

	static Map<String, Long> store = new HashMap<String, Long>();

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day14-input.file"));
		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	private static SimpleEntry<String, Long> scan(String s) {
		String[] strim = s.trim().split(" ");
		return new SimpleEntry<String, Long>(strim[1], Long.parseLong(strim[0]));
	}

	private static Map<String, Long> scanLeft(String s) {
		String[] strimlist = s.trim().split(",");
		return Arrays.asList(strimlist).stream().map(String::trim).map(Day14::scan)
				.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	}

	public static void part1(List<String> lines) {
		resetStatic(lines);
		long numberOfOreRequired = reduce("FUEL", 1L);
		System.out.println(numberOfOreRequired);
		System.out.println(searchIn(1000000000000L, lines));
	}

	private static void resetStatic(List<String> lines) {
		String x = "";
		String y = "";
		for (String s : lines) {
			Pattern pattern = Pattern.compile("(.*)=>(.*)");
			Matcher matcher = pattern.matcher(s);
			while (matcher.find()) {
				x = (matcher.group(1));
				y = (matcher.group(2));
				dict.put(scan(y), scanLeft(x));
				store.put(scan(y).getKey(), 0L);
				for (String key : scanLeft(x).keySet()) {
					store.put(key, 0L);
				}
			}
		}
	}

	private static long searchIn(long l, List<String> lines) {
		resetStatic(lines);
		long inf = 0L;
		long sup = l;
		while (inf < sup) {
			long middle = (inf + sup + 1) / 2;
			if (reduce("FUEL", middle) <= l) {
				inf = middle;
			} else {
				sup = middle - 1;
			}
		}
		return inf;
	}

	private static long reduce(String f, long l) {
		if ("ORE".equals(f)) {
			store.put("ORE", store.containsKey("ORE") ? store.get("ORE") + l : l);
			return l;
		} else {
			long total = 0L;
			long numToProduce = dict.entrySet().stream().filter(e -> f.equals(e.getKey().getKey())).findAny()
					.orElse(null).getKey().getValue();
			long howMuchInStore = store.get(f);
			long k = (long) (Math.ceil((0.0 + l - howMuchInStore) / numToProduce));
			store.put(f, howMuchInStore - l + (k * numToProduce));
			for (Entry<String, Long> i : dict.entrySet().stream().filter(e -> f.equals(e.getKey().getKey())).findAny()
					.orElse(null).getValue().entrySet()) {
				total += reduce(i.getKey(), k * i.getValue());
			}
			return total;
		}
	}

}