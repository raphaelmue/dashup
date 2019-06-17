package de.dashup.model.service;

import de.dashup.shared.layout.Widget;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    /**
     * Literals
     */
    private static final String DISABLED = "disabled";
    private static final String LAYOUT = "layout";
    private static final String VALUE = "value";

    public static final String EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final Whitelist WHITELIST;

    static {
        WHITELIST = new Whitelist();
        WHITELIST.addAttributes("dashup-button", "name", LAYOUT, "text", DISABLED, "handleOnStart",
                                                     "mode", "dataAPI", "storageAPI", "params", "consumers", "producers");
        WHITELIST.addAttributes("dashup-chart", "name", LAYOUT, "title", "category");
        WHITELIST.addAttributes("dashup-checkbox", "name", LAYOUT, VALUE, "checked", DISABLED);
        WHITELIST.addAttributes("dashup-clock", "name", LAYOUT);
        WHITELIST.addAttributes("dashup-component", "name", LAYOUT);
        WHITELIST.addAttributes("dashup-display", "name", LAYOUT, "label", "displayedText", "quantity");
        WHITELIST.addAttributes("dashup-grid-layout", "name", LAYOUT);
        WHITELIST.addAttributes("dashup-list", "name", LAYOUT, "items", "selectable");
        WHITELIST.addAttributes("dashup-radio-button-group", "name", LAYOUT, VALUE);
        WHITELIST.addAttributes("dashup-radio-button", "name", LAYOUT, "group", VALUE, "checked", DISABLED);
        WHITELIST.addAttributes("dashup-switch", "name", LAYOUT, "valueRight", "valueLeft", "active", DISABLED);
        WHITELIST.addAttributes("dashup-text-input", "name", LAYOUT, VALUE, "placeholder", DISABLED, "clear");
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
