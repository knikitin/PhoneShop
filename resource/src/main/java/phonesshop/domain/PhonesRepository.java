package phonesshop.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kostya.nikitin on 6/28/2016.
 */
public interface PhonesRepository extends JpaRepository<Phones, Long> {

    List<Phones> findAll();

}
