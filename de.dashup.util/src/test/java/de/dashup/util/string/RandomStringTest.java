package de.dashup.util.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@SuppressWarnings("WeakerAccess")
@Tag("unit")
public class RandomStringTest {
    @Test
    public void testRandomStringLength() {
        final int length = 32;
        final RandomString randomString = new RandomString(length);

        Assertions.assertEquals(randomString.nextString().length(), length);
    }

    @Test
    public void testRandomStringAlphabet() {
        final int length = 32;
        final RandomString randomString = new RandomString(length, RandomString.DIGITS);

        final String string = randomString.nextString();
        Assertions.assertEquals(length, string.length());

        for (int i = 0; i < RandomString.LOWER.length(); i++) {
            char c = string.charAt(i);
            Assertions.assertTrue(c >= 48 && c <= 57);
        }
    }
}
