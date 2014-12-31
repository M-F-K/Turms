import java.io.IOException;
import java.util.Properties;


public class Settings {

	public Settings(){
		
		Properties properties = new java.util.Properties();

		try {
			properties.load(new java.io.FileInputStream("settings.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BOT_SLEEP_PERIOD = Integer.parseInt(properties.getProperty("BOT_SLEEP_PERIOD", "300000"));
		
		SERIAL_PORT = properties.getProperty("SERIAL_PORT", "COM1");
		SERIAL_BAUDRATE = Integer.parseInt(properties.getProperty("SERIAL_BAUDRATE", "9600"));
		SERIAL_DATABITS = Integer.parseInt(properties.getProperty("SERIAL_DATABITS", "8"));
		SERIAL_STOPBITS = Integer.parseInt(properties.getProperty("SERIAL_STOPBITS", "1"));
		SERIAL_PARITY = Integer.parseInt(properties.getProperty("SERIAL_PARITY", "0"));

		SMTP_HOST_NAME = properties.getProperty("SMTP_HOST_NAME");
		SMTP_USER = properties.getProperty("SMTP_USER");
		SMTP_PWD = properties.getProperty("SMTP_PWD");
		
		POP3_HOST_NAME = properties.getProperty("POP3_HOST_NAME");
		POP3_USER = properties.getProperty("POP3_USER");
		POP3_PWD = properties.getProperty("POP3_PWD");
	}
	

	
	
	// BOT sleep setting
	protected static int BOT_SLEEP_PERIOD;
	
	// Serial settings - used for serial port connection
	protected static String SERIAL_PORT;
	protected static int SERIAL_BAUDRATE;
	protected static int SERIAL_DATABITS;
	protected static int SERIAL_STOPBITS;
	protected static int SERIAL_PARITY;

	
	// SMTP settings - used for sending 
	protected static String SMTP_HOST_NAME;
	protected static String SMTP_USER;
	protected static String SMTP_PWD;
	
	
	// POP3 settings - used for reading
	protected static String POP3_HOST_NAME;
	protected static String POP3_USER;
	protected static String POP3_PWD;
	
}
