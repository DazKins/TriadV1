package src;

import static org.lwjgl.opengl.GL11.*;

public class Item {
	public int x,y;
	public int value;
	public Sprite spr;
	public int weight;
	public int tileset;
	public float inventoryx,inventoryy;
	String name;
	String description;
	public static enum ItemType{WEAPON,HELMET,BA,BOOTS,GAUNTS,LEGGINGS,MISC,NECKLACE,RING,SHIELD};
	public ItemType itemType;
	
	public Item(int tileset,Sprite tex,String name,String description,ItemType i){
		this.name = name;
		this.description = description;
		this.spr = tex;
		this.tileset=tileset;
		itemType=i;
	}
	
	public void render(float x,float y,float w,float h){
		glBindTexture(GL_TEXTURE_2D,tileset);
		glBegin(GL_QUADS);
			glTexCoord2d((float)((spr.tex[0]*20))/800,(float)((spr.tex[1]*20))/800);
			glVertex2f(x,y);
			glTexCoord2d((float)((spr.tex[0]*20)+20)/800,(float)((spr.tex[1]*20))/800);
			glVertex2f(x+w,y);
			glTexCoord2d((float)((spr.tex[0]*20)+20)/800,(float)((spr.tex[1]*20)+20)/800);
			glVertex2f(x+w,y+h);
			glTexCoord2d((float)((spr.tex[0]*20))/800,(float)((spr.tex[1]*20)+20)/800);
			glVertex2f(x,y+h);
		glEnd();
	}
}
