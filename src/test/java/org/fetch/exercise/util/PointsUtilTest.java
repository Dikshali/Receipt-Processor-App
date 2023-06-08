package org.fetch.exercise.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PointsUtilTest {
    private final PointsUtil pointsUtil;

    @Autowired
    public PointsUtilTest(final PointsUtil pointsUtil) {
        this.pointsUtil = pointsUtil;
    }

    @ParameterizedTest
    @MethodSource("setupAlphaNumericStringScenarios")
    void getAlphaNumericCharacterCount_givenStringWithOnlyAlphaNumericCharacter_returnsAlphaNumericCharCount(
            final String retailerName, final int expectedResult) {
        final int actualResult = pointsUtil.getAlphaNumericCharacterCount(retailerName);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void isRoundNumber_givenDecimalNumber_returnsFalse() {
        final double num = 102.39;
        Assertions.assertFalse(pointsUtil.isRoundNumber(num));
    }

    @Test
    void isRoundNumber_givenWholeNumber_returnsTrue() {
        final double num = 102.00;
        Assertions.assertTrue(pointsUtil.isRoundNumber(num));
    }

    @Test
    void isMultipleOf_givenMultiplierAndNumDivisible_returnsTrue() {
        final double multiplier1 = 3;
        final double num1 = 1377.00;
        Assertions.assertTrue(pointsUtil.isMultipleOf(multiplier1, num1));

        final double multiplier2 = 0.25;
        final double num2 = 114.75;
        Assertions.assertTrue(pointsUtil.isMultipleOf(multiplier2, num2));
    }

    @Test
    void isMultipleOf_givenMultiplierAndNumNotDivisible_returnsFalse() {
        final double multiplier1 = 3;
        final double num1 = 1457.00;
        Assertions.assertFalse(pointsUtil.isMultipleOf(multiplier1, num1));

        final double multiplier2 = 0.25;
        final double num2 = 3.74;
        Assertions.assertFalse(pointsUtil.isMultipleOf(multiplier2, num2));
    }

    @Test
    void isOdd_givenOddNum_returnTrue() {
        final int num = 25;
        Assertions.assertTrue(pointsUtil.isOdd(num));
    }

    @Test
    void isOdd_givenEvenNum_returnFalse() {
        final int num = 28;
        Assertions.assertFalse(pointsUtil.isOdd(num));
    }

    @Test
    void isTimeBetween2pmAnd4pm_givenTime_returnTrue() {
        final String time = "15:13";
        Assertions.assertTrue(pointsUtil.isTimeBetween2pmAnd4pm(LocalTime.parse(time)));
    }

    @Test
    void isTimeBetween2pmAnd4pm_givenTime_returnFalse() {
        final String time = "07:13";
        Assertions.assertFalse(pointsUtil.isTimeBetween2pmAnd4pm(LocalTime.parse(time)));
    }

    private static Stream<Arguments> setupAlphaNumericStringScenarios() {
        return Stream.of(
                Arguments.of("23AndMe", 7),
                Arguments.of("A1 Steak Sauce", 12),
                Arguments.of("su:m37°", 5)
        );
    }
}
