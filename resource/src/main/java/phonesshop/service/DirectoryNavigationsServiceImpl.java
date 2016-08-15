package phonesshop.service;

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

    @Autowired
    DirectoryNavigationsRepository repository;

    @Override
    public DirectoryNavigations getOneNavigation( long id){
        return repository.findOne(id);
    };

    @Override
    public DirectoryNavigations addOneNavigation(DirectoryNavigations oneNavigation){
        DirectoryNavigations oneDN = new DirectoryNavigations(oneNavigation.getTypeNavigation());
        return repository.saveAndFlush(oneDN);
    };

    @Override
    public DirectoryNavigations updateOneNavigation( long id, DirectoryNavigations oneNavigation){
        DirectoryNavigations oneDN = repository.findOne(id);
        if (oneDN != null) {
            oneDN.setTypeNavigation(oneNavigation.getTypeNavigation());
            return repository.saveAndFlush(oneDN);
        }
        return null;
    };

    @Override
    public List<DirectoryNavigations> findAll(){
        return repository.findAll();
    };

    @Override
    public void deleteOneNavigation(long id){
        repository.delete(id);
    };

}
