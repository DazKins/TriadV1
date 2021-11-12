package src;

public class MapChanger {
	private float x,y;
	private float w,h;
	private int map;
	private float newX,newY;
	
	public MapChanger(float x,float y,float w,float h,int m){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.map=m;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getW(){
		return w;
	}
	
	public float getH(){
		return h;
	}
	
	public int getMap(){
		return map;
	}
	
	public float getNewX(){
		return newX;
	}
	
	public float getNewY(){
		return newY;
	}
}
