package org.example.back_end.repo;

import org.example.back_end.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Integer> {
}
