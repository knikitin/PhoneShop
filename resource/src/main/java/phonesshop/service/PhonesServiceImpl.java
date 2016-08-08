package phonesshop.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.dto.PagePhonesListForWeb;
import phonesshop.dto.PhoneForList;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by kostya.nikitin on 8/4/2016.
 */
@Service
public class PhonesServiceImpl implements PhonesService{
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesRepository phonesRepository;

    @Override
    public String getNameForFileWithPhoneImage(long id){
        return Long.toString(id, 16)+".jpg";
    }

    @Override
    public String deleteImgPhone(String root, long id){
        String filename =  getNameForFileWithPhoneImage(id);
        logger.debug("Delete image for phone with id =" + id );
        try {
            File f1 = new File(root + "/" + filename);
            if (f1.exists()) {
                if (f1.delete())
                    return "ok";
                else
                    return "You failed to delete " + filename;
            } else {
                return "notFound";
            }
        } catch (Exception e) {
            return "notFound";
        }
    }

    @Override
    @Transactional
    public void deletePhone(String root, long id){
        deleteImgPhone(root, id);
        phonesRepository.delete(id);
    }

    @Override
    public String getNameFileWithPhoneImage( String root, long id){
        String filename =  getNameForFileWithPhoneImage(id);
        if (new File(root + "/" + filename).exists()) {
            return Paths.get(root, filename).toString();
        } else {
            return Paths.get(root, "no-image.jpg").toString();
        }

    }

    @Override
    public Phones findOne(long id){
        return phonesRepository.findOne(id);
    }

    @Override
    public Phones updatePhone(Phones phone){
        return phonesRepository.saveAndFlush(phone);
    }

    public PagePhonesListForWeb findAllPage(int cur, int countonpage) throws Exception{
        // test correct value cur and countpage
        if ((countonpage > 200)||(countonpage < 5) ) {
            throw new Exception("Error number of phones on the page. The number must be in the range of 5 ... 200");
        }

        Page<Phones> pagePhones = phonesRepository.findAll(new PageRequest(cur-1, countonpage));

        Page<PhoneForList> pageSmallPhones = pagePhones.map(PhoneForList::new);

        return new PagePhonesListForWeb(pageSmallPhones.getContent(), pageSmallPhones.getTotalPages());

    };


}
