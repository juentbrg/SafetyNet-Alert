package com.julien.safetynet.controller;

import com.julien.safetynet.entity.PersonEntity;
import com.julien.safetynet.service.PersonService;
import com.julien.safetynet.utils.ApiResponse;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<PersonEntity>> getPersonByFullName(@RequestParam Map<String, String> requestMap) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName or lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: Parameters firstName and lastName cannot be empty.", null));
            }

            PersonEntity person = personService.getPersonByFullName(firstName, lastName);

            if (person != null) {
                logger.info("Successful request for {} {}", firstName, lastName);
                return ResponseEntity.ok(new ApiResponse<>("Successfully retrieved person.", person));
            } else {
                logger.error("No person found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request for {} {}", firstName, lastName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addPerson(@RequestBody PersonEntity personEntity) {
        boolean created = personService.addPerson(personEntity);
        try {
            if (created) {
                logger.info("Person successfully created");
                return ResponseEntity.ok(new ApiResponse<>("Person successfully created.", null));
            } else {
                logger.error("Unable to add new person");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Unable to add new person.", null));
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updatePerson(@RequestParam Map<String, String> requestMap, @RequestBody PersonEntity personEntity) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        boolean updated = personService.updatePerson(firstName, lastName, personEntity);

        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName or lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: firstName or lastName cannot be empty.", null));
            }

            if (updated) {
                logger.info("Person {} {} successfully updated", firstName, lastName);
                return ResponseEntity.ok(new ApiResponse<>("Person successfully updated.", null));
            } else {
                logger.error("No person found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deletePerson(@RequestParam Map<String, String> requestMap) {
        String firstName = requestMap.get("firstName");
        String lastName = requestMap.get("lastName");
        boolean deleted = personService.deletePerson(firstName, lastName);

        try {
            if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
                logger.error("Invalid request: firstName and lastName cannot be empty.");
                return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid request: firstName and lastName cannot be empty.", null));
            }

            if (deleted) {
                logger.info("Person successfully deleted");
                return ResponseEntity.ok(new ApiResponse<>("Person successfully deleted.", null));
            } else {
                logger.error("No person found for {} {}", firstName, lastName);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
