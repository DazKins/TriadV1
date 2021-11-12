package src;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.lwjgl.input.Keyboard;

public class Player {
	public float x,y;
	public float xvel,yvel;
	public float w=60,h=60;
	public float movementSpeed = 2;
	public float health = 100;
	public boolean playerRunning = true;
	public boolean isInteracting = false;
	public Inventory inventory;
	public boolean openInventory = false;
	public int interactingNpc;
	public enum Facing{FRONT,BACK,LEFT,RIGHT};
	public Facing facing;
	public Animation right = new Animation();
	public Animation left = new Animation();
	public Animation up = new Animation();
	public Animation down = new Animation();
	public Animation currentAnimation = new Animation();
	public int animationSpeed;
	public CollisionBox colBox = new CollisionBox(x+10,y,w-20,h);
	
	public Player(float x,float y){
		this.facing=Facing.BACK;
		currentAnimation = down;
		this.x=x;
		this.y=y;
		inventory = new Inventory();
		up.frames.add(new Frame(new Sprite(0,0,20),10));
		up.frames.add(new Frame(new Sprite(1,0,20),10));
		up.frames.add(new Frame(new Sprite(2,0,20),10));
		down.frames.add(new Frame(new Sprite(3,0,20),10));
		down.frames.add(new Frame(new Sprite(4,0,20),10));
		down.frames.add(new Frame(new Sprite(5,0,20),10));
		left.frames.add(new Frame(new Sprite(6,0,20),10));
		left.frames.add(new Frame(new Sprite(7,0,20),10));
		left.frames.add(new Frame(new Sprite(8,0,20),10));
		right.frames.add(new Frame(new Sprite(9,0,20),10));
		right.frames.add(new Frame(new Sprite(10,0,20),10));
		right.frames.add(new Frame(new Sprite(11,0,20),10));
	}
	
	public void drawCollisionBox(){
		glDisable(GL_TEXTURE_2D);
		glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
		glLineWidth(1);
		glColor4f(1.0f,0.0f,0.0f,1.0f);
		glBegin(GL_QUADS);
			glVertex2f(colBox.x,colBox.y);
			glVertex2f(colBox.x+colBox.w,colBox.y);
			glVertex2f(colBox.x+colBox.w,colBox.y+colBox.h);
			glVertex2f(colBox.x,colBox.y+colBox.h);
		glEnd();
		glLineWidth(1);
		glColor4f(1.0f,1.0f,1.0f,1.0f);
		glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
		glEnable(GL_TEXTURE_2D);
	}
	
	public ItemWeapon getWeapon(){
		return (ItemWeapon) inventory.equips.get(9).i;
	}
	
	public void pausePlayer(){
		playerRunning=false;
	}
	
	public void resumePlayer(){
		playerRunning=true;
	}
	
