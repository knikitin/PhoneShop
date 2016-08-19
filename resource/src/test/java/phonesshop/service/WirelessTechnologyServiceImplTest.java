package phonesshop.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import phonesshop.domain.WirelessTechnology;
import phonesshop.domain.WirelessTechnologyRepository;

import static org.mockito.Mockito.*;

/**
 * Created by kostya.nikitin on 8/18/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WirelessTechnologyServiceImplTest {
    @MockBean(name="repository")
    private WirelessTechnologyRepository repository;

    @Autowired
    private WirelessTechnologyService wirelessTechnologyService;

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void updateOneTechnology_GoodUpdate_ReturnEntity(){
        WirelessTechnology oneWT = new WirelessTechnology("test");
        oneWT.setId(1111L);
        when(repository.findOne(1111L)).thenReturn(oneWT);
        when(repository.saveAndFlush(oneWT)).thenReturn(oneWT);

        WirelessTechnology result = wirelessTechnologyService.updateOneTechnology(1111L, oneWT);

        Assert.assertNotNull(result);
        Assert.assertEquals(oneWT, result);
        verify(repository, times(1)).findOne(1111L);
        verify(repository, times(1)).saveAndFlush(oneWT);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void updateOneTechnology_EntityNotFound_ReturnNull(){
        WirelessTechnology oneWT = new WirelessTechnology("test");
        oneWT.setId(1111L);
        when(repository.findOne(1111L)).thenReturn(null);

        WirelessTechnology result = wirelessTechnologyService.updateOneTechnology(1111L, oneWT);

        Assert.assertNull(result);
        verify(repository, times(1)).findOne(1111L);
        verifyNoMoreInteractions(repository);
    }

}