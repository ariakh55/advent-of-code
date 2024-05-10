import java.io.*;
import java.util.*;

public class App {
    static final Map<String, Integer> colorToValidCubeCount = new HashMap<String, Integer>();

    static {
        colorToValidCubeCount.put("blue", 14);
        colorToValidCubeCount.put("green", 13);
        colorToValidCubeCount.put("red", 12);
    }

    static String replaceString(String cube) {
        return cube.replaceAll("\\D", "").trim();
    }

    static List<Map<String, Integer>> parseGameline(String line) {
        List<Map<String, Integer>> gameSets = new ArrayList<Map<String, Integer>>();

        String fullsets = line.substring(line.indexOf(':'));
        String[] sets = fullsets.split(";");

        for (String set : sets) {
            Map<String, Integer> gameSet = new HashMap<String, Integer>();

            Integer redCubes = 0;
            Integer greenCubes = 0;
            Integer blueCubes = 0;

            String[] cubes = set.split(",");

            for (String cube : cubes) {
                if (cube.contains("red")) {
                    redCubes = Integer.parseInt(replaceString(cube));
                }
                if (cube.contains("green")) {
                    greenCubes = Integer.parseInt(replaceString(cube));
                }
                if (cube.contains("blue")) {
                    blueCubes = Integer.parseInt(replaceString(cube));
                }
            }

            gameSet.put("red", redCubes);
            gameSet.put("green", greenCubes);
            gameSet.put("blue", blueCubes);

            gameSets.add(gameSet);
        }

        return gameSets;
    }

    static boolean validateGameSet(String line) {
        List<Map<String, Integer>> gameSets = parseGameline(line);
        boolean isValid = true;

        for (Map<String, Integer> gameSet : gameSets) {
            if (gameSet.get("red") > colorToValidCubeCount.get("red"))
                isValid = false;
            if (gameSet.get("green") > colorToValidCubeCount.get("green"))
                isValid = false;
            if (gameSet.get("blue") > colorToValidCubeCount.get("blue"))
                isValid = false;
        }

        return isValid;
    }

    static Integer getTheLeastPossibleCubes(String line) {
        List<Map<String, Integer>> gameSets = parseGameline(line);
        Map<String, Integer> firstSet = gameSets.get(0);

        Integer maxRedCubes = firstSet.get("red");
        Integer maxGreenCubes = firstSet.get("green");
        Integer maxBlueCubes = firstSet.get("blue");

        for (Map<String, Integer> gameSet : gameSets.subList(1, gameSets.size())) {
            if (gameSet.get("red") > maxRedCubes)
                maxRedCubes = gameSet.get("red");
            if (gameSet.get("green") > maxGreenCubes)
                maxGreenCubes = gameSet.get("green");
            if (gameSet.get("blue") > maxBlueCubes)
                maxBlueCubes = gameSet.get("blue");
        }

        return maxRedCubes * maxGreenCubes * maxBlueCubes;
    }

    public static void main(String[] args) {
        String filePath = "cube-conundrum.txt";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int gameId = 1, validSum = 0, sum = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (validateGameSet(line)) {
                    validSum += gameId;
                }

                gameId++;

                sum += getTheLeastPossibleCubes(line);
            }

            System.out.println(sum);
            System.out.println(validSum);

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
