package src;

import java.util.ArrayList;
import java.util.List;

public class ItemDrops {
	private static List<DroppedItemEntity> droppedItems = new ArrayList<DroppedItemEntity>();
	
	public static void addDroppedItem(float x,float y,Item i){
		droppedItems.add(new DroppedItemEntity(x,y,30,30,i));
	}
	
	public static void render(){
		for(int i=0;i<droppedItems.size();i++){
			droppedItems.get(i).render();
		}
	}
	
	public static void clearAllDroppedItems(){
		droppedItems.clear();
	}
	
	public static void update(){
		for(int i=0;i<droppedItems.size();i++){
			if(droppedItems.get(i).update()){
				Main.player.inventory.addItem(droppedItems.get(i).item);
				droppedItems.remove(i);
			}
		}
	}
}