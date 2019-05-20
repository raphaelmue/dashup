package de.dashup.model.service;

import de.dashup.shared.Widget;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final Whitelist whitelist;

    static {
        whitelist = new Whitelist();
        whitelist.addAttributes("dashup-button", "name", "text", "mode", "api", "params", "consumers", "producers", "disabled");
        whitelist.addAttributes("dashup-chart", "name", "title", "category", "data");
        whitelist.addAttributes("dashup-checkbox", "name", "value", "checked", "disabled");
        whitelist.addAttributes("dashup-component", "name");
        whitelist.addAttributes("dashup-display", "name", "label", "displayedText", "quantity");
        whitelist.addAttributes("dashup-grid-layout", "name");
        whitelist.addAttributes("dashup-list", "name", "selectable");
        whitelist.addAttributes("dashup-radio-button-group", "name", "value");
        whitelist.addAttributes("dashup-radio-button", "name", "group", "value", "checked", "disabled");
        whitelist.addAttributes("dashup-switch", "name", "valueRight", "valueLeft", "active", "disabled");
        whitelist.addAttributes("dashup-text-input", "name", "value", "placeholder", "disabled");
    }

    public static boolean validateWidget(Widget widget) {
        return validateWidget(widget, false);
    }

    public static boolean validateWidget(Widget widget, boolean cleanCode) {
        boolean result = true;
        for (Widget.Size size : Widget.Size.values()) {
            if (widget.getCode(size) != null) {
                if (!Jsoup.isValid(widget.getCode(size), whitelist)) {
                    result = false;
                }

                if (cleanCode) {
                    widget.setCode(Jsoup.clean(widget.getCode(size), whitelist), size);
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }


    public static boolean validate(String string, @SuppressWarnings("SameParameterValue") String pattern) {
        // this class will be extended, so that the warning "Parameter is always the same" will disappear.
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
