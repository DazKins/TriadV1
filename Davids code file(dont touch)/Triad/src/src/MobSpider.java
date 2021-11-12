package src;

public class MobSpider extends MobAggressive{
	private static float w=30,h=30;
	public static int id = 1;
	private static float movementSpeed = 1;
	public static int tileset = Tile.mobtileset_1;
	public static String name = "Spider";
	public static int agroRange = 200;
	
	public MobSpider(float x, float y){
		super(x,y,w,h,movementSpeed,tileset,name,100,new CollisionBox(x,y,w,h),200);
		alive = true;
		damage = 10;
		attackSpeed=1;
		hitTimer=100/attackSpeed;
		downAnimation.addAnimationFrame(0, 0, 20, 50);
		upAnimation.addAnimationFrame(1, 0, 20, 50);
		leftAnimation.addAnimationFrame(0, 0, 20, 50);
		rightAnimation.addAnimationFrame(1, 0, 20, 50);
	}
	
	public void update(){
		super.update();
		moveToPlayer();
	}
}
