package org.example.back_end.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.back_end.dto.AuthDTO;
import org.example.back_end.dto.ProfileDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.service.EmailService;
import org.example.back_end.service.impl.UserServiceImpl;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;
    private final EmailService emailService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;


    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO, EmailService emailService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.emailService = emailService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUserDTO(loadedUser);
        authDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
    }


    @GetMapping("/googleLogin")
    public RedirectView googleLogin(@RequestParam("code") String code) {
        String accessTokenGoogle = getOauthAccessTokenGoogle(code);
        JsonObject profileDetailsGoogle = getProfileDetailsGoogle(accessTokenGoogle);

        String googleId = profileDetailsGoogle.get("id").getAsString();
        String email = profileDetailsGoogle.get("email").getAsString();
        String name = profileDetailsGoogle.get("name").getAsString();
        String picture = profileDetailsGoogle.get("picture").getAsString();

        UserDTO user = userService.searchUser(email);
        boolean isNew = false;
        if (user == null) {
            isNew = true;
            user = new UserDTO();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(googleId);
            user.setProfileImage(picture);
            user.setGoogleUser(true);
            userService.saveUser(user);  // Save new user
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(user.getName());
        profileDTO.setEmail(user.getEmail());
        profileDTO.setPhone(user.getTelNo());


        String encodedProfile = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String profileJson = profileJson = objectMapper.writeValueAsString(profileDTO);
            encodedProfile = URLEncoder.encode(profileJson, StandardCharsets.UTF_8.toString());
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        // Generate a JWT token for your system (for session management)
        String jwtToken = jwtUtil.generateToken(user);

        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:63342/Pet-Clinic-FinalProject/Front_End/view/client.html")
                .queryParam("token", jwtToken)
                .queryParam("accessToken", accessTokenGoogle)
                .queryParam("user", encodedProfile)
                .build()
                .toUriString();

        if (isNew) {
            emailService.sendRegisteredEmail(name, email, "Login Alert!");
        } else {
            emailService.sendRegisteredEmail(name, email, "Registered Successfully!");
        }

        return new RedirectView(redirectUrl);
    }

    private String getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/api/v1/auth/googleLogin");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        String response = restTemplate.postForObject(url, requestEntity, String.class);
        assert response != null;
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    private JsonObject getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return new Gson().fromJson(response.getBody(), JsonObject.class);
    }

}

