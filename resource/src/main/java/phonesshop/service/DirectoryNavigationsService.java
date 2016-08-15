package phonesshop.service;

import phonesshop.domain.DirectoryNavigations;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
public interface DirectoryNavigationsService {
    DirectoryNavigations getOneNavigation(long id);
    DirectoryNavigations addOneNavigation(DirectoryNavigations oneNavigation);
    DirectoryNavigations updateOneNavigation( long id, DirectoryNavigations oneNavigation);
    List<DirectoryNavigations> findAll();
    void deleteOneNavigation(long id);
}
