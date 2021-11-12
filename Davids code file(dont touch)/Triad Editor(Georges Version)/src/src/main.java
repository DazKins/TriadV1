package src;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class main {
	
	public Level level;
	public static BlockMenu blockMenu;
	
	public main(){
		try {
			Display.setDisplayMode(new DisplayMode(1280,720));
			Display.setResizable(false);
			Display.create();
		}
		 catch (LWJGLException e) {
			e.printStackTrace();
		}
		initProg();
		initOpengl();
		while(!Display.isCloseRequested()){
			update();
			render();
			Display.update();
			Display.sync(60);
		}
	}
	
	public static void main(String args[]){
		new main();
		/**
		BufferedWriter out = null; 
		try{
			  // Create file 
			 FileWriter fstream = new FileWriter("World.dat");
			 out = new BufferedWriter(fstream);

			for(int x=0;x<43;x++){
				for(int y=0;y<24;y++){
					out.write("TILE_BEGIN " + "X:" + x*30 + " Y:" + y*30 + " IDX:" + "0" + " IDY:" + "0" + " COLLISION:" + "false" + " LAYER:2");
					out.newLine();
				}
			}
			out.close();
		 }catch(Exception e){
			 
		 }
		 **/
	}
	
	public void initProg(){
		new Tile();
		level = new Level();
		blockMenu = new BlockMenu();
	}
	
	public void initOpengl(){
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, 1600,900, 1.0, -1.0, 1.0);
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	    glEnable(GL_TEXTURE_2D);
	}
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		level.render();
		blockMenu.render();
	}
	
	public void update(){
		level.update();
	}
}
