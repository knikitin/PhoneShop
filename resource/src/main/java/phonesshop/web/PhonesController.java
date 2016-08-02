package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.domain.WirelessTechnology;
import phonesshop.util.DebugMode;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kostya.nikitin on 7/28/2016.
 */
@RestController
@RequestMapping(value="/phones")
public class PhonesController {


    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesRepository phonesRepository;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public Phones getOnePhone(@PathVariable long id, HttpServletResponse response) throws Exception {
        if (DebugMode.isDebug())
            logger.debug("Request to get phone with id =" + id );

        Phones onePhone = phonesRepository.findOne(id);
        if( null == onePhone ){
            response.setStatus( HttpStatus.NO_CONTENT.value());
            logger.warn("Request to get phone with id =" + id + " is not completed. Phone is not found");
        }
        else
            if (DebugMode.isDebug())
                logger.debug("Phone with id =" + id + " was found and was returned");
        return onePhone;
    }

    @RequestMapping(method= RequestMethod.POST)
    public Phones addOnePhones(@RequestBody Phones phones) throws Exception {
        try {
            if (DebugMode.isDebug())
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
            if (DebugMode.isDebug())
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
            if (DebugMode.isDebug())
                logger.debug("DeletePhone with id =" + id );
            phonesRepository.delete(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Phone: " + e.getMessage());
            throw e;
        }
    }

}
