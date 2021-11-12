package src;

public class CollisionBox {
	public float x,y;
	public float w,h;
	
	public CollisionBox(float x,float y,float w,float h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
	
	public boolean Intersects(CollisionBox c1, CollisionBox c2){
		if(Collision.quadCollision(c1.x, c1.y, c1.w, c1.h, c2.x, c2.y, c2.w, c2.h)){
			return true;
		}
		return false;
	}
	
	public void move(float x,float y){
		this.x+=x;
		this.y+=y;
	}
}
