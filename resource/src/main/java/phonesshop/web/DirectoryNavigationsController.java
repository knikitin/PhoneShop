package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.service.DirectoryNavigationsService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/directorynavigations")
@EnableGlobalMethodSecurity( securedEnabled = true)
public class DirectoryNavigationsController {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private DirectoryNavigationsService directoryNavigationsService;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public DirectoryNavigations getOneNavigation(@PathVariable long id, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Request to get DirectoryNavigations with id =" + id );
            DirectoryNavigations oneDN = directoryNavigationsService.getOneNavigation(id);
            if( null == oneDN ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.warn("Request to get DirectoryNavigations with id =" + id + " is not completed. DirectoryNavigations is not found");
            }
            else
                logger.debug("Done (id =" + id + ") = " + oneDN);
            return oneDN;
        } catch (Exception e) {
            logger.error("Parameter (id = " + id +  "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method= RequestMethod.POST)
    public DirectoryNavigations addOneNavigation(@RequestBody DirectoryNavigations oneNavigation) throws Exception {
        try {
            DirectoryNavigations oneDN = directoryNavigationsService.addOneNavigation(oneNavigation);
            logger.debug(" Done (" + oneNavigation + ") = " + oneDN );
            return oneDN;
        } catch (Exception e) {
            logger.error("Parameter ( navigation = " + oneNavigation + "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public DirectoryNavigations updateOneNavigation(@PathVariable long id, @RequestBody DirectoryNavigations navigation, HttpServletResponse response) throws Exception {
        try {
            DirectoryNavigations oneDN = directoryNavigationsService.updateOneNavigation(id, navigation);
            if ( null == oneDN ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.error("Not updated ( id = " + id + ", navigation = " + navigation + " ). Return null from service layer.");
            }
            else
                logger.debug("Done ( id  = " + id +  " , navigation = " + navigation + ")" );
            return oneDN;
        } catch (Exception e) {
            logger.error("Parameters ( id  = " + id +  " , navigation = " + navigation + "). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<DirectoryNavigations> findAllDirectoryNavigations() throws Exception {
        try {
            return directoryNavigationsService.findAll() ;
        } catch (Exception e) {
            logger.error(" Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    };

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneNavigation(@PathVariable long id) {
        try {
            directoryNavigationsService.deleteOneNavigation(id);
            logger.debug("Done (id =" + id + " )");
        } catch (Exception e) {
            logger.error(" Parameter ( id  = " + id + " ). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    }

}
