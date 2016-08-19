package phonesshop.service;

import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.dto.PagePhonesListForWeb;

import java.io.IOException;

/**
 * Created by kostya.nikitin on 8/5/2016.
 */
@EnableGlobalMethodSecurity( securedEnabled = true)
public interface PhonesService {

    @Secured("ROLE_ADMIN")
    String deleteImgPhone(long id);

    @Secured("ROLE_ADMIN")
    boolean uploadImgPhone(MultipartFile file, long id) throws IOException;

    @Secured("ROLE_ADMIN")
    void deletePhone(long id);

    Phones findOne(long id);

    @Secured("ROLE_ADMIN")
    Phones updatePhone(Phones phone);

    @Secured("ROLE_ADMIN")
    Phones updateExistingPhone(Phones phone);

    PagePhonesListForWeb findAllPage(int cur, int countonpage) throws IllegalArgumentException;

    Resource getImagePhone(long id);
}
