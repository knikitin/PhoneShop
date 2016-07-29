package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.util.DebugMode;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kostya.nikitin on 7/28/2016.
 */
@RestController
public class PhoneController {


    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesRepository phonesRepository;

    @RequestMapping(value="/phone/{id:[\\d]+}", method= RequestMethod.GET)
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

}
