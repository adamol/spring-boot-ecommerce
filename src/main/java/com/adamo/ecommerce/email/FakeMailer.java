package com.adamo.ecommerce.email;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Qualifier("fake")
public class FakeMailer implements Mailer {

    private static ArrayList<Email> sentEmails = new ArrayList<>();

    @Override
    public void sendMail(Email mail) {
        sentEmails.add(mail);
    }

    public Email getLatest() {
        if (sentEmails.size() < 1) {
            return null;
        }
        return sentEmails.get(sentEmails.size() - 1);
    }
}
