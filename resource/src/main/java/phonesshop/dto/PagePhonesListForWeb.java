package phonesshop.dto;

import java.util.List;

/**
 * Created by kostya.nikitin on 7/12/2016.
 */
public class PagePhonesListForWeb {
    private List<PhoneForList> phonesList;
    private int pageCount;

    public PagePhonesListForWeb(List<PhoneForList> phonesList, int pageCount){
        this.phonesList = phonesList;
        this.pageCount = pageCount;
    }

    public List<PhoneForList> getPhonesList() {
        return phonesList;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        if (phonesList != null)
            return "PagePhonesListForWeb{" +
                "phonesList.size=" + phonesList.size() +
                ", pageCount=" + pageCount +
                '}';
        else
            return "PagePhonesListForWeb{" +
                    "phonesList = null"   +
                    ", pageCount=" + pageCount +
                    '}';

    }
}
