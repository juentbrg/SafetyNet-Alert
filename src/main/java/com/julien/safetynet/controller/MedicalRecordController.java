package com.julien.safetynet.controller;

import com.julien.safetynet.entity.MedicalRecordEntity;
import com.julien.safetynet.service.MedicalRecordService;
import com.julien.safetynet.utils.ApiResponse;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<MedicalRecordEntity>> getMedicalRecordByFullName(@RequestParam Map<String, String> requestMap) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName or lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: Parameters firstName or lastName cannot be empty.", null));
            }

            MedicalRecordEntity medicalRecord = medicalRecordService.getMedicalRecordByFullName(firstName, lastName);

            if (medicalRecord != null) {
                logger.info("Successful request for {} {}", firstName, lastName);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved medical record.", medicalRecord));
            } else {
                logger.error("No medical record found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request for {} {}", firstName, lastName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addMedicalRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        boolean created = medicalRecordService.addMedicalRecord(medicalRecordEntity);
        try {
            if (created) {
                logger.info("Medical record successfully added");
                return ResponseEntity.ok(new ApiResponse<>("Medical record successfully added.", null));
            } else {
                logger.error("Unable to add new medical record");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Unable to add new medical record.", null));
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateMedicalRecord(@RequestParam Map<String, String> requestMap, @RequestBody MedicalRecordEntity medicalRecordEntity) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        boolean updated = medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecordEntity);

        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName or lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: firstName or lastName cannot be empty.", null));
            }

            if (updated) {
                logger.info("Medical record for {} {} successfully updated", firstName, lastName);
                return ResponseEntity.ok(new ApiResponse<>("Medical record successfully updated.", null));
            } else {
                logger.error("No medical record found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteMedicalRecord(@RequestParam Map<String, String> requestMap) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);

        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName or lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: firstName or lastName cannot be empty.", null));
            }

            if (deleted) {
                logger.info("Medical record successfully deleted");
                return ResponseEntity.ok(new ApiResponse<>("Medical record successfully deleted.", null));
            } else {
                logger.error("No medical record found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
