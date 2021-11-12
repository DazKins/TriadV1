package src;

public class Sprite {
	int tex[] = new int[2];
	int size;
	
	public Sprite(int x,int y,int s){
		this.tex[0]=x;
		this.tex[1]=y;
		this.size=s;
	}
}
