package org.example.back_end.service.impl;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.VeterinarianDTO;
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

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;


/*    @Override
    public void savePet(PetDTO petDTO) {
        User user = userRepo.findByEmail(petDTO.getUserEmail());
        Pet pet = modelMapper.map(petDTO, Pet.class);
        pet.setUser(user);
        petRepo.save(pet);

    }*/

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
        PetDTO petDTO = new PetDTO();
        petDTO.setPetName(addPetFormDTO.getPetName());
        petDTO.setBirthDate(addPetFormDTO.getBirthDate());
        petDTO.setSpecies(addPetFormDTO.getSpecies());
        petDTO.setWeight(addPetFormDTO.getWeight());
        petDTO.setBreed(addPetFormDTO.getBreed());
        petDTO.setAge(addPetFormDTO.getAge());
        petDTO.setGender(addPetFormDTO.getGender());
        petDTO.setPetImage(addPetFormDTO.getPetImage().getOriginalFilename());
        return petDTO;
    }

    @Override
    public List<PetDTO> getPetsFromUser(UserDTO userDTO) {
        return modelMapper.map(petRepo.findByUserEmail(userDTO.getEmail()), new TypeToken<List<PetDTO>>() {}.getType());
    }
}
