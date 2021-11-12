package src;

import java.util.*;

public class Level {
	
	int currentMap = 0;
	List<Map> map = new ArrayList<Map>();
	
	public Level(){
		map.add(new TestMap()); //0
		map.add(new TestMap2()); //1
		map.get(currentMap).setupMapLighting();
	}
	
	public void render(){
		map.get(currentMap).render();
		Spell.renderAll();
	}
	
	public void update(){
		map.get(currentMap).update();
		Spell.updateAll();
	}
}
