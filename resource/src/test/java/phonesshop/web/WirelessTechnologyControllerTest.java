package phonesshop.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import phonesshop.config.OAuthHelper;
import phonesshop.domain.WirelessTechnology;
import phonesshop.service.WirelessTechnologyService;

import java.util.Arrays;

import static net.minidev.json.JSONValue.toJSONString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by kostya.nikitin on 8/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WirelessTechnologyControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private WirelessTechnologyService wirelessTechnologyServiceMock;

    @Autowired
    private OAuthHelper helper;

    private WirelessTechnology getNewWirelessTechnology(long id, String caption){
        WirelessTechnology wireless = new WirelessTechnology(caption);
        wireless.setId(id);
        return wireless;
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void findAllWirelessTechnology_GoodFindAll_ReturnAllEntity() throws Exception {

        given(this.wirelessTechnologyServiceMock. findAll()
        ).willReturn(Arrays.asList(getNewWirelessTechnology(1111L, "test"), getNewWirelessTechnology(1112L, "test")));

        this.mvc.perform(get("/wirelesstechnology"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1111)))
                .andExpect(jsonPath("$[0].technology", is("test")))
                .andExpect(jsonPath("$[1].id", is(1112)))
                .andExpect(jsonPath("$[1].technology", is("test")))
                ;
        verify(wirelessTechnologyServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }

    @Test
    public void getOneWireless_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {

        this.mvc.perform(get("/wirelesstechnology/1111"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void getOneWireless_GoodFindOne_ReturnEntity() throws Exception {

        given(this.wirelessTechnologyServiceMock.getOneTechnology(1111)
        ).willReturn(getNewWirelessTechnology(1111L, "test"));

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mvc.perform(MockMvcRequestBuilders.get("/wirelesstechnology/1111")
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.technology", is("test")))
        ;
        verify(wirelessTechnologyServiceMock, times(1)).getOneTechnology(1111);
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }

    @Test
    public void getOneWireless_ErrorNoEntityWithID_ReturnNoContent() throws Exception {

        given(this.wirelessTechnologyServiceMock.getOneTechnology(1111)
        ).willReturn(null);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mvc.perform(MockMvcRequestBuilders.get("/wirelesstechnology/1111")
                .with(bearerToken))
                .andExpect(status().isNoContent())
        ;
        verify(wirelessTechnologyServiceMock, times(1)).getOneTechnology(1111);
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }


    @Test
    public void addOneWireless_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewWirelessTechnology(1111L, "test"));

        this.mvc.perform(post("/wirelesstechnology")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void addOneWireless_GoodAddOne_ReturnEntity() throws Exception {
        WirelessTechnology oneTechnology = getNewWirelessTechnology(1111L, "test");
        given(this.wirelessTechnologyServiceMock.addOneTechnology(Mockito.any(WirelessTechnology.class))
        ).willReturn(oneTechnology);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneTechnology);

        this.mvc.perform(post("/wirelesstechnology")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.technology", is("test")))
        ;
        verify(wirelessTechnologyServiceMock, times(1)).addOneTechnology(Mockito.any(WirelessTechnology.class));
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }


    @Test
    public void updateOneWireless_ErrorNoEntityWithID_ReturnNoContent() throws Exception {
        WirelessTechnology oneTechnology = getNewWirelessTechnology(1111L, "test");
        given(this.wirelessTechnologyServiceMock.updateOneTechnology(Mockito.anyLong(),Mockito.any(WirelessTechnology.class))
        ).willReturn(null);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneTechnology);

        mvc.perform(MockMvcRequestBuilders.put("/wirelesstechnology/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isNoContent())
        ;
        verify(wirelessTechnologyServiceMock, times(1)).updateOneTechnology(Mockito.anyLong(),Mockito.any(WirelessTechnology.class));
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }


    @Test
    public void updateOneWireless_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewWirelessTechnology(1111L, "test"));

        this.mvc.perform(put("/wirelesstechnology/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void updateOneWireless_GoodUpdateOne_ReturnEntity() throws Exception {
        WirelessTechnology oneTechnology = getNewWirelessTechnology(1111L, "test");
        given(this.wirelessTechnologyServiceMock.updateOneTechnology(Mockito.anyLong(), Mockito.any(WirelessTechnology.class))
        ).willReturn(oneTechnology);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneTechnology);

        this.mvc.perform(put("/wirelesstechnology/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.technology", is("test")))
        ;
        verify(wirelessTechnologyServiceMock, times(1)).updateOneTechnology(Mockito.anyLong(), Mockito.any(WirelessTechnology.class));
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }

    @Test
    public void deleteOneWireless_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {

        this.mvc.perform(delete("/wirelesstechnology/1111"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deleteOneWireless_GoodDeleteOne_ReturnStatusOk() throws Exception {

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        this.mvc.perform(delete("/wirelesstechnology/1111")
                .with(bearerToken))
                .andExpect(status().isOk())
        ;
        verify(wirelessTechnologyServiceMock, times(1)).deleteOneTechnology(1111);
        verifyNoMoreInteractions(wirelessTechnologyServiceMock);
    }


}
