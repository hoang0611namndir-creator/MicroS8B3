package org.example.appointmentservice.controller;

import org.example.appointmentservice.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService
    ) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/check-doctor/{doctorId}")
    public String checkDoctor(
            @PathVariable Long doctorId
    ) {

        return appointmentService.checkDoctorSchedule(doctorId);
    }

    @GetMapping("/doctor/{doctorId}")
    public String getDoctor(
            @PathVariable Long doctorId
    ) {

        return appointmentService.getDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public String getPatient(
            @PathVariable Long patientId
    ) {

        return appointmentService.getPatient(patientId);
    }
}