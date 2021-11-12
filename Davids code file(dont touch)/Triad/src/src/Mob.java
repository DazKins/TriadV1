package src;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.Display;
import src.Player.Facing;

public class Mob {
	public String name;
	public float x,y;
	public float w,h;
	public float xvel=0,yvel=0;
	public float movementSpeed;
	public boolean alive;
	public int damage;
	public int tileset;
	public int health;
	public Facing facing;
	public Animation upAnimation = new Animation();
	public Animation downAnimation = new Animation();
	public Animation leftAnimation = new Animation();
	public Animation rightAnimation = new Animation();
	public Animation currentAnimation = new Animation();
	public double randomMovementTimer;
	public CollisionBox colBox;
	public boolean agroOnPlayer;
	public List<Item> drops = new ArrayList<Item>();
	
	public Mob(float x,float y,float w,float h,float mov,int tileset,String name,int health,CollisionBox c){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.movementSpeed = mov;
		this.alive = true;
		this.tileset=tileset;
		this.name=name;
		this.health=health;
		this.randomMovementTimer=0;
		this.colBox=c;
		facing = Facing.LEFT;
		currentAnimation.addAnimationFrame(-1, -1, 0, 0);
	}
	
	public Mob(){
		this.x=0;
		this.y=0;
		this.movementSpeed = 0;
		this.alive=false;
	}

	public void hitPlayer(){
		Main.player.health-=damage;
	}
	
	public void detectAgro(float r){
		if(Math.abs(Main.player.colBox.y-y) <= r){
			agroOnPlayer=true;
		}else if(Math.abs(Main.player.colBox.x-x) <= r){
			agroOnPlayer=true;
		}
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
	
	public void randomMovement(){
		if(randomMovementTimer >= new Random().nextInt(800)+100){
			int movementRandom = new Random().nextInt(100);
			if(movementRandom < 50){
				if(movementRandom < 25)
					xvel = -movementSpeed;
				else
					xvel = movementSpeed;
				if(x+xvel < 0){
					xvel = movementSpeed;
				}else if(x+w+xvel > Display.getWidth()){
					xvel = -movementSpeed;
				}
			}else{
				if(movementRandom < 75)
					yvel = -movementSpeed;
				else
					yvel = movementSpeed;
				if(y+yvel < 0){
					yvel = movementSpeed;
				}else if(y+h+yvel > Display.getHeight()){
					yvel = -movementSpeed;
				}
			}
			randomMovementTimer=0;
		}else{
			randomMovementTimer+=new Random().nextDouble();
			if(randomMovementTimer >= new Random().nextInt(200)+20){
				xvel=0;
				yvel=0;
			}
		}
	}
	
	public void moveToPlayer(){
		double dX = (Main.player.colBox.x+(Main.player.colBox.w/2f))-(this.colBox.x+(this.colBox.w/2f));
		double dY = (Main.player.colBox.y+(Main.player.colBox.h/2f))-(this.colBox.y+(this.colBox.h/2f));
		double hyp = Math.sqrt((dX*dX)+(dY*dY));
		dX /= hyp;
		dY /= hyp;
		xvel=(float) dX;
		yvel=(float) dY;
		if(Collision.quadCollision(Main.player.colBox.x, Main.player.colBox.y, Main.player.colBox.w, Main.player.colBox.h, colBox.x, colBox.y, colBox.w, colBox.h)){
			xvel = 0;
			yvel = 0;
		}
	}
	
	public void render(){
		if(alive){
			glPushMatrix();
			glTranslatef(x,y,0);
			glBindTexture(GL_TEXTURE_2D,tileset);
			glBegin(GL_QUADS);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20))/800);
				glVertex2f(0,0);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20))/800);
				glVertex2f(w,0);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
				glVertex2f(w,h);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
				glVertex2f(0,h);
			glEnd();
			glPopMatrix();
			//drawCollisionBox();
		}
	}
	
	public void hitMob(ItemWeapon i){
		if(alive){
			health-=i.damage;
			if(Main.player.facing==Facing.FRONT){
				y-=15;
				this.colBox.y-=15;
			}else if(Main.player.facing==Facing.BACK){
				y+=15;
				this.colBox.y+=15;
			}else if(Main.player.facing==Facing.LEFT){
				x-=15;
				this.colBox.x-=15;
			}else if(Main.player.facing==Facing.RIGHT){
				x+=15;
				this.colBox.x+=15;
			}
		}
	}
	
	public void hitMob(int d){
		if(alive){
			health-=d;
			if(Main.player.facing==Facing.FRONT){
				y-=15;
				this.colBox.y-=15;
			}else if(Main.player.facing==Facing.BACK){
				y+=15;
				this.colBox.y+=15;
			}else if(Main.player.facing==Facing.LEFT){
				x-=15;
				this.colBox.x-=15;
			}else if(Main.player.facing==Facing.RIGHT){
				x+=15;
				this.colBox.x+=15;
			}
		}
	}
	
	public void facePlayer(){
		if(Math.abs(colBox.x-Main.player.colBox.x)>Math.abs(colBox.y-Main.player.colBox.y)){
			if(xvel>0) facing = Facing.RIGHT;
			if(xvel<0) facing = Facing.LEFT;
		}
		if(Math.abs(colBox.y-Main.player.colBox.y)>Math.abs(colBox.x-Main.player.colBox.x)){
			if(yvel>0) facing = Facing.FRONT;
			if(yvel<0) facing = Facing.BACK;
		}
	}
	
	public void update(){
		if(xvel!=0 || yvel!=0)
			currentAnimation.tickAnimation();
		if(facing == Facing.RIGHT){
			currentAnimation = rightAnimation;
		}else if(facing == Facing.LEFT){
			currentAnimation = leftAnimation;
		}else if(facing == Facing.FRONT){
			currentAnimation = downAnimation;
		}else if(facing == Facing.BACK){
			currentAnimation = upAnimation;
		}
		if(yvel>0) facing = Facing.FRONT;
		else if(yvel<0) facing = Facing.BACK;
		else if(xvel>0) facing = Facing.RIGHT;
		else if(xvel<0) facing = Facing.LEFT;
		for(int i=0;i<Main.level.map.get(Main.level.currentMap).blocksb.size();i++){
			if(Main.level.map.get(Main.level.currentMap).blocksb.get(i).collision){
				if(Collision.quadCollision(colBox.x+xvel, colBox.y+yvel, colBox.w, colBox.h, Main.level.map.get(Main.level.currentMap).blocksb.get(i).x, Main.level.map.get(Main.level.currentMap).blocksb.get(i).y,Tile.tileSize, Tile.tileSize)){
					xvel=0;
				}
			}
		}
		for(int i=0;i<Main.level.map.get(Main.level.currentMap).npc.size();i++){
			if(Main.level.map.get(Main.level.currentMap).npc.get(i).alive){
				if(Collision.quadCollision(this.colBox.x+xvel, this.colBox.y, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.x,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.y,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.w,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.h)){
					xvel=0;
				}
				if(Collision.quadCollision(this.colBox.x, this.colBox.y+yvel, this.colBox.w, this.colBox.h, Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.x,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.y,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.w,Main.level.map.get(Main.level.currentMap).npc.get(i).colBox.h)){
					yvel=0;
				}
			}
		}
		if(colBox.x+colBox.w+xvel > Display.getWidth() || colBox.x+xvel < 0)
			xvel=0;
		if(colBox.y+colBox.h+yvel > Display.getHeight() || colBox.y+yvel < 0)
			yvel=0;
		colBox.move(xvel, yvel);
		x+=xvel;
		y+=yvel;
	}
}