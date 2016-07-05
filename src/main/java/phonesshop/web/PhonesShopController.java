package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import phonesshop.domain.PhoneForList;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.util.DebugMode;

import java.util.List;

/**
 * Created by kostya.nikitin on 6/29/2016.
 */
@RestController
public class PhonesShopController {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PhonesRepository phonesRepository;


    @RequestMapping("/phoneslist/page{cur:[\\d]+}for{countonpage:[\\d]+}")
    public List<PhoneForList> findAllPage(@PathVariable int cur, @PathVariable int countonpage) throws Exception {
        try {
            if (DebugMode.isDebug())
                logger.debug("Get page " + cur + " phones list. Count phones on page is " + countonpage);

            // test correct value cur and countpage
            if ((countonpage > 200)||(countonpage < 5) ) {
                throw new Exception("Error number of phones on the page. The number must be in the range of 5 ... 200");
            }

            Page<Phones> pagePhones = phonesRepository.findAll(new PageRequest(cur, countonpage));

            Page<PhoneForList> pageSmallPhones = pagePhones.map(phones -> new PhoneForList(phones));

            return pageSmallPhones.getContent();
        } catch (Exception e) {
            logger.error(" In find All pages: " + e.getMessage());
            throw e;
        }


    };


}
