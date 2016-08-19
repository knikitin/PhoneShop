package phonesshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import phonesshop.config.OAuthHelper;
import phonesshop.domain.DirectoryNavigations;
import phonesshop.service.DirectoryNavigationsService;

import java.util.Arrays;

import static net.minidev.json.JSONValue.toJSONString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kostya.nikitin on 8/15/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryNavigationsControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webapp;

    @MockBean
    private DirectoryNavigationsService directoryNavigationsServiceMock;

    @Autowired
    private OAuthHelper helper;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webapp)
                .apply(springSecurity())
                .build();
    }


    private DirectoryNavigations getNewDirectoryNavigations(long id, String caption){
        DirectoryNavigations navigations = new DirectoryNavigations(caption);
        navigations.setId(id);
        return navigations;
    }

    @Test
    public void findAllDirectoryNavigations_GoodFindAll_ReturnAllEntity() throws Exception {

        given(this.directoryNavigationsServiceMock. findAll()
        ).willReturn(Arrays.asList(getNewDirectoryNavigations(1111L, "test"), getNewDirectoryNavigations(1112L, "test")));

        this.mvc.perform(get("/directorynavigations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1111)))
                .andExpect(jsonPath("$[0].typeNavigation", is("test")))
                .andExpect(jsonPath("$[1].id", is(1112)))
                .andExpect(jsonPath("$[1].typeNavigation", is("test")))
        ;
        verify(directoryNavigationsServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(directoryNavigationsServiceMock);
    }    

    @Test
    public void getOneNavigation_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {

        this.mvc.perform(get("/directorynavigations/1111"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void getOneNavigation_GoodFindOne_ReturnEntity() throws Exception {

        given(this.directoryNavigationsServiceMock.getOneNavigation(1111)
        ).willReturn(getNewDirectoryNavigations(1111L, "test"));

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mvc.perform(MockMvcRequestBuilders.get("/directorynavigations/1111")
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.typeNavigation", is("test")))
        ;
        verify(directoryNavigationsServiceMock, times(1)).getOneNavigation(1111);
        verifyNoMoreInteractions(directoryNavigationsServiceMock);
    }

    @Test
    public void getOneNavigation_ErrorNoEntityWithID_ReturnNoContent() throws Exception {

        given(this.directoryNavigationsServiceMock.getOneNavigation(1111)
        ).willReturn(null);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mvc.perform(MockMvcRequestBuilders.get("/directorynavigations/1111")
                .with(bearerToken))
                .andExpect(status().isNoContent())
        ;
        verify(directoryNavigationsServiceMock, times(1)).getOneNavigation(1111);
        verifyNoMoreInteractions(directoryNavigationsServiceMock);
    }


    @Test
    public void addOneNavigation_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewDirectoryNavigations(1111L, "test"));

        this.mvc.perform(post("/directorynavigations")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void addOneNavigation_GoodAddOne_ReturnEntity() throws Exception {
        DirectoryNavigations oneNavigation = getNewDirectoryNavigations(1111L, "test");
        given(this.directoryNavigationsServiceMock.addOneNavigation(Mockito.any(DirectoryNavigations.class))
        ).willReturn(oneNavigation);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneNavigation);

        this.mvc.perform(post("/directorynavigations")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.typeNavigation", is("test")))
        ;
    }


    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void updateOneNavigation_ErrorNoEntityWithID_ReturnNoContent() throws Exception {
        DirectoryNavigations oneNavigation = getNewDirectoryNavigations(1111L, "test");
        given(this.directoryNavigationsServiceMock.updateOneNavigation(Mockito.anyLong(),Mockito.any(DirectoryNavigations.class))
        ).willReturn(null);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneNavigation);

        mvc.perform(MockMvcRequestBuilders.put("/directorynavigations/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isNoContent())
        ;
    }


    @Test
    public void updateOneNavigation_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewDirectoryNavigations(1111L, "test"));

        this.mvc.perform(put("/directorynavigations/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void updateOneNavigation_GoodUpdateOne_ReturnEntity() throws Exception {
        DirectoryNavigations oneNavigation = getNewDirectoryNavigations(1111L, "test");
        given(this.directoryNavigationsServiceMock.updateOneNavigation(Mockito.anyLong(), Mockito.any(DirectoryNavigations.class))
        ).willReturn(oneNavigation);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(oneNavigation);

        this.mvc.perform(put("/directorynavigations/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.typeNavigation", is("test")))
        ;
    }

    @Test
    public void deleteOneNavigation_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {

        this.mvc.perform(delete("/directorynavigations/1111"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deleteOneNavigation_GoodDeleteOne_ReturnStatusOk() throws Exception {

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        this.mvc.perform(delete("/directorynavigations/1111")
                .with(bearerToken))
                .andExpect(status().isOk())
        ;
        verify(directoryNavigationsServiceMock, times(1)).deleteOneNavigation(1111);
        verifyNoMoreInteractions(directoryNavigationsServiceMock);
    }

}
