package phonesshop.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import phonesshop.domain.WirelessTechnology;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
@EnableGlobalMethodSecurity( securedEnabled = true)
public interface WirelessTechnologyService {

    WirelessTechnology getOneTechnology(long id);

    @Secured("ROLE_ADMIN")
    WirelessTechnology addOneTechnology(WirelessTechnology oneTechnology);

    @Secured("ROLE_ADMIN")
    WirelessTechnology updateOneTechnology( long id, WirelessTechnology oneTechnology);

    List<WirelessTechnology> findAll();

    @Secured("ROLE_ADMIN")
    void deleteOneTechnology(long id);
}
