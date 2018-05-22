package com.ld48.nidjo123.tinygame;

public class Screen {
	public static int pixels[] = new int[TinyGame.HEIGHT * TinyGame.WIDTH];
	private int width;
	private int height;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public static void render(TinyGame game) {		
		for (int i = 0; i < game.entities.size(); i++) {
			game.entities.get(i).render(pixels);
		}
	}
}
