package phonesshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by kostya.nikitin on 7/29/2016.
 */
@Entity
public class DirectoryNavigations {

    private long id;

    @Column( name = "type_navigation")
    private String typeNavigation;

    private Set<Phones> phones;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeNavigation() {
        return typeNavigation;
    }

    public void setTypeNavigation(String typeNavigation) {
        this.typeNavigation = typeNavigation;
    }

    public DirectoryNavigations(String typeNavigation) {
        this.typeNavigation = typeNavigation;
    }

    public DirectoryNavigations() {
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "directoryNavigations", cascade = CascadeType.ALL )
    @JsonIgnore
    public Set<Phones> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phones> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "DirectoryNavigations{" +
                "id=" + id +
                ", typeNavigation='" + typeNavigation + '\'' +
                '}';
    }
}
