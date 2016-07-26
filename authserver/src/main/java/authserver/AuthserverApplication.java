package authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.security.Principal;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
public class AuthserverApplication {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}


	@Autowired
	private DataSource dataSource;

	@Autowired
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.jdbcAuthentication().dataSource(dataSource);
		// @formatter:on
	}


}