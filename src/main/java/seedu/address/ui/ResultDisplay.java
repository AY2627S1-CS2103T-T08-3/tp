package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.Messages;

/**
 * A UI component that displays the result of a command execution.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final String WARNING_STYLE_CLASS = "warning";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Displays {@code feedbackToUser}, highlighting it if it contains a warning so that the warning is not missed.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        resultDisplay.getStyleClass().remove(WARNING_STYLE_CLASS);
        if (Messages.hasWarning(feedbackToUser)) {
            resultDisplay.getStyleClass().add(WARNING_STYLE_CLASS);
        }
    }

}
