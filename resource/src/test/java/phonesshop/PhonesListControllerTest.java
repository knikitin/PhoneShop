package phonesshop;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import phonesshop.domain.PhonesRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by kostya.nikitin on 6/30/2016.
 */
public class PhonesListControllerTest extends ApplicationTests  {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

    }


    @Test
    public void validate_get_address() throws Exception {

        mockMvc.perform(get("/phoneslist/page10for10"))
                .andExpect(status().isOk())
                .andExpect(
                       content().contentType("application/json;charset=UTF-8"));

    }


}
