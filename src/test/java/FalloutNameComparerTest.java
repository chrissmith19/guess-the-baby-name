import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FalloutNameComparerTest {

    @ParameterizedTest(name = "{index} => guessedName1={0}, realName1={1}, expectedSimilarity={2}")
    @MethodSource("namesLetters")
    void nameCompareByLetters(String guessedName, String realName, int expectedSimilarity) {
        int similarity = FalloutNameComparer.nameCompareByLetters(guessedName, realName);
        Assertions.assertEquals(similarity,expectedSimilarity);
    }

    private static Stream<Arguments> namesLetters(){
        return Stream.of(
                Arguments.of("abc","a",1),
                Arguments.of("abc","ba",2),
                Arguments.of("abc","cba",3),
                Arguments.of("l","bill",1),
                Arguments.of("ll","bill",2),
                Arguments.of("lll","bill",2),
                Arguments.of("sam","chris",1),
                Arguments.of("emma","reMuS",2),
                Arguments.of("EmMa","remus",2)
        );}


    @ParameterizedTest(name = "{index} => guessedName1={0}, realName1={1}, expectedSimilarity={2}")
    @MethodSource("namesPosition")
    void nameCompareByPosition(String guessedName, String realName, int expectedSimilarity) {
        int similarity = FalloutNameComparer.nameCompareByPosition(guessedName, realName);
        Assertions.assertEquals(similarity,expectedSimilarity);
    }

    private static Stream<Arguments> namesPosition(){
        return Stream.of(
                Arguments.of("rob","rob",3),
                Arguments.of("Rob","ROB",3), //case insentive
                Arguments.of("robi","rob",3), //case insentive
                Arguments.of("robI","rOb",3), //case insentive
                Arguments.of("robi","robi",4), //case insentive
                Arguments.of("rob","bill",0),//no matches
                Arguments.of("rob","bob",2),//partial match
                Arguments.of("rob","ro",2),//partial match
                Arguments.of("ro","rob",2),//partial match
                Arguments.of("hillary","boaeofuerughqfb",0), //if the real name is longer than guessed
                Arguments.of("boaeofuerughqfb","hillary",0), //if the guessed name is longer than real
                Arguments.of("ro","bin",0),
                Arguments.of("bin","ro",0),
                Arguments.of("bin","mam",0)

        );}

    @ParameterizedTest(name = "{index} => guessedName1={0}, realName1={1}, expectedSimilarity={2}")
    @MethodSource("names")
    void nameComparer(String guessedName1, String realName1, int expectedSimilarity) {
        int similarity = FalloutNameComparer.compareNames(guessedName1, realName1);
        Assertions.assertEquals(similarity,expectedSimilarity);
    }

    private static Stream<Arguments> names(){
        return Stream.of(
                Arguments.of("a","a",1000),
                Arguments.of("aa","a",2),
                Arguments.of("aa","aa",1000),
                Arguments.of("l","bill",1),
                Arguments.of("ll","bill",2),
                Arguments.of("lll","bill",3),
                Arguments.of("sam","chris",1),
                Arguments.of("emma","reMuS",3),
                Arguments.of("EmMa","remus",3),
                Arguments.of("Jim","james",4),
                Arguments.of("annabal","anna",8),
                Arguments.of("anna","annabal",8)
        );}

    @Test
    void readListFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/csm40/workspace/guess-the-baby-name/src/test/resources/listOfNames.txt"));
        String thisLine = "";
        ArrayList<String> namesList = new ArrayList<String>();
        while ((thisLine = br.readLine()) != null) {
            namesList.add(thisLine);
        }

        String realName="???";
        namesList.forEach(guessname->{
            int similarity = FalloutNameComparer.compareNames(guessname, realName);
            System.out.println(guessname + ": "+similarity);
        });
    }
}