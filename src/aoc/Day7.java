package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * --- Day 7: Amplification Circuit ---
 * IntCode loop input-output
 * 117312
 * 1336480
 */
public class Day7 {
	static LinkedList<Integer> i1 = new LinkedList<>();
	static LinkedList<Integer> i2 = new LinkedList<>();
	static LinkedList<Integer> i3 = new LinkedList<>();
	static LinkedList<Integer> i4 = new LinkedList<>();
	static LinkedList<Integer> i5 = new LinkedList<>();
	static LinkedList<Integer> o1 = new LinkedList<>();
	static LinkedList<Integer> o2 = new LinkedList<>();
	static LinkedList<Integer> o3 = new LinkedList<>();
	static LinkedList<Integer> o4 = new LinkedList<>();
	static LinkedList<Integer> o5 = new LinkedList<>();
	static LinkedList<Integer> f5 = new LinkedList<>();

	public static void main(String[] args) throws IOException {

		long timeStart = System.currentTimeMillis();
		List<String> input = Files.readAllLines(Paths.get("src/main/resources/day7-input.file"));
		doPart1(input, "01234");
		doPart2(input, "56789");
		System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

	}

	private static void doPart2(List<String> input, String param) {
		List<String> myList = permutations(param);
		for (String s : myList) {
			calculate(input, s);
		}
		System.out.println(f5.stream().max(Comparator.comparing(Integer::valueOf)).get());
	}

