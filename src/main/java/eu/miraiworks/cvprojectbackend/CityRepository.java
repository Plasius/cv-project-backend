package eu.miraiworks.cvprojectbackend;
import eu.miraiworks.cvprojectbackend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    // Define custom query methods if needed
    List<City> findTop5ByNameStartingWith(String name);
}