package phonesshop.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import phonesshop.ApplicationTests;
import phonesshop.domain.WirelessTechnology;
import phonesshop.service.WirelessTechnologyService;
import phonesshop.web.WirelessTechnologyController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;

/**
 * Created by kostya.nikitin on 8/11/2016.
 */
public class WirelessTechnologyControllerTestsDuplicate extends ApplicationTests {


   @Autowired
    private WirelessTechnologyController wirelessTechnologyController;

    @MockBean
    private WirelessTechnologyService wirelessTechnologyServiceMock;

    @Test
    public void findAll_GoodFindAll_ReturnAllEntity() throws Exception {
        WirelessTechnology wireless1 = new WirelessTechnology("test");
        wireless1.setId(1111L);
        WirelessTechnology wireless2 = new WirelessTechnology("test");
        wireless2.setId(1112L);

        given(this.wirelessTechnologyServiceMock.
                findAll()
        ).willReturn(Arrays.asList(wireless1, wireless2));

        List<WirelessTechnology> result = wirelessTechnologyController.findAllWirelessTechnology();

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void getOneWireless_GoodFindOne_ReturnEntity() throws Exception {
        WirelessTechnology wireless1 = new WirelessTechnology("test");
        wireless1.setId(1111L);

        given(this.wirelessTechnologyServiceMock.getOneTechnology(1111)
        ).willReturn(wireless1);

        HttpServletResponse response = new MockHttpServletResponse();
        WirelessTechnology result = wirelessTechnologyController.getOneWireless(1111L, response) ;

        Assert.assertNotNull(result);
    }

}
