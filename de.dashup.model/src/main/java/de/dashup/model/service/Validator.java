package de.dashup.model.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Validator {
    static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    static boolean validate(String string, String pattern) {
        return validate(string, Pattern.compile(pattern, Pattern.CASE_INSENSITIVE));
    }

        /**
         * This method validates the input email address with EMAIL_REGEX pattern
         *
         * @param string  string to validate
         * @param pattern pattern which applies to the string
         * @return boolean
         */
    private static boolean validate(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
