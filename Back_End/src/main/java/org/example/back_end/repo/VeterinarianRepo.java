package org.example.back_end.repo;


import org.example.back_end.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepo extends JpaRepository<Veterinarian, Integer> {
}
