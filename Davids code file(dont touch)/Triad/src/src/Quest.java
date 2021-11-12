package src;

import java.util.*;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

public class Quest{
	int xpReward;
	int goldReward;
	public List<Item> itemRewards = new ArrayList<Item>();
	public boolean isActive;
	boolean isComplete;
	boolean questInterfaceOpen;
	String quest,desc;
	
	public Quest(int i1,String s1,String s2){
		this.xpReward=i1;
		this.quest=s1;
		this.desc=s2;
	}
	
	public Quest(int x,int g,String s1,String s2){
		this.goldReward=g;
		this.xpReward=x;
		this.quest=s1;
		this.desc=s2;
	}
	
	public void aqquireQuestRewards(){
		for(int i=0;i<itemRewards.size();i++){
			Main.player.inventory.addItem(itemRewards.get(i));
		}
	}
	
	public void drawQuestInterface(){
		if(Collision.quadCollision(Mouse.getX(), -(Mouse.getY()-720), 0, 0, 0, 500, 115, 32)){
			if(Mouse.isButtonDown(0)){
				Main.level.map.get(Main.level.currentMap).npc.get(Main.player.interactingNpc).quest.activate();
				Main.level.map.get(Main.level.currentMap).npc.get(Main.player.interactingNpc).questInterfaceOpen=false;
				Main.level.map.get(Main.level.currentMap).npc.get(Main.player.interactingNpc).textInterfaceOpen=false;
				Main.level.map.get(Main.level.currentMap).resumeMobs();
				Main.level.map.get(Main.level.currentMap).resumeNPCs();
				Main.player.resumePlayer();
			}
			Fonts.drawString(0,500,"Accept",Color.green);
		}else{
			Fonts.drawString(0,500,"Accept",Color.white);
		}
		if(Collision.quadCollision(Mouse.getX(), -(Mouse.getY()-720), 0, 0, 650, 500, 120, 32)){
			if(Mouse.isButtonDown(0)){
				Main.level.map.get(Main.level.currentMap).npc.get(Main.player.interactingNpc).questInterfaceOpen=false;
				Main.level.map.get(Main.level.currentMap).npc.get(Main.player.interactingNpc).textInterfaceOpen=false;
				Main.level.map.get(Main.level.currentMap).resumeMobs();
				Main.level.map.get(Main.level.currentMap).resumeNPCs();
				Main.player.resumePlayer();
			}
			Fonts.drawString(650, 500, "Decline",Color.red);
		}else{
			Fonts.drawString(650, 500, "Decline",Color.white);
		}
	}
	
	public void update(){
		if(isActive)
			System.out.println("hit");
	}
	
	public void activate(){
		isActive=true;
	}
}
