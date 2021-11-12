package src;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class LightPlane {
	private static int lightPixelResolution = ConfigFile.getLightPixelResolution();
	public static float defaultLightValue = 0.5f;
	private static LightPixel lightPixels[][] = new LightPixel[(int)Display.getWidth()/lightPixelResolution+1][(int)Display.getHeight()/lightPixelResolution];
	private static float lightSources[][] = new float[(int)Display.getWidth()/lightPixelResolution+1][(int)Display.getHeight()/lightPixelResolution];
	public static int lightDisList;
	
	public LightPlane(){
		initializeLightPlane();
	}
	
	public static void initializeLightPlane(){
		for(int x=0;x<lightSources.length;x++){
			for(int y=0;y<lightSources[0].length;y++){
				lightSources[x][y]=-1;
			}
		}
		for(int x=0;x<lightPixels.length;x++){
			for(int y=0;y<lightPixels[0].length;y++){
				lightPixels[x][y] = new LightPixel(x*lightPixelResolution,y*lightPixelResolution,lightPixelResolution,lightPixelResolution,0.0f,0.0f,0.0f,defaultLightValue);
			}
		}
	}
	
	public static void setLightPixelResolution(int p){
		lightPixelResolution=p;
	}
	
	public static int getLightPixelResolution(){
		return lightPixelResolution;
	}
	
	public static void clearLightSources(){
		for(int x=0;x<lightSources.length;x++){
			for(int y=0;y<lightSources[0].length;y++){
				lightSources[x][y]=-1;
			}
		}
		for(int x=0;x<lightPixels.length;x++){
			for(int y=0;y<lightPixels[0].length;y++){
				lightPixels[x][y].setLightValue(defaultLightValue);
			}
		}
		updateLightPlane();
		lightDisList = glGenLists(1);
		glNewList(lightDisList,GL_COMPILE);
		for(int x=0;x<lightPixels.length;x++){
			for(int y=0;y<lightPixels[0].length;y++){
				lightPixels[x][y].render();
			}
		}
		glEndList();
	}
	
	public static void renderLightPlane(){
		glCallList(lightDisList);
	}
	
	public static void generateLightPlane(){
		glDeleteLists(lightDisList,1);
		lightDisList = glGenLists(1);
		glNewList(lightDisList,GL_COMPILE);
		for(int x=0;x<lightPixels.length;x++){
			for(int y=0;y<lightPixels[0].length;y++){
				lightPixels[x][y].render();
			}
		}
		glEndList();
	}
	
	public static void setDefaultLightValue(float a){
		defaultLightValue=a;
	}
	
	public static int[] addLightSource(float x,float y,float l){
		lightSources[(int)x/lightPixelResolution][(int)y/lightPixelResolution] = l;
		int tmp[] = {(int)x/lightPixelResolution,(int)y/lightPixelResolution};
		return tmp;
	}
	
	public static void updateLightPlane(){
		for(int x=0;x<lightPixels.length;x++){
			for(int y=0;y<lightPixels[0].length;y++){
				if(lightPixels[x][y].getLightValue()!=defaultLightValue){
					lightPixels[x][y].setLightValue(defaultLightValue);
				}
			}
		}
		for(int x=0;x<lightSources.length;x++){
			for(int y=0;y<lightSources[0].length;y++){
				try{
					if(lightSources[x][y]!=-1){
						for(int x1=0;x1<lightPixels.length;x1++){
							for(int y1=0;y1<lightPixels[0].length;y1++){
								if(Math.abs(x1-x)<=10/(lightPixelResolution/30.0f)){
									if(Math.abs(y1-y)<=10/(lightPixelResolution/30.0f)){
										float ltmp = (float)((Math.abs((float)x1-(float)x)/10.0f*(lightPixelResolution/30.0f))+(Math.abs(Math.abs((float)y1-(float)y)/10.0f*(lightPixelResolution/30.0f))));
										if(ltmp < lightPixels[x1][y1].getLightValue())
											if(ltmp<=defaultLightValue)
												lightPixels[x1][y1].setLightValue(ltmp);
									}
								}
							}
						}
					}
				}catch(Exception e){
					
				}
			}
		}
	}
}	
