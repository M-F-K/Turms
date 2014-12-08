import dk.visualdesign.mail.Mailreader;
import dk.visualdesign.mail.SendMailUsingAuthentication;
import dk.visualdesign.serial.SerialTransfer;

public class Turms {
	
	private static Mailreader _mailReader;
	private static SendMailUsingAuthentication _mailWriter;
	private static SerialTransfer _serialTransfer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Setting up various connections
		init();

		while (true) {
			// Calling processMail Function to read from POP3 Account
			_mailReader.processMail();

			// Calling postMail Function to send via SMTP Account
			//_mailWriter.postMail(recipients, subject, message, from);
			
			// Test serialtransfer
			// _serialTransfer.write();
			
			// TODO: maybe move the various connection to some sort of controller object, and create some sort of simple mapping scheme setup between input => output
			
			try {
				Thread.sleep(Settings.BOT_SLEEP_PERIOD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void init(){
		_mailReader = new Mailreader(Settings.POP3_HOST_NAME, Settings.POP3_USER, Settings.POP3_PWD);
		
		_mailWriter = new SendMailUsingAuthentication(Settings.SMTP_HOST_NAME, Settings.SMTP_USER, Settings.SMTP_PWD);
		
		_serialTransfer = new SerialTransfer(Settings.SERIAL_PORT, Settings.SERIAL_BAUDRATE, Settings.SERIAL_DATABITS, Settings.SERIAL_STOPBITS, Settings.SERIAL_PARITY);		
	}

}
