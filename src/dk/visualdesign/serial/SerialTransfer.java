package dk.visualdesign.serial;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialTransfer {

	private final String _SERIAL_PORT;
	private final int _SERIAL_BAUDRATE;
	private final int _SERIAL_DATABITS;
	private final int _SERIAL_STOPBITS;
	private final int _SERIAL_PARITY;

	public SerialTransfer(String port, int baudrate, int databits, int stopbits, int parity) {
		this._SERIAL_PORT = port;
		this._SERIAL_BAUDRATE = baudrate;
		this._SERIAL_DATABITS = databits;
		this._SERIAL_STOPBITS = stopbits;
		this._SERIAL_PARITY = parity;
	}
	
	public void write() {
		
		String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            System.out.println(portNames[i]);
        }

		SerialPort serialPort = new SerialPort(_SERIAL_PORT);
		try {
			serialPort.openPort();
			serialPort.setParams(_SERIAL_BAUDRATE,
								 _SERIAL_DATABITS,
								 _SERIAL_STOPBITS,
								 _SERIAL_PARITY);
			serialPort.writeBytes("This is a test string".getBytes());
			serialPort.closePort();
		} catch (SerialPortException e) {
			System.out.println(e);
		}

	}

}