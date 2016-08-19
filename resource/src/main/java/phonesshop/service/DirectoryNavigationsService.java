package phonesshop.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import phonesshop.domain.DirectoryNavigations;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
@EnableGlobalMethodSecurity( securedEnabled = true)
public interface DirectoryNavigationsService {

    DirectoryNavigations getOneNavigation(long id);

    @Secured("ROLE_ADMIN")
    DirectoryNavigations addOneNavigation(DirectoryNavigations oneNavigation);

    @Secured("ROLE_ADMIN")
    DirectoryNavigations updateOneNavigation( long id, DirectoryNavigations oneNavigation);

    List<DirectoryNavigations> findAll();

    @Secured("ROLE_ADMIN")
    void deleteOneNavigation(long id);
}
