package org.example.back_end.repo;

import org.example.back_end.entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalReportRepo extends JpaRepository<MedicalReport, Integer> {
}
