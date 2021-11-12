package src;

import java.io.*;
import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;

public class Level {
	public int mapW = 43,mapH = 24;
	
	public List<String> levelData = new ArrayList<String>();
	
	public Block blocksa[] = new Block[mapW*mapH];
	public Block blocksb[] = new Block[mapW*mapH];
	
	public Level(){
		BufferedReader br = null;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("res/World.dat"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				levelData.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		int i=0;
		for(int x=0;x<mapW;x++){
			for(int y=0;y<mapH;y++){
				int[] tmpid = {-1,-1};
				blocksa[i] = new Block(x*Tile.tileSize,y*Tile.tileSize,false,tmpid);
				i++;
			}
		}
		i=0;
		for(int x=0;x<mapW;x++){
			for(int y=0;y<mapH;y++){
				int[] tmpid = {-1,-1};
				blocksb[i] = new Block(x*Tile.tileSize,y*Tile.tileSize,false,tmpid);
				i++;
			}
		}
		int tileCountera=0,tileCounterb=0;
		for(int n=0;n<levelData.size();n++){
			if(levelData.get(n).startsWith("TILE_BEGIN")){
				float tmpx,tmpy;
				int[] tmpid = {0,0};
				boolean tmpcollision;
				int layer;
				tmpx = Float.parseFloat(levelData.get(n).split(" ")[1].replace("X:", ""));
				tmpy = Float.parseFloat(levelData.get(n).split(" ")[2].replace("Y:", ""));
				tmpid[0] = Integer.parseInt(levelData.get(n).split(" ")[3].replace("IDX:", ""));
				tmpid[1] = Integer.parseInt(levelData.get(n).split(" ")[4].replace("IDY:", ""));
				tmpcollision = Boolean.parseBoolean(levelData.get(n).split(" ")[5].replace("COLLISION:", ""));
				layer=Integer.parseInt(levelData.get(n).split(" ")[6].replace("LAYER:", ""));
				try{
					if(layer==1){
						blocksa[tileCountera] = new Block(tmpx,tmpy,tmpcollision,tmpid);
						tileCountera++;
						System.out.println(layer);
					}else if(layer==2){
						blocksb[tileCounterb] = new Block(tmpx,tmpy,tmpcollision,tmpid);
						tileCounterb++;
					}
				}catch(Exception e){
					
				}
			}
			/**
			else if(levelData.get(n).startsWith("MOB_BEGIN")){
				if(Integer.parseInt(levelData.get(n).split(" ")[1].replace("TYPE:", "")) == MobSpider.id){
					float mobtmpx,mobtmpy;
					mobtmpx = Float.parseFloat(levelData.get(n).split(" ")[2].replace("X:", ""));
					mobtmpy = Float.parseFloat(levelData.get(n).split(" ")[3].replace("Y:", ""));
					mobs[mobCounter] = new MobSpider(mobtmpx,mobtmpy);
				}
				mobCounter++;
			}
			**/
		}
	}
	
	public void render(){
		for(int i=0;i<blocksb.length;i++){
			blocksb[i].render();
			if(blocksb[i].collision){
				glColor4f(1.0f,0.0f,0.0f,0.5f);
				glBegin(GL_QUADS);
					glVertex2f(blocksb[i].x,blocksb[i].y);
					glVertex2f(blocksb[i].x+Tile.tileSize,blocksb[i].y);
					glVertex2f(blocksb[i].x+Tile.tileSize,blocksb[i].y+Tile.tileSize);
					glVertex2f(blocksb[i].x,blocksb[i].y+Tile.tileSize);
				glEnd();
			}
		}
		for(int i=0;i<blocksa.length;i++){
			blocksa[i].render();
			if(blocksa[i].collision){
				glColor4f(1.0f,0.0f,0.0f,0.5f);
				glBegin(GL_QUADS);
					glVertex2f(blocksa[i].x,blocksa[i].y);
					glVertex2f(blocksa[i].x+Tile.tileSize,blocksa[i].y);
					glVertex2f(blocksa[i].x+Tile.tileSize,blocksa[i].y+Tile.tileSize);
					glVertex2f(blocksa[i].x,blocksa[i].y+Tile.tileSize);
				glEnd();
			}
		}
	}
	
	public void save(){
		BufferedWriter out = null; 
		try{
			 FileWriter fstream = new FileWriter("res/World.dat");
			 out = new BufferedWriter(fstream);
			 
			int i=0;
			for(int x=0;x<43;x++){
				for(int y=0;y<24;y++){
					out.write("TILE_BEGIN " + "X:" + blocksa[i].x + " Y:" + blocksa[i].y + " IDX:" + blocksa[i].id[0] + " IDY:" + blocksa[i].id[1] + " COLLISION:" + blocksa[i].collision + " LAYER:1");
					out.newLine();
					i++;
				}
			}
			i=0;
			for(int x=0;x<43;x++){
				for(int y=0;y<24;y++){
					out.write("TILE_BEGIN " + "X:" + blocksb[i].x + " Y:" + blocksb[i].y + " IDX:" + blocksb[i].id[0] + " IDY:" + blocksb[i].id[1] + " COLLISION:" + blocksb[i].collision + " LAYER:2");
					out.newLine();
					i++;
				}
			}
			out.close();
		 }catch(Exception e){
			 
		 }
	}
	
	public void update(){
		while(Keyboard.next()){
			if (Keyboard.getEventKey() == Keyboard.KEY_S){
				save();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_C)){
				for(int i=0;i<blocksa.length;i++){
					if(Collision.quadCollision(Mouse.getX()*1.25f, -(Mouse.getY()-720)*1.25f, 0, 0, blocksa[i].x, blocksa[i].y, Tile.tileSize, Tile.tileSize)){
						blocksa[i].collision = !blocksa[i].collision;
					}
				}
			}
		}
		if(Mouse.isButtonDown(0)){
			for(int i=0;i<blocksa.length;i++){
				if(Collision.quadCollision(Mouse.getX()*1.25f, -(Mouse.getY()-720)*1.25f, 0, 0, blocksa[i].x, blocksa[i].y, Tile.tileSize, Tile.tileSize)){
					if(BlockMenu.layer=="A"){
						blocksa[i].id = main.blockMenu.selectedTile;
					}
					if(BlockMenu.layer=="B"){
						blocksb[i].id = main.blockMenu.selectedTile;
					}
				}
			}
		}
	}
}
