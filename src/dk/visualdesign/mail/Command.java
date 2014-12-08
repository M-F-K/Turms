package dk.visualdesign.mail;

 public class Command {
	
	private String _subject;
	private String _from;
	
	public Command (String from, String subject){
		this._subject = subject;
		this._from = from;
	}
	
	public String getSubject() {
		return _subject;
	}
//	public void setSubject(String subject) {
//		this._subject = subject;
//	}
	public String getFrom() {
		return _from;
	}
//	public void setFrom(String from) {
//		this._from = from;
//	}

}
