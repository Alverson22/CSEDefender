package helpers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glRotatef;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glBlendFunc;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Artist {

	public static final int WIDTH = 1472, HEIGHT = 960, TILE_SIZE = 64;
	
	public static void BeginSession()
	{
		Display.setTitle("CSEDefender");
		try {
			Display.setDisplayMode( new DisplayMode( WIDTH ,HEIGHT ));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode( GL_PROJECTION );
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0 ,1, -1);
		glMatrixMode( GL_MODELVIEW );
		glEnable( GL_TEXTURE_2D );
		glEnable( GL_BLEND );
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("src/res/gam1088.png"));
		} catch (IOException e1) {
			// TODO 自動產生的 catch 區塊
			e1.printStackTrace();
		}
		try {
			loadCursor(image);
		} catch (LWJGLException e1) {
			// TODO 自動產生的 catch 區塊
			e1.printStackTrace();
		}
	}
	public static boolean CheckCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2)
	{
		if(x1 + width1 > x2 && x1 < x2 +width2 && y1 + height1 > y2 && y1 < y2 + height2)
		{
			return true;
		}
		return false;
	}
	public static void DrawQuad(float x, float y, float width, float height)
	{
		glBegin( GL_QUADS );
		glVertex2f(x, y); // Top left corner
		glVertex2f(x + width, y); // Top right corner
		glVertex2f(x + width, y + height); // Bottom right corner
		glVertex2f(x, y + height); // Buttom left corner
		glEnd();
	}
	
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height)
	{
		tex.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
		
	}
	
	public static void DrawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle)
	{
		tex.bind();
		glTranslatef(x + width / 2, y + height / 2, 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(- width / 2, - height / 2, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
		
	}
	
	public static Texture LoadTexture(String path, String fileType)
	{
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	public static Texture QuickLoad(String name)
	{
		Texture tex = null;
		tex = LoadTexture("res/" + name + ".png", "PNG");
		return tex;
	}
	
	public static void loadCursor(BufferedImage img) throws LWJGLException
	{
	    final int w = img.getWidth();
	    final int h = img.getHeight();

	    int rgbData[] = new int[w * h];

	    for (int i = 0; i < rgbData.length; i++)
	    {
	        int x = i % w;
	        int y = h - 1 - i / w; // this will also flip the image vertically

	        rgbData[i] = img.getRGB(x, y);
	    }

	    IntBuffer buffer = BufferUtils.createIntBuffer(w * h);
	    buffer.put(rgbData);
	    buffer.rewind();

	    Cursor cursor = new Cursor(w, h, 2, h - 2, 1, buffer, null);

	    Mouse.setNativeCursor(cursor);
	}
}
