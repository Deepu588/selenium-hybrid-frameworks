
package com.scart.utilities;
import javax.mail.*;

import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;
import java.io.File;

public class MailSender {
    private static void attachFile(Multipart multipart, File file) throws MessagingException {
        if (file.exists()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());
            multipart.addBodyPart(attachmentPart);
        }
    }

    public static void sendEmailWithAttachment(String attachFilePath) throws Exception {
    	
    	
   
 
        // Set email properties
        String host = "smtp.gmail.com";  // Replace with your SMTP host
        String port = "587";             // SMTP port (for Gmail, it's 587 for TLS)
        String mailFrom = "annamdeepak1912@gmail.com";  // Sender's email
        String password = "buhpssooxkllznbk";         // Sender's email password
        //buhp ssoo xkll znbk
        String mailTo = "deepuannam484@gmail.com";  // Recipient's email
        String subject =ReadMailContent.getEmailGHeader();
        String message =ReadMailContent.getEmailBody()+ReadMailContent.getEmailFooter();

        // Set properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Authentication
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        try {
            // Create a new email message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            msg.setSubject(subject);

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Add the HTML file attachment
            MimeBodyPart attachPart = new MimeBodyPart();
            DataSource source = new FileDataSource(new File(attachFilePath));
            attachPart.setDataHandler(new DataHandler(source));
            attachPart.setFileName(new File(attachFilePath).getName());
            multipart.addBodyPart(attachPart);

            // Get base directory for relative paths
            String baseDir = System.getProperty("user.dir");

            // Attach HTML report
            String htmlReport = ExtentReportManager.getLatestHtmlFile();
            if (htmlReport != null) {
                File htmlFile = new File(baseDir, htmlReport);
                attachFile(multipart, htmlFile);
            }

            // Attach MP4 recording
            String mp4File = ExtentReportManager.getLatestMp4File();
            if (mp4File != null) {
                File videoFile = new File(baseDir, mp4File);
                attachFile(multipart, videoFile);
            }

            // Attach PNG graph
            String pngFile = ExtentReportManager.getLatestPngFile();
            if (pngFile != null) {
                File graphFile = new File(baseDir, pngFile);
                attachFile(multipart, graphFile);
            }
            
            
            // Attach Excel File
            String excelFile = ExtentReportManager.getLatestExcelFile();
            if (excelFile != null) {
                File ordersFile = new File(baseDir, excelFile);
                attachFile(multipart, ordersFile);
            }
            
            
            
            String audioFile = ExtentReportManager.getLatestAudioFile();
            if (audioFile != null) {
                File audioFil = new File(baseDir, audioFile);
                attachFile(multipart, audioFil);
            }
            
            
//
//          String headerImage = ExtentReportManager.getHeaderImageFile();
//            if (headerImage != null) {
//                File htmlFile = new File(baseDir, headerImage);
//                attachFile(multipart, htmlFile);
//            }

            
//            String headerImage = ExtentReportManager.getHeaderImageFile();
//            if (headerImage != null) {
//                File headerFile = new File(baseDir + File.separator + headerImage);
//                if (headerFile.exists()) {
//                    System.out.println("Attaching header image from: " + headerFile.getAbsolutePath());
//                    attachFile(multipart, headerFile);
//                } else {
//                    System.out.println("Header image file not found at: " + headerFile.getAbsolutePath());
//                }
//            }
//            
            
            
            // Set the multipart content to the email message
            msg.setContent(multipart);

            // Send the email
            Transport.send(msg);
          //  System.out.println("Email sent successfully with attachment.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
