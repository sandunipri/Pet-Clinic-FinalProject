package org.example.back_end.service.impl;

import org.example.back_end.dto.PetDTO;
import org.example.back_end.dto.UserDTO;
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
    public void savePet(PetDTO petDTO, UserDTO userDTO) {
        //set the user to the pet.
        Pet pet = modelMapper.map(petDTO, Pet.class);
        User user = modelMapper.map(userDTO, User.class);
        pet.setUser(user);
        petRepo.save(pet);

    }

    @Override
    public List<PetDTO> getAllPets() {
        return modelMapper.map(petRepo.findAll(),new TypeToken<List<PetDTO>>(){}.getType());
    }


}
