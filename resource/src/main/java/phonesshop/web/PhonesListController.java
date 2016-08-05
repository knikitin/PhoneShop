package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import phonesshop.dto.PhoneForList;
import phonesshop.domain.Phones;
import phonesshop.domain.PhonesRepository;
import phonesshop.dto.PagePhonesListForWeb;

/**
 * Created by kostya.nikitin on 6/29/2016.
 */
@RestController
public class PhonesListController {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesRepository phonesRepository;


    @RequestMapping("/phoneslist/page{cur:[\\d]+}for{countonpage:[\\d]+}")
    public PagePhonesListForWeb findAllPage(@PathVariable int cur, @PathVariable int countonpage) throws Exception {
        try {
            logger.debug("Get page " + cur + " phones list. Count phones on page is " + countonpage);

            // test correct value cur and countpage
            if ((countonpage > 200)||(countonpage < 5) ) {
                throw new Exception("Error number of phones on the page. The number must be in the range of 5 ... 200");
            }

            Page<Phones> pagePhones = phonesRepository.findAll(new PageRequest(cur-1, countonpage));

            Page<PhoneForList> pageSmallPhones = pagePhones.map(PhoneForList::new);

            return new PagePhonesListForWeb(pageSmallPhones.getContent(), pageSmallPhones.getTotalPages());
        } catch (Exception e) {
            logger.error(" In find All pages: " + e.getMessage());
            throw e;
        }
    };

}
