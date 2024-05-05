package we.hack.securityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import we.hack.securityservice.model.entity.ParkingInfo;
import we.hack.securityservice.repository.ParkingInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class GooglePlacesService {
    private final ParkingInfoRepository parkingInfoRepository;


    @Value("${google.api.key}")
    private String apiKey;

    private final String GOOGLE_PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    public String getParkingLocations(double latitude, double longitude, int radius) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?location=%f,%f&radius=%d&types=parking&key=%s",
                GOOGLE_PLACES_API_URL, latitude, longitude, radius, apiKey);
        return restTemplate.getForObject(url, String.class);
    }
    public ParkingInfo addParkingInfo(ParkingInfo parkingInfo) {
        parkingInfoRepository.save(parkingInfo);
        return parkingInfo;
    }
    public List<ParkingInfo> getParkingInfoFromDatabase(double latitude, double longitude, int radius) {
        return parkingInfoRepository.findParkingByLocationAndRadius(latitude, longitude, radius);
    }
}
