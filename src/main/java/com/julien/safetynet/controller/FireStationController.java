package com.julien.safetynet.controller;

import com.julien.safetynet.entity.FireStationEntity;
import com.julien.safetynet.pojo.PersonCovered;
import com.julien.safetynet.service.FireStationService;
import com.julien.safetynet.utils.ApiResponse;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fire-station")
public class FireStationController {

    private final FireStationService fireStationService;

    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<FireStationEntity>> getFireStationByAddress(@RequestParam String address) {
        try {
            if (StringUtils.isBlank(address)) {
                logger.error("Invalid request: address cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: address cannot be empty.", null));
            }

            FireStationEntity fireStation = fireStationService.getFireStationByAddress(address);

            if (fireStation != null) {
                logger.info("Successful request for {}", fireStation);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved fire station.", fireStation));
            } else {
                logger.error("No fire station found for {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request for {}", address, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addFireStation(@RequestBody FireStationEntity firestationEntity) {
        boolean created = fireStationService.addFireStation(firestationEntity);
        try {
            if (created) {
                logger.info("Fire station successfully created");
                return ResponseEntity.ok(new ApiResponse<>("Fire station successfully created.", null));
            } else {
                logger.error("Unable to add new fire station");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Unable to add new fire station.", null));
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateFireStation(@RequestParam String address, @RequestBody FireStationEntity fireStationEntity) {
        boolean updated = fireStationService.updateFireStation(address, fireStationEntity);

        try {
            if (StringUtils.isBlank(address)) {
                logger.error("Invalid request: address cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: address cannot be empty.", null));
            }

            if (updated) {
                logger.info("Fire station for address {} successfully updated", address);
                return ResponseEntity.ok(new ApiResponse<>("Fire station successfully updated.", null));
            } else {
                logger.error("No fire station found for address {}", address);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteFireStation(@RequestParam String address) {
        boolean deleted = fireStationService.deleteFireStation(address);

        try {
            if (StringUtils.isBlank(address)) {
                logger.error("Invalid request: address cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: address cannot be empty.", null));
            }

            if (deleted) {
                logger.info("Fire station successfully deleted");
                return ResponseEntity.ok(new ApiResponse<>("Fire station successfully deleted.", null));
            } else {
                logger.error("No fire station found for address {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
