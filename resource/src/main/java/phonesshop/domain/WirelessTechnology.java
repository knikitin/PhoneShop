package phonesshop.domain;

import javax.persistence.*;

/**
 * Created by kostya.nikitin on 7/29/2016.
 */
@Entity
public class WirelessTechnology {

    private long id;

    private String technology;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public WirelessTechnology(String technology) {
        this.technology = technology;
    }

    public WirelessTechnology() {
    }
}
