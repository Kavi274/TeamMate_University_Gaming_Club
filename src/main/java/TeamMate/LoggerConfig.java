package TeamMate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * Central logger configuration. Call LoggerConfig.setup() once at application start.
 * Produces console output and a rotating file "teamate.log".
 */
public class LoggerConfig {
    private static final String LOG_FILE = "teamate.log";
    private static final int FILE_LIMIT = 5 * 1024 * 1024; // 5 MB
    private static final int FILE_COUNT = 3;

    public static void setup() {
        Logger root = Logger.getLogger("");
        // Remove default handlers (optional)
        Handler[] handlers = root.getHandlers();
        for (Handler h : handlers) {
            root.removeHandler(h);
        }

        // Console handler
        ConsoleHandler console = new ConsoleHandler();
        console.setLevel(Level.INFO);
        console.setFormatter(new SimpleFormatter());
        root.addHandler(console);

        // File handler with rotation
        try {
            Path logPath = Paths.get(System.getProperty("user.dir")).toAbsolutePath().resolve(LOG_FILE);
            // ensure directory exists (usually project root)
            Files.createDirectories(logPath.getParent());
            // create rotating file handler
            FileHandler fileHandler = new FileHandler(logPath.toString(), FILE_LIMIT, FILE_COUNT, true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter());
            root.addHandler(fileHandler);

            root.setLevel(Level.INFO);
            root.log(Level.INFO, "Logger initialized. Log file: {0}", logPath.toString());
            // include reference to uploaded brief for context (if exists)
            Path brief = Paths.get("/mnt/data/02. Assessment Brief COURSEWORK CM2601.pdf");
            if (Files.exists(brief)) {
                root.log(Level.INFO, "Assessment brief found at: {0}", brief.toAbsolutePath().toString());
            } else {
                root.log(Level.FINE, "Assessment brief not found at the expected uploaded path.");
            }
        } catch (IOException ioe) {
            // fallback: at least keep console logging
            root.addHandler(console);
            root.log(Level.SEVERE, "Failed to create log file handler: " + ioe.getMessage(), ioe);
        }
    }
}

