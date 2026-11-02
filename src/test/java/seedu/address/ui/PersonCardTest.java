package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import javafx.scene.control.Label;
import seedu.address.model.person.Stage;
import seedu.address.testutil.PersonBuilder;

class PersonCardTest {

    @Test
    void getStageStyleClass_eachStage_returnsDistinctHyphenatedClass() {
        assertEquals("stage-enquiry", PersonCard.getStageStyleClass(Stage.ENQUIRY));
        assertEquals("stage-shot-completed", PersonCard.getStageStyleClass(Stage.SHOT_COMPLETED));
        assertEquals("stage-awaiting-payment", PersonCard.getStageStyleClass(Stage.AWAITING_PAYMENT));
        assertEquals(Stage.values().length,
                Arrays.stream(Stage.values()).map(PersonCard::getStageStyleClass).distinct().count());
    }

    @Test
    void getStageStyleClass_turkishDefaultLocale_matchesStylesheetClass() {
        Locale originalLocale = Locale.getDefault();
        try {
            // Turkish lowercases I to a dotless i, which would not match the stylesheet
            Locale.setDefault(Locale.forLanguageTag("tr"));
            assertEquals("stage-enquiry", PersonCard.getStageStyleClass(Stage.ENQUIRY));
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "RUN_UI_TESTS", matches = "true")
    void constructor_bookedEngagement_showsColouredStageBadge() throws Exception {
        JavaFxTestUtil.runOnJavaFxThread(() -> {
            PersonCard card = new PersonCard(new PersonBuilder().withStage("Booked").build(), 1);
            Label stage = (Label) card.getRoot().lookup("#stage");

            assertEquals("Booked", stage.getText());
            assertTrue(stage.getStyleClass().containsAll(Arrays.asList("stage-chip", "stage-booked")));
            return null;
        });
    }
}
