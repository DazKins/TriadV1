package src;

public class MobWizard extends MobAggressive{
	private static float w=30,h=30;
	public static int id = 1;
	private static float movementSpeed = 1;
	public static int tileset = Tile.mobtileset_1;
	public static String name = "Spider";
	public static int agroRange = 200;
	
	public MobWizard(float x, float y){
		super(x,y,w,h,movementSpeed,tileset,name,100,new CollisionBox(x,y,w,h),agroRange);
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
		if(agroOnPlayer){
			if(hitTimer==100/attackSpeed){
				Spell.castSpell(this.colBox.x+this.colBox.w/2, this.colBox.y+this.colBox.h/2, Main.player.colBox.x+Main.player.colBox.w/2,Main.player.colBox.y+Main.player.colBox.h/2, SpellStorage.slowerTestSpell,true);
				xvel=0;
				yvel=0;
				hitTimer=0;
			}
		}
	}
}