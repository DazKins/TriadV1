package src;

public class MobRabbit extends MobPassive{
	public static float w=30,h=30;
	public static float movementSpeed = 1;
	public static int tileset = Tile.mobtileset_1;
	public static String name = "Rabbit";
	public static int health = 10;
	
	public MobRabbit(float x,float y){
		super(x,y,w,h,movementSpeed,tileset,name,health,new CollisionBox(x+8,y+8,w-20,h-15));
		downAnimation.addAnimationFrame(26, 0, 20, 10);
		downAnimation.addAnimationFrame(27, 0, 20, 10);
		leftAnimation.addAnimationFrame(28, 0, 20, 10);
		leftAnimation.addAnimationFrame(29, 0, 20, 10);
		rightAnimation.addAnimationFrame(30, 0, 20, 10);
		rightAnimation.addAnimationFrame(31, 0, 20, 10);
		upAnimation.addAnimationFrame(32, 0, 20, 10);
		upAnimation.addAnimationFrame(33, 0, 20, 10);
	}
	
	public void update(){
		moveToPlayer();
		super.update();
	}
}