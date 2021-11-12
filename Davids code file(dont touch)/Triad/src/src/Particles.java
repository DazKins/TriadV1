package src;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.*;

public class Particles {
	private static List<ParticleEntity> particles = new ArrayList<ParticleEntity>();
	
	public static void addParticle(boolean ac,float x,float y,float w,float h,float r,float g,float b,float a,float xv,float yv){
		particles.add(new ParticleEntity(ac,x,y,w,h,r,g,b,a,xv,yv));
	}
	
	public static void addParticle(boolean ac,float x,float y,float w,float h,float r,float g,float b,float a,float xv,float yv,int life){
		particles.add(new ParticleEntity(ac,x,y,w,h,r,g,b,a,xv,yv,life));
	}
	
	public static void update(){
		for(int i=0;i<particles.size();i++){
			if(particles.get(i).update()){
				particles.remove(i);
			}
		}
	}
	
	public static void clearAll(){
		particles.clear();
	}
	
	public static void render(){
		glDisable(GL_TEXTURE_2D);
		if(particles.size()!=0){
			for(int i=particles.size()-1;i>-1;i--){
				particles.get(i).render();
			}
		}
		glColor4f(1,1,1,1);
		glEnable(GL_TEXTURE_2D);
	}
}