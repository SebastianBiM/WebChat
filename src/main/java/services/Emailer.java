package services;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emailer {


    public void createEmail(String recipient, String subject, String content) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");

        JsonObject auth = authEmailer();

        Session sessionEmail = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(auth.getString("email"), auth.getString("password"));
                    }
                });

        try {

            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(auth.getString("email")));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=UTF-8");

            Transport.send(message);

        } catch (
                MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonObject authEmailer() {

        //parse JSON response and return 'success' value
        JsonReader jsonReader = Json.createReader(getClass().getResourceAsStream("/authEmailer.json"));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        return jsonObject;
    }

}
