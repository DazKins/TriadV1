package src;

public class MobStredlite extends MobAggressive{
	public static float w=120;
	public static float h=120;
	public static float mov = 0.5f;
	public static int tileset = Tile.mobtileset_1;
	public static String name = "Stredlite";
	public static int health = 50;
	public static int agroRange = 200;

	public MobStredlite(float x, float y){
		super(x, y, w, h, mov, tileset, name, health, new CollisionBox(x+20,y+20,w-40,h-40),agroRange);
		upAnimation.addAnimationFrame(20, 2, 40, 10);
		upAnimation.addAnimationFrame(22, 2, 40, 10);
		upAnimation.addAnimationFrame(24, 2, 40, 10);
		upAnimation.addAnimationFrame(26, 2, 40, 10);
		upAnimation.addAnimationFrame(28, 2, 40, 10);
		upAnimation.addAnimationFrame(30, 2, 40, 10);
		upAnimation.addAnimationFrame(32, 2, 40, 10);
		upAnimation.addAnimationFrame(34, 2, 40, 10);
		upAnimation.addAnimationFrame(36, 2, 40, 10);
		downAnimation.addAnimationFrame(2, 2, 40, 10);
		downAnimation.addAnimationFrame(4, 2, 40, 10);
		downAnimation.addAnimationFrame(6, 2, 40, 10);
		downAnimation.addAnimationFrame(8, 2, 40, 10);
		downAnimation.addAnimationFrame(10, 2, 40, 10);
		downAnimation.addAnimationFrame(12, 2, 40, 10);
		downAnimation.addAnimationFrame(14, 2, 40, 10);
		downAnimation.addAnimationFrame(16, 2, 40, 10);
		downAnimation.addAnimationFrame(18, 2, 40, 10);
		leftAnimation.addAnimationFrame(2, 0, 40, 10);
		leftAnimation.addAnimationFrame(4, 0, 40, 10);	
		leftAnimation.addAnimationFrame(6, 0, 40, 10);	
		leftAnimation.addAnimationFrame(8, 0, 40, 10);	
		leftAnimation.addAnimationFrame(10, 0, 40, 10);	
		leftAnimation.addAnimationFrame(12, 0, 40, 10);	
		rightAnimation.addAnimationFrame(14, 0, 40, 10);
		rightAnimation.addAnimationFrame(16, 0, 40, 10);
		rightAnimation.addAnimationFrame(18, 0, 40, 10);
		rightAnimation.addAnimationFrame(20, 0, 40, 10);
		rightAnimation.addAnimationFrame(22, 0, 40, 10);
		rightAnimation.addAnimationFrame(24, 0, 40, 10);
		drops.add(ItemStorage.testWeapon);
	}
		
	public void update(){
		super.update();
		moveToPlayer();
	}
}