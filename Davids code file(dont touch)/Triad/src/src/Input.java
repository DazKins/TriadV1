package src;

import org.lwjgl.input.*;

public class Input{
	public static boolean enter,space,i,e,f;
	public static boolean leftMouse;
	
	public Input(){
		enter=false;
		space=false;
		i=false;
		e=false;
		leftMouse=false;
		f=false;
		while(Keyboard.next()){
			if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				enter = true;
				break;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				space = true;
				break;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_I)){
				i = true;
				break;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_E)){
				e=true;
				break;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_F)){
				f=true;
				break;
			}
		}
		while(Mouse.next()){
			if(Mouse.isButtonDown(0)){
				leftMouse=true;
				break;
			}
		}
	}
}