package com.ld48.nidjo123.tinygame;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Art {
	public static final Art art = new Art();
	
	public static Bitmap[][] spriteSheet = loadNCut("/gfx/spritesheet.png");
	public Bitmap bg = load("/gfx/bg.png");
	public Bitmap endWon = load("/gfx/endWon.png");
	public Bitmap endLost = load("/gfx/endLost.png");
	public Bitmap start = load("/gfx/start.png");
	
	public static BufferedImage img;
	
	public static int width;
	public static int height;
	
	public Bitmap load(String string) {
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(string));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			Bitmap bitmap = new Bitmap(w, h);
			
			img.getRGB(0, 0, w, h, bitmap.pixels, 0, w);
			for (int i = 0; i < w * h; i++) {
				if (bitmap.pixels[i] == 0xffff00ff || bitmap.pixels[i] == -6409985) {
					bitmap.pixels[i] = -3;
				}
			}
			
			return bitmap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Bitmap cut(Bitmap bitmap, int w, int h, int r, int c) {
		Bitmap bm = new Bitmap(w, h);
		int k = 0;
		for (int i = r * 16; i < r * 16 + 16; i++) {
			for (int j = c * 16; j < c * 16 + 16; j++) {
				bm.pixels[k] = bitmap.pixels[i * bitmap.w + j];
				k++;
			}
		}
		
		return bm;
	}
	
	public static Bitmap[][] loadNCut(String string) {
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(string));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			Bitmap bitmap = new Bitmap(w, h);
			
			Bitmap[][] bitmaps = new Bitmap[bitmap.h / 16][bitmap.w / 16];
			
			img.getRGB(0, 0, w, h, bitmap.pixels, 0, w);
			for (int n = 0; n < bitmap.w / 16; n++) {
				for (int z = 0; z < bitmap.h / 16; z++) {
					Bitmap bm = new Bitmap(16, 16);
					int k = 0;
					for (int i = n * 16; i < n * 16 + 16; i++) {
						for (int j = z * 16; j < z * 16 + 16; j++) {
							bm.pixels[k] = bitmap.pixels[i * bitmap.w + j];
							if (bm.pixels[k] == 0xffff00ff || bm.pixels[k] == -6409985) {
								bm.pixels[k] = -3;
							}
							k++;
						}
					}
					bitmaps[n][z] = bm;
				}
			}
			
			return bitmaps;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
