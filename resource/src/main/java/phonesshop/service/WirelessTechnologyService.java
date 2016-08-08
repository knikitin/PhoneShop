package phonesshop.service;

import phonesshop.domain.WirelessTechnology;

import java.util.List;

/**
 * Created by kostya.nikitin on 8/8/2016.
 */
public interface WirelessTechnologyService {
    WirelessTechnology getOneTechnology(long id);
    WirelessTechnology addOneTechnology(WirelessTechnology oneTechnology);
    WirelessTechnology updateOneTechnology( long id, WirelessTechnology oneTechnology);
    List<WirelessTechnology> findAll();
    void deleteOneTechnology(long id);
}
