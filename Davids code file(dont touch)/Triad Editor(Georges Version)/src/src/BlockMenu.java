package src;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class BlockMenu {
	
	public List<int[]> levelData = new ArrayList<int[]>();
	int sbarx=1290,sbary=0;
	public float dy;
	public Block[] b = new Block[9*177];
	public int[] selectedTile = {-1,-1};
	public static String layer;
	
	public BlockMenu(){
		layer="B";
		Display.setTitle("TRIAD | " + "Layer: " + layer);
		for(int x=0;x<40;x++){
			for(int y=0;y<40;y++){
				int[] tmp = {y,x};
				levelData.add(tmp);
			}
		}
	}
	
	public void render(){
		int i=0;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			layer="A";
			Display.setTitle("TRIAD | " + "Layer: " + layer);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_B)){
			layer="B";
			Display.setTitle("TRIAD | " + "Layer: " + layer);
		}
		for(int x=0;x<9;x++){
			for(int y=0;y<177;y++){
				b[i] = new Block((float)x*30+1330,(float)y*30,false,levelData.get(i)); 
				i++;
			}
		}
		dy=Mouse.getDY();
		if(Mouse.isButtonDown(0)){
			System.out.println(Mouse.getX()*1.25f + " " + -(Mouse.getY()-720)*1.25f);
			if(Collision.quadCollision(Mouse.getX()*1.25f, -(Mouse.getY()-720)*1.25f, 0, 0, sbarx, sbary, 40, 100)){
				if(sbary >= 0 && sbary+100 <= 900){
					sbary-=dy;
				}
			}
			for(int u=0;u<b.length;u++){
				if(Collision.quadCollision(Mouse.getX()*1.25f, -(Mouse.getY()-720)*1.25f, 0, 0, b[u].x, b[u].y-sbary*6, 30, 30)){
					selectedTile = b[u].id;
				}
			}
		}
		glPushMatrix();
		glTranslatef(0,-sbary*6,0);
		for(int n=0;n<b.length;n++){
			b[n].render();
		}
		glPopMatrix();
		if(sbary < 0)
			sbary++;
		if(sbary+100 > 900)
			sbary--;
		glDisable(GL_TEXTURE_2D);
		glColor3f(1.0f,1.0f,1.0f);
		glBegin(GL_QUADS);
			glVertex2f(sbarx,sbary);
			glVertex2f(sbarx+40,sbary);
			glVertex2f(sbarx+40,sbary+100);
			glVertex2f(sbarx,sbary+100);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
}
