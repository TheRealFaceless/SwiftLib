package dev.faceless.swiftlib.lib.text;

import dev.faceless.swiftlib.SwiftLib;
import lombok.Getter;

import java.util.logging.Logger;

public class ConsoleLogger {

    @Getter private static final Logger logger = SwiftLib.getPluginLogger();

    public static void logWarning(String msg) {
        logger.warning(msg);
    }
    public static void logInfo(String msg) {
        logger.info(msg);
    }
    public static void logError(String msg) {
        logger.severe(msg);
    }
}
