package org.example.back_end.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.AddPetFormDTO;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.User;
import org.example.back_end.repo.PetRepo;
import org.example.back_end.repo.UserRepository;
import org.example.back_end.service.PetService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void savePet(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        petRepo.save(pet);
    }

    @Override
    public List<PetDTO> getAllPets() {
        return modelMapper.map(petRepo.findAll(), new TypeToken<List<PetDTO>>() {
        }.getType());
    }
    @Override
    public PetDTO searchPet(int petId) {
        return petRepo.findById(petId)
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .orElse(null);
    }

    @Override
    public PetDTO searchPetByUserEmail(String email) {
        return modelMapper.map(petRepo.findByUserEmail(email), PetDTO.class);
    }

    @Override
    public PetDTO convertFormToPetDTO(AddPetFormDTO addPetFormDTO) {
        PetDTO petDTO = modelMapper.map(addPetFormDTO, PetDTO.class);
        petDTO.setPetImage(addPetFormDTO.getPetImage().getOriginalFilename());
        return petDTO;
    }

    @Override
    public List<PetDTO> getPetsFromUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        String jpql = "SELECT p FROM Pet p WHERE p.user = :user";

        TypedQuery<Pet> query = entityManager.createQuery(jpql, Pet.class);
        query.setParameter("user", user);

        List<Pet> resultList = query.getResultList();

       return modelMapper.map(resultList, new TypeToken<List<PetDTO>>() {
       }.getType());
    }

    @Override
    public boolean deletePet(int petId) {
        if (petRepo.existsById(petId)) {
            petRepo.deleteById(petId);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void updatePet(PetDTO petDTO) {
        if (petRepo.existsById(petDTO.getPetId())) {
            petRepo.save(modelMapper.map(petDTO, Pet.class));
        } else {
            throw new RuntimeException("Pet not found");

        }
    }


}
