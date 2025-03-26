package org.example.back_end.repo;

import org.example.back_end.entity.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepo extends JpaRepository<Pet, Integer> {
    Pet findByUserEmail(String email);
    Pet findByPetId(Integer petId);
}
