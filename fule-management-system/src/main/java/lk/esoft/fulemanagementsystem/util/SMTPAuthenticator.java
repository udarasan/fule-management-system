package lk.esoft.fulemanagementsystem.util;

import org.springframework.stereotype.Component;

import javax.mail.PasswordAuthentication;

/**
 * @author Udara San
 * @TimeStamp 9:42 AM | 11/20/2022 | 2022
 * @ProjectDetails ecom-api
 */
@Component
public class SMTPAuthenticator extends javax.mail.Authenticator{

    private final String senderEmailId="esoftassigmenets@gmail.com";
    // TODO: 5/25/2022  please make sure turn on google account less secure app access
    private final String senderPassword="djg6C014mZvzc5y8";//put your gmail password here

    public PasswordAuthentication getPasswordAuthentication() {
        System.out.println(senderEmailId);
        return new PasswordAuthentication(senderEmailId,
                senderPassword);
    }

}