	private static void calculate(List<String> input, String s) {
		int[] index = new int[5];
		int[] n1 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		int[] n2 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		int[] n3 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		int[] n4 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		int[] n5 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
		i1 = new LinkedList<>();
		i2 = new LinkedList<>();
		i3 = new LinkedList<>();
		i4 = new LinkedList<>();
		i5 = new LinkedList<>();
		o1 = new LinkedList<>();
		o2 = new LinkedList<>();
		o3 = new LinkedList<>();
		o4 = new LinkedList<>();
		o5 = new LinkedList<>();

		i1.add(Integer.parseInt(s.substring(0, 1)));
		i1.add(0);

		i2.add(Integer.parseInt(s.substring(1, 2)));
		i3.add(Integer.parseInt(s.substring(2, 3)));
		i4.add(Integer.parseInt(s.substring(3, 4)));
		i5.add(Integer.parseInt(s.substring(4, 5)));
		try {
			while (true) {
				index[0] = intCode(n1, 1, index[0]);
				if (index[0] == -1) {
					break;
				} else {
					i2.addLast(o1.pollLast());
				}
				index[1] = intCode(n2, 2, index[1]);
				if (index[1] == -1) {
					break;
				} else {
					i3.addLast(o2.pollLast());
				}
				index[2] = intCode(n3, 3, index[2]);
				if (index[2] == -1) {
					break;
				} else {
					i4.addLast(o3.pollLast());
				}
				index[3] = intCode(n4, 4, index[3]);
				if (index[3] == -1) {
					break;
				} else {
					i5.addLast(o4.pollLast());
				}
				index[4] = intCode(n5, 5, index[4]);
				if (index[4] == -1) {
					break;
				} else {
					i1.addLast(o5.pollLast());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int intCode(int[] n, int number, int save) throws Exception {
		int ret = -1;
		int i = save;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int p1 = 0;
		int p2 = 0;
		int c = 0;
		boolean stop = false;
		while (!stop) {
			r2 = i + 1 < n.length ? n[i + 1] : 0;
			r3 = i + 2 < n.length ? n[i + 2] : 0;
			r4 = i + 3 < n.length ? n[i + 3] : 0;
			c = n[i] % 10;
			p1 = n[i] % 1000 >= 100 || c == 3 || c == 9 ? r2 : n[r2];
			p2 = n[i] % 10000 >= 1000 ? r3 : c == 3 || c == 4 || c == 9 ? 0 : n[r3];
			if (c == 1) {
				n[r4] = p1 + p2;
				i += 4;
			} else if (c == 2) {
				n[r4] = p1 * p2;
				i += 4;
			} else if (c == 3) {
				switch (number) {
				case 1:
					if (i1.size() != 0) {
						n[r2] = i1.pollFirst();
					} else {
						throw new Exception("no more input");
					}
					break;
				case 2:
					if (i2.size() != 0) {
						n[r2] = i2.pollFirst();
					} else {
						throw new Exception("no more input");
					}
					break;
				case 3:
					if (i3.size() != 0) {
						n[r2] = i3.pollFirst();
					} else {
						throw new Exception("no more input");
					}
					break;
				case 4:
					if (i4.size() != 0) {
						n[r2] = i4.pollFirst();
					} else {
						throw new Exception("no more input");
					}
					break;
				case 5:
					if (i5.size() != 0) {
						n[r2] = i5.pollFirst();
					} else {
						throw new Exception("no more input");
					}
					break;
				default:
					break;
				}

				i += 2;
			} else if (c == 4) {
				switch (number) {
				case 1:
					o1.add(p1);
					break;
				case 2:
					o2.add(p1);
					break;
				case 3:
					o3.add(p1);
					break;
				case 4:
					o4.add(p1);
					break;
				case 5:
					o5.add(p1);
					f5.add(p1);
					break;
				default:
					break;
				}

				ret = i + 2;
				break;
			} else if (c == 5) {
				if (p1 != 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 6) {
				if (p1 == 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 7) {
				n[r4] = p1 < p2 ? 1 : 0;
				i += 4;
			} else if (c == 8) {
				n[r4] = p1 == p2 ? 1 : 0;
				i += 4;
			} else if (c == 9) {
				stop = true;
			} else {
				System.out.println("? " + n[i]);
				stop = true;
			}
		}
		return ret;
	}

	private static void doPart1(List<String> input, String param) {
		List<String> myList = permutations(param);
		int[] listOutput = new int[5];
		List<Integer> myListOutput = new ArrayList<Integer>();
		Map<Integer, String> myMap = new HashMap<>();

		int max = 0;
		for (String s : myList) {
			int[] n1 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
			int[] n2 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
			int[] n3 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
			int[] n4 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();
			int[] n5 = Arrays.asList(input.get(0).split(",")).stream().mapToInt(Integer::parseInt).toArray();

			try {
				listOutput[0] = part1(n1, Integer.parseInt(s.substring(0, 1)), 0);
				listOutput[1] = part1(n2, Integer.parseInt(s.substring(1, 2)), listOutput[0]);
				listOutput[2] = part1(n3, Integer.parseInt(s.substring(2, 3)), listOutput[1]);
				listOutput[3] = part1(n4, Integer.parseInt(s.substring(3, 4)), listOutput[2]);
				listOutput[4] = part1(n5, Integer.parseInt(s.substring(4, 5)), listOutput[3]);
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (listOutput[4] > max) {
					myMap.put(listOutput[4], s);
					myListOutput.add(listOutput[4]);
					max = listOutput[4];
				}
			}

		}

//		System.out.println(myListOutput);
		System.out.println(myListOutput.stream().max(Comparator.comparing(Integer::valueOf)).get());
//		System.out.println(myMap.get(myListOutput.stream().max(Comparator.comparing(Integer::valueOf)).get()));
	}

	public static int part1(int[] n, int beginInput, int input2) throws Exception {
		int i = 0;
		int r2 = 0;
		int r3 = 0;
		int r4 = 0;
		int p1 = 0;
		int p2 = 0;
		int c = 0;
		boolean firstInput = true;
		boolean stop = false;
		while (!stop) {
			r2 = i + 1 < n.length ? n[i + 1] : 0;
			r3 = i + 2 < n.length ? n[i + 2] : 0;
			r4 = i + 3 < n.length ? n[i + 3] : 0;
			c = n[i] % 10;
			p1 = n[i] % 1000 >= 100 || c == 3 || c == 9 ? r2 : n[r2];
			p2 = n[i] % 10000 >= 1000 ? r3 : c == 3 || c == 4 || c == 9 ? 0 : n[r3];
			if (c == 1) {
				n[r4] = p1 + p2;
				i += 4;
			} else if (c == 2) {
				n[r4] = p1 * p2;
				i += 4;
			} else if (c == 3) {
				if (firstInput) {
					n[r2] = beginInput;
					firstInput = false;
				} else {
					n[r2] = input2;
				}
				i += 2;
			} else if (c == 4) {
				beginInput = p1;
				i += 2;
			} else if (c == 5) {
				if (p1 != 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 6) {
				if (p1 == 0) {
					i = p2;
				} else {
					i += 3;
				}
			} else if (c == 7) {
				n[r4] = p1 < p2 ? 1 : 0;
				i += 4;
			} else if (c == 8) {
				n[r4] = p1 == p2 ? 1 : 0;
				i += 4;
			} else if (c == 9) {
				stop = true;
			} else {
				System.out.println("? " + n[i]);
				stop = true;

				throw new Exception();
			}
		}
		return beginInput;
	}

	public static List<String> permutations(String s) {
		// create an empty ArrayList to store (partial) permutations
		List<String> partial = new ArrayList<>();

		// initialize the list with the first character of the string
		partial.add(String.valueOf(s.charAt(0)));

		// do for every character of the specified string
		for (int i = 1; i < s.length(); i++) {
			// consider previously constructed partial permutation one by one

			// (iterate backwards to avoid ConcurrentModificationException)
			for (int j = partial.size() - 1; j >= 0; j--) {
				// remove current partial permutation from the ArrayList
				String str = partial.remove(j);

				// Insert next character of the specified string in all
				// possible positions of current partial permutation. Then
				// insert each of these newly constructed string in the list

				for (int k = 0; k <= str.length(); k++) {
					// Advice: use StringBuilder for concatenation
					partial.add(str.substring(0, k) + s.charAt(i) + str.substring(k));
				}
			}
		}

		return partial;
	}
}
