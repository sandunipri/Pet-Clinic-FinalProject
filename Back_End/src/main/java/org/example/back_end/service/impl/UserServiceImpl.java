package org.example.back_end.service.impl;


import org.example.back_end.dto.UserDTO;
import org.example.back_end.dto.formDTO.RegisterFormDTO;
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

import java.util.HashSet;
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
            User user=userRepository.findByEmail(email);
            return modelMapper.map(user,UserDTO.class);
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
//            userDTO.setRole("USER");
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
}
