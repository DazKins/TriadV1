package src;

import java.util.*;

public class NpcBob extends Npc{
	private static int tex[] = {0,0};
	private static float w=60,h=60;
	private static int tileset = Tile.npctileset_1;
	private static boolean questGiver = true;
	public static Quest q = new QuestTest();
	public static List<MessageBox> speechSet1 = new ArrayList<MessageBox>();
	public static List<MessageBox> speechSet2 = new ArrayList<MessageBox>();
	public static List<MessageBox> speechSet3 = new ArrayList<MessageBox>();
	public static List<MessageBox> speechSet4 = new ArrayList<MessageBox>();
	
	public NpcBob(float x,float y){
		super(x,y,w,h,tileset,tex,questGiver,q,new CollisionBox(x,y,w,h));
		//0
		speechSet1.add(new MessageBox("Hello, I am Bob the test NPC! Unfortunately I am being attacked by#spiders and they are REALLY annoying me!",true));
		speechSet1.add(new MessageBox("Will you help me by getting rid of all the spiders?",false));
		speech.add(new MessageBoxSet(speechSet1));
		//1
		speechSet2.add(new MessageBox("Thank you so much for killing those spiders!",false));
		speech.add(new MessageBoxSet(speechSet2));
		//2
		speechSet3.add(new MessageBox("Please hurry and kill all of those spiders!",false));
		speech.add(new MessageBoxSet(speechSet3));
		//3
		speechSet4.add(new MessageBox("What a lovely day",false));
		speech.add(new MessageBoxSet(speechSet4));
		questRewardMessageBoxSet=1;
		rightanim.addAnimationFrame(3, 0, 20, 10);
		leftanim.addAnimationFrame(2, 0, 20, 10);
		upanim.addAnimationFrame(0, 0, 20, 10);
		downanim.addAnimationFrame(1, 0, 20, 10);
	}
	
	public void update(){
		super.update();
		
	}
	
	public void interact(){
		if(!q.isActive && !q.isComplete) selectedMessageBoxSet=0;
		else if(q.isComplete) selectedMessageBoxSet=1;
		else if(q.isComplete && !q.isActive) selectedMessageBoxSet=3;
		else selectedMessageBoxSet=2;
		super.interact();
	}
}
