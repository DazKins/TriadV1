package src;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Spell {
	float x,y;
	float w=10,h=10;
	float xvel,yvel;
	Animation hitAnim;
	Animation currentAnimation;
	CollisionBox colBox;
	int damage;
	int tileset;
	public static List<Spell> spells = new ArrayList<Spell>();
	public float rotation;
	public boolean hostileCaster;
	
	public static void castSpell(float sX,float sY,float tX,float tY,SpellType type,boolean h){
		spells.add(new Spell(type.getTileSet(),sX,sY,tX,tY,type,h));
	}
	
	public static void renderAll(){
		for(int i=0;i<spells.size();i++){
			spells.get(i).render();
		}
	}
	
	public static void updateAll(){
		for(int i=0;i<spells.size();i++){
			if(spells.get(i).update(i)){
				if(spells.get(i).x>Display.getWidth()){
					spells.remove(i);
				}else if(spells.get(i).y>Display.getHeight()){
					spells.remove(i);
				}else if(spells.get(i).x<0){
					spells.remove(i);
				}else if(spells.get(i).y<0){
					spells.remove(i);
				}
			}
		}
	}
	
	public void render(){
		glPushMatrix();
		
		glTranslatef(x,y,0);
		
		glRotatef(rotation,0,0,1);
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,this.tileset);
		glBegin(GL_QUADS);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20))/800);
			glVertex2f(-w/2,-h/2);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20))/800);
			glVertex2f(w/2,-h/2);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
			glVertex2f(w/2,h/2);
			glTexCoord2d((float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[0]*20))/800,(float)((currentAnimation.frames.get(currentAnimation.currentFrame).spr.tex[1]*20)+currentAnimation.frames.get(currentAnimation.currentFrame).spr.size)/800);
			glVertex2f(-w/2,h/2);
		glEnd();
		glPopMatrix();
	}
	
	public boolean update(int i){
		if(this.currentAnimation!=this.hitAnim){
			this.currentAnimation.tickAnimation();
		}
		else{
			if(this.currentAnimation.tickAnimation()){
				spells.remove(i);
				return false;
			}
		}
		currentAnimation.tickAnimation();
		if(this.hostileCaster){
			if(Collision.quadCollision(this.x, this.y, this.w, this.h, Main.player.colBox.x, Main.player.colBox.y, Main.player.colBox.w, Main.player.colBox.h)){
				Main.player.health-=damage;
				xvel=0;
				yvel=0;
				this.w=30;
				this.h=30;
				this.rotation=0;
				this.currentAnimation=this.hitAnim;
			}
		}else{
			for(int i1=0;i1<Main.level.map.get(Main.level.currentMap).mobs.size();i1++){
				if(Collision.quadCollision(this.x, this.y, this.w, this.h, Main.level.map.get(Main.level.currentMap).mobs.get(i).x, Main.level.map.get(Main.level.currentMap).mobs.get(i).y, Main.level.map.get(Main.level.currentMap).mobs.get(i).w, Main.level.map.get(Main.level.currentMap).mobs.get(i).h)){
					Main.level.map.get(Main.level.currentMap).mobs.get(i).hitMob(this.damage);
				}
			}
		}
		x+=xvel;
		y+=yvel;
		return true;
	}
	
	public static void clearAllSpells(){
		spells.clear();
	}
	
	public Spell(int tileset,float x,float y,float tX,float tY,SpellType type,boolean h){
		this.x=x;
		this.y=y;
		double dirX = tX-x;
		double dirY = tY-y;
		double hyp = Math.sqrt(dirX*dirX + dirY*dirY);
		dirX /= hyp;
		dirY /= hyp;
		this.xvel = (float)dirX*type.getSpeed();
		this.yvel = (float)dirY*type.getSpeed();
		this.currentAnimation=type.getAnimation();
		this.damage=type.getDamage();
		this.rotation = -(float) Math.toDegrees(Math.atan((x-tX)/(y-tY)));
		if(tY>y) rotation -= 180.0f;
		this.tileset=tileset;
		this.hitAnim=type.getHitAnimation();
		this.hostileCaster=h;
	}
}
