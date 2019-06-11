package de.dashup.model.service;

import de.dashup.shared.layout.Widget;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final Whitelist WHITELIST;

    static {
        WHITELIST = new Whitelist();
        WHITELIST.addAttributes("dashup-button", "name", "layout", "text", "disabled", "handleOnStart",
                                                     "mode", "dataAPI", "storageAPI", "params", "consumers", "producers");
        WHITELIST.addAttributes("dashup-chart", "name", "layout", "title", "category");
        WHITELIST.addAttributes("dashup-checkbox", "name", "layout", "value", "checked", "disabled");
        WHITELIST.addAttributes("dashup-clock", "name", "layout");
        WHITELIST.addAttributes("dashup-component", "name", "layout");
        WHITELIST.addAttributes("dashup-display", "name", "layout", "label", "displayedText", "quantity");
        WHITELIST.addAttributes("dashup-grid-layout", "name", "layout");
        WHITELIST.addAttributes("dashup-list", "name", "layout", "items", "selectable");
        WHITELIST.addAttributes("dashup-radio-button-group", "name", "layout", "value");
        WHITELIST.addAttributes("dashup-radio-button", "name", "layout", "group", "value", "checked", "disabled");
        WHITELIST.addAttributes("dashup-switch", "name", "layout", "valueRight", "valueLeft", "active", "disabled");
        WHITELIST.addAttributes("dashup-text-input", "name", "layout", "value", "placeholder", "disabled", "clear");
    }

    private Validator() {
        super();
    }


    public static boolean validateWidget(Widget widget) {
        return validateWidget(widget, false);
    }

    public static boolean validateWidget(Widget widget, boolean cleanCode) {
        boolean result = true;
        for (Widget.Size size : Widget.Size.values()) {
            if (widget.getCode(size) != null) {
                if (!Jsoup.isValid(widget.getCode(size), WHITELIST)) {
                    result = false;
                }

                if (cleanCode) {
                    widget.setCode(Jsoup.clean(widget.getCode(size), WHITELIST), size);
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
