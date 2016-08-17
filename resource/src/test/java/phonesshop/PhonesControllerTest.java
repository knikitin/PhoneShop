package phonesshop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.config.OAuthHelper;
import phonesshop.domain.Phones;

import phonesshop.service.PhonesService;

import java.io.IOException;

import static net.minidev.json.JSONValue.toJSONString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kostya.nikitin on 8/16/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhonesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PhonesService phonesServiceMock;

    @Autowired
    private OAuthHelper helper;

    private Phones getNewPhones(Long id, String model){
        Phones newPhone = new Phones();
        newPhone.setId(id);
        newPhone.setBrand("testBrand");
        newPhone.setModel(model);
        return newPhone;
    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }
    
    @Test
    public void getOnePhone_GoodFindOne_ReturnEntity() throws Exception {

        given(this.phonesServiceMock.findOne(1111)
        ).willReturn(getNewPhones(1111L, "test"));

        mockMvc.perform(get("/phones/1111"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.model", is("test")))
                .andExpect(jsonPath("$.brand", is("testBrand")))
        ;
        verify(phonesServiceMock, times(1)).findOne(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void getOnePhone_ErrorNoEntityWithID_ReturnNoContent() throws Exception {

        given(this.phonesServiceMock.findOne(1111)
        ).willReturn(null);

        mockMvc.perform(get("/phones/1111"))
                .andExpect(status().isNoContent())
        ;
        verify(phonesServiceMock, times(1)).findOne(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }


    @Test
    public void addOnePhones_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewPhones(1111L, "test"));

        mockMvc.perform(post("/phones")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void addOnePhones_GoodAddOne_ReturnEntity() throws Exception {
        Phones onePhone = getNewPhones(1111L, "test");
        given(this.phonesServiceMock.updatePhone(Mockito.any(Phones.class))
        ).willReturn(onePhone);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(onePhone);

        mockMvc.perform(post("/phones")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.model", is("test")))
        ;
        verify(phonesServiceMock, times(1)).updatePhone(Mockito.any(Phones.class));
        verifyNoMoreInteractions(phonesServiceMock);
    }


    @Test
    public void updateOnePhones_ErrorNoEntityWithID_ReturnNoContent() throws Exception {
        Phones onePhone = getNewPhones(1111L, "test");
        given(this.phonesServiceMock.updateExistingPhone(Mockito.any(Phones.class))
        ).willReturn(null);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(onePhone);

        mockMvc.perform(put("/phones/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isConflict())
        ;
        verify(phonesServiceMock, times(1)).updateExistingPhone(Mockito.any(Phones.class));
        verifyNoMoreInteractions(phonesServiceMock);
    }


    @Test
    public void updateOnePhones_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        String jsonString = toJSONString(getNewPhones(1111L, "test"));

        mockMvc.perform(put("/phones")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void updateOnePhones_GoodUpdateOne_ReturnEntity() throws Exception {
        Phones onePhone = getNewPhones(1111L, "test");
        given(this.phonesServiceMock.updateExistingPhone(Mockito.any(Phones.class))
        ).willReturn(onePhone);
        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        String jsonString = toJSONString(onePhone);

        this.mockMvc.perform(put("/phones/1111")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString)
                .with(bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1111)))
                .andExpect(jsonPath("$.model", is("test")))
        ;
        verify(phonesServiceMock, times(1)).updateExistingPhone(Mockito.any(Phones.class));
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void deleteOnePhones_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        mockMvc.perform(delete("/phones/1111"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void deleteOnePhones_GoodDeleteOne_ReturnStatusOk() throws Exception {

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");
        mockMvc.perform(delete("/phones/1111")
                .with(bearerToken))
                .andExpect(status().isOk())
        ;
        verify(phonesServiceMock, times(1)).deletePhone(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void handleFileUpload_GoodUploadFile_ReturnStatusOk() throws Exception {
        given(this.phonesServiceMock.uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong())
        ).willReturn(true);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        MockMultipartFile firstFile = new MockMultipartFile( "file", "img.jpg", "image/jpeg", "some img".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/phones/1111/img")
                .file(firstFile)
                .accept(MediaType.IMAGE_JPEG_VALUE)
                .with(bearerToken))
                .andExpect(status().isOk())
        ;

        verify(phonesServiceMock, times(1)).uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong());
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void handleFileUpload_UploadingEmptyFile_ReturnStatusConflict() throws Exception {
        given(this.phonesServiceMock.uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong())
        ).willReturn(false);

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        MockMultipartFile firstFile = new MockMultipartFile( "file", "img.jpg", "image/jpeg", "some img".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/phones/1111/img")
                .file(firstFile)
                .accept(MediaType.IMAGE_JPEG_VALUE)
                .with(bearerToken))
                .andExpect(status().isConflict())
                .andExpect(content().string("You failed to upload image because the file was empty"))
        ;

        verify(phonesServiceMock, times(1)).uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong());
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void handleFileUpload_ErrorSaveFileInService_ReturnStatusConflict() throws Exception {
        given(this.phonesServiceMock.uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong())
        ).willThrow(new IOException());

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        MockMultipartFile firstFile = new MockMultipartFile( "file", "img.jpg", "image/jpeg", "some img".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/phones/1111/img")
                .file(firstFile)
                .accept(MediaType.IMAGE_JPEG_VALUE)
                .with(bearerToken))
                .andExpect(status().isConflict())
                .andExpect(content().string("Error save for phone with id =1111"))
        ;

        verify(phonesServiceMock, times(1)).uploadImgPhone(Mockito.any(MultipartFile.class), Mockito.anyLong());
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void handleFileDelete_ErrorUnautorized_ReturnUnauthorizedStatus() throws Exception {
        mockMvc.perform(delete("/phones/1111/img"))
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    public void handleFileDelete_GoodDeletingFile_ReturnStatusOk() throws Exception {
        given(this.phonesServiceMock.deleteImgPhone(1111)
        ).willReturn("ok");

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mockMvc.perform(delete("/phones/1111/img")
                .with(bearerToken))
                .andExpect(status().isOk())
        ;
        verify(phonesServiceMock, times(1)).deleteImgPhone(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }


    @Test
    public void handleFileDelete_FileForPhoneNotFound_ReturnStatusNotFound() throws Exception {
        given(this.phonesServiceMock.deleteImgPhone(1111)
        ).willReturn("notFound");

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mockMvc.perform(delete("/phones/1111/img")
                .with(bearerToken))
                .andExpect(status().isNotFound())
        ;
        verify(phonesServiceMock, times(1)).deleteImgPhone(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void handleFileDelete_AbnormalBehaviorPhonesServices_ReturnStatusConflict() throws Exception {
        given(this.phonesServiceMock.deleteImgPhone(1111)
        ).willReturn("something else");

        RequestPostProcessor bearerToken = helper.bearerToken("myclientwith");

        mockMvc.perform(delete("/phones/1111/img")
                .with(bearerToken))
                .andExpect(status().isConflict())
                .andExpect(content().string("something else"))
        ;
        verify(phonesServiceMock, times(1)).deleteImgPhone(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }

    @Test
    public void getFile_GoodGetFile_ReturnStatusOk() throws Exception {
        Resource resource = new ByteArrayResource("testString".getBytes());
        given(this.phonesServiceMock.getImagePhone(1111)
        ).willReturn(resource);

        mockMvc.perform(get("/phones/1111/img"))
                .andExpect(status().isOk())
        ;
        verify(phonesServiceMock, times(1)).getImagePhone(1111);
        verifyNoMoreInteractions(phonesServiceMock);
    }

}
