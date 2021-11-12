package src;

public class QuestTest extends Quest{
	public static int goldReward = 200;
	public static int xpReward = 500;
	public static int kills = 0;
	public static int killCount = 0;
	public static String name;
	public static String desc;
	
	public QuestTest(){
		super(goldReward,xpReward,name,desc);
		itemRewards.add(ItemStorage.testWeapon);
	}
	
	public static void addKill(){
		kills++;
		if(kills>=killCount){
			NpcBob.q.isComplete=true;
		}
	}
}