package phonesshop.service;

import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.dto.PagePhonesListForWeb;

import java.io.IOException;

/**
 * Created by kostya.nikitin on 8/5/2016.
 */
public interface PhonesService {
    String getNameForFileWithPhoneImage(long id);
    String deleteImgPhone(long id);
    boolean uploadImgPhone(MultipartFile file, long id) throws IOException;
    void deletePhone(long id);
    String getNameFileWithPhoneImage(long id);
    Phones findOne(long id);
    Phones updatePhone(Phones phone);
    Phones updateExistingPhone(Phones phone);
    PagePhonesListForWeb findAllPage(int cur, int countonpage) throws Exception;
}
