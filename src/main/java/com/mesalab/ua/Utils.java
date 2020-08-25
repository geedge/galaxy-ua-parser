package com.mesalab.ua;

import java.util.regex.Matcher;

/**
 * @description:
 * @author: zhq
 * @create: 2020-08-21
 **/
public class Utils {

    public static String getReplacement(Matcher matcher, String replacement) {
        if (isBackReference(replacement)) {
            int group = getGroup(replacement);
            return matcher.group(group);
        } else {
            return replacement;
        }
    }

    /**
     * Checks if the replacement is a backreference (i.e. $1, $2, $3, etc) to a capturing group in the regular expression.
     */
    public static boolean isBackReference(String replacement) {
        return replacement.startsWith("$");
    }
    /**
     * Extracts the group number from a backreference like $1, $2, $3, etc.
     */
    public static int getGroup(String backReference) {
        return Integer.valueOf(backReference.substring(1));
    }
}
