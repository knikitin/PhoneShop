package phonesshop.service;

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

    @Autowired
    WirelessTechnologyRepository repository;

    @Override
    public WirelessTechnology getOneTechnology(long id){
        return repository.findOne(id);
    };

    @Override
    public WirelessTechnology addOneTechnology(WirelessTechnology oneTechnology){
        WirelessTechnology oneTech = new WirelessTechnology(oneTechnology.getTechnology());
        return repository.saveAndFlush(oneTech);
    };

    @Override
    public WirelessTechnology updateOneTechnology( long id, WirelessTechnology oneTechnology){
        WirelessTechnology oneTech = repository.findOne(id);
        if (oneTech != null) {
            oneTech.setTechnology(oneTechnology.getTechnology());
            return repository.saveAndFlush(oneTech);
        }
        return null;
    };

    @Override
    public List<WirelessTechnology> findAll(){
        return repository.findAll();
    };

    @Override
    public void deleteOneTechnology(long id){
        repository.delete(id);
    };

}
