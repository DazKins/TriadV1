package src;

public class TimeCycle {
	private static float dayTime = 36000;
	private static float currentTime = 0;
	private static float dayLightLevel=0.0f;
	private static float nightLightLevel=0.8f;
	private static float lightLevel;
	public enum tof{DAY,NIGHT};
	public static tof TimeOfDay;
	
	public static void updateTime(){
		currentTime+=1;
		if(currentTime<=dayTime){
			TimeOfDay=tof.DAY;
		}else{
			TimeOfDay=tof.NIGHT;
			if(currentTime>=dayTime*2){
				currentTime=0;
				TimeOfDay=tof.DAY;
			}
		}
		if(Main.level.map.get(Main.level.currentMap).outdoors){
			LightPlane.defaultLightValue = lightLevel;
			if(TimeOfDay==tof.DAY){
				if(lightLevel>dayLightLevel){
					lightLevel-=0.001f;
					LightPlane.updateLightPlane();
					LightPlane.generateLightPlane();
				}
			}else{
				if(lightLevel<nightLightLevel){
					lightLevel+=0.001f;
					LightPlane.updateLightPlane();
					LightPlane.generateLightPlane();
				}
			}
		}
	}
}
