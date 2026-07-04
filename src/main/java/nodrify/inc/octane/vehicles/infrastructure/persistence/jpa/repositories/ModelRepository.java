package nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.vehicles.domain.model.aggregates.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {
    boolean existsByName(String name);

    List<Model> findByBrand(String brand);

    @Query("SELECT DISTINCT m.brand from Model m ORDER BY m.brand")
    List<String> findAllBrands();
}
