package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.domain.DirectoryNavigationsRepository;
import phonesshop.util.DebugMode;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/directorynavigations")
public class DirectoryNavigationsController {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private DirectoryNavigationsRepository repository;

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.GET)
    public DirectoryNavigations getOneNavigation(@PathVariable long id, HttpServletResponse response) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Request to get DirectoryNavigations with id =" + id );
            DirectoryNavigations oneDN = repository.findOne(id);
            if( null == oneDN ){
                response.setStatus( HttpStatus.NO_CONTENT.value());
                logger.warn("Request to get DirectoryNavigations with id =" + id + " is not completed. DirectoryNavigations is not found");
            }
            else
            if (DebugMode.isDebug())
                logger.debug("DirectoryNavigations with id =" + id + " was found and was returned");
            return oneDN;
        } catch (Exception e) {
            logger.error(" In getOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method= RequestMethod.POST)
    public DirectoryNavigations addOneNavigation(@RequestBody DirectoryNavigations navigation) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Add new Navigations with name =" + navigation.getTypeNavigation() );
            DirectoryNavigations oneDN = new DirectoryNavigations(navigation.getTypeNavigation());
            return repository.saveAndFlush(oneDN);
        } catch (Exception e) {
            logger.error(" In addOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value="/{id:[\\d]+}", method= RequestMethod.PUT)
    public DirectoryNavigations updateOneNavigation(@PathVariable long id, @RequestBody DirectoryNavigations navigation) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Update Navigations with id =" + id );
            DirectoryNavigations oneDN = repository.findOne(id);
            if (oneDN != null) {
                oneDN.setTypeNavigation(navigation.getTypeNavigation());
                return repository.saveAndFlush(oneDN);
            }
            return null;
        } catch (Exception e) {
            logger.error(" In updateOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

    @RequestMapping( method= RequestMethod.GET)
    public List<DirectoryNavigations> findAllDirectoryNavigations() throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Get Directory Navigations list.");
            return repository.findAll() ;
        } catch (Exception e) {
            logger.error(" In find Directory Navigations: " + e.getMessage());
            throw e;
        }
    };

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteOneNavigation(@PathVariable long id) {
        try {
            if (DebugMode.isDebug())
                logger.debug("Delete Directory Navigations with id =" + id );
            repository.delete(id);
        } catch (Exception e) {
            logger.error(" In deleteOne Directory Navigations: " + e.getMessage());
            throw e;
        }
    }

}
