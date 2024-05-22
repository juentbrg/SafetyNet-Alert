package com.julien.safetynet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julien.safetynet.entity.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonDataUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonDataUtils.class);

    @Value("${json.filePath}")
    private String jsonFilePath;

    public Data loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        if (!file.exists()) {
            logger.error("JSON file not found");
        }

        try {
            return objectMapper.readValue(file, Data.class);
        } catch (Exception e) {
            logger.error("Error loading data from JSON file", e);
            return null;
        }
    }

    public void saveAllData(Data data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            logger.error("Error saving fireStations to JSON file", e);
        }
    }
}
