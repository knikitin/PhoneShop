package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.WirelessTechnology;
import phonesshop.domain.WirelessTechnologyRepository;
import phonesshop.service.WirelessTechnologyService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by kostya.nikitin on 8/1/2016.
 */
@RestController
@RequestMapping("/wirelesstechnology")
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
                logger.debug("WirelessTechnology with id =" + id + " was found and was returned");
            return oneWT;
        } catch (Exception e) {
            logger.error(" In getOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method= RequestMethod.POST)
    public WirelessTechnology addOneWireless(@RequestBody WirelessTechnology wirelessTechnology) throws Exception {
        try {
            logger.debug("Add new Wireless Technology with name =" + wirelessTechnology.getTechnology() );
            return wirelessTechnologyService.addOneTechnology(wirelessTechnology);
        } catch (Exception e) {
            logger.error(" In addOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public WirelessTechnology updateOneWireless(@PathVariable long id, @RequestBody WirelessTechnology wirelessTechnology, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Update Wireless Technology with id =" + id );
            WirelessTechnology oneWT = wirelessTechnologyService.updateOneTechnology(id, wirelessTechnology);
            if ( null == oneWT )
                response.setStatus( HttpStatus.NO_CONTENT.value());
            return oneWT;
        } catch (Exception e) {
            logger.error(" In updateOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<WirelessTechnology> findAllWirelessTechnology() throws Exception {
        try {
            logger.debug("Get Wireless Technology list.");
            return wirelessTechnologyService.findAll() ;
        } catch (Exception e) {
            logger.error(" In find Wireless Technology: " + e.getMessage());
            throw e;
        }
    };

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneWireless(@PathVariable long id) {
        try {
            logger.debug("Delete Wireless Technology with id =" + id );
            wirelessTechnologyService.deleteOneTechnology(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

}
