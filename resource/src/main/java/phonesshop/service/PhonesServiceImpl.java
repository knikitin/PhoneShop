package phonesshop.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.dto.PagePhonesListForWeb;
import phonesshop.dto.PhoneForList;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Value("${resource.img.root}")
    public String ROOT;

    private final ResourceLoader resourceLoader;

    @Autowired
    public PhonesServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String deleteImgPhone(long id){
        String filename =  getNameForFileWithPhoneImage(id);
        logger.debug("Delete image for phone with id =" + id );
        try {
            File f1 = new File(ROOT + "/" + filename);
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
    public boolean uploadImgPhone(MultipartFile file, long id) throws IOException {
        String filename =  getNameForFileWithPhoneImage(id);
        logger.debug("Upload image for phone with id =" + id );

        if (!file.isEmpty()) {
            try {
                logger.debug("Image not empty for phone with id =" + id );

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File( ROOT +"/"+ filename)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                logger.debug("Image save for phone with id =" + id );
                return true;
            }
            catch (Exception e) {
                logger.debug("Error upload for phone with id =" + id +". Error:" + e.getMessage());
                throw e;
            }
        }
        else {
            logger.error("Upload empty image for phone with id =" + id);
            return false;
        }
    };


    @Override
    @Transactional
    public void deletePhone(long id){
        deleteImgPhone(id);
        phonesRepository.delete(id);
    }

    @Override
    public String getNameFileWithPhoneImage(long id){
        String filename =  getNameForFileWithPhoneImage(id);
        if (new File(ROOT + "/" + filename).exists()) {
            return Paths.get(ROOT, filename).toString();
        } else {
            return Paths.get(ROOT, "no-image.jpg").toString();
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

    @Override
    public Phones updateExistingPhone(Phones phone){
        Phones onePhone = findOne(phone.getId());
        if (onePhone != null){
            return updatePhone(phone);
        }
        return null;
    };

    @Override
    public PagePhonesListForWeb findAllPage(int cur, int countonpage) throws Exception{
        // test correct value cur and countpage
        if ((countonpage > 200)||(countonpage < 5) ) {
            throw new Exception("Error number of phones on the page. The number must be in the range of 5 ... 200");
        }

        Page<Phones> pagePhones = phonesRepository.findAll(new PageRequest(cur-1, countonpage));

        Page<PhoneForList> pageSmallPhones = pagePhones.map(PhoneForList::new);

        return new PagePhonesListForWeb(pageSmallPhones.getContent(), pageSmallPhones.getTotalPages());
    };

    @Override
    public Resource getImagePhone(long id){
        return resourceLoader.getResource("file:" + getNameFileWithPhoneImage(id));
    }


}
