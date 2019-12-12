package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javafx.geometry.Pos;

/**
 * Day10
 */
public class Day10 {
    public static void main(String[] args) throws IOException {
        long timeStart = System.currentTimeMillis();
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day10-input.file"));
        part1(input);
        System.out.println("runned time : " + (System.currentTimeMillis() - timeStart) + " ms");
    }

    public static void part1(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '#') {
                    new Asteroid(i, j);
                }
            }
        }
        Map<Integer, Asteroid> numbersAst = new HashMap<>();
        for (Asteroid a : Asteroid.listOfAsteroid) {
            Set<Ratio> ratios = new HashSet<>();
            for (Asteroid a1 : Asteroid.listOfAsteroid) {
                if (a != a1) {
                    if (a.getY() != a1.getY()) {
                        ratios.add(new Ratio(a.getX() < a1.getX() ? 1 : 0,
                                ((float) a.getX() - a1.getX()) / ((float) a.getY() - a1.getY())));

                    } else if (a.getX() < a1.getX()) {
                        ratios.add(new Ratio(1, Float.POSITIVE_INFINITY));
                    } else {
                        ratios.add(new Ratio(0, Float.NEGATIVE_INFINITY));
                    }
                }
            }
            numbersAst.put(ratios.size(), a);
        }
        Entry<Integer, Asteroid> maxEntry = numbersAst.entrySet().stream().max(Map.Entry.comparingByKey()).get();
        Asteroid myStation = maxEntry.getValue();
        System.out.println(maxEntry.getKey());
    }
}

class Ratio {
    private int positiveSign = 0;
    private float ratio = 0.0F;

    public Ratio(int positiveSign, float ratio) {
        this.positiveSign = positiveSign;
        this.ratio = ratio;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + positiveSign;
        result = prime * result + Float.floatToIntBits(ratio);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ratio other = (Ratio) obj;
        if (positiveSign != other.positiveSign)
            return false;
        if (Float.floatToIntBits(ratio) != Float.floatToIntBits(other.ratio))
            return false;
        return true;
    }

    public int getPositiveSign() {
        return positiveSign;
    }

    public void setPositiveSign(int positiveSign) {
        this.positiveSign = positiveSign;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}

class Asteroid {
    static List<Asteroid> listOfAsteroid = new ArrayList<>();
    private int x = 0;
    private int y = 0;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
        listOfAsteroid.add(this);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Asteroid other = (Asteroid) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Asteroid [x=" + x + ", y=" + y + "]";
    }

}