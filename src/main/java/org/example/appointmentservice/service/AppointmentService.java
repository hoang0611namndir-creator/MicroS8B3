package org.example.appointmentservice.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.example.appointmentservice.client.DoctorClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.appointmentservice.client.PatientClient;
import org.example.appointmentservice.dto.ApiResponseError;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final DoctorClient doctorClient;
    private final PatientClient patientClient;


    public AppointmentService(DoctorClient doctorClient, PatientClient patientClient) {
        this.doctorClient = doctorClient;
        this.patientClient = patientClient;
    }

    @Retry(
            name = "patientRetry",
            fallbackMethod = "getPatientFallback"
    )
    public String getPatient(Long patientId) {

        System.out.println(
                "Calling Patient-Service..."
        );

        return patientClient.getPatient(patientId);
    }

    public String getPatientFallback(
            Long patientId,
            Exception e
    ) {

        return "Không thể kết nối tới Patient-Service. "
                + "Vui lòng thử lại sau.";
    }

    @CircuitBreaker(
            name = "doctorServiceCB",
            fallbackMethod = "doctorServiceFallback"
    )
    public String checkDoctorSchedule(Long doctorId) {

        System.out.println("Calling Doctor-Service...");

        return doctorClient.getDoctorSchedule(doctorId);
    }

    public String doctorServiceFallback(
            Long doctorId,
            Throwable throwable
    ) {

        System.out.println(
                "Doctor-Service is unavailable: "
                        + throwable.getMessage()
        );

        return "Doctor-Service đang tạm thời không khả dụng. "
                + "Vui lòng thử lại sau.";
    }

    @CircuitBreaker(
            name = "doctorServiceCB",
            fallbackMethod = "getDoctorFallback"
    )
    public String getDoctor(Long doctorId) {

        return doctorClient.getDoctor(doctorId);
    }

    public ApiResponseError getDoctorFallback(Exception e) {

        return new ApiResponseError(
                503,
                "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây"
        );
    }
}