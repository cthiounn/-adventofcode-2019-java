package aoc;

import java.io.IOException;
import java.math.BigInteger;
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
 * Day11
 */
// 1883
// APUGURFH
public class Day11 {

    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day11-input.file"));
        part1(input, BigInteger.ZERO);
        part1(input, BigInteger.ONE);
        System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

    }

    public static BigInteger g(BigInteger i, Map<BigInteger, BigInteger> m) {
        return m.containsKey(i) ? m.get(i) : BigInteger.ZERO;
    }

    public static void part1(List<String> lines, BigInteger beginInput) {
        long[] n = Arrays.asList(lines.get(0).split(",")).stream().mapToLong(Long::parseLong).toArray();
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
        LinkedList<BigInteger> inps = new LinkedList<>();
        LinkedList<BigInteger> outs = new LinkedList<>();
        BigInteger color = BigInteger.ZERO;
        BigInteger steer = BigInteger.ZERO;
        int direction = 0;
        int x = 0;
        int y = 0;
        SimpleEntry<Integer, Integer> coord = new SimpleEntry<>(x, y);
        Map<SimpleEntry<Integer, Integer>, Integer> m2 = new HashMap<>();
        inps.add(beginInput);
        boolean globalStop = false;
        int countFirstPaint = 0;
        while (!globalStop) {
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
                    m.put(p1, inps.pop());
                    i = i.add(BigInteger.ONE).add(BigInteger.ONE);
                } else if ("04".equals(c)) {
                    outs.add(p1);
                    i = i.add(BigInteger.ONE).add(BigInteger.ONE);
                    if (outs.size() == 2) {
                        break;
                    }
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
                    globalStop = true;
                    break;
                } else if ("09".equals(c)) {
                    rb = rb.add(p1);
                    i = i.add(BigInteger.ONE).add(BigInteger.ONE);
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

            if (m2.containsKey(coord)) {
                countFirstPaint++;
            }
            m2.put(coord, Integer.parseInt(color + ""));

            int increment = steer == BigInteger.ONE ? 1 : -1;
            direction = (direction + increment) % 4;
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
            BigInteger next = m2.containsKey(coord) && m2.get(coord) == 1 ? BigInteger.ONE : BigInteger.ZERO;
            inps.add(next);
        }

        if (beginInput == BigInteger.ZERO) {
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