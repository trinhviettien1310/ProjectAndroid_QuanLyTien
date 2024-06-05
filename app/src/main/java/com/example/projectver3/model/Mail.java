package com.example.projectver3.model;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    /* khởi tạo biến để random password*/
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;
    private static final Random generator = new Random();

    //Biến lưu mật khẩu mới khi gửi về email
    public static String newPass= "";

    //Biến lưu mã otp khi gửi về email
    public static String otp = "";

    //Hàm gửi mã otp về email
    public static void sendEmailOTP(String sReceiverEmail) {
        try {

            String stringSenderEmail = "21211tt1444@mail.tdc.edu.vn";
            String stringReceiverEmail = sReceiverEmail;
            String stringPasswordSenderEmail = "01692465301";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            properties.put("mail.smtp.port", "587"); //TLS Port
            properties.put("mail.smtp.auth", "true"); //enable authentication
            properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Quản Lý Tài Chính - Mã OTP");
            otp = createOtp();
            mimeMessage.setText("Mã otp của bạn là: " + otp);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //tạo mã otp
    public static String createOtp() {
        //Vòng lặp in ra 5 số ngẫu nhiên
        String otp = "";
        for (int i=0; i<5; i++) {
            Random rand = new Random();
            int ranNum = rand.nextInt(10);
            otp += ranNum;
        }
        return  otp;
        //Log.d("otp",otp);
    }

    //hàm gửi mật khẩu mới về email
    public static void sendEmailNewPass(String sReceiverEmail) {

        //gọi hàm random tạo mật khẩu mới
        int numberOfCharactor = 8;
        newPass = randomAlphaNumeric(numberOfCharactor);

        try {

            String stringSenderEmail = "21211tt1444@mail.tdc.edu.vn";
            String stringReceiverEmail = sReceiverEmail;
            String stringPasswordSenderEmail = "01692465301";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            properties.put("mail.smtp.port", "587"); //TLS Port
            properties.put("mail.smtp.auth", "true"); //enable authentication
            properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Quản lý Tài Chính - Đặt Lại Mật Khẩu");
            otp = createOtp();
            mimeMessage.setText("Mật khẩu mới của bạn là: " + newPass +"\n\nHãy đổi lại mật khẩu theo ý bạn trong phần cài đặt");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //hàm random số
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    //hàm random
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
}
