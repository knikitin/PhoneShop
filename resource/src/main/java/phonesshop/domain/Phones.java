package phonesshop.domain;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Phones {
//    private static final Logger logger = Logger.getLogger("forPhonesShop");

    private long id;

    private String model;

    private String brand;

    private String os;

    @Column( name = "screen_resolution")
    private String screenResolution;

    private Float price;

    @Column( name = "screen_size")
    private Float screenSize;

    @Column( name = "camera_resolution")
    private String cameraResolution;

    private String cpu;

    @Column( name = "core_number")
    private Integer coreNumber;

    @Column( name = "memory_in")
    private Integer memoryIn;


    private Integer ram;

    private Float weight;

    private Float height;

    private Float width;

    private Float depth;

    @Column( name = "sim_type")
    private String simType;

    @Column( name = "sim_number")
    private Integer simNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Float screenSize) {
        this.screenSize = screenSize;
    }

    public String getCameraResolution() {
        return cameraResolution;
    }

    public void setCameraResolution(String cameraResolution) {
        this.cameraResolution = cameraResolution;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Integer getCoreNumber() {
        return coreNumber;
    }

    public void setCoreNumber(Integer coreNumber) {
        this.coreNumber = coreNumber;
    }

    public Integer getMemoryIn() {
        return memoryIn;
    }

    public void setMemoryIn(Integer memoryIn) {
        this.memoryIn = memoryIn;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public Integer getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(Integer simNumber) {
        this.simNumber = simNumber;
    }


}
