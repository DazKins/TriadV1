package src;

import src.Item.ItemType;

public class ItemStorage {
	public static Item nullItem = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.WEAPON);
	public static Item nullWeapon = new ItemWeapon(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",0);
	public static Item nullBoots = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.BOOTS);
	public static Item nullRing = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.RING);
	public static Item nullLegs = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.LEGGINGS);
	public static Item nullShield = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.SHIELD);
	public static Item nullNecklace = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.NECKLACE);
	public static Item nullBa = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.BA);
	public static Item nullGaunts = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.GAUNTS);
	public static Item nullHelmet = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.HELMET);
	public static Item nullMisc = new Item(Tile.itemtileset_1,new Sprite(-1,-1,20),"","",ItemType.MISC);
	public static Item testWeapon = new ItemWeapon(Tile.itemtileset_1,new Sprite(0,0,20),"TEST ITEM","This is an#item to#test the#game!",5);
}