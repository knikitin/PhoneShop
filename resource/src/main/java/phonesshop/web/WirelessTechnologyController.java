package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.WirelessTechnology;
import phonesshop.domain.WirelessTechnologyRepository;
import phonesshop.util.DebugMode;

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
    private WirelessTechnologyRepository repository;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public WirelessTechnology getOneWireless(@PathVariable long id, HttpServletResponse response) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Request to get Wireless Technology with id =" + id );
            WirelessTechnology oneWT = repository.findOne(id);
            if( null == oneWT ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.warn("Request to get Wireless Technology with id =" + id + " is not completed. Wireless Technology is not found");
            }
            else
            if (DebugMode.isDebug())
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
            if (DebugMode.isDebug())
                logger.debug("Add new Wireless Technology with name =" + wirelessTechnology.getTechnology() );
            WirelessTechnology oneWT = new WirelessTechnology(wirelessTechnology.getTechnology());
            return repository.saveAndFlush(oneWT);
        } catch (Exception e) {
            logger.error(" In addOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public WirelessTechnology updateOneWireless(@PathVariable long id, @RequestBody WirelessTechnology wirelessTechnology) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Update Wireless Technology with id =" + id );
            WirelessTechnology oneWT = repository.findOne(id);
            if (oneWT != null) {
                oneWT.setTechnology(wirelessTechnology.getTechnology());
                return repository.saveAndFlush(oneWT);
            }
            return null;
        } catch (Exception e) {
            logger.error(" In updateOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<WirelessTechnology> findAllWirelessTechnology() throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Get Wireless Technology list.");
            return repository.findAll() ;
        } catch (Exception e) {
            logger.error(" In find Wireless Technology: " + e.getMessage());
            throw e;
        }
    };

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneWireless(@PathVariable long id) {
        try {
            if (DebugMode.isDebug())
                logger.debug("Delete Wireless Technology with id =" + id );
            repository.delete(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Wireless Technology: " + e.getMessage());
            throw e;
        }
    }

}
