package phonesshop;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import phonesshop.util.DebugMode;

@SpringBootApplication
public class Application {


    private static final Logger logger = Logger.getLogger("forPhonesShop");

    public static void main(String[] args) {

        if (args.length > 0)
            if (args[0].equals("debug")) {
                logger.info("Start application with Debug.");
                DebugMode.setDebugModeOn();
            }

        SpringApplication.run(Application.class, args);
        if (DebugMode.isDebug())
            logger.debug("Made application launch.");

    }

}
