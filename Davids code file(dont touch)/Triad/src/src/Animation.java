package src;

import java.util.*;

public class Animation {
	List<Frame> frames = new ArrayList<Frame>();
	int currentFrame = 0;
	
	public boolean tickAnimation(){
		boolean frameticked = frames.get(currentFrame).tickFrame();
		if(frameticked && currentFrame < frames.size()){
			currentFrame++;
		}
		if(currentFrame == frames.size()){
			currentFrame=0;
			return true;
		}
		return false;
	}
	
	public void addAnimationFrame(int x,int y,int s,int time){
		frames.add(new Frame(new Sprite(x,y,s),time));
	}
}