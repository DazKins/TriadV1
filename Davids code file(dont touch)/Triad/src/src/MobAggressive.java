package src;

public class MobAggressive extends Mob{
	public float hitTimer;
	public float attackSpeed;
	public int[] attackTex;
	public int agroRange;
	
	public MobAggressive(float x,float y,float w,float h,float mov,int tileset,String name,int health,CollisionBox c,int ag){
		super(x,y,w,h,mov,tileset,name,health,c);
		alive = true;
		agroRange = ag;
	}
	
	public void update(){
		if(alive){
			super.update();
			detectAgro(agroRange);
			if(agroOnPlayer){
				facePlayer();
				if(Collision.quadCollision(Main.player.x, Main.player.y, Main.player.w, Main.player.h, x, y, w, h) && hitTimer==100/attackSpeed){
					hitPlayer();
					hitTimer=0;
				}
				if(hitTimer<100/attackSpeed){
					hitTimer+=attackSpeed;
				}
				if(health<=0){
					for(int i=0;i<drops.size();i++){
						ItemDrops.addDroppedItem(colBox.x+colBox.w/2, colBox.y+colBox.h/2, drops.get(i));
					}
					alive = false;
					if(NpcBob.q.isActive)
						QuestTest.addKill();
				}
			}else{
				randomMovement();
			}
		}
	}
}