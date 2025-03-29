package org.example.back_end.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveProfileImage(MultipartFile profileImage);
    String saveVetProfileImage(MultipartFile profileImage);
    String savePetImage(MultipartFile petImage);
}
