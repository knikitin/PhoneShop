package phonesshop.web;

import phonesshop.domain.PhoneForList;

import java.util.List;

/**
 * Created by kostya.nikitin on 7/12/2016.
 */
public class PagePhonesListForWeb {
    private List<PhoneForList> phonesList;
    private int pageCount;

    PagePhonesListForWeb(List<PhoneForList> phonesList, int pageCount){
        this.phonesList = phonesList;
        this.pageCount = pageCount;
    }

    public List<PhoneForList> getPhonesList() {
        return phonesList;
    }

    public int getPageCount() {
        return pageCount;
    }

}
