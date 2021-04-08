package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTest {
	private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopkrst.com.bd"};
	private static final String[] TEST_EMAILS_EMPTY = {""};
	private static final String[] TEST_EMAILS_NULL = null;
	private static final String CONTENT = "content";
	private static final String CONTENT_TYPE = "Content type";
	private static final String FROM_ADDRESS = "From@Address.com";
	private static final String SUBJECT = "subject";
	private static final String CHARSET = "standard";
	private static final String HOST_NAME = "host name";
	private static final Date DATE = new Date();
	private static final int SOCKET_CONNECTION_TIMEOUT = 1;
	private static MimeMessage MESSAGE;
	private static MimeMultipart EMAIL_BODY = new MimeMultipart();
	private static final String SESSION = "Session";
	private static Collection<InternetAddress> TO_LIST = new ArrayList<InternetAddress>();
	private static Collection<InternetAddress> CC_LIST = new ArrayList<InternetAddress>();
	private static Collection<InternetAddress> BCC_LIST = new ArrayList<InternetAddress>();
	
	
	
	
	/* Concrete Email Class for testing */
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
		
	    
		
	}
	
	@After
	public void tearDownEmailTest() throws Exception{
		
	}
	
	/*
	 * Test addBcc(String email) function
	 */
	@Test
	public void testAddBcc() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	/*
	 * Test addBcc(String email =) function with empty email
	 */
	@Test(expected = EmailException.class)
	public void testAddBccEmpty() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.addBcc(TEST_EMAILS_EMPTY);
	}
	
	/*
	 * Test addBcc(String email =) function with null email
	 */
	@Test(expected = EmailException.class)
	public void testAddBccNull() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.addBcc(TEST_EMAILS_NULL);
	}
	
	/*
	 * Test addCc(String email) function
	 */
	@Test
	public void testAddCc() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.addCc(TEST_EMAILS[0]);
		assertEquals(1, email.getCcAddresses().size());
	}
	
	/*
	 * Test addHeader(String name, String value) function
	 */
	@Test
	public void testAddHeader() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		String name = "name";
		String value = "value";
		email.addHeader(name, value);
		assertEquals(1, email.headers.size());
	}
	
	/*
	 * Test addHeader(String name, String value) function with null name
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderNull() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		String value = "value";
		email.addHeader(TEST_EMAILS_EMPTY[0], value);
	}
	
	/*
	 * Test addReplyTo(String email, String name) function
	 */
	@Test
	public void testAddReplyTo() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.addReplyTo(TEST_EMAILS[0], TEST_EMAILS[1]);
		assertEquals(1, email.replyList.size());
	}
	
	/*
	 * Test buildMimeMessage() with empty from address function
	 */
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageEmptyFromAddress() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.buildMimeMessage();
	}
	
	/*
	 * Test buildMimeMessage() with this.toList.size() + this.ccList.size() + this.bccList.size() = 0
	 */
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageNoReciever() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		email.buildMimeMessage();
	}
	
	/*
	 * Test buildMimeMessage() with this.toList.size() > 0
	 */
	@Test
	public void testBuildMimeMessageWtoList() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		TO_LIST.add(new InternetAddress("to@list.com"));
		email.setTo(TO_LIST);
		email.buildMimeMessage();
		assertEquals(email.message.getAllRecipients(), email.toInternetAddressArray(email.toList));
	}
	
	/*
	 * Test buildMimeMessage() with this.ccList.size() > 0
	 */
	@Test
	public void testBuildMimeMessageWccList() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		CC_LIST.add(new InternetAddress("cc@list.com"));
		email.setCc(CC_LIST);
		email.buildMimeMessage();
		assertEquals(email.message.getAllRecipients(), email.toInternetAddressArray(email.ccList));
	}
	
	/*
	 * Test buildMimeMessage() with this.bccList.size() > 0
	 */
	@Test
	public void testBuildMimeMessageWbccList() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		BCC_LIST.add(new InternetAddress("bcc@list.com"));
		email.setBcc(BCC_LIST);
		email.buildMimeMessage();
		assertEquals(email.message.getAllRecipients(), email.toInternetAddressArray(email.bccList));
	}
	
	/*
	 * Test buildMimeMessage() with content 
	 */
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageContent() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		MimeBodyPart EMAIL_BODY_PART = new MimeBodyPart();
		EMAIL_BODY_PART.setText("Email Body");
		EMAIL_BODY.addBodyPart(EMAIL_BODY_PART);
		email.content = EMAIL_BODY;
		email.setFrom(FROM_ADDRESS);
		email.buildMimeMessage();
	}
	
	/*
	 * Test buildMimeMessage() with string content 
	 */
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageStringContent() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.updateContentType("text/plain");
		email.content = CONTENT;
		email.setFrom(FROM_ADDRESS);
		email.buildMimeMessage();
	}
	
	/*
	 * Test buildMimeMessage() with email body 
	 */
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageEmailBody() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		MimeBodyPart EMAIL_BODY_PART = new MimeBodyPart();
		EMAIL_BODY_PART.setText("Email Body");
		EMAIL_BODY.addBodyPart(EMAIL_BODY_PART);
		email.setContent(EMAIL_BODY);
		email.setFrom(FROM_ADDRESS);
		email.buildMimeMessage();
	}
	
	/*
	 * Test buildMimeMessage() with email body and receivers 
	 */
	@Test
	public void testBuildMimeMessageBodyAndReceivers() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		MimeBodyPart EMAIL_BODY_PART = new MimeBodyPart();
		EMAIL_BODY_PART.setText("Email Body");
		EMAIL_BODY.addBodyPart(EMAIL_BODY_PART);
		email.setContent(EMAIL_BODY);
		email.setFrom(FROM_ADDRESS);
		BCC_LIST.add(new InternetAddress("bcc@list.com"));
		email.setBcc(BCC_LIST);
		CC_LIST.add(new InternetAddress("cc@list.com"));
		email.setCc(CC_LIST);
		TO_LIST.add(new InternetAddress("to@list.com"));
		email.setTo(TO_LIST);
		email.buildMimeMessage();
		assertEquals(email.message.getSentDate(), email.getSentDate());
	}
	
	/*
	 * Test buildMimeMessage() with subject  
	 */
	@Test
	public void testBuildMimeMessageSubject() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		email.setSubject(SUBJECT);
		TO_LIST.add(new InternetAddress("to@list.com"));
		email.setTo(TO_LIST);
		email.buildMimeMessage();
		assertEquals(email.message.getSubject().toString(), email.getSubject());
	}
	
	/*
	 * Test buildMimeMessage() with subject and charset
	 */
	@Test
	public void testBuildMimeMessageSubjectCharset() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		email.setFrom(FROM_ADDRESS);
		email.setSubject(SUBJECT);
		TO_LIST.add(new InternetAddress("to@list.com"));
		email.setTo(TO_LIST);
		email.charset = CHARSET;
		email.buildMimeMessage();
		assertEquals(email.message.getSubject().toString(), email.getSubject());
	}
	
	
	/*
	 * Test buildMimeMessage() function with null message
	 */
	@Test(expected = IllegalStateException.class)
	public void testBuildMimeMessageNullMessage() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		MimeBodyPart EMAIL_BODY_PART = new MimeBodyPart();
		EMAIL_BODY_PART.setText("Email Body");
		EMAIL_BODY.addBodyPart(EMAIL_BODY_PART);
		email.setContent(EMAIL_BODY);
		email.setFrom(FROM_ADDRESS);
		BCC_LIST.add(new InternetAddress("bcc@list.com"));
		email.setBcc(BCC_LIST);
		CC_LIST.add(new InternetAddress("cc@list.com"));
		email.setCc(CC_LIST);
		TO_LIST.add(new InternetAddress("to@list.com"));
		email.setTo(TO_LIST);
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
	
	/*
	 * Test getHostName() function 
	 */
	@Test
	public void testGetHostName() throws Exception {
		Properties properties = new Properties();
	    Session session = Session.getDefaultInstance(properties, email.authenticator);
	    email.setMailSession(session);
		assertEquals(email.hostName, email.getHostName());
	}
	
	/*
	 * Test getHostName() function with null session
	 */
	@Test
	public void testGetHostNameNullSession() throws Exception {
		assertEquals(email.hostName, email.getHostName());
	}
	
	/*
	 * Test getSentDate() function 
	 */
	@Test
	public void testGetSentDate() throws Exception {
		email.setSentDate(DATE);
		assertEquals(email.getSentDate(), DATE);
	}
	
	/*
	 * Test getSocketConnectionTimeout() function 
	 */
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		email.setSocketConnectionTimeout(SOCKET_CONNECTION_TIMEOUT);
		assertEquals(SOCKET_CONNECTION_TIMEOUT, email.getSocketConnectionTimeout());
	}
	
	/*
	 * Test setFrom(String email) function 
	 */
	@Test
	public void testSetFrom() throws Exception {
		email.setFrom(FROM_ADDRESS);
		assertEquals(FROM_ADDRESS, email.getFromAddress().toString());
	}
	
	/*
	 * Test getMailSession() function 
	 */
	@Test(expected = EmailException.class)
	public void testGetMailSession() throws Exception {
		email.getMailSession();
	}
	
	/*
	 * Test getMailSession() function with host name 
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetMailSessionHostName() throws Exception {
		email.setHostName(HOST_NAME);
        Session emailSession = email.getMailSession();
        email.setHostName(HOST_NAME);
	}
	
	
}


