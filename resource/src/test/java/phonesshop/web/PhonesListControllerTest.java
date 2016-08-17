package phonesshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import phonesshop.ApplicationTests;
import phonesshop.domain.Phones;
import phonesshop.dto.PagePhonesListForWeb;
import phonesshop.dto.PhoneForList;
import phonesshop.service.PhonesService;

import javax.management.BadAttributeValueExpException;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kostya.nikitin on 6/30/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhonesListControllerTest extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PhonesService phonesServiceMock;

    private PhoneForList getNewPhoneForList(Long id, String model){
        Phones newPhone = new Phones();
        newPhone.setId(id);
        newPhone.setBrand("testBrand");
        newPhone.setModel(model);
        return new PhoneForList(newPhone);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void findAllPage_GoodGetOnePage_ReturnStatusOkAndListEntities() throws Exception {
        given(this.phonesServiceMock.findAllPage(1,10)
        ).willReturn(new PagePhonesListForWeb(Arrays.asList(getNewPhoneForList(1111L, "test"), getNewPhoneForList(1112L, "test")), 1));

        mockMvc.perform(get("/phoneslist/page1for10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.phonesList", hasSize(2)))
                .andExpect(jsonPath("$.phonesList[0].id", is(1111)))
                .andExpect(jsonPath("$.phonesList[0].model", is("test")))
                .andExpect(jsonPath("$.phonesList[0].brand", is("testBrand")))
                .andExpect(jsonPath("$.phonesList[1].id", is(1112)))
                .andExpect(jsonPath("$.phonesList[1].model", is("test")))
                .andExpect(jsonPath("$.phonesList[1].brand", is("testBrand")))
        ;
        verify(phonesServiceMock, times(1)).findAllPage(1,10);
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void findAllPage_ErrorFromPhoneService_ReturnStatusOkAndListEntities() throws Exception {
        given(this.phonesServiceMock.findAllPage(100,1000)
        ).willThrow(new IllegalArgumentException("something"));

        try {
            mockMvc.perform(get("/phoneslist/page100for1000"))
            ;
        } catch (Exception e){
            assertThat(e)
                    .isInstanceOf(org.springframework.web.util.NestedServletException.class)
                    .hasMessage("Request processing failed; nested exception is java.lang.IllegalArgumentException: something");
        }
        verify(phonesServiceMock, times(1)).findAllPage(100,1000);
        verifyNoMoreInteractions(phonesServiceMock);
    }
}
