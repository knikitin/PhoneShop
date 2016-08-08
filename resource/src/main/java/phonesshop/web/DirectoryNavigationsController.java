package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.service.DirectoryNavigationService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/directorynavigations")
public class DirectoryNavigationsController {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private DirectoryNavigationService directoryNavigationService;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public DirectoryNavigations getOneNavigation(@PathVariable long id, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Request to get DirectoryNavigations with id =" + id );
            DirectoryNavigations oneDN = directoryNavigationService.getOneNavigation(id);
            if( null == oneDN ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.warn("Request to get DirectoryNavigations with id =" + id + " is not completed. DirectoryNavigations is not found");
            }
            else
                logger.debug("DirectoryNavigations with id =" + id + " was found and was returned");
            return oneDN;
        } catch (Exception e) {
            logger.error(" In getOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method= RequestMethod.POST)
    public DirectoryNavigations addOneNavigation(@RequestBody DirectoryNavigations oneNavigation) throws Exception {
        try {
            logger.debug("Add new Navigations with name =" + oneNavigation.getTypeNavigation() );
            return directoryNavigationService.addOneNavigation(oneNavigation);
        } catch (Exception e) {
            logger.error(" In addOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public DirectoryNavigations updateOneNavigation(@PathVariable long id, @RequestBody DirectoryNavigations navigation, HttpServletResponse response) throws Exception {
        try {
            logger.debug("Update Navigations with id =" + id );
            DirectoryNavigations oneDN = directoryNavigationService.updateOneNavigation(id, navigation);
            if ( null == oneDN )
                response.setStatus( HttpStatus.NO_CONTENT.value());
            return oneDN;
        } catch (Exception e) {
            logger.error(" In updateOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<DirectoryNavigations> findAllDirectoryNavigations() throws Exception {
        try {
            logger.debug("Get Directory Navigations list.");
            return directoryNavigationService.findAll() ;
        } catch (Exception e) {
            logger.error(" In find Directory Navigations: " + e.getMessage());
            throw e;
        }
    };

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneNavigation(@PathVariable long id) {
        try {
            logger.debug("Delete Directory Navigations with id =" + id );
            directoryNavigationService.deleteOneNavigation(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

}
