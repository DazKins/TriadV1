package src;

public class ItemWeapon extends Item{
	int damage;
	
	public ItemWeapon(int tileset,Sprite tex,String name,String description,int damage){
		super(tileset,tex,name,description,ItemType.WEAPON);
		this.damage = damage;
	}
}
