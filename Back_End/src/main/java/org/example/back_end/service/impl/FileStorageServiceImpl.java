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
/*    private static final String DEFAULT_DIRECTORY = "C:\\Users\\priya\\Desktop\\Projects\\Advanced API\\Spring\\Pet-Clinic-FinalProject\\Back_End\\src\\main\\resources\\static\\";
    private static final String DEFAULT_IMAGE_DIRECTORY = DEFAULT_DIRECTORY + "images\\";
    private static final String PROFILE_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "profileImages\\";
    private static final String VET_PROFILE_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "vetProfileImages\\";*/

    private static final String DEFAULT_DIRECTORY ="C:\\Users\\priya\\Desktop\\Projects\\Advanced API\\Spring\\Pet-Clinic-FinalProject\\Front_End\\assets\\images\\";
    private static final String DEFAULT_IMAGE_DIRECTORY = DEFAULT_DIRECTORY + "\\backendImages\\";
    private static final String PROFILE_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "profileImages\\";
    private static final String VET_PROFILE_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "vetProfileImages\\";


    static {
        createIfNotExistDirectory(DEFAULT_DIRECTORY);
        createIfNotExistDirectory(DEFAULT_IMAGE_DIRECTORY);
        createIfNotExistDirectory(PROFILE_UPLOAD_DIR);
        createIfNotExistDirectory(VET_PROFILE_UPLOAD_DIR);
    }

    @Override
    public String saveProfileImage(MultipartFile profileImage) {
        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
        Path filePath = Paths.get(PROFILE_UPLOAD_DIR + fileName);

        try {
            //save the image
            profileImage.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.toString();
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
        return filePath.toString();
    }

    static void createIfNotExistDirectory(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); //Create directory if it doesn’t exist
        }
    }

}
