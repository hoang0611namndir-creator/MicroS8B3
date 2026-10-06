package org.example.appointmentservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientClient {

    private final RestTemplate restTemplate;

    public PatientClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPatient(Long patientId) {

        return restTemplate.getForObject(
                "http://localhost:8081/api/patients/" + patientId,
                String.class
        );
    }
}