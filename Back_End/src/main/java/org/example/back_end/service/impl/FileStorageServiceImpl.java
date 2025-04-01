package org.example.back_end.service.impl;

import org.example.back_end.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String PROFILE_IMAGE_DIRECTORY = "profileImages\\";
    private static final String VET_PROFILE_IMAGE_DIRECTORY = "vetProfileImages\\";
    private static final String PET_IMAGE_DIRECTORY = "petImages\\";

    private static final String UPLOAD_DIRECTORY = "assets\\images\\backendImages\\";

    private static final String DEFAULT_DIRECTORY ="C:\\Users\\priya\\Desktop\\Projects\\Advanced API\\Spring\\Pet-Clinic-FinalProject\\Front_End\\" + UPLOAD_DIRECTORY;
    private static final String USER_PROFILE_UPLOAD_DIR = DEFAULT_DIRECTORY + PROFILE_IMAGE_DIRECTORY;
    private static final String VET_PROFILE_UPLOAD_DIR = DEFAULT_DIRECTORY + VET_PROFILE_IMAGE_DIRECTORY;
    private static final String PET_IMAGE_UPLOAD_DIR = DEFAULT_DIRECTORY + PET_IMAGE_DIRECTORY;


    static {
        createIfNotExistDirectory(USER_PROFILE_UPLOAD_DIR);
        createIfNotExistDirectory(VET_PROFILE_UPLOAD_DIR);
        createIfNotExistDirectory(PET_IMAGE_UPLOAD_DIR);
    }

    @Override
    public String saveProfileImage(MultipartFile profileImage) {
        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
        Path filePath = Paths.get(USER_PROFILE_UPLOAD_DIR + fileName);

        try {
            //save the image
            profileImage.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return UPLOAD_DIRECTORY + PROFILE_IMAGE_DIRECTORY + fileName;
    }

    @Override
    public String saveVetProfileImage(MultipartFile profileImage) {
        //genarete unique file name
        String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
        Path filePath = Paths.get(VET_PROFILE_UPLOAD_DIR + fileName);

        try {
            //save the image
            profileImage.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return UPLOAD_DIRECTORY + VET_PROFILE_IMAGE_DIRECTORY + fileName;
    }

    @Override
    public String savePetImage(MultipartFile petImage) {
        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + petImage.getOriginalFilename();
        Path filePath = Paths.get(PET_IMAGE_UPLOAD_DIR + fileName);

        try {
            //save the image
            petImage.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return UPLOAD_DIRECTORY + PET_IMAGE_DIRECTORY + fileName;

    }

    static void createIfNotExistDirectory(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); //Create directory if it doesn’t exist
        }
    }

}
