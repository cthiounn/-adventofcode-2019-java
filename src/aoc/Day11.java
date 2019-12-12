package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Day11
 */
public class Day11 {

    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day11-input.file"));
        part1(input);
        System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");

    }

    public static void part1(List<String> lines) {
        for (String s : lines) {
            System.out.println(s);
        }
    }
}