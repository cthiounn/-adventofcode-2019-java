package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
			for (Moon m : Moon.allMoon) {
				for (Moon m2 : Moon.allMoon) {
					if (m.getId() == m2.getId()) {
					} else {
						if (m.getX() < m2.getX()) {
							m.setVx(m.getVx() + 1);
						} else if (m.getX() > m2.getX()) {
							m.setVx(m.getVx() - 1);
						}
						if (m.getY() < m2.getY()) {
							m.setVy(m.getVy() + 1);
						} else if (m.getY() > m2.getY()) {
							m.setVy(m.getVy() - 1);
						}
						if (m.getZ() < m2.getZ()) {
							m.setVz(m.getVz() + 1);
						} else if (m.getZ() > m2.getZ()) {
							m.setVz(m.getVz() - 1);
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

			String xcoord = Moon.allMoon.stream().map(e -> e.getX() + ";" + e.getVx()).collect(Collectors.toList())
					.toString();
			if (xString.contains(xcoord)) {
				xInt.add(j);
			} else {
				xString.add(xcoord);
			}
			String ycoord = Moon.allMoon.stream().map(e -> e.getY() + ";" + e.getVy()).collect(Collectors.toList())
					.toString();
			if (yString.contains(ycoord)) {
				yInt.add(j);
			} else {
				yString.add(ycoord);
			}
			String zcoord = Moon.allMoon.stream().map(e -> e.getZ() + ";" + e.getVz()).collect(Collectors.toList())
					.toString();
			if (zString.contains(zcoord)) {
				zInt.add(j);
			} else {
				zString.add(zcoord);
			}

			if (!xInt.isEmpty() && !yInt.isEmpty() && !zInt.isEmpty()) {
				break;
			}

			j++;
		}

		System.out.println(lcm(xInt.get(0), lcm(yInt.get(0), zInt.get(0))));
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
	private int id = 0;
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private int vx = 0;
	private int vy = 0;
	private int vz = 0;
	public static List<Moon> allMoon = new ArrayList<Moon>();

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

	@Override
	public String toString() {
		return "Moon [id=" + id + ", x=" + x + ", y=" + y + ", z=" + z + ", vx=" + vx + ", vy=" + vy + ", vz=" + vz
				+ "]";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getVz() {
		return vz;
	}

	public void setVz(int vz) {
		this.vz = vz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
