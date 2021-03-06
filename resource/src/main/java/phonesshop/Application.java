package phonesshop;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RestController;
import phonesshop.util.DebugMode;

@SpringBootApplication
@RestController
@EnableResourceServer
public class Application extends ResourceServerConfigurerAdapter {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    public static void main(String[] args) {

        logger.debug("Start application.");
        DebugMode.testArgsOnLoggingLevel(args);

        SpringApplication.run(Application.class, args);
        logger.debug("Made application launch.");

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/phoneslist/**","/img/**").permitAll()
                .antMatchers(HttpMethod.GET, "/directorynavigations", "/wirelesstechnology", "/phones/**").permitAll()
                .antMatchers("/phones/**", "/directorynavigations/**", "/wirelesstechnology/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

}


