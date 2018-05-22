package com.ld48.nidjo123.tinygame;

import java.applet.Applet;
import java.awt.BorderLayout;

public class TinyGameApplet extends Applet {
	private static final long serialVersionUID = 1L;
	
	private TinyGame game = new TinyGame();
	
	public void init() {
		add(game, BorderLayout.CENTER);
	}
	
	public void start() {
		System.out.println(getSize());
		game.init();
		game.gameLoop();
	}
	
	public void stop() {
		game.stop();
	}
}
