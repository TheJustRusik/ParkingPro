package we.hack.securityservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import we.hack.securityservice.model.entity.ParkingInfo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingCoordinateExtractor {

    public List<ParkingInfo> extractParkingInfo(String jsonResponse) {
        List<ParkingInfo> parkingInfoList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode resultsNode = root.get("results");
            if (resultsNode != null && resultsNode.isArray()) {
                for (JsonNode resultNode : resultsNode) {
                    ParkingInfo parkingInfo = new ParkingInfo();

                    JsonNode geometryNode = resultNode.get("geometry");
                    if (geometryNode != null) {
                        JsonNode locationNode = geometryNode.get("location");
                        if (locationNode != null) {
                            double lat = locationNode.get("lat").asDouble();
                            double lng = locationNode.get("lng").asDouble();
                            parkingInfo.setLatitude(lat);
                            parkingInfo.setLongitude(lng);
                        }
                    }

                    JsonNode nameNode = resultNode.get("name");
                    if (nameNode != null) {
                        parkingInfo.setName(nameNode.asText());
                    }

                    parkingInfo.setType("Underground");
                    parkingInfo.setPrice(5.0);
                    parkingInfo.setCapacity(50);
                    parkingInfo.setCurrent(20);

                    parkingInfoList.add(parkingInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parkingInfoList;
    }
}


