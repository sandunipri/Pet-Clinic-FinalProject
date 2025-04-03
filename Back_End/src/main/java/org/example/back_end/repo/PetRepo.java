package org.example.back_end.repo;

import org.example.back_end.entity.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PetRepo extends JpaRepository<Pet, Integer> {
    List<Pet> findPetsByUserEmail(String email);
    Pet findByPetId(Integer petId);
}
