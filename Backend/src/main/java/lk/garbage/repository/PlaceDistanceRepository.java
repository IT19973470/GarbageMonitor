package lk.garbage.repository;

import lk.garbage.entity.PlaceDistance;
import lk.garbage.entity.PlaceDistancePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceDistanceRepository extends JpaRepository<PlaceDistance, PlaceDistancePK> {

    @Query(value = "from PlaceDistance where placeFrom.mainLocation=?1 and placeTo.mainLocation=?1")
    List<PlaceDistance> getDistances(String mainLocation);

}
