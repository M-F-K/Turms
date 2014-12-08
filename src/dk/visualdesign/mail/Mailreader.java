package dk.visualdesign.mail;

import java.io.IOException;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;

public class Mailreader {

	private final String _POP3_HOST_NAME;
	private final String _POP3_USER;
	private final String _POP3_PWD;
	
	public Mailreader(String hostname, String user, String password) {
		this._POP3_HOST_NAME = hostname;
		this._POP3_USER = user;
		this._POP3_PWD = password;
	}

	//Responsible for printing Data to Console
	private void printData(String data) {
		System.out.println(data);
	}

	public void processMail() {
		Session session = null;
		Store store = null;
		Folder folder = null;
		Message message = null;
		Message[] messages = null;
		Object messagecontentObject = null;
//		String sender = null;
//		String subject = null;
//		Multipart multipart = null;
//		Part part = null;
//		String contentType = null;

		try {
			printData("-------------- POP3 connecting-----------------");
			session = Session.getDefaultInstance(System.getProperties(), null);

			//printData("getting the session for accessing email.");
			store = session.getStore("pop3");

			store.connect(_POP3_HOST_NAME, _POP3_USER, _POP3_PWD);
			//printData("Connection established with POP3 server.");

			// Get a handle on the default folder
			folder = store.getDefaultFolder();

			//printData("Getting the Inbox folder.");

			// Retrieve the "Inbox"
			folder = folder.getFolder("inbox");

			//Reading the Email Index in Read / Write Mode
			folder.open(Folder.READ_WRITE);

			// Retrieve the messages
			messages = folder.getMessages();
			
			Command[] commands = new Command[messages.length];
			
			// Loop over all of the messages
			for (int messageNr = 0; messageNr < messages.length; messageNr++) {
				// Retrieve the next message to be read
				message = messages[messageNr];

				// Retrieve the message content
				messagecontentObject = message.getContent();

				// Determine and parse email type
				commands[messageNr] = (messagecontentObject instanceof Multipart) ? parseMultiPartMessage(message) : parseMessage(message);
				
				//message.setFlag(Flags.Flag.DELETED, true); //TODO:
			}

			// Close the folder
			folder.close(true);

			// Close the message store
			store.close();

			printData("-------------- POP3 disconnecting-----------------");
			
		} catch(AuthenticationFailedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch(FolderClosedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch(FolderNotFoundException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		}  catch(NoSuchProviderException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch(ReadOnlyFolderException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch(StoreClosedException e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		} catch (Exception e) {
			printData("Not able to process the mail reading.");
			e.printStackTrace();
		}
	}

	
	
	private Command parseMessage(Message message) throws MessagingException {
		printData("Found Mail Without Attachment");

		// If the "personal" information has no entry, get the address for the sender information
		printData("If the personal information has no entry, check the address for the sender information.");
		String from = ( ((InternetAddress) message.getFrom()[0]).getPersonal() != null ? 
				        ((InternetAddress) message.getFrom()[0]).getPersonal() : 
				        ((InternetAddress) message.getFrom()[0]).getAddress()            );

		Command com = new Command(from, message.getSubject());

		printData("from    = " + com.getFrom());
		printData("subject = " + com.getSubject());
		
		return com;
	}

	
	
	
	private Command parseMultiPartMessage(Message message) throws MessagingException, IOException{
		printData("Found Mail Without Attachment");

		// If the "personal" information has no entry, get the address for the sender information
		printData("If the personal information has no entry, check the address for the sender information.");
		String from = ( ((InternetAddress) message.getFrom()[0]).getPersonal() != null ? 
				        ((InternetAddress) message.getFrom()[0]).getPersonal() : 
				        ((InternetAddress) message.getFrom()[0]).getAddress()            );

		Command com = new Command(from, message.getSubject());

		printData("from    = " + com.getFrom());
		printData("subject = " + com.getSubject());
		
		// Retrieve the Multipart object from the message
		Multipart multipart = (Multipart) message.getContent();

		printData("Retrieve the Multipart object from the message");

		// Loop over the parts of the email
		for (int i = 0; i < multipart.getCount(); i++) {
			// Retrieve the next part
			Part part = multipart.getBodyPart(i);

			// Get the content type
			String contentType = part.getContentType();

			// Display the content type
			printData("Content: " + contentType);

			if (contentType.startsWith("text/plain")) {
				printData("---------reading content type text/plain  mail -------------");
			} else {
				// Retrieve the file name
				String fileName = part.getFileName();
				printData("retrive the fileName="+ fileName);
			}
		}
		
		
		return com; //FIXME : clean whole method and nice invariants + set command object variables.....
	}

}