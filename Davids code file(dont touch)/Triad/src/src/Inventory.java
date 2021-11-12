package src;

import java.util.*;

import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;

public class Inventory {
	int inventorySize = 64;
	public int inventoryTileSize = 50;
	public int invX=10,invY=10;
	public int inventoryTileSpacingx = 6,inventoryTileSpacingy = 2;
	List<InventorySlot> items = new ArrayList<InventorySlot>();
	List<InventorySlot> equips = new ArrayList<InventorySlot>();
	public int misc,head,necklace,ring,leggings,gaunts,boots,ba,weapon,shield;
	public InventorySlot minv = new InventorySlot(Mouse.getX(),-(Mouse.getY()-720),ItemStorage.nullItem);
	
	public Inventory(){
		for(int x=0;x<invX;x++){
			for(int y=0;y<invY;y++){
				items.add(new InventorySlot(x*(inventoryTileSize + inventoryTileSpacingx)+70+4,y*(inventoryTileSize + inventoryTileSpacingy)+140+4,ItemStorage.nullItem));
			}
		}
		equips.add(new InventorySlot(992,297,ItemStorage.nullMisc)); // 0 misc
		equips.add(new InventorySlot(1042,297,ItemStorage.nullHelmet)); // 1 helm
		equips.add(new InventorySlot(992,347,ItemStorage.nullGaunts)); // 2 gloves
		equips.add(new InventorySlot(1042,347,ItemStorage.nullBa)); // 3 ba
		equips.add(new InventorySlot(1092,347,ItemStorage.nullNecklace)); // 4 necklace
		equips.add(new InventorySlot(992,397,ItemStorage.nullShield)); //5 shield
		equips.add(new InventorySlot(1042,397,ItemStorage.nullLegs)); //6 legs
		equips.add(new InventorySlot(1092,397,ItemStorage.nullRing)); // 7 ring
		equips.add(new InventorySlot(1042,447,ItemStorage.nullBoots)); // 8 boots
		equips.add(new InventorySlot(1092,447,ItemStorage.nullWeapon)); // 9 weapon
	}
	
	public void addItem(Item item){
		for(int i=0;i<items.size();i++){
			if(items.get(i).i == ItemStorage.nullItem){
				items.get(i).changeItem(item);
				return;
			}
		}
	}
	
	public void update(){
		minv.x=Mouse.getX()-15;
		minv.y=-(Mouse.getY()-720)-15;
		if(Input.leftMouse){
			for(int i=0;i<items.size();i++){
				if(Collision.quadCollision(Mouse.getX(),-(Mouse.getY()-720),0,0,items.get(i).x,items.get(i).y,inventoryTileSize-4,inventoryTileSize-4)){
					Item id1 = items.get(i).i;
					Item id2 = minv.i;
					items.get(i).i = id2;
					minv.i = id1;
				}
			}
			for(int i=0;i<equips.size();i++){
				if(Collision.quadCollision(Mouse.getX(),-(Mouse.getY()-720),0,0,equips.get(i).x,equips.get(i).y,inventoryTileSize-4,inventoryTileSize-4)){
					Item id1 = equips.get(i).i;
					Item id2 = minv.i;
					if(id1.itemType == id2.itemType){
						equips.get(i).i = id2;
						minv.i = id1;
					}
				}
			}
		}
		for(int i=0;i<items.size();i++){
			if(Collision.quadCollision(Mouse.getX(),-(Mouse.getY()-720),0,0,items.get(i).x,items.get(i).y,inventoryTileSize-4,inventoryTileSize-4)){
				if(items.get(i).i.spr.tex[0]!=-1 && items.get(i).i.spr.tex[1]!=-1 ){
					items.get(i).i.render(707, 90, 150, 150);
					Fonts.drawString(680, 60, items.get(i).i.name);
					Fonts.drawString(680, 284, items.get(i).i.description);
				}
			}
		}
		for(int i=0;i<equips.size();i++){
			if(Collision.quadCollision(Mouse.getX(),-(Mouse.getY()-720),0,0,equips.get(i).x,equips.get(i).y,inventoryTileSize-4,inventoryTileSize-4)){
				if(equips.get(i).i.spr.tex[0]!=-1 && equips.get(i).i.spr.tex[1]!=-1){
					equips.get(i).i.render(707, 90, 150, 150);
					Fonts.drawString(680, 60, equips.get(i).i.name);
					Fonts.drawString(680, 284, equips.get(i).i.description);
				}
			}
		}
		for(int i=0;i<items.size();i++){
			items.get(i).update();
		}
	}
	
	public void render(){
		glBindTexture(GL_TEXTURE_2D,Tile.guitileset_1);
		glBegin(GL_QUADS);
			glTexCoord2f(0,0.025f);
			glVertex2f(0,0);
			glTexCoord2f(1,0.025f);
			glVertex2f(1290,0);
			glTexCoord2f(1,1);
			glVertex2f(1290,720);
			glTexCoord2f(0,1);
			glVertex2f(0,720);
		glEnd();
		glBindTexture(GL_TEXTURE_2D,Tile.guitileset_1);
		for(int x=0;x<invX;x++){
			for(int y=0;y<invY;y++){
				glBegin(GL_QUADS);
					glTexCoord2f(0,0);
					glVertex2f(x*(inventoryTileSize + inventoryTileSpacingx)+70,y*(inventoryTileSize + inventoryTileSpacingy)+140);
					glTexCoord2f(0.015625f,0);
					glVertex2f(x*(inventoryTileSize + inventoryTileSpacingx)+inventoryTileSize+70,y*(inventoryTileSize + inventoryTileSpacingy)+140);
					glTexCoord2f(0.015625f,0.027027f);
					glVertex2f(x*(inventoryTileSize + inventoryTileSpacingx)+inventoryTileSize+70,y*(inventoryTileSize + inventoryTileSpacingy)+inventoryTileSize+140);
					glTexCoord2f(0,0.027027f);
					glVertex2f(x*(inventoryTileSize + inventoryTileSpacingx)+70,y*(inventoryTileSize + inventoryTileSpacingy)+inventoryTileSize+140);
				glEnd();
			}
		}
		for(int i=0;i<equips.size();i++){
			glBegin(GL_QUADS);
				glTexCoord2f(0,0);
				glVertex2f(equips.get(i).x-6,equips.get(i).y-6);
				glTexCoord2f(0.015625f,0);
				glVertex2f(equips.get(i).x+inventoryTileSize-2,equips.get(i).y-6);
				glTexCoord2f(0.015625f,0.027027f);
				glVertex2f(equips.get(i).x+inventoryTileSize-2,equips.get(i).y+inventoryTileSize-2);
				glTexCoord2f(0,0.027027f);
				glVertex2f(equips.get(i).x-6,equips.get(i).y+inventoryTileSize-2);
			glEnd();
		}
		for(int u=0;u<items.size();u++){
			if(items.get(u).i.spr.tex[0]!=-1 && items.get(u).i.spr.tex[1]!=-1 )
				items.get(u).render();
		}
		for(int i=0;i<equips.size();i++){
			if(equips.get(i).i.spr.tex[0]!=-1 && equips.get(i).i.spr.tex[1]!=-1 )
				equips.get(i).render();
		}
		if(minv.i != ItemStorage.nullItem){
			if(minv.i.spr.tex[0]!=-1 && minv.i.spr.tex[1]!=-1)
				minv.render();
		}
	}
}
