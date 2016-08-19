package phonesshop.service;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.dto.PagePhonesListForWeb;
import phonesshop.dto.PhoneForList;
import phonesshop.util.FilesOperationsService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by kostya.nikitin on 8/17/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhonesServiceImplTest {

    @Autowired
    private PhonesService phonesService;

    @MockBean(name="phonesRepository")
    private PhonesRepository phonesRepositoryMock;

    @MockBean
    private FilesOperationsService filesOperationsServiceMock;

    @Value("${resource.img.root}")
    public String ROOT;

    private String getNameFile(Object obj){
        return ((File) obj).getName();
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void deleteImgPhone_GoodDelete_ReturnStringOk() throws Exception {
        long phoneId = 1111L;

        when(filesOperationsServiceMock.exists(Mockito.any(File.class))).thenReturn(true);
        when(filesOperationsServiceMock.delete(Mockito.any(File.class))).thenReturn(true);

        String result = phonesService.deleteImgPhone(phoneId);

        Assert.assertNotNull(result);
        Assert.assertEquals("ok", result);

        verify(filesOperationsServiceMock, times(1)).exists(Mockito.any(File.class));
        verify(filesOperationsServiceMock, times(1)).delete(Mockito.any(File.class));
        verifyNoMoreInteractions(filesOperationsServiceMock);
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void deleteImgPhone_TestMethodGetNameForFileWithPhoneImage_ReturnStringOk() throws Exception {
        long phoneId = 1111L;

        when(filesOperationsServiceMock.exists(Mockito.any(File.class))).thenReturn(true);
        when(filesOperationsServiceMock.delete(Mockito.any(File.class))).thenReturn(true);

        String result = phonesService.deleteImgPhone(phoneId);

        BaseMatcher<File> nameFileEquals = new BaseMatcher<File>() {
            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matches(Object item) {
                File actual = (File) item;
                return actual.getName().equals("457.jpg");
            }
        };
        verify(filesOperationsServiceMock).exists(argThat(nameFileEquals));
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void deleteImgPhone_FailedInvokeDeleteFile_ReturnStringWithMessage() throws Exception {
        long phoneId = 1111L;

        when(filesOperationsServiceMock.exists(Mockito.any(File.class))).thenReturn(true);
        when(filesOperationsServiceMock.delete(Mockito.any(File.class))).thenReturn(false);

        String result = phonesService.deleteImgPhone(phoneId);

        Assert.assertNotNull(result);
        Assert.assertEquals("You failed to delete 457.jpg", result);

        verify(filesOperationsServiceMock, times(1)).exists(Mockito.any(File.class));
        verify(filesOperationsServiceMock, times(1)).delete(Mockito.any(File.class));
        verifyNoMoreInteractions(filesOperationsServiceMock);
    }


    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void deleteImgPhone_FileNotFound_ReturnStringNotFound() throws Exception {
        long phoneId = 1111L;

        when(filesOperationsServiceMock.exists(Mockito.any(File.class))).thenReturn(false);

        String result = phonesService.deleteImgPhone(phoneId);

        Assert.assertNotNull(result);
        Assert.assertEquals("notFound", result);

        verify(filesOperationsServiceMock, times(1)).exists(Mockito.any(File.class));
        verifyNoMoreInteractions(filesOperationsServiceMock);
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void uploadImgPhone_GoodUpload_ReturnTrue() throws Exception {
        long phoneId = 1111L;
        MultipartFile mock = mock(MultipartFile.class);

        when(mock.isEmpty()).thenReturn(false);

        boolean result = phonesService.uploadImgPhone(mock, phoneId);

        Assert.assertNotNull(result);
        Assert.assertTrue(result);
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void uploadImgPhone_UploadEmptyFile_ReturnFalse() throws Exception {
        long phoneId = 1111L;
        MultipartFile mock = mock(MultipartFile.class);

        when(mock.isEmpty()).thenReturn(true);

        boolean result = phonesService.uploadImgPhone(mock, phoneId);

        Assert.assertNotNull(result);
        Assert.assertFalse(result);
    }

    @Test
    public void findAllPage_GoodReturnPage_ReturnPagePhonesListForWeb() throws Exception {

        Page mockPagePhones = mock(Page.class);
        Page mockPagePhonesForList = mock(Page.class);

        when(phonesRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockPagePhones);

        when(mockPagePhones.map(Mockito.anyObject())).thenReturn(mockPagePhonesForList);
        Phones phones = new Phones();
        phones.setId(1111);
        PhoneForList phoneForList = new PhoneForList(phones);
        List<PhoneForList> listPhoneForList = new ArrayList<PhoneForList>();
        listPhoneForList.add(phoneForList);
        when(mockPagePhonesForList.getContent()).thenReturn(listPhoneForList);

        PagePhonesListForWeb result = phonesService.findAllPage(5, 7);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getPhonesList().size());
        Assert.assertEquals(1111, result.getPhonesList().get(0).getId());

    }

    @Test
    public void findAllPage_IllegalArgumentCountOfGreaterThePossibility_ReturnError() throws Exception {
        try {
            PagePhonesListForWeb result = phonesService.findAllPage(100 , 1000);
            ;
        } catch (Exception e){
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Error number of phones on the page. The number must be in the range of 5 ... 200");
        }
    }

    @Test
    public void findAllPage_IllegalArgumentCountOfLessThePossibility_ReturnError() throws Exception {
        try {
            PagePhonesListForWeb result = phonesService.findAllPage(100 , 3);
            ;
        } catch (Exception e){
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Error number of phones on the page. The number must be in the range of 5 ... 200");
        }
    }
}
