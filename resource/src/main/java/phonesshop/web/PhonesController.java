package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity( securedEnabled = true)
public class PhonesController {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    PhonesService phonesService;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public Phones getOnePhone(@PathVariable long id, HttpServletResponse response) throws Exception {

        Phones onePhone = phonesService.findOne(id);
        if( null == onePhone ){
            response.setStatus( HttpStatus.NO_CONTENT.value());
            logger.warn("Request to get phone with id =" + id + " is not completed. Phone is not found");
        }
        else
            logger.debug("Done (id =" + id + "). Phone ( " + onePhone + " ) was found and was returned");
        return onePhone;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method= RequestMethod.POST)
    public Phones addOnePhones(@RequestBody Phones phones) throws Exception {
        try {
            Phones onePhone = phonesService.updatePhone(phones);
            logger.debug("Done( phone = " + phones+") = "+onePhone);
            return onePhone;
        } catch (Exception e) {
            logger.error(" Parameter (phones = " + phones + ") Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public Phones updateOnePhones(@PathVariable long id, @RequestBody Phones phones, HttpServletResponse response) throws Exception {
        try {
            Phones onePhone = phonesService.updateExistingPhone(phones);
            if (onePhone != null) {
                response.setStatus( HttpStatus.OK.value());
                logger.debug("Done( id = " + id + " , phone = " + phones+" ) = " + onePhone);
                return onePhone;
            } else {
                response.setStatus( HttpStatus.CONFLICT.value() );
                logger.warn("Request to update phone ( id = " + id + " , phone = " + phones + " ) a is not completed. Phone is not found");
                return null;
            }
        } catch (Exception e) {
            logger.error(" Parameters ( id = " + id + " , phones = " + phones + "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOnePhones(@PathVariable long id) {
        try {
            phonesService.deletePhone(id);
            logger.debug("Done ( id = " + id + " )");
        } catch (Exception e) {
            logger.error(" Parameter ( id = " + id + " ). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileUpload(@PathVariable long id,
                                   @RequestParam("file") MultipartFile file) {
        try {
            if (phonesService.uploadImgPhone(file, id)) {
                logger.debug("Upload image for phone with id =" + id );
                return ResponseEntity.ok().build();
            } else {
                logger.debug("Failed image for phone with id =" + id + " because the file was empty");
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You failed to upload image because the file was empty" );
            }
        }
        catch (Exception e) {
            logger.error(" Parameter ( id = " + id + " ). Error " + e.getClass() + " with message " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error save for phone with id =" + id);
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?>  handleFileDelete(@PathVariable long id) {
        String response = phonesService.deleteImgPhone(id);
        switch (response){
            case "ok": {
                logger.debug("Done( id =" + id + " )");
                return ResponseEntity.ok().build();
            }
            case "notFound": {
                logger.warn("Not delete ( id =" + id + " )");
                return ResponseEntity.notFound().build();
            }
            default: {
                logger.warn("Error ( id =" + id + " ) not delete in service layer. " +  response);
                return ResponseEntity.status(HttpStatus.CONFLICT).body( response );
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}/img")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable long id) {
        try {
            return ResponseEntity.ok(phonesService.getImagePhone(id));
        } catch (Exception e) {
            logger.error(" Error ( id = " + id + " ). Error " + e.getClass() + " with message " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
