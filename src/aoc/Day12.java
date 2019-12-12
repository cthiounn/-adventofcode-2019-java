package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day12-input.file"));
		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines) {
		Set<String> hs = new HashSet<>();
		Map<Long, Set<String>> registry = new HashMap<>();
		int twoNumber = 0;
		Set<String> xString = new HashSet<>();
		Set<String> yString = new HashSet<>();
		Set<String> zString = new HashSet<>();
		List<Integer> xInt = new ArrayList<Integer>();
		List<Integer> yInt = new ArrayList<Integer>();
		List<Integer> zInt = new ArrayList<Integer>();
		int i = 0;
		for (String s : lines) {
			Pattern pattern = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
			Matcher matcher = pattern.matcher(s);
			int x = 0;
			int y = 0;
			int z = 0;
			while (matcher.find()) {
				x = Integer.parseInt(matcher.group(1));
				y = Integer.parseInt(matcher.group(2));
				z = Integer.parseInt(matcher.group(3));
			}
			new Moon(i, x, y, z, 0, 0, 0);
			i++;
		}

		int j = 0;
		while (true) {
			// velocity
//			System.out.println("step i=" + j);
			for (Moon m : Moon.allMoon) {
//				System.out.println(m);
				for (Moon m2 : Moon.allMoon) {
					if (m.id == m2.id) {

					} else {
						if (m.x < m2.x) {
							m.vx += 1;
						} else if (m.x > m2.x) {
							m.vx += -1;
						}
						if (m.y < m2.y) {
							m.vy += 1;
						} else if (m.y > m2.y) {
							m.vy += -1;
						}
						if (m.z < m2.z) {
							m.vz += 1;
						} else if (m.z > m2.z) {
							m.vz += -1;
						}
					}
				}
			}
			// velocity applied
			for (Moon m : Moon.allMoon) {
				m.move();
			}
			if (j == 999) {
				System.out.println(Moon.total());
			}

//			long hssHC = Moon.hCode().hashCode();
//			String hss = Moon.hCode();
//			if (registry.containsKey(hssHC)) {
//				if (registry.get(hssHC).contains(hss)) {
//					break;
//				} else {
//					registry.get(hssHC).add(hss);
//				}
//			} else {
//				Set<String> hs2 = new HashSet<>();
//				hs2.add(hss);
//				registry.put(hssHC, hs2);
//			}
			String xcoord = Moon.allMoon.stream().map(e -> e.x + ";" + e.vx).collect(Collectors.toList()).toString();
			if (xString.contains(xcoord)) {
				xInt.add(j);
				twoNumber++;
			} else {
				xString.add(xcoord);
			}
			String ycoord = Moon.allMoon.stream().map(e -> e.y + ";" + e.vy).collect(Collectors.toList()).toString();
			if (yString.contains(ycoord)) {
				yInt.add(j);
				twoNumber++;
			} else {
				yString.add(ycoord);
			}
			String zcoord = Moon.allMoon.stream().map(e -> e.z + ";" + e.vz).collect(Collectors.toList()).toString();
			if (zString.contains(zcoord)) {
				zInt.add(j);
				twoNumber++;
			} else {
				zString.add(zcoord);
			}

			if (!xInt.isEmpty() && !yInt.isEmpty() && !zInt.isEmpty()) {
				System.out.println(xInt.get(0));
				System.out.println(yInt.get(0));
				System.out.println(zInt.get(0));
				break;
			}

			j++;
		}
//		Long l = new Long(xInt.get(0) * yInt.get(0));
//		Long ll = l / gcdByEuclidsAlgorithm(xInt.get(0), yInt.get(0));
//
//		Long l2 = new Long(ll * zInt.get(0));
//
//		Long ll2 = l2 / gcdByEuclidsAlgorithm(zInt.get(0), l2);
//		System.out.println(ll2);

		System.out.println(lcm(xInt.get(0), lcm(yInt.get(0), zInt.get(0))));
	}

	static int gcdByEuclidsAlgorithm(int n1, int n2) {
		if (n2 == 0) {
			return n1;
		}
		return gcdByEuclidsAlgorithm(n2, n1 % n2);
	}

	private static long gcd(long a, long b) {
		while (b > 0) {
			long temp = b;
			b = a % b; // % is remainder
			a = temp;
		}
		return a;
	}

	private static long lcm(long a, long b) {
		return a * (b / gcd(a, b));
	}
}

class Moon {
	int id = 0;
	int x = 0;
	int y = 0;
	int z = 0;
	int vx = 0;
	int vy = 0;
	int vz = 0;
	static List<Moon> allMoon = new ArrayList<Moon>();

	public Moon(int id, int x, int y, int z, int vx, int vy, int vz) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		allMoon.add(this);
	}

	public void move() {
		this.x += vx;
		this.y += vy;
		this.z += vz;
	}

	public int cinetic() {

		return Math.abs(vx) + Math.abs(vy) + Math.abs(vz);
	}

	public int pot() {

		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	public static long total() {
		return allMoon.stream().mapToLong(x -> x.cinetic() * x.pot()).sum();
	}

	public static String hCode() {
		return allMoon.toString();
	}

	@Override
	public String toString() {
		return "Moon [id=" + id + ", x=" + x + ", y=" + y + ", z=" + z + ", vx=" + vx + ", vy=" + vy + ", vz=" + vz
				+ "]";
	}

}
