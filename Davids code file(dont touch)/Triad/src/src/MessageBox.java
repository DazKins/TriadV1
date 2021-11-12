package src;

public class MessageBox{
	String message;
	boolean hasNext;
	
	public MessageBox(String m,boolean h){
		this.message = m;
		hasNext = h;
	}
	
	public void render(){
		Fonts.drawString(0, 500, message);
	}
}
