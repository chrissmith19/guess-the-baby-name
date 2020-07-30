import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FalloutNameComparer {

    public static int compareNames(String guessName, String realName) {
        int bonus =0;
        if(guessName.equals(realName)){
            bonus=1;
        }
        return nameCompareByLetters(guessName,realName)+nameCompareByPosition(guessName,realName)+bonus;
    }

    public static int nameCompareByLetters(String guessName, String realName) {
        Map<Character, Integer> guessNameCharacterFrequencyMap = stringToCharacterFrequencyMapConverter(guessName);
        Map<Character, Integer> realNameCharacterFrequencyMap = stringToCharacterFrequencyMapConverter(realName);

        Set realKey = realNameCharacterFrequencyMap.keySet();

        int similarity = realKey.stream().mapToInt(letter -> {
            int realFrequency = realNameCharacterFrequencyMap.getOrDefault(letter,0);
            int guessFrequency = guessNameCharacterFrequencyMap.getOrDefault(letter,0);

            if (realFrequency == guessFrequency | realFrequency < guessFrequency) {
                return realFrequency;
            } else {
                return guessFrequency;
            }
        }).sum();
        return similarity;
    }

    private static Map<Character, Integer> stringToCharacterFrequencyMapConverter(String name) {
        char[] charArray = name.toLowerCase().toCharArray();
        Map<Character, Integer> charFrequencyMap = new HashMap<>();

        for (int i = 0; i < charArray.length; i++) {
            int frequency = charFrequencyMap.getOrDefault(charArray[i], 0);
            charFrequencyMap.put(charArray[i], frequency + 1);
        }
        return charFrequencyMap;
    }

    public static int nameCompareByPosition(String guessName, String realName) {
        //will compare from the first to the last characters of the real name.
        char[] guessNameCharacters = guessName.toLowerCase().toCharArray();
        char[] realNameCharacters = realName.toLowerCase().toCharArray();
        int positionSimilarity = 0;

        for (int i = 0; i < realNameCharacters.length; i++) {
            if (i < guessNameCharacters.length) {
                if (realNameCharacters[i] == guessNameCharacters[i]) {
                    positionSimilarity += 1;
                }
            }
        }
        return positionSimilarity;
    }

}
