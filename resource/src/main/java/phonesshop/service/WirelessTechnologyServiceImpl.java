package phonesshop.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phonesshop.domain.WirelessTechnology;
import phonesshop.domain.WirelessTechnologyRepository;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
@Service
public class WirelessTechnologyServiceImpl implements WirelessTechnologyService{
    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    WirelessTechnologyRepository repository;

    @Override
    public WirelessTechnology getOneTechnology(long id){
        return repository.findOne(id);
    };

    @Override
    public WirelessTechnology addOneTechnology(WirelessTechnology oneTechnology){
        WirelessTechnology oneTech = new WirelessTechnology(oneTechnology.getTechnology());
        oneTech = repository.saveAndFlush(oneTech);
        logger.debug("Done ( oneTechnology = " + oneTechnology + " ) = " + oneTech);
        return oneTech;
    };

    @Override
    public WirelessTechnology updateOneTechnology( long id, WirelessTechnology oneTechnology){
        WirelessTechnology oneTech = repository.findOne(id);
        if (oneTech != null) {
            oneTech.setTechnology(oneTechnology.getTechnology());
            oneTech = repository.saveAndFlush(oneTech);
            logger.debug("Done ( id = " + id + ", oneTechnology = " + oneTechnology + " ) = " + oneTech);
            return oneTech;
        }
        logger.error("Not found ( id = " + id + ", oneTechnology = " + oneTechnology + " ) in DB");
        return null;
    };

    @Override
    public List<WirelessTechnology> findAll(){
        return repository.findAll();
    };

    @Override
    public void deleteOneTechnology(long id){
        repository.delete(id);
        logger.debug("Done ( id = " +id + ")");
    };

}
