package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.WirelessTechnology;
import phonesshop.service.WirelessTechnologyService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by kostya.nikitin on 8/1/2016.
 */
@RestController
@RequestMapping("/wirelesstechnology")
@EnableGlobalMethodSecurity( securedEnabled = true)
public class WirelessTechnologyController {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private WirelessTechnologyService wirelessTechnologyService;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public WirelessTechnology getOneWireless(@PathVariable long id, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Request to get Wireless Technology with id =" + id );
            WirelessTechnology oneWT = wirelessTechnologyService.getOneTechnology(id);
            if( null == oneWT ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.warn("Request to get Wireless Technology with id =" + id + " is not completed. Wireless Technology is not found");
            }
            else
                logger.debug("Done (id =" + id + ") = " + oneWT);
            return oneWT;
        } catch (Exception e) {
            logger.error("Parameter (id = " + id +  "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method= RequestMethod.POST)
    public WirelessTechnology addOneWireless(@RequestBody WirelessTechnology wirelessTechnology) throws Exception {
        try {
            WirelessTechnology oneWT = wirelessTechnologyService.addOneTechnology(wirelessTechnology);
            logger.debug(" Done (" + wirelessTechnology + ") = " + oneWT );
            return oneWT;
        } catch (Exception e) {
            logger.error("Parameter ( wireless technology = " + wirelessTechnology + "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public WirelessTechnology updateOneWireless(@PathVariable long id, @RequestBody WirelessTechnology wirelessTechnology, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Update Wireless Technology with id =" + id );
            WirelessTechnology oneWT = wirelessTechnologyService.updateOneTechnology(id, wirelessTechnology);
            if ( null == oneWT ){
                logger.error("Not updated ( id = " + id + ", wireless technology = " + wirelessTechnology + " ). Return null from service layer.");
                response.setStatus( HttpStatus.NO_CONTENT.value());
            } else
                logger.debug("Done ( id  = " + id +  " , wireless technology = " + wirelessTechnology  + ")" );
            return oneWT;
        } catch (Exception e) {
            logger.error("Parameters ( id  = " + id +  " , wireless technology = " + wirelessTechnology + "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<WirelessTechnology> findAllWirelessTechnology() throws Exception {
        try {
            return wirelessTechnologyService.findAll() ;
        } catch (Exception e) {
            logger.error(" Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    };

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneWireless(@PathVariable long id) {
        try {
            logger.debug("Done (id =" + id + " )");
            wirelessTechnologyService.deleteOneTechnology(id);
        } catch (Exception e) {
            logger.error(" Parameter ( id  = " + id + " ). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

}
