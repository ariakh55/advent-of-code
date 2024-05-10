import java.io.*;
import java.util.*;
import java.util.regex.*;

public class App {
    static String[] numbersList = {
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
    };
    static final Map<String, String> wordsToNumbers = new HashMap<String, String>();
    static {
        for (int i = 0; i < 10; ++i) {
            wordsToNumbers.put(numbersList[i], Integer.toString(i));
        }
    }

    public static int extractNumbers(String line) {
        String numbers = String.join("|", Arrays.asList(numbersList));
        String regexPattern = String.format("(?=(%s|\\d))", numbers);

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(line);
        StringBuilder onlyNumbers = new StringBuilder();

        while (matcher.find()) {
            String word = matcher.group(1);
            String digit = wordsToNumbers.getOrDefault(word, null);
            if (digit != null) {
                onlyNumbers.append(digit);
            } else {
                onlyNumbers.append(word);
            }
        }
        System.out.println(onlyNumbers.toString());

        char firstNumber = onlyNumbers.charAt(0);
        char lastNumber = onlyNumbers.charAt(onlyNumbers.length() - 1);

        int finalNumber = Integer.parseInt("" + firstNumber + lastNumber);

        return finalNumber;
    }

    public static void main(String[] args) {
        String filePath = "trebuchet.txt";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int sum = 0;
            while ((line = bufferedReader.readLine()) != null) {
                sum += extractNumbers(line);
            }

            bufferedReader.close();
            System.out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
