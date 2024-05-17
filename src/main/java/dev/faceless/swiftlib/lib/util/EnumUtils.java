package dev.faceless.swiftlib.lib.util;

@SuppressWarnings("unused")
public class EnumUtils {
    public static String toHumanReadableString(Enum<?> enumValue) {
        String name = enumValue.name();
        String[] words = name.split("_");
        StringBuilder humanReadableName = new StringBuilder();

        for (String word : words) {
            if (!humanReadableName.isEmpty()) {
                humanReadableName.append(" ");
            }
            humanReadableName.append(word.substring(0, 1).toUpperCase())
                             .append(word.substring(1).toLowerCase());
        }

        return humanReadableName.toString();
    }
}
