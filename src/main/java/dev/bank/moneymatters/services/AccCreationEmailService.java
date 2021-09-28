package dev.bank.moneymatters.services;

import dev.bank.moneymatters.constants.TransactionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AccCreationEmailService {
    @Autowired
    private JavaMailSender mailSender;



    public void sendEmail(String emailUserName, String emailPassword, String customerEmail) {

            SimpleMailMessage message = new SimpleMailMessage();

            System.out.println(customerEmail);

            message.setFrom("moneymatterscompanies@gmail.com");

            message.setTo(customerEmail);

            message.setText("ACCOUNT CREATION SUCCESSFULL!"

                    + "\n\nPlease find your username and password here"

                    + "\nChange your password in your first login"

                    + "\nUserName: "+ emailUserName +"\nPassword: "+emailPassword);

            message.setSubject("User credentials");

            mailSender.send(message);

            System.out.println("Mail sent...");

    }
}
