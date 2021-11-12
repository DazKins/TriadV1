package src;

import java.util.*;

public class MessageBoxSet {
	List<MessageBox> messages = new ArrayList<MessageBox>();
	
	public MessageBoxSet(List<MessageBox> m){
		this.messages=m;
	}
	
	public int getMax(){
		return messages.size();
	}
	
	public void render(int i){
		messages.get(i).render();
	}
}
