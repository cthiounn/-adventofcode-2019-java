package aoc;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day12-input.file"));
		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines) {
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

		for (int j = 0; j < 1000; j++) {
			// velocity
			System.out.println("step i=" + j);
			for (Moon m : Moon.allMoon) {
				System.out.println(m);
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
		}
		System.out.println(Moon.total());
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

	@Override
	public String toString() {
		return "Moon [id=" + id + ", x=" + x + ", y=" + y + ", z=" + z + ", vx=" + vx + ", vy=" + vy + ", vz=" + vz
				+ "]";
	}

}
