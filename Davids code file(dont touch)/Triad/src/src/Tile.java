package src;

import java.io.*;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import de.matthiasmann.twl.utils.*;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import static org.lwjgl.opengl.GL11.*;

public class Tile {
	public static float tileSize = 30;
	
	public static int tileset_1;
	public static int mobtileset_1;
	public static int npctileset_1;
	public static int itemtileset_1;
	public static int guitileset_1;
	public static int charactertileset;
	public static int particleTileSet;
	
	public Tile(){
		tileset_1 = loadTexture("res/tileset_1.png");
		mobtileset_1 = loadTexture("res/mobtileset_1.png");
		npctileset_1 = loadTexture("res/npctileset_1.png");
		itemtileset_1 = loadTexture("res/itemtileset_1.png");
		guitileset_1 = loadTexture("res/Inventory.png");
		charactertileset = loadTexture("res/Character.png");
		particleTileSet = loadTexture("res/particletileset_1.png");
	}
	public static int loadTexture(String f){
		int tmp = glGenTextures();
        InputStream in = null;
        try {
            in = new FileInputStream(f);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
            buffer.flip();
            glBindTexture(GL_TEXTURE_2D, tmp);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glBindTexture(GL_TEXTURE_2D, 0);
        } 
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tmp;
	}
}
