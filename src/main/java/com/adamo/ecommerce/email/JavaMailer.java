package com.adamo.ecommerce.email;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Qualifier("default")
public class JavaMailer implements Mailer {

    @Override
    public void sendMail(Email mail) {

    }
}
