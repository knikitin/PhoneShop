package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.service.PhonesService;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

/**
 * Created by kostya.nikitin on 7/28/2016.
 */
@RestController
@RequestMapping(value="/phones")
public class PhonesController {


    private static final Logger logger = Logger.getLogger("forPhonesShop");

    public static final String ROOT = "static/img";

    private final ResourceLoader resourceLoader;

    @Autowired
    public PhonesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private PhonesRepository phonesRepository;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public Phones getOnePhone(@PathVariable long id, HttpServletResponse response) throws Exception {
        logger.debug("Request to get phone with id =" + id );

        Phones onePhone = phonesRepository.findOne(id);
        if( null == onePhone ){
            response.setStatus( HttpStatus.NO_CONTENT.value());
            logger.warn("Request to get phone with id =" + id + " is not completed. Phone is not found");
        }
        else
            logger.debug("Phone with id =" + id + " was found and was returned");
        return onePhone;
    }

    @RequestMapping(method= RequestMethod.POST)
    public Phones addOnePhones(@RequestBody Phones phones) throws Exception {
        try {
            logger.debug("Add new Phones with name =" + phones.getModel()+" "+phones.getBrand());
            return phonesRepository.saveAndFlush(phones);
        } catch (Exception e) {
            logger.error(" In addOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public Phones updateOnePhones(@PathVariable long id, @RequestBody Phones phones) throws Exception {
        try {
            logger.debug("Update Phones with id =" + id);
            Phones onePhone = phonesRepository.findOne(phones.getId());
            if (onePhone != null){
                return phonesRepository.saveAndFlush(phones);
            }
            return null;
        } catch (Exception e) {
            logger.error(" In updateOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOnePhones(@PathVariable long id) {
        try {
            logger.debug("DeletePhone with id =" + id );
            PhonesService.deleteImgPhone(ROOT, id);
            phonesRepository.delete(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileUpload(@PathVariable long id,
                                   @RequestParam("file") MultipartFile file) {
        String filename =  Long.toString(id, 16)+".jpg";
        logger.debug("Upload image for phone with id =" + id );

        if (!file.isEmpty()) {
            try {
                logger.debug("Image not empty for phone with id =" + id );

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File( ROOT +"/"+ filename)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                logger.debug("Image save for phone with id =" + id );
                return ResponseEntity.ok().build();
            }
            catch (Exception e) {
                logger.debug("Error upload for phone with id =" + id +". Error:" + e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error save for phone with id =" +  e.getMessage());
            }
        }
        else {
            logger.error("Upload empty image for phone with id =" + id);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You failed to upload " + filename + " because the file was empty" );
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileDelete(@PathVariable long id) {
        logger.debug("Delete image for phone with id =" + id );
        return PhonesService.deleteImgPhone(ROOT, id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable long id) {
        logger.debug("Get image for phone with id =" + id );
        String filename =  Long.toString(id, 16)+".jpg";
        try {
            if (new File(ROOT + "/" + filename).exists()) {
                return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
            } else {
                return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, "no-image.jpg").toString()));
            }
        } catch (Exception e) {
            logger.error("In getting image for phone with id =" + id +" was error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
