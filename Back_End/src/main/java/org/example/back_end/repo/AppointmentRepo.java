package org.example.back_end.repo;

import org.example.back_end.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointments , Integer> {
}
