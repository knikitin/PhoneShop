package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.service.PhonesService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kostya.nikitin on 7/28/2016.
 */
@RestController
@RequestMapping(value="/phones")
public class PhonesController {


    private static final Logger logger = Logger.getLogger("forPhonesShop");

    private final ResourceLoader resourceLoader;

    @Autowired
    public PhonesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    PhonesService phonesService;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public Phones getOnePhone(@PathVariable long id, HttpServletResponse response) throws Exception {
        logger.debug("Request to get phone with id =" + id );

        Phones onePhone = phonesService.findOne(id);
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
            return phonesService.updatePhone(phones);
        } catch (Exception e) {
            logger.error(" In addOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public Phones updateOnePhones(@PathVariable long id, @RequestBody Phones phones, HttpServletResponse response) throws Exception {
        try {
            logger.debug("updateOnePhones. Update Phones with id =" + id);
            Phones onePhone = phonesService.updateExistingPhone(phones);
            if (onePhone != null) {
                response.setStatus( HttpStatus.OK.value());
                return onePhone;
            } else {
                response.setStatus( HttpStatus.CONFLICT.value());
                return null;
            }
        } catch (Exception e) {
            logger.error(" In updateOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOnePhones(@PathVariable long id) {
        try {
            logger.debug("DeletePhone with id =" + id );
            phonesService.deletePhone(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Phone: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileUpload(@PathVariable long id,
                                   @RequestParam("file") MultipartFile file) {
        logger.debug("handleFileUpload. Upload image for phone with id =" + id );

        try {
            if (phonesService.uploadImgPhone(file, id)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You failed to upload image because the file was empty" );
            }
        }
        catch (Exception e) {
            logger.debug("handleFileUpload. Error upload for phone with id =" + id +". Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error save for phone with id =" +  e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileDelete(@PathVariable long id) {
        logger.debug("Delete image for phone with id =" + id );
        String response = phonesService.deleteImgPhone(id);
        switch (response){
            case "ok": return ResponseEntity.ok().build();
            case "notFound": return ResponseEntity.notFound().build();
            default: return ResponseEntity.status(HttpStatus.CONFLICT).body( response );
        }

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable long id) {
        logger.debug("Get image for phone with id =" + id );
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + phonesService.getNameFileWithPhoneImage(id)));
        } catch (Exception e) {
            logger.error("In getting image for phone with id =" + id +" was error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
