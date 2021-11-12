package src;

import static org.lwjgl.opengl.GL11.*;

public class LightPixel {
	private float r,g,b;
	private float lightValue;
	private float x,y,w,h;
	
	public LightPixel(float x,float y,float w,float h,float r,float g,float b,float a){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.r=r;
		this.g=g;
		this.b=b;
		this.lightValue=a;
	}
	
	public void setLightValue(float a){
		lightValue=a;
	}
	
	public void addLightValue(float a){
		lightValue-=a;
	}
	
	public float getLightValue(){
		return lightValue;
	}
	
	public void setRed(float r){
		this.r=r;
	}
	
	public void setGreen(float g){
		this.g=g;
	}
	
	public void setBlue(float b){
		this.b=b;
	}
	
	public void render(){
		if(lightValue>0.0f){
			glDisable(GL_TEXTURE_2D);
			glColor4f(r,g,b,lightValue);
			glBegin(GL_QUADS);
				glVertex2f(x,y);
				glVertex2f(x+w,y);
				glVertex2f(x+w,y+h);
				glVertex2f(x,y+h);
			glEnd();
			glColor4f(1,1,1,1);
			glEnable(GL_TEXTURE_2D);
		}
	}
}
