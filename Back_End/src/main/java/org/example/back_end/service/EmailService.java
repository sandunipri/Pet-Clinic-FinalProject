package org.example.back_end.service;

import org.apache.commons.lang3.ClassUtils;

public interface EmailService {

    void sendRegisteredEmail(String name, String email, String subject);
}


