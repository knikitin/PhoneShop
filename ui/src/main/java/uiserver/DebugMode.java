package uiserver;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DebugMode {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    public static void testArgsOnLoggingLevel(String[] args){
        String startLevel = logger.getName();
        for (String arg:args) {
            switch (arg){
                case "-llwarn": logger.setLevel(Level.WARN); break;
                case "-lldebug": logger.setLevel(Level.DEBUG); break;
                case "-llall": logger.setLevel(Level.ALL); break;
                case "-lloff": logger.setLevel(Level.OFF); break;
            }
        }
        if (!startLevel.equals(logger.getName())) {
            logger.warn("Change logger level from " + startLevel + " to " + logger.getName());
        }
    }

}
