package src;

import static org.lwjgl.opengl.GL11.*;
import java.util.*;
import src.Player.*;

public class Npc {
	public float x,y;
	public float w,h;
	public float xvel,yvel;
	List<MessageBoxSet> speech = new ArrayList<MessageBoxSet>();
	public int tex[] = new int[2];
	boolean alive,interacting;
	public int selectedMessageBox;
	public int afterQuestText;
	public int tileset;
	public Quest quest;
	boolean questGiver,questInterfaceOpen,textInterfaceOpen;
	public int selectedMessageBoxSet;
	public int questRewardMessageBoxSet;
	public CollisionBox colBox;
	public Facing facing;
	public float rotation;
	public Animation rightanim = new Animation();
	public Animation leftanim = new Animation();
	public Animation downanim = new Animation();
	public Animation upanim = new Animation();
	public Animation currentAnimation = new Animation();
	
	public Npc(float x,float y,float w,float h,int tileset,int[] tex,boolean questGiver,Quest q,CollisionBox c){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.tileset=tileset;
		this.tex=tex;
		this.alive=true;
		this.questGiver=questGiver;
		this.quest=q;
		this.colBox=c;
		facing = Facing.FRONT;
		currentAnimation.addAnimationFrame(-1, -1, 0, 0);
	}
	public Npc(){
		this.x=0;
		this.y=0;
		this.tex[0] = -1;
		this.tex[1] = -1;
		this.alive=false;
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
	
	public void facePlayer(){
		if(Math.abs(x-Main.player.colBox.x)>Math.abs(y-Main.player.colBox.y)){
			if(Main.player.colBox.x > colBox.x+w) facing = Facing.RIGHT;
			if(Main.player.colBox.x+Main.player.colBox.w < colBox.x) facing = Facing.LEFT;
		}
		if(Math.abs(y-Main.player.colBox.y)>Math.abs(x-Main.player.colBox.x)){
			if(Main.player.colBox.y > colBox.y+h) facing = Facing.FRONT;
			if(Main.player.colBox.y+Main.player.colBox.h < colBox.y) facing = Facing.BACK;
		}
		if(facing == Facing.BACK) currentAnimation = upanim; 
		else if(facing == Facing.FRONT) currentAnimation = downanim;
		else if(facing == Facing.LEFT) currentAnimation = leftanim;
		else if(facing == Facing.RIGHT) currentAnimation = rightanim;
	}
	
	public void interact(){
		if(Main.player.isInteracting){
			facePlayer();
			Main.level.map.get(Main.level.currentMap).pauseMobs();
			Main.level.map.get(Main.level.currentMap).pauseNPCs();
			Main.player.pausePlayer();
			this.interacting = true;
			if(textInterfaceOpen){
				speech.get(selectedMessageBoxSet).messages.get(selectedMessageBox).render();
				questInterfaceOpen=false;
			}
			if(questInterfaceOpen){
				quest.drawQuestInterface();
				textInterfaceOpen=false;
			}
			if(Input.enter){
				if(selectedMessageBox<speech.get(selectedMessageBoxSet).messages.size()-1){
					selectedMessageBox++;
					textInterfaceOpen = true;
				}else if(selectedMessageBox==speech.get(selectedMessageBoxSet).messages.size()-1 && questGiver && !quest.isActive){
					questInterfaceOpen = true;
					textInterfaceOpen = false;
					selectedMessageBox=0;
				}else{
					if(selectedMessageBoxSet==questRewardMessageBoxSet){
						if(quest.isActive)
							quest.aqquireQuestRewards();
						quest.isActive=false;
					}
					selectedMessageBox=0;
					Main.player.resumePlayer();
					Main.level.map.get(Main.level.currentMap).resumeMobs();
					Main.level.map.get(Main.level.currentMap).resumeNPCs();
				}
			}
		}
	}
	public void render(){
		if(alive){
			glPushMatrix();
			glTranslatef(x,y,0);
			glBindTexture(GL_TEXTURE_2D,tileset);
			glBegin(GL_QUADS);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/800);
				glVertex2f(0,0);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/800);
				glVertex2f(w,0);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
				glVertex2f(w,h);
				glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
				glVertex2f(0,h);
			glEnd();
			glPopMatrix();
			drawCollisionBox();
		}
	}
	
	public void update(){
		if(facing == Facing.BACK) currentAnimation = upanim; 
		else if(facing == Facing.FRONT) currentAnimation = downanim;
		else if(facing == Facing.LEFT) currentAnimation = leftanim;
		else if(facing == Facing.RIGHT) currentAnimation = rightanim;
		x+=xvel;
		y+=yvel;
	}
}