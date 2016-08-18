package phonesshop.service;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ResourceLoader;
import phonesshop.dto.PagePhonesListForWeb;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by kostya.nikitin on 8/18/2016.
 * Duplicate test. Trying done parametrized test
 */
@RunWith(DataProviderRunner.class)
public class PhonesServiceImplTests {
    @Test
    @DataProvider(value = {"100,1000","100,3"}, splitBy = ",")
    public void findAllPage_IllegalArgumentCountOnPage_ReturnError(int cur, int countonpage) throws Exception {
        PhonesService phonesService = new PhonesServiceImpl(mock(ResourceLoader.class));
        try {
            PagePhonesListForWeb result = phonesService.findAllPage(cur , countonpage);
            ;
        } catch (Exception e){
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Error number of phones on the page. The number must be in the range of 5 ... 200");
        }
    }
}
