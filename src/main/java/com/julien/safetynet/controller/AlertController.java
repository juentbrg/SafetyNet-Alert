package com.julien.safetynet.controller;

import com.julien.safetynet.pojo.*;
import com.julien.safetynet.service.AlertService;
import com.julien.safetynet.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AlertController {
    private final AlertService alertService;
    private static final Logger logger = LoggerFactory.getLogger(AlertController.class);

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PersonCovered>> getPeopleCovered(@RequestParam int stationNumber) {
        try {
            PersonCovered personCovered = alertService.getPersonCovered(stationNumber);

            if (null != personCovered) {
                logger.info("Successful request for station number {}", stationNumber);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved covered persons.", personCovered));
            } else {
                logger.error("No covered persons found for {}", stationNumber);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", stationNumber, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ApiResponse<List<Child>>> findChildByAdress(@RequestParam String address){
        if (address.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Address field is missing.", null));
        }
        try {
            List<Child> childList = alertService.findChildByAddress(address);

            if (!childList.isEmpty()) {
                logger.info("Successful request for address {}", address);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved children.", childList));
            } else {
                logger.error("No children found for {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", address, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<ApiResponse<List<String>>> getPhoneNumberResidentServed(@RequestParam int firestation){
        try {
            List<String> phoneList = alertService.getPhoneNumberResidentServed(firestation);

            if (!phoneList.isEmpty()) {
                logger.info("Successful request for station number {}", firestation);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved resident served phone number.", phoneList));
            } else {
                logger.error("No resident served phone number found for {}", firestation);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", firestation, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<ApiResponse<InhabitantWithFireStation>> getInhabitantAndFireStationByAddress(@RequestParam String address){
        if (address.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Address field is missing.", null));
        }
        try {
            InhabitantWithFireStation inhabitantAndFireStation = alertService.getInhabitantAndFireStationByAddress(address);

            if (null != inhabitantAndFireStation) {
                logger.info("Successful request for address {}", address);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved inhabitant list.", inhabitantAndFireStation));
            } else {
                logger.error("No inhabitant found for {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", address, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<ApiResponse<List<Hearth>>> getHearthByStationNumber(@RequestParam List<Integer> stations) {
        try {
            List<Hearth> hearthList = alertService.getHearthByStationNumber(stations);

            if (!hearthList.isEmpty()) {
                logger.info("Successful request for stations number {}", stations);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved hearth list.", hearthList));
            } else {
                logger.error("No hearth found for stations {}", stations);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", stations, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<ApiResponse<List<InhabitantWithEmail>>> getInhabitantByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Firstname or lastname field is missing.", null));
        }
        try {
            List<InhabitantWithEmail> inhabitantList = alertService.getInhabitantByFullName(firstName, lastName);

            if (!inhabitantList.isEmpty()) {
                logger.info("Successful request for firstName {} and lastName {}", firstName, lastName);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved inhabitant.", inhabitantList));
            } else {
                logger.error("No inhabitant found for firstName {} and lastName {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {} {}", firstName, lastName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<ApiResponse<List<String>>> getCommunityEmailByCity(@RequestParam String city) {
        if (city.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("City field is missing.", null));
        }
        try {
            List<String> emailCommunity = alertService.getCommunityEmailByCity(city);

            if (!emailCommunity.isEmpty()) {
                logger.info("Successful request for city {}", city);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved community emails.", emailCommunity));
            } else {
                logger.error("No inhabitant found for city {}", city);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request with {}", city, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
