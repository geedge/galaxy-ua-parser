package com.mesalab.ua;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: zhq
 * @create: 2020-08-21
 **/
public class Utils {

    private static final Pattern SUBSTITUTIONS_PATTERN = Pattern.compile("\\$\\d");

     static String getReplacement(Matcher matcher, String replacement) {
        if (isBackReference(replacement)) {
            int group = getGroup(replacement);
            return matcher.group(group);
        } else {
            return replacement;
        }
    }

     static String getReplaceResult(String model, Matcher matcher, int groupCount, String replacementString) {
        if (replacementString != null) {
            if (replacementString.contains("$")) {
                model = replacementString;
                for (String substitution : getSubstitutions(replacementString)) {
                    int i = Integer.parseInt(substitution.substring(1));
                    String replacement = matcher.groupCount() >= i && matcher.group(i) != null
                            ? Matcher.quoteReplacement(matcher.group(i)) : "";
                    model = model.replaceFirst("\\" + substitution, replacement);
                }
                model = model.trim();
            } else {
                model = replacementString;
            }
        } else if (groupCount >= 1) {
            model = matcher.group(1);
        }
        return model;
    }


     static List<String> getSubstitutions(String nameReplacement) {
        Matcher matcher = SUBSTITUTIONS_PATTERN.matcher(nameReplacement);
        List<String> substitutions = new ArrayList<String>();
        while (matcher.find()) {
            substitutions.add(matcher.group());
        }
        return substitutions;
    }

    /**
     * Checks if the replacement is a backreference (i.e. $1, $2, $3, etc) to a capturing group in the regular expression.
     */
     static boolean isBackReference(String replacement) {
        return replacement.startsWith("$");
    }

    /**
     * Extracts the group number from a backreference like $1, $2, $3, etc.
     */
     static int getGroup(String backReference) {
        return Integer.parseInt(backReference.substring(1));
    }



     static boolean contains(String str, String[] strArr) {
        if (strArr == null)
            return false;
        for (String arrStr : strArr) {
            if (str.contains(arrStr)) {
                return true;
            }
        }
        return false;
    }
     static String[] toLowerCase(String[] strArr) {
        if (strArr == null) return null;
        String[] res = new String[strArr.length];
        for (int i=0; i<strArr.length; i++) {
            res[i] = strArr[i].toLowerCase();
        }
        return res;
    }
}
