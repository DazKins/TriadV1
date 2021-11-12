package src;

public class SpellType {
	private float speed;
	private int damage;
	private Animation flight;
	private Animation hit;
	private int tileset;
	
	public SpellType(int tileset,float s,int d,Animation sp,Animation sp1){
		this.speed=s;
		this.damage=d;
		this.flight=sp;
		this.tileset=tileset;
		this.hit=sp1;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getTileSet(){
		return tileset;
	}
	
	public Animation getAnimation(){
		return flight;
	}
	
	public Animation getHitAnimation(){
		return hit;
	}
}
