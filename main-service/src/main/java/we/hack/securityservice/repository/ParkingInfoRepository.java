package we.hack.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import we.hack.securityservice.model.entity.ParkingInfo;

import java.util.List;

@Repository
public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {
    @Query("SELECT p FROM ParkingInfo p WHERE " +
            "FUNCTION('acos', FUNCTION('sin', :latitude) * FUNCTION('sin', p.latitude) " +
            "+ FUNCTION('cos', :latitude) * FUNCTION('cos', p.latitude) * FUNCTION('cos', :longitude - p.longitude)) * 6371 <= :radius")
    List<ParkingInfo> findParkingByLocationAndRadius(double latitude, double longitude, int radius);

}

