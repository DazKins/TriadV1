package src;

public class Collision {
	public static boolean quadCollision(float x1,float y1,float w1,float h1,float x2,float y2,float w2,float h2){
		if(x1>x2+w2) return false;
		if(x1+w1<x2) return false;
		if(y1>y2+h2) return false;
		if(y1+h1<y2) return false;
		return true;
	}
}
