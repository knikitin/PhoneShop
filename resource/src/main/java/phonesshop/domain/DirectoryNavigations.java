package phonesshop.domain;

import javax.persistence.*;

/**
 * Created by kostya.nikitin on 7/29/2016.
 */
@Entity
public class DirectoryNavigations {

    private long id;

    @Column( name = "type_navigation")
    private String typeNavigation;

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

}
