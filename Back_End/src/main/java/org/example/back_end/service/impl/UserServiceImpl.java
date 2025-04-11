package org.example.back_end.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
import org.example.back_end.dto.tableModalDTO.UserTableModalDTO;
import org.example.back_end.entity.User;
import org.example.back_end.repo.UserRepository;
import org.example.back_end.service.UserService;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EntityManager entityManager;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        userDTO.setRole("USER");
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public int saveAdmin(UserDTO userDTO) {
        userDTO.setRole("ADMIN");
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public int updateUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.OK;
        } else {
            return VarList.Not_Acceptable;
        }
    }

    @Override
    public String getUserRoleByToken(String token) {
        return jwtUtil.getRoleFromToken(token);
    }

    @Override
    public UserDTO convertFormToUserDTO(RegisterFormDTO registerFormDTO, String savedPath) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(registerFormDTO.getEmail());
        userDTO.setName(registerFormDTO.getName());
        userDTO.setPassword(registerFormDTO.getPassword());
        userDTO.setAddress(registerFormDTO.getAddress());
        userDTO.setTelNo(registerFormDTO.getTelNo());
        userDTO.setGender(registerFormDTO.getGender());
        userDTO.setNic(registerFormDTO.getNic());
        userDTO.setEmergencyContact(registerFormDTO.getEmergencyContact());
        userDTO.setEmergencyContactName(registerFormDTO.getEmergencyContactName());
        userDTO.setProfileImage(savedPath);
        return userDTO;

    }

    @Override
    public UserDTO getUserByToken(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return searchUser(username);
    }

    @Override
    public boolean deleteUser(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        userRepository.deleteByEmail(username);

        if (userRepository.existsByEmail(username)) {
            return false;
        }
        return true;
    }

    @Override
    public List<UserTableModalDTO> getAllUsers(UserDTO userDTO) {
        //check the user role
    /*    if (userDTO.getRole().equals("ADMIN")) {
            List<User> userList = userRepository.findAll();
            List<UserTableModalDTO> userTableModalDTOList = new ArrayList<>();
            for(User user : userList) {
                UserTableModalDTO modalDTO = modelMapper.map(user, UserTableModalDTO.class);
                modalDTO.setPetCount(user.getPetList().size());
                userTableModalDTOList.add(modalDTO);
            }
            return userTableModalDTOList;
        }else{
            return null;
        }*/
        User user = modelMapper.map(userDTO, User.class);

        String jpql = "SELECT u FROM User u WHERE u.role = : role";

        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("role", "USER");

        List<User> resultList = query.getResultList();

        List<UserTableModalDTO> userTableModalDTOList = new ArrayList<>();
        for(User user1 : resultList) {
            UserTableModalDTO modalDTO = modelMapper.map(user1, UserTableModalDTO.class);
            modalDTO.setPetCount(user1.getPetList().size());
            userTableModalDTOList.add(modalDTO);
        }
        return userTableModalDTOList;


    }

    @Override
    public int getAllUsersCount() {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.role = :role";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("role", "USER");
        Long count = query.getSingleResult();
        return count.intValue();

    }


}
