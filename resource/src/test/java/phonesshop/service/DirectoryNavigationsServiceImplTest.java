package phonesshop.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.domain.DirectoryNavigationsRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by kostya.nikitin on 8/18/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryNavigationsServiceImplTest {
    @MockBean(name="repository")
    private DirectoryNavigationsRepository repository;

    @Autowired
    private DirectoryNavigationsService directoryNavigationsService;

    @Test
    public void updateOneNavigation_GoodUpdate_ReturnEntity(){
        DirectoryNavigations oneDN = new DirectoryNavigations("test");
        oneDN.setId(1111L);
        when(repository.findOne(1111L)).thenReturn(oneDN);
        when(repository.saveAndFlush(oneDN)).thenReturn(oneDN);

        DirectoryNavigations result = directoryNavigationsService.updateOneNavigation(1111L, oneDN);

        Assert.assertNotNull(result);
        Assert.assertEquals(oneDN, result);
        verify(repository, times(1)).findOne(1111L);
        verify(repository, times(1)).saveAndFlush(oneDN);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void updateOneNavigation_EntityNotFound_ReturnNull(){
        DirectoryNavigations oneDN = new DirectoryNavigations("test");
        oneDN.setId(1111L);
        when(repository.findOne(1111L)).thenReturn(null);

        DirectoryNavigations result = directoryNavigationsService.updateOneNavigation(1111L, oneDN);

        Assert.assertNull(result);
        verify(repository, times(1)).findOne(1111L);
        verifyNoMoreInteractions(repository);
    }

}
