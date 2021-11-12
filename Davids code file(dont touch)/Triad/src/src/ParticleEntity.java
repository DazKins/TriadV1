package src;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class ParticleEntity {
	float x,y;
	float w,h;
	float r,g,b,a;
	float xvel,yvel;
	private float life = 30;
	private int lifeTimer;
	private boolean accel;
	
	public ParticleEntity(boolean ac,float x,float y,float w,float h,float r,float g,float b,float a,float xv,float yv){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
		this.xvel=xv;
		this.yvel=yv;
		this.accel=ac;
	}
	
	public ParticleEntity(boolean ac,float x,float y,float w,float h,float r,float g,float b,float a,float xv,float yv,int f){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
		this.xvel=xv;
		this.yvel=yv;
		this.life=f;
		this.accel=ac;
	}
	
	public boolean update(){
		y+=yvel;
		x+=xvel;
		if(accel){
			yvel+=0.1;
			if(xvel>0) xvel-=0.1;
			else xvel+=0.1;
		}
		lifeTimer++;
		if(life==lifeTimer) return true;
		if(life==-1){
			if(y>Display.getHeight()){
				return true;
			}
		}
		return false;
	}
	
	public void render(){
		glColor4f(r,g,b,a);
		glBegin(GL_QUADS);
			glVertex2f(x,y);
			glVertex2f(x+w,y);
			glVertex2f(x+w,y+h);
			glVertex2f(x,y+h);
		glEnd();
	}
}
