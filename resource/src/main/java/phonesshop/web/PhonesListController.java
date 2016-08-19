package phonesshop.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phonesshop.dto.PagePhonesListForWeb;
import phonesshop.service.PhonesService;

/**
 * Created by kostya.nikitin on 6/29/2016.
 */
@RestController
public class PhonesListController {

    private static final Logger logger = Logger.getLogger("forPhonesShop");

    @Autowired
    private PhonesService phonesService;


    @RequestMapping("/phoneslist/page{cur:[\\d]+}for{countonpage:[\\d]+}")
    public PagePhonesListForWeb findAllPage(@PathVariable int cur, @PathVariable int countonpage) throws Exception {
        try {
            PagePhonesListForWeb pagePhonesListForWeb = phonesService.findAllPage(cur, countonpage);
            if (pagePhonesListForWeb != null){
                logger.debug("Get page " + cur + " phones list. Count phones on page is " + countonpage + ". All of page for this request = "  + pagePhonesListForWeb.getPageCount());
            } else {
                logger.debug("Get page " + cur + " phones list. Count phones on page is " + countonpage + ". But no page in answer. " );
            }
            return pagePhonesListForWeb;
        } catch (IllegalArgumentException e) {
            logger.error(" Parameters ( current page = " + cur + " , count phones on page = " + countonpage + " ). Error " + e.getClass() + " with message " + e.getMessage());
            throw e;
        }
    };

}
