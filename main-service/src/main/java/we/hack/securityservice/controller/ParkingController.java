package we.hack.securityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import we.hack.securityservice.model.entity.ParkingInfo;
import we.hack.securityservice.service.GooglePlacesService;
import we.hack.securityservice.service.ParkingCoordinateExtractor;

import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    private GooglePlacesService googlePlacesService;

    @Autowired
    private ParkingCoordinateExtractor parkingCoordinateExtractor;

    @GetMapping("/parking-info")
    public List<ParkingInfo> getParkingInfo(@RequestParam double latitude,
                                            @RequestParam double longitude,
                                            @RequestParam int radius) {
        String jsonResponse = googlePlacesService.getParkingLocations(latitude, longitude, radius);
        List<ParkingInfo> parkingInfoList = parkingCoordinateExtractor.extractParkingInfo(jsonResponse);

        ParkingInfo nearestParking = findNearestParkingInDatabase(latitude, longitude, radius);
        int[] arr = new int[2];
        arr[0]=(int)latitude*2;
        arr[1]=(int)longitude*2;

        if (nearestParking == null) {
            ParkingInfo newParking = new ParkingInfo();
            newParking.setLatitude(latitude);
            newParking.setLongitude(longitude);
            newParking.setType("Underground");
            newParking.setPrice(5.0);
            newParking.setCapacity(50);
            newParking.setCurrent(20);
            newParking.setPoints(arr);

            nearestParking = googlePlacesService.addParkingInfo(newParking);

            parkingInfoList.add(nearestParking);
        }

        return parkingInfoList;
    }

    private ParkingInfo findNearestParkingInDatabase(double latitude, double longitude, int radius) {
        List<ParkingInfo> parkingList = googlePlacesService.getParkingInfoFromDatabase(latitude, longitude, radius);

        if (!parkingList.isEmpty()) {
            return parkingList.get(0);
        }

        return null;
    }

    @PostMapping("/parking-add")
    public ParkingInfo addParkingInfo(@RequestBody ParkingInfo parkingInfo) {
        return googlePlacesService.addParkingInfo(parkingInfo);
    }
}



