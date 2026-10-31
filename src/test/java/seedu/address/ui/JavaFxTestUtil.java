package seedu.address.ui;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * Provides helpers for running assertions on the JavaFX Application Thread.
 */
final class JavaFxTestUtil {
    private static final int TIMEOUT_SECONDS = 15;

    private JavaFxTestUtil() {}

    /**
     * Runs {@code checks} on the JavaFX Application Thread and propagates failures to the test thread.
     */
    static void runOnJavaFxThread(Callable<Void> checks) throws Exception {
        FutureTask<Void> task = new FutureTask<>(() -> {
            Platform.setImplicitExit(false);
            return checks.call();
        });
        try {
            Platform.startup(task);
        } catch (IllegalStateException e) {
            // Another UI test has already started the shared JavaFX toolkit.
            Platform.runLater(task);
        }
        task.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
}
