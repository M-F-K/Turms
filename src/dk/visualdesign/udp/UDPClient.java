package dk.visualdesign.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	DatagramSocket _socket = null;
	DatagramPacket _inPacket = null;
	DatagramPacket _outPacket = null;
	byte[] _inBuf;
	byte[] _outBuf;
	final int _port = 7777;
	String _msg = null;

	public UDPClient() {
		//TODO : instantiate various variables here + cleanup the rest of the code
	}

	public void send() {
		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");
			_socket = new DatagramSocket();

			// Convert string to byte and send to server
			_msg = "Hello";
			_outBuf = _msg.getBytes();
			_outPacket = new DatagramPacket(_outBuf, 0, _outBuf.length, address, _port);
			_socket.send(_outPacket);
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	public void recieve() {
		try {
			// Receive reversed message from server
			_inBuf = new byte[256];
			_inPacket = new DatagramPacket(_inBuf, _inBuf.length);
			_socket.receive(_inPacket);

			String data = new String(_inPacket.getData(), 0, _inPacket.getLength());

			System.out.println("Server : " + data);

		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

}
