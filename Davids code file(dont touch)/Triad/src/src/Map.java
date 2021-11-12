package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

public class Map {
	public int mapW = 43,mapH = 24;
	public List<Mob> mobs = new ArrayList<Mob>();
	public List<Block> blocksa = new ArrayList<Block>();
	public List<Block> blocksb = new ArrayList<Block>();
	public List<Npc> npc = new ArrayList<Npc>();
	public List<String> levelData = new ArrayList<String>();
	public List<MapChanger> mapChangers = new ArrayList<MapChanger>();
	public int upper,lower,left,right;
	boolean mobsRunning = true,NPCRunning = true;
	public float defaultLightLevel;
	public float[][] lights = new float[(int)Display.getWidth()/LightPlane.getLightPixelResolution()+1][(int)Display.getHeight()/LightPlane.getLightPixelResolution()];
	public enum Weather{NONE,RAIN,SNOW};
	public Weather weather;
	public boolean outdoors;
	
	public void pauseMobs(){
		mobsRunning=false;
	}
	
	public void pauseNPCs(){
		NPCRunning = false;
	}
	
	public void resumeMobs(){
		mobsRunning=true;
	}
	
	public void resumeNPCs(){
		NPCRunning = true;
	}
	
	public void addLight(float x,float y){
		for(int x1=0;x1<lights.length;x1++){
			for(int y1=0;y1<lights[x1].length;y1++){
				if(lights[x1][y1]==-1){
					if(Collision.quadCollision(x, y, 0, 0, x1*LightPlane.getLightPixelResolution(), y1*LightPlane.getLightPixelResolution(), LightPlane.getLightPixelResolution(), LightPlane.getLightPixelResolution())){
						lights[x1][y1]=0;
					}
				}
			}
		}
	}
	
	public void setupMapLighting(){
		LightPlane.setDefaultLightValue(defaultLightLevel);
		for(int x=0;x<lights.length;x++){
			for(int y=0;y<lights[x].length;y++){
				if(lights[x][y]!=-1){
					LightPlane.addLightSource(x*LightPlane.getLightPixelResolution(), y*LightPlane.getLightPixelResolution(), 0);
				}
			}
		}
		LightPlane.updateLightPlane();
		LightPlane.generateLightPlane();
	}
	
	public void prepareMap(String filename){
		for(int x=0;x<lights.length;x++){
			for(int y=0;y<lights[x].length;y++){
				lights[x][y]=-1;
			}
		}
		BufferedReader br = null;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(filename));
 
			while ((sCurrentLine = br.readLine()) != null) {
				levelData.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		for(int x=0;x<mapW;x++){
			for(int y=0;y<mapH;y++){
				int[] tmpid = {0,0};
				blocksa.add(new Block(0,0,false,tmpid));
			}
		}
		for(int x=0;x<mapW;x++){
			for(int y=0;y<mapH;y++){
				int[] tmpid = {0,0};
				blocksb.add(new Block(0,0,false,tmpid));
			}
		}
		for(int u=0;u<mobs.size();u++){
			mobs.add(new Mob());
		}
		for(int u=0;u<npc.size();u++){
			npc.add(new Npc());
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
				layer = Integer.parseInt(levelData.get(n).split(" ")[6].replace("LAYER:", ""));
				if(layer==1){
					blocksa.get(tileCountera).id = tmpid;
					blocksa.get(tileCountera).x = tmpx;
					blocksa.get(tileCountera).y = tmpy;
					blocksa.get(tileCountera).collision = tmpcollision;
					tileCountera++;
				}else if(layer==2){
					blocksb.get(tileCounterb).id = tmpid;
					blocksb.get(tileCounterb).x = tmpx;
					blocksb.get(tileCounterb).y = tmpy;
					blocksb.get(tileCounterb).collision = tmpcollision;
					tileCounterb++;
					
				}
			}
		}
	}
	
	public Map(String filename){
		prepareMap(filename);
	}
	
	public void renderLayerA(){
		for(int i=0;i<blocksa.size();i++){
			blocksa.get(i).render();
		}
	}
	
	public void renderLayerB(){
		for(int i=0;i<blocksb.size();i++){
			blocksb.get(i).render();
		}
	}
	
	public void render(){
		for(int i=0;i<mobs.size();i++){
			mobs.get(i).render();
		}
		for(int i=0;i<npc.size();i++){
			npc.get(i).render();
		}
		
	}
	
	public void update(){
		if(mobsRunning){
			for(int i=0;i<mobs.size();i++){
				if(mobs.get(i).alive)
					mobs.get(i).update();
			}
		}
		if(NPCRunning){
			for(int i=0;i<npc.size();i++){
				npc.get(i).update();
			}
		}else{
			for(int i=0;i<npc.size();i++){
				if(npc.get(i).interacting){
					npc.get(i).interact();
				}
			}
		}
		if(weather != Weather.NONE){
			if(weather == Weather.RAIN){
				Particles.addParticle(false,new Random().nextInt(1280), 0, 2.5f, 15, 0.2f, 0.2f, 1, 1, 0, 8+(new Random().nextInt(2)),160);
			}
			else if(weather == Weather.SNOW){
				Particles.addParticle(false,new Random().nextInt(1280), 0, 5, 5, 0.9f, 0.9f, 1, 1, 0, 0.7f+(new Random().nextFloat()),-1);
			}
		}
	}
}