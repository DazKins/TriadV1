package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {
	private static List<String> configFileData = new ArrayList<String>();
	private static int lightPixelResolution;
	private static int particleThreshold;
	
	private static void loadConfigFile(String f){
		BufferedReader br = null;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(f));
 
			while ((sCurrentLine = br.readLine()) != null){
				configFileData.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getLightPixelResolution(){
		return lightPixelResolution;
	}
	
	public static int getParticleThreshold(){
		return particleThreshold;
	}
	
	public ConfigFile(){
		loadConfigFile("res/config.cfg");
		for(int i=0;i<configFileData.size();i++){
			if(configFileData.get(i).startsWith("LIGHT_RESOLUTION")){
				lightPixelResolution=Integer.parseInt(configFileData.get(i).replace("LIGHT_RESOLUTION ",""));
			}
			if(configFileData.get(i).startsWith("PARTICLE_THRESHOLD")){
				particleThreshold=Integer.parseInt(configFileData.get(i).replace("PARTICLE_THRESHOLD ",""));
			}
		}
	}
}
