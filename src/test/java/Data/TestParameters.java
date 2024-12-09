package Data;

import org.junit.jupiter.params.provider.Arguments;


import java.util.stream.Stream;

public class TestParameters {
    public static Stream<Arguments> validTextValues() {
        return Stream.of(
                Arguments.of("FIVE WINE EXPERTS JOKINGLY QUIZZED SAMPLE CHABLIS"),
                Arguments.of("brick quiz whangs jumpy veldt fox"),
                Arguments.of("1234567890"),
                Arguments.of("!@#$%^&*()_+}{][,<>-.~"),
                Arguments.of("五位葡萄酒专家开玩笑地测试了夏布利"),
                Arguments.of("Южно-эфиопский грач увёл мышь за хобот на съезд ящериц"),
                Arguments.of(" To Do name with spaces ")
        );
    }
    public static Stream<Arguments> validTextCharactersQuantity() {
        return Stream.of(
                //255 symbols
                Arguments.of("The quick brown fox jumps over the lazy dog while the wind gently blows through the trees. The sky is clear, and the sun shines brightly. It's a perfect day to relax outside, enjoy nature, and appreciate the simple moments that life has to offer right now"),
                //1 symbol
                Arguments.of("b")
        );
    }

    static Stream<Arguments> completedValues() {
        return Stream.of(
                Arguments.of(true, false),
                Arguments.of(false, true)
        );
    }
}
