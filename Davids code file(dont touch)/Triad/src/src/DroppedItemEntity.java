package src;

import static org.lwjgl.opengl.GL11.*;

public class DroppedItemEntity {
	float x,y;
	float w,h;
	Item item;
	
	public DroppedItemEntity(float x,float y,float w,float h,Item i){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.item=i;
	}
	
	public boolean update(){
		if(Collision.quadCollision(x, y, w, h, Main.player.colBox.x, Main.player.colBox.y, Main.player.colBox.w, Main.player.colBox.h)){
			return true;
		}
		return false;
	}
	
	public void render(){
		glBindTexture(GL_TEXTURE_2D,item.tileset);
		glBegin(GL_QUADS);
			glTexCoord2d((float)((item.spr.tex[0]*20))/800,(float)((item.spr.tex[1]*20))/800);
			glVertex2f(x,y);
			glTexCoord2d((float)((item.spr.tex[0]*20)+20)/800,(float)((item.spr.tex[1]*20))/800);
			glVertex2f(x+w,y);
			glTexCoord2d((float)((item.spr.tex[0]*20)+20)/800,(float)((item.spr.tex[1]*20)+20)/800);
			glVertex2f(x+w,y+h);
			glTexCoord2d((float)((item.spr.tex[0]*20))/800,(float)((item.spr.tex[1]*20)+20)/800);
			glVertex2f(x,y+h);
		glEnd();
	}
}
