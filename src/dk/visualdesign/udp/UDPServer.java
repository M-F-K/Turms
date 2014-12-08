package dk.visualdesign.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
	 	DatagramSocket _socket;
	    DatagramPacket _inPacket = null; //recieving packet
	    DatagramPacket _outPacket = null; //sending packet
	    private int _inBuffersize;
	    private int _outBuffersize;
	    private byte[] _inBuffer;
	    private byte[] _outBuffer;
	    String _msg;
	    private final int _UDPPort;
	    
	    public UDPServer(int UDPPort, int inBuffersize, int outBuffersize){
	    	this._UDPPort = UDPPort;
	    	this._inBuffersize = inBuffersize;
	    	this._outBuffersize = outBuffersize;
	    	try {
				this._socket = new DatagramSocket(_UDPPort);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	public void recieve() {
		try {
			while (true) {
				System.out.println("Waiting for client...");

				// Receiving datagram from client
				_inBuffer = new byte[_inBuffersize];
				_inPacket = new DatagramPacket(_inBuffer, _inBuffer.length);
				_socket.receive(_inPacket);

				// Extract data, ip and port
				int source_port = _inPacket.getPort();
				InetAddress source_address = _inPacket.getAddress();
				_msg = new String(_inPacket.getData(), 0, _inPacket.getLength());
				System.out.println("Client " + source_address + ":" + _msg);

				// Send back to client as an echo // TODO: move this part...
				_msg = "ok";
				_outBuffer = _msg.getBytes();
				_outPacket = new DatagramPacket(_outBuffer, 0, _outBuffer.length, source_address, source_port);
				_socket.send(_outPacket);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	public void send() {
		//TODO
	}
	
	
	  }