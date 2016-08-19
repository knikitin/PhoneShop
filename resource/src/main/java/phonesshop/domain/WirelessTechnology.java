package phonesshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by kostya.nikitin on 7/29/2016.
 */
@Entity
public class WirelessTechnology {

    private long id;

    private String technology;

    private Set<Phones> phones;

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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "wirelessTechnology", cascade = CascadeType.ALL )
    @JsonIgnore
    public Set<Phones> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phones> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "WirelessTechnology{" +
                "technology='" + technology + '\'' +
                ", id=" + id +
                '}';
    }
}
