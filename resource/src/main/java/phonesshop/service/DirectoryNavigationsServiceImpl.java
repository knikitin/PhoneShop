package phonesshop.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.domain.DirectoryNavigationsRepository;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
@Service
public class DirectoryNavigationsServiceImpl implements DirectoryNavigationsService {
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    DirectoryNavigationsRepository repository;

    @Override
    public DirectoryNavigations getOneNavigation( long id){
        return repository.findOne(id);
    };

    @Override
    public DirectoryNavigations addOneNavigation(DirectoryNavigations oneNavigation){
        DirectoryNavigations oneDN = new DirectoryNavigations(oneNavigation.getTypeNavigation());
        oneDN = repository.saveAndFlush(oneDN);
        logger.debug("Done ( oneNavigation = " + oneNavigation + " ) = " + oneDN);
        return oneDN;
    };

    @Override
    public DirectoryNavigations updateOneNavigation( long id, DirectoryNavigations oneNavigation){
        DirectoryNavigations oneDN = repository.findOne(id);
        if (oneDN != null) {
            oneDN.setTypeNavigation(oneNavigation.getTypeNavigation());
            oneDN = repository.saveAndFlush(oneDN);
            logger.debug("Done ( id = " + id + ", oneNavigation = " + oneNavigation + " ) = " + oneDN);
            return oneDN;
        }
        logger.error("Not found ( id = " + id + ", oneNavigation = " + oneNavigation + " ) in DB");
        return null;
    };

    @Override
    public List<DirectoryNavigations> findAll(){
        return repository.findAll();
    };

    @Override
    public void deleteOneNavigation(long id){
        repository.delete(id);
        logger.debug("Done ( id = " +id + ")");
    };

}
