package src;

import static org.lwjgl.opengl.GL11.*;

public class Block {
	public float x,y;
	public int[] id;
	public boolean collision;
	
	public Block(float x,float y,boolean col,int[] id){
		this.x=x;
		this.y=y;
		this.id=id;
		this.collision=col;
	}
	
	public void render(){
		glEnable(GL_TEXTURE_2D);
		if(id[0]!=-1 && id[1]!=-1){
			glColor3f(1.0f,1.0f,1.0f);
			glBindTexture(GL_TEXTURE_2D,Tile.tileset_1);
			glBegin(GL_QUADS);
				glTexCoord2d((float)((id[0]*20))/800,(float)((id[1]*20))/800);
				glVertex2f(x,y);
				glTexCoord2d((float)((id[0]*20)+20)/800,(float)((id[1]*20))/800);
				glVertex2f(x+Tile.tileSize,y);
				glTexCoord2d((float)((id[0]*20)+20)/800,(float)((id[1]*20)+20)/800);
				glVertex2f(x+Tile.tileSize,y+Tile.tileSize);
				glTexCoord2d((float)((id[0]*20))/800,(float)((id[1]*20)+20)/800);
				glVertex2f(x,y+Tile.tileSize);
			glEnd();
		}
		glDisable(GL_TEXTURE_2D);
	}
}