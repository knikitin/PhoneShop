package phonesshop.service;

import phonesshop.domain.Phones;
import phonesshop.dto.PagePhonesListForWeb;

/**
 * Created by kostya.nikitin on 8/5/2016.
 */
public interface PhonesService {
    String getNameForFileWithPhoneImage(long id);
    String deleteImgPhone(String root, long id);
    void deletePhone(String root, long id);
    String getNameFileWithPhoneImage( String root, long id);
    Phones findOne(long id);
    Phones updatePhone(Phones phone);
    PagePhonesListForWeb findAllPage(int cur, int countonpage) throws Exception;
}
