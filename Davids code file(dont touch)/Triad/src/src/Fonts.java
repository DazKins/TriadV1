package src;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Fonts {

	public static UnicodeFont font;
	public static String fontPath = "lazy.ttf";
	
	@SuppressWarnings("unchecked")
	public Fonts(){
		try{
			font = new UnicodeFont(fontPath, 32, true, false);
			font.addAsciiGlyphs();
			font.addGlyphs(400, 600);
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			font.loadGlyphs();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void drawString(float x,float y,String message){
		String[] tmp = message.split("#");
		for(int i=0;i<tmp.length;i++){
			font.drawString(x, y, tmp[i]);
			y+=32;
		}
	}
	
	public static void drawString(float x,float y,String message,Color c){
		Color.green.bind();
		String[] tmp = message.split("#");
		for(int i=0;i<tmp.length;i++){
			font.drawString(x, y, tmp[i],c);
			y+=32;
		}
	}
}
