package org.fetch.exercise.util;

import java.time.LocalTime;

public class PointsUtil {

    private static final String TIME_2PM = "14:00";
    private static final String TIME_4PM = "16:00";

    public int getAlphaNumericCharacterCount(final String retailerName) {
        final int length = retailerName.length();
        int alphaNumCharCount = 0;
        for(int i = 0; i < length; i++){
            if (Character.isLetterOrDigit(retailerName.charAt(i))) {
                alphaNumCharCount++;
            }
        }
        return alphaNumCharCount;
    }

    public boolean isRoundNumber(final double number) {
        return number % 1 == 0;
    }

    public boolean isMultipleOf(final double multipleOf, final double value) {
        return value % multipleOf == 0;
    }

    public boolean isOdd(final int value) {
        return value % 2 != 0;
    }

    public boolean isTimeBetween2pmAnd4pm(final LocalTime localTime) {
        return localTime.isAfter(LocalTime.parse(TIME_2PM)) && localTime.isBefore(LocalTime.parse(TIME_4PM));
    }
}
