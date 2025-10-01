package org.vm.test.plugins;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DetailedReporter implements EventListener {

    private static final String REPORT_FILE = "target/cucumber-reports/detailed-api-report.txt";
    private FileWriter writer;

    public DetailedReporter() {
        try {
            writer = new FileWriter(REPORT_FILE, true);
            writer.write("\n" + "=".repeat(80) + "\n");
            writer.write("DETAILED API TEST REPORT - "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("=".repeat(80) + "\n\n");
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        try {
            writer.write("\n" + "-".repeat(60) + "\n");
            writer.write("TEST CASE: " + event.getTestCase().getName() + "\n");
            writer.write("FEATURE: " + event.getTestCase().getUri() + "\n");
            writer.write("STARTED AT: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
            writer.write("-".repeat(60) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        try {
            writer.write("\nTEST CASE RESULT: " + event.getResult().getStatus() + "\n");
            writer.write("FINISHED AT: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
            if (event.getResult().getError() != null) {
                writer.write("ERROR: " + event.getResult().getError().getMessage() + "\n");
            }
            writer.write("-".repeat(60) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }

    private void handleTestStepStarted(TestStepStarted event) {
        try {
            writer.write("\nSTEP: " + event.getTestStep().getCodeLocation() + "\n");
            writer.write(
                    "STARTED AT: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        try {
            writer.write("RESULT: " + event.getResult().getStatus() + "\n");
            writer.write("DURATION: " + event.getResult().getDuration().toMillis() + " ms\n");
            if (event.getResult().getError() != null) {
                writer.write("ERROR: " + event.getResult().getError().getMessage() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.write("\n" + "=".repeat(80) + "\n");
                writer.write("REPORT END - "
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                writer.write("=".repeat(80) + "\n");
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error writing to detailed report: " + e.getMessage());
        }
    }
}
