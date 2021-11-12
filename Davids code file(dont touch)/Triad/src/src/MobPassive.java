package src;

public class MobPassive extends Mob{
	
	public MobPassive(float x,float y,float w,float h,float mov,int tileset,String name,int health,CollisionBox c){
		super(x,y,w,h,mov,tileset,name,health,c);
	}
	
	public void update(){
		super.update();
	}
}