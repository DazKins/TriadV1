package src;

import org.lwjgl.input.Mouse;

public class InventorySlot {
	public Item i;
	public float x,y;
	public boolean dragging;
	public InventorySlot(float x,float y,Item i){
		this.i=i;
		this.x=x;
		this.y=y;
	}
	public void render(){
		i.render(x, y, Main.player.inventory.inventoryTileSize-8, Main.player.inventory.inventoryTileSize-8);
	}
	public void update(){
		if(dragging){
			this.x=Mouse.getX()-Main.player.inventory.inventoryTileSize/2;
			this.y=-(Mouse.getY()-720)-Main.player.inventory.inventoryTileSize/2;
		}
	}
	public void changeItem(Item i){
		this.i=i;
	}
}
