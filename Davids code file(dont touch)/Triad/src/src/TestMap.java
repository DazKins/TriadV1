package src;

import java.util.Random;

public class TestMap extends Map{
	public TestMap(){
		super("res/World.dat");
		upper=-1;
		lower=-1;
		left=-1;
		right=-1;
		addLight(200,600);
		addLight(600,600);
		addLight(1000,600);
		addLight(200,200);
		addLight(600,200);
		addLight(1000,200);
		addLight(400,400);
		addLight(800,400);
		mobs.add(new MobWizard(500,500));
		mobs.add(new MobStredlite(400,200));
		weather = Weather.RAIN;
		outdoors=true;
	}
}