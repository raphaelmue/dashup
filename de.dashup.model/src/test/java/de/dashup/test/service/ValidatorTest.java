package de.dashup.test.service;

import de.dashup.model.service.Validator;
import de.dashup.shared.Widget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@SuppressWarnings("WeakerAccess")
public class ValidatorTest {
    @Test
    public void testValidateRegex() {
        final String validEmail = "test@test.com";
        Assertions.assertTrue(Validator.validate(validEmail, Validator.EMAIL_REGEX));

        final String invalidEmail = "not a valid mail";
        Assertions.assertFalse(Validator.validate(invalidEmail, Validator.EMAIL_REGEX));
    }

    @Test
    public void testValidateWidget() {
        Widget widget = new Widget(0, "Widget Name", "Description", 0, 0, 0, 0, LocalDate.now(), "cloud");
        Assertions.assertFalse(Validator.validateWidget(widget));

        widget.setShortDescription("Short Description");
        final String validHTML = "<dashup-display label=\"valid\"></dashup-display>";
        final String invalidHTML = "<label label=\"valid\"></dashup-display>";
        widget.setCodeLarge(validHTML);
        widget.setCodeMedium(validHTML);
        widget.setCodeSmall(invalidHTML);

        Assertions.assertFalse(Validator.validateWidget(widget, true));
        Assertions.assertNotEquals(invalidHTML, widget.getCodeSmall());
        Assertions.assertTrue(Validator.isNullOrEmpty(widget.getCodeSmall()));
    }
}
