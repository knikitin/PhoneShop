package phonesshop.domain;

/**
 * Created by kostya.nikitin on 6/29/2016.
 * this class using for reduce the amount of data transmitted
 */
public class PhoneForList {

    private long Id;
    private String Model;
    private String Brand;
    private Float Price;
    private Float ScreenSize;
    private String ScreenResolution;
    private String CameraResolution;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public Float getScreenSize() {
        return ScreenSize;
    }

    public void setScreenSize(Float screenSize) {
        ScreenSize = screenSize;
    }

    public String getScreenResolution() {
        return ScreenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        ScreenResolution = screenResolution;
    }

    public String getCameraResolution() {
        return CameraResolution;
    }

    public void setCameraResolution(String cameraResolution) {
        CameraResolution = cameraResolution;
    }

    public PhoneForList( Phones phones ){
        setId(phones.getId());
        setModel(phones.getModel());
        setBrand(phones.getBrand());
        setPrice(phones.getPrice());
        setScreenSize(phones.getScreenSize());
        setScreenResolution(phones.getScreenResolution());
        setCameraResolution(phones.getCameraResolution());
    }

}
