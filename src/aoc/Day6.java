package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Day6 {

	public static List<Orbit> listOrb = new ArrayList<Orbit>();

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day6-input.file"));

		part1(input);
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	public static void part1(List<String> lines) {
		for (String string : lines) {
			String[] n = string.split("\\)");
			if (listOrb.stream().map(Orbit::getName).filter(x -> x.equals(n[1])).count() == 0) {
				listOrb.add(new Orbit(n[1], n[0]));

			}
		}
		System.out.println("go");
		System.out.println(Orbit.allOrbit.stream().mapToInt(Orbit::calculateOrbiting).sum());

		List<Orbit> myList = Orbit.allOrbit.stream().filter(x -> x.getName().equals("YOU") || x.getName().equals("SAN"))
				.collect(Collectors.toList());
		myList.get(0).commonNode(myList.get(1));
		int distCommonNode = Orbit.getOrbit("25D").calculateOrbiting();
		int dist1 = myList.get(0).calculateOrbiting();
		int dist2 = myList.get(1).calculateOrbiting();
		System.out.println(Math.abs(dist1 - distCommonNode) + Math.abs(dist2 - distCommonNode) - 2);

	}
}

class Orbit {
	static List<Orbit> allOrbit = new ArrayList<>();
	String name;
	String prev = "";
	Orbit previousOrbit = null;
	List<Orbit> listPrev = new ArrayList<>();

	public String getName() {
		return name;
	}

	public Orbit(String name, String prev) {
		this.name = name;
		this.prev = prev;
		allOrbit.add(this);
	}

	public static Orbit getOrbit(String st) {
		List<Orbit> os = allOrbit.stream().filter(x -> x.getName().equals(st)).collect(Collectors.toList());
		return os.isEmpty() ? null : os.get(0);
	}

	public int calculateOrbiting() {
		Orbit o = getOrbit(prev);
		if (o == null) {
			return 1;
		} else {
			previousOrbit = o;
			return o.calculateOrbiting() + 1;
		}
	}

	public void setPrevs() {
		String p = prev;
		Orbit prevO = null;
		while (p != "") {
			prevO = previousOrbit;
			if (prevO != null) {
				listPrev.add(prevO);
				prevO = prevO.previousOrbit;
			} else {
				break;
			}
		}
	}

	public Orbit commonNode(Orbit o1) {
		List<Orbit> listOfPrev = new ArrayList<Orbit>();
		Orbit ow = previousOrbit;
		Orbit ov = o1.previousOrbit;
		int i = 0;
		while (true) {
			i++;
			if (listOfPrev.contains(ow)) {
				System.out.println("v=" + ow.getName());
				break;
			} else {
				System.out.println("v adding " + ow.getName());
				listOfPrev.add(ow);
			}
			ow = ow.previousOrbit;
			if (listOfPrev.contains(ov)) {
				System.out.println("t=" + ov.getName());
				break;
			} else {
				System.out.println("t adding " + ov.getName());
				listOfPrev.add(ov);
			}
			ov = ov.previousOrbit;

		}
		System.out.println(i);
		return null;
	}
}