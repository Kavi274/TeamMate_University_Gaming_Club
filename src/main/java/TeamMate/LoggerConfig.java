package TeamMate;

import java.io.IOException;
import java.util.logging.*;

/**
 * LoggerConfig
 * Centralized logging configuration for the TeamMate CLI.
 */
public class LoggerConfig {

    // Match existing CLI log file
    private static final String LOG_FILE = "teamate.log.0";

    /**
     * Initialize logging configuration.
     */
    public static void setup() {

        try {
            Logger rootLogger = Logger.getLogger("");

            // Remove default handlers
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            // File handler (append mode)
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);

            // Console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO);

            rootLogger.addHandler(fileHandler);
            rootLogger.addHandler(consoleHandler);
            rootLogger.setLevel(Level.INFO);

        } catch (IOException e) {
            System.err.println("Failed to initialize logging: " + e.getMessage());
        }
    }
}
