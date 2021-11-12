package src;


import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Main{
	
	public static String fpsRate = "";
	
	public static boolean running = false;
	
	public static Level level;
	public static Player player;
	
	public static long lastFrame,lastFPS,fps;
	
	public int width=1280,height=720;
	public DisplayMode displayMode = new DisplayMode(width,height);
	
	public Main(){
		try{
			Display.setDisplayMode(displayMode);
			Display.setTitle("Triad");
			Display.create();
		}catch (LWJGLException e){
			System.out.println("Failed to initialise window");
		}
		initOpengl();
		initProg();
		running = true;
		
		while(running){
			render();
			update();
			if(Display.isCloseRequested()){
				running = false;
			}
			Display.sync(60);
			Display.update();
		}
		Display.destroy();
		System.exit(0);
	}
	
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
	    long time = getTime();
		int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    	
	    return delta;
	}
	
	public void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	    	fpsRate = "FPS: " + fps;
	        fps = 0;
	        lastFPS += 1000;
	    }
	    fps++;
	}
	
	public void initProg(){
		new ConfigFile();
		new TimeCycle();
		new ItemDrops();
		new Tile();
		new LightPlane();
		new SpellStorage();
		new Fonts();
		lastFPS = getTime();
		level = new Level();
		player = new Player(5,5);
		player.inventory.addItem(ItemStorage.testWeapon);
	}
	
	public void initOpengl(){
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, 1290,720, 1.0, -1.0, 1.0);
	    glEnable(GL_BLEND);
	    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	    glEnable(GL_TEXTURE_2D);
	    glDepthFunc(GL_NEVER);
	}
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		level.map.get(level.currentMap).renderLayerB();
		level.render();
		player.render();
		level.map.get(level.currentMap).renderLayerA();
		Particles.render();
		ItemDrops.render();
		LightPlane.renderLightPlane();
		if(player.openInventory){
			player.inventory.render();
		}
		Fonts.drawString(1160, 0, fpsRate);
	}
	
	public void update(){
		TimeCycle.updateTime();
		player.update();
		level.update();
		ItemDrops.update();
		Particles.update();
		updateFPS();
		new Input();
	}
	
	public static void main(String args[]){
		new Main();
	}
}