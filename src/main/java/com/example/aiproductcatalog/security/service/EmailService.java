package com.example.aiproductcatalog.security.service;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${support.email}")
    private String fromEmail;

    private SendGrid sendGridClient;

    @PostConstruct
    public void init() {
        sendGridClient = new SendGrid(sendGridApiKey);
    }

    public void sendVerificationEmail(String to, String subject, String htmlContent) throws IOException {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGridClient.api(request);

        if (response.getStatusCode() >= 400) {
            throw new IOException("SendGrid error: " + response.getStatusCode() + " - " + response.getBody());
        }
    }
}

