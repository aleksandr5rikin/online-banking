package ua.nure.petrikin.OnlineBanking.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.tomcat.util.codec.binary.Base64;

public class SecureUtils {
	
	private SecureUtils(){
		
	}
	
	private static final Random RANDOM = new SecureRandom();
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String hash(String str, String alg) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest digest;
		StringBuffer hexString = new StringBuffer();
		digest = MessageDigest.getInstance(alg);
		digest.update(str.getBytes("UTF-8"));
		for (byte d : digest.digest()) {
			hexString.append(getFirstHexDigit(d)).append(getSecondHexDigit(d));
		}
		return hexString.toString();
	}
	
	public static String getNextSalt() {
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return Base64.encodeBase64String( salt );
	}
	
	public static void sendEmail(String to, String text){
		
		String username = "aleksandr.5rikin@gmail.com";
		String password = "letsplay9379992@";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

	   try{
		   Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject("Testing Subject");
			message.setText(text);

			Transport.send(message);
	   }catch (MessagingException mex) {
	         mex.printStackTrace();
	   }
	}

	private static char getFirstHexDigit(byte x) {
		return HEX_DIGITS[(0xFF & x) / 16];
	}

	private static char getSecondHexDigit(byte x) {
		return HEX_DIGITS[(0xFF & x) % 16];
	}
	
}