	public void update(){
		boolean w = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
		boolean a = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
		boolean s = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		boolean d = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		if(facing == Facing.FRONT && w){
			currentAnimation = up;
		}
		else if(facing == Facing.BACK && s){
			currentAnimation = down;
		}
		else if(facing == Facing.LEFT && a){
			currentAnimation = left;
		}
		else if(facing == Facing.RIGHT && d){
			currentAnimation = right;
		}
		if(w || a || s || d){
			currentAnimation.tickAnimation();
		}
		if(playerRunning){
			if(w){
				yvel=-movementSpeed;
				facing=Facing.FRONT;
			}else if(s){
				yvel=movementSpeed;
				facing=Facing.BACK;
			}else{
				yvel=0;
			}
			if(a){
				xvel=-movementSpeed;
				facing=Facing.LEFT;
			}else if(d){
				xvel=movementSpeed;
				facing=Facing.RIGHT;
			}else{
				xvel=0;
			}
			if(colBox.x+xvel<0){
				if(Main.level.map.get(Main.level.currentMap).left >= 0){
					colBox.move(x+1250,0);
					x=1250;
					Main.level.currentMap = Main.level.map.get(Main.level.currentMap).left;
					Main.level.map.get(Main.level.currentMap).setupMapLighting();
					xvel=0;
					Particles.clearAll();
					Spell.clearAllSpells();
					ItemDrops.clearAllDroppedItems();
				}else{
					xvel=0;
				}
			}
			if(colBox.y+yvel<0){
				if(Main.level.map.get(Main.level.currentMap).upper >= 0){
					colBox.move(0, y+640);
					y=640;
					Main.level.currentMap = Main.level.map.get(Main.level.currentMap).upper;
					Main.level.map.get(Main.level.currentMap).setupMapLighting();
					yvel=0;
					Particles.clearAll();
					Spell.clearAllSpells();
					ItemDrops.clearAllDroppedItems();
				}else{
					yvel=0;
				}
			}
			if(colBox.x+colBox.w+xvel>1290){
				if(Main.level.map.get(Main.level.currentMap).right >= 0){
					colBox.move(10-x, 0);
					x=10;
					Main.level.currentMap = Main.level.map.get(Main.level.currentMap).right;
					Main.level.map.get(Main.level.currentMap).setupMapLighting();
					xvel=0;
					Particles.clearAll();
					Spell.clearAllSpells();
					ItemDrops.clearAllDroppedItems();
				}else{
					xvel=0;
				}
			}
			if(colBox.y+colBox.h+yvel>720){
				if(Main.level.map.get(Main.level.currentMap).lower >= 0){
					colBox.move(0, 10-y);
					y=10;
					Main.level.currentMap = Main.level.map.get(Main.level.currentMap).lower;
					Main.level.map.get(Main.level.currentMap).setupMapLighting();
					yvel=0;
					Particles.clearAll();
					Spell.clearAllSpells();
					ItemDrops.clearAllDroppedItems();
				}else{
					yvel=0;
				}
			}
			for(int i=0;i<Main.level.map.get(Main.level.currentMap).blocksb.size();i++){
				if(Main.level.map.get(Main.level.currentMap).blocksb.get(i).collision){
					if(Collision.quadCollision(this.colBox.x+xvel, this.colBox.y+yvel, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).blocksb.get(i).x,Main.level.map.get(Main.level.currentMap).blocksb.get(i).y,Tile.tileSize,Tile.tileSize)){
						xvel=0;
						yvel=0;
					}
				}
			}
			for(int i=0;i<Main.level.map.get(Main.level.currentMap).blocksa.size();i++){
				if(Main.level.map.get(Main.level.currentMap).blocksa.get(i).collision){
					if(Collision.quadCollision(this.colBox.x+xvel, this.colBox.y+yvel, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).blocksa.get(i).x,Main.level.map.get(Main.level.currentMap).blocksa.get(i).y,Tile.tileSize,Tile.tileSize)){
						xvel=0;
						yvel=0;
					}
				}
			}
			for(int i=0;i<Main.level.map.get(Main.level.currentMap).npc.size();i++){
				if(Main.level.map.get(Main.level.currentMap).npc.get(i).alive){
					if(Collision.quadCollision(this.colBox.x+xvel, this.colBox.y+yvel, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.x,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.y,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.w,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.h)){
						xvel=0;
						yvel=0;
					}
				}
			}
			for(int i=0;i<Main.level.map.get(Main.level.currentMap).mapChangers.size();i++){
				MapChanger m = Main.level.map.get(Main.level.currentMap).mapChangers.get(i);
				if(Collision.quadCollision(colBox.x, colBox.y, colBox.w, colBox.h, m.getX(), m.getY(), m.getW(), m.getH())){
					Main.level.currentMap = m.getMap();
					x=m.getNewX();
					y=m.getNewY();
					updateCollisionBox();
				}
			}
			colBox.move(xvel, yvel);
			x+=xvel;
			y+=yvel;
		}
		if(Input.space){
			for(int i=0;i<Main.level.map.get(Main.level.currentMap).npc.size();i++){
				if(Main.level.map.get(Main.level.currentMap).npc.get(i).alive){
					if(Collision.quadCollision(this.colBox.x+xvel, this.colBox.y+yvel, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.x-10,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.y-10,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.w+20,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.h+20)){
						Main.level.map.get(Main.level.currentMap).npc.get(i).interact();
						if(!Main.level.map.get(Main.level.currentMap).npc.get(i).questInterfaceOpen){
							Main.level.map.get(Main.level.currentMap).npc.get(i).textInterfaceOpen=true;
						}
						interactingNpc = i;
						isInteracting = true;
					}
				}
			}	
		}
		
		if(Input.e){
			if(getWeapon() != ItemStorage.nullWeapon){
				for(int i=0;i<Main.level.map.get(Main.level.currentMap).mobs.size();i++){
					Mob m = Main.level.map.get(Main.level.currentMap).mobs.get(i);
					if(Collision.quadCollision(this.colBox.x, this.colBox.y, this.colBox.w, this.colBox.h, m.colBox.x-10, m.colBox.y-10, m.colBox.w+20, m.colBox.h+20)){
						if(m.alive){
							Main.level.map.get(Main.level.currentMap).mobs.get(i).hitMob(getWeapon());
							for(int i1=0;i1<3*ConfigFile.getParticleThreshold();i1++){
								Particles.addParticle(true,Main.level.map.get(Main.level.currentMap).mobs.get(i).x+Main.level.map.get(Main.level.currentMap).mobs.get(i).w/2, Main.level.map.get(Main.level.currentMap).mobs.get(i).y+Main.level.map.get(Main.level.currentMap).mobs.get(i).h/2, 5, 5, 1.0f, 0.0f, 0.0f, 1.0f, ((float)(new Random().nextInt(100)/10)-5)/2, -((float)new Random().nextInt(100)/40),30);
							}
						}
					}
				}
			}
		}
		
		if(Input.i){
			if(openInventory){
				closeInventory();
			}
			else{
				openInventory();
			}
		}
		if(openInventory){
			isInteracting=false;
			inventory.update();
		}
	}
	
	public void updateCollisionBox(){
		colBox = new CollisionBox(x+10,y,w-20,h);
	}
	
	public void openInventory(){
		openInventory = true;
		Main.level.map.get(Main.level.currentMap).pauseMobs();
		Main.level.map.get(Main.level.currentMap).pauseNPCs();
		pausePlayer();
	}
	
	public void closeInventory(){
		openInventory = false;
		Main.level.map.get(Main.level.currentMap).resumeMobs();
		Main.level.map.get(Main.level.currentMap).resumeNPCs();
		resumePlayer();
	}
	
	public void render(){
		glBindTexture(GL_TEXTURE_2D,Tile.charactertileset);
		glPushMatrix();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/240,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/20);
			glVertex2f(0,0);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/240,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/20);
			glVertex2f(w,0);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/240,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/20);
			glVertex2f(w,h);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/240,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/20);
			glVertex2f(0,h);
		glEnd();
		glPopMatrix();
		//drawCollisionBox();
	}
}