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
import phonesshop.util.FilesOperationsService;

import java.io.File;
import java.io.IOException;

/**
 * Created by kostya.nikitin on 8/4/2016.
 */
@Service
public class PhonesServiceImpl implements PhonesService{
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesRepository phonesRepository;

    @Value("${resource.img.root}")
    public String ROOT;

    private final ResourceLoader resourceLoader;

    @Autowired
    private FilesOperationsService filesOperationsService;

    @Autowired
    public PhonesServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private String getNameForFileWithPhoneImage(long id){
        return Long.toString(id, 16)+".jpg";
    }

    @Override
    public String deleteImgPhone(long id){
        String filename =  getNameForFileWithPhoneImage(id);
        logger.debug("Delete image for phone with id =" + id );
        try {
            File f1 = new File(ROOT + "/" + filename);
            if (filesOperationsService.exists(f1)) {
                if (filesOperationsService.delete(f1)){
                    logger.debug("Done (id = " + id +")");
                    return "ok";
                }
                else{
                    logger.debug("Failed (id = " + id +" ). File name = " + filename);
                    return "You failed to delete " + filename;
                }
            } else {
                logger.debug("Not found (id = " + id +" ). File name = " + filename);
                return "notFound";
            }
        } catch (Exception e) {
            logger.error("Error (id = " + id +" ). File name = " + filename + ". Error class " + e.getClass() + " with message :" + e.getMessage());
            return "notFound";
        }
    }

    @Override
    public boolean uploadImgPhone(MultipartFile file, long id) throws IOException {
        String filename =  getNameForFileWithPhoneImage(id);

        if (!file.isEmpty()) {
            try {
                filesOperationsService.copy(file, ROOT +"/"+ filename);
                logger.debug("Done (id =" + id + " ). File name = " + filename);
                return true;
            }
            catch (Exception e) {
                logger.error("Error (id =" + id +" ). Error class " + e.getClass() + " with message :" + e.getMessage());
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
        logger.debug("Done ( id =" + id + " )");
    }

    private String getNameFileWithPhoneImage(long id){
        String filename =  getNameForFileWithPhoneImage(id);
        if (filesOperationsService.exists(new File(ROOT + "/" + filename))) {
            return filesOperationsService.getToString(ROOT, filename);
        } else {
            return filesOperationsService.getToString(ROOT, "no-image.jpg");
        }
    }

    @Override
    public Phones findOne(long id){
        return phonesRepository.findOne(id);
    }

    @Override
    public Phones updatePhone(Phones phone){
        Phones onePhone = phonesRepository.saveAndFlush(phone);
        logger.debug("Done ( phone =" + phone + " ) = " + onePhone);
        return onePhone;
    }

    @Override
    public Phones updateExistingPhone(Phones phone){
        Phones onePhone = findOne(phone.getId());
        if (onePhone != null){
            onePhone = updatePhone(phone);
            logger.debug("Done ( phone =" + phone + " ) = " + onePhone);
            return onePhone;
        }
        logger.error("Error. Not found ( phone =" + phone + " )" );
        return null;
    };

    @Override
    public PagePhonesListForWeb findAllPage(int cur, int countonpage) throws IllegalArgumentException {
        // test correct value cur and countpage
        if ((countonpage > 200)||(countonpage < 5) ) {
            throw new IllegalArgumentException("Error number of phones on the page. The number must be in the range of 5 ... 200");
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
