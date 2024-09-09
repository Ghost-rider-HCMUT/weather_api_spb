package com.example.spb_api.util;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class StringUtil {
    // Constructor
    private StringUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Clean String
    public static String cleanString(String input) {
        String noDiacritics = removeDiacritics(input);
        // Delete " "; "-"
        String cleanedString = noDiacritics.replaceAll("[- ]", "");

        if (cleanedString.isEmpty()) {
            return cleanedString;
        }

        String result = cleanedString.toLowerCase();
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    // Remove accent marks
    private static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    // Handle Date
    public static LocalDateTime handleTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime currentTime = LocalDateTime.parse(time, formatter);
        return currentTime.minusMinutes(30);
    }
}
