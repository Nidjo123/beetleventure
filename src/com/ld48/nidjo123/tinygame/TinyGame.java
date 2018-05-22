package com.ld48.nidjo123.tinygame;

import java.awt.*;
import java.awt.image.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ld48.nidjo123.tinygame.entity.Boss;
import com.ld48.nidjo123.tinygame.entity.BossBullet;
import com.ld48.nidjo123.tinygame.entity.Bullet;
import com.ld48.nidjo123.tinygame.entity.Entity;
import com.ld48.nidjo123.tinygame.entity.GreenEnemy;
import com.ld48.nidjo123.tinygame.entity.Player;
import com.ld48.nidjo123.tinygame.Bitmap;

public class TinyGame extends Canvas {
	
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private BufferStrategy bs;
	
	public int[] pixels = new int[WIDTH * HEIGHT];
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
	private BufferedImage img;
	public Random random = new Random();
	boolean focused = true;
	public static int step = 0;
	int score = 0;
	boolean add = true, setThis = true;
	public static boolean bossFight = false;
	private boolean lost = false, won = false;
	private boolean pressedX = false;
	
	public static final TinyGame gameInstance = new TinyGame();
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	private void createWindow() {
		int w = WIDTH * SCALE;
		int h = HEIGHT * SCALE;
		
		Dimension size = new Dimension(w, h);
		
		// setSize(size);
		// setMaximumSize(size);
		// setMinimumSize(size);
		// setPreferredSize(size);
		
		JFrame frame = new JFrame("BeetleVenture");
		JPanel panel = (JPanel) frame.getContentPane();
		
		panel.add(this);
		
		setBounds(0, 0, 470, 470);
		
		frame.setContentPane(panel);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		addKeyListener(new KeyInputHandler());
		
		createBufferStrategy(3);
		
		bs = getBufferStrategy();
		
		requestFocus();
	}
	
	public void gameLoop() {
		createWindow();
		
		bs = getBufferStrategy();
		
		Bitmap endWon = Art.art.endWon;
		Bitmap endLost = Art.art.endLost;
		Bitmap start = Art.art.start;
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		while (!pressedX) {
			if (bs == null) {
				createBufferStrategy(3);
			}
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			Art.art.start.render(0, 0, pixels);
			
			g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, this);
			
			g.dispose();
			bs.show();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Sound.start.play();
		
		boolean running = true;
		
		init();
		
		long now = System.currentTimeMillis();
		long fpsTime = System.currentTimeMillis();
		int fps = 0;
		double timer = 0.;
		double shouldPass = 1000 / 60.0;
		
		String pause = "PAUSED!";
		
		while (running) {
			long passed = System.currentTimeMillis() - now;
			timer += passed;
			
			while (timer > shouldPass) {
				timer -= shouldPass;
				
				focused = isFocusOwner();
				
				if (Player.player.health <= 0) {
					lost = true;
				} else if (Boss.health <= 0) {
					won = true;
				}
				
				if (focused && !won && !lost)
					doLogic();
				
				if (bs == null) {
					createBufferStrategy(3);
				}
				
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				
				g.setColor(Color.black);
				g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
				
				if (!won && !lost)
					Art.art.bg.render(0, 0, pixels);
				
				if (!won && !lost)
					for (int i = 0; i < entities.size(); i++) {
						entities.get(i).render(pixels);
					}
				
				if (won && !lost) {
					endWon.render(0, 0, pixels);
				}
				
				if (lost && !won) {
					endLost.render(0, 0, pixels);
				}
				
				g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, this);
				
				if (!won && !lost) {
					if (!focused) {
						g.setFont(new Font("Arial", Font.BOLD, 22));
						g.drawString(pause, 200, 190);
						g.setFont(new Font("Arial", Font.PLAIN, 12));
					}
					if (bossFight) {
						g.setFont(new Font("Arial", Font.BOLD, 18));
						g.drawString("Boss Health: " + Boss.health, 260, 425);
						g.setFont(new Font("Arial", Font.PLAIN, 12));
					}
					
					int health = Player.player.getHealth();
					
					g.drawString("Score: " + score, 50, 425);
					g.drawString("Health: " + health, 50, 445);
				}
				
				g.dispose();
				bs.show();
				
				if (now - fpsTime >= 1000) {
					System.out.println(fps);
					fpsTime = System.currentTimeMillis();
					fps = 0;
				}
				
				fps++;
				
			}
			
			now = System.currentTimeMillis();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void init() {
		entities.clear();
		entities.add(Player.player);
	}
	
	private void doLogic() {
		if (!focused) {
			Player.player.resetKeys();
		}
		
		if (score >= 300 && setThis) {
			bossFight = true;
			setThis = false;
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				if (!(e instanceof Player)) {
					entities.remove(this);
				}
			}
			
			entities.add(new Boss(204, 100));
		}
		
		if (random.nextInt(40) == 5 && !bossFight) {
			entities.add(new GreenEnemy(240, random.nextInt(150) + 16));
		}
		
		step++;
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).doLogic(gameInstance);
		}
		
		checkCollisions();
		
		entities.removeAll(entitiesToRemove);
	}
	
	private void checkCollisions() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			int x1 = e.getX();
			int y1 = e.getY();
			for (int j = 0; j < entities.size(); j++) {
				Entity e1 = entities.get(j);
				int x2 = e1.getX();
				int y2 = e1.getY();
				if (x1 > x2 && x1 < x2 + 16 && y1 > y2 && y1 < y2 + 16 && !e.getClass().equals(e1.getClass()) && !(e instanceof Boss)
						&& !(e1 instanceof Boss) && !(e instanceof Player) && !(e1 instanceof Player)) {
					if (e instanceof Player && !(e1 instanceof Bullet)) {
						((Player) e).hit();
					} else if (e1 instanceof Player && !(e instanceof Bullet)) {
						((Player) e1).hit();
					}
					if (!(e instanceof Player))
						entitiesToRemove.add(e);
					if (!(e1 instanceof Player))
						entitiesToRemove.add(e1);
					if (!(e instanceof BossBullet) && !(e1 instanceof BossBullet)) {
						score += 10;
					}
					if ((e instanceof GreenEnemy) || (e1 instanceof GreenEnemy)) {
						Sound.hurt.play();
					}
				}
				if (e instanceof Player && x1 > x2 && x1 < x2 + 16 && y1 > y2 && y1 < y2 + 16 && !(e1 instanceof Bullet)) {
					entitiesToRemove.add(e1);
					((Player) e).hit();
				}
				if (e1 instanceof Player && x1 > x2 && x1 < x2 + 16 && y1 > y2 && y1 < y2 + 16 && !(e instanceof Bullet)) {
					entitiesToRemove.add(e);
					((Player) e1).hit();
				}
				if (e instanceof Boss && x1 > x2 && x1 < x2 + 32 && y1 > y2 && y1 < y2 + 32 && !(e1 instanceof BossBullet)) {
					entitiesToRemove.add(e1);
					((Boss) e).hit();
				}
				if (e1 instanceof Boss && x1 > x2 && x1 < x2 + 32 && y1 > y2 && y1 < y2 + 32 && !(e instanceof BossBullet)) {
					entitiesToRemove.add(e);
					((Boss) e1).hit();
				}
			}
		}
	}
	
	public void setLost() {
		lost = true;
	}
	
	public void setWin() {
		won = true;
	}
	
	private class KeyInputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			int num = 3;
			if (e.getKeyCode() == e.VK_UP) {
				Player.player.up = true;
			}
			if (e.getKeyCode() == e.VK_DOWN) {
				Player.player.down = true;
			}
			if (e.getKeyCode() == e.VK_RIGHT) {
				Player.player.right = true;
			}
			if (e.getKeyCode() == e.VK_LEFT) {
				Player.player.left = true;
			}
			if (e.getKeyCode() == e.VK_SPACE) {
				Player.player.shoot = true;
			}
			if (e.getKeyCode() == e.VK_X) {
				pressedX = true;
			}
		}
		
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == e.VK_UP) {
				Player.player.up = false;
			}
			if (e.getKeyCode() == e.VK_DOWN) {
				Player.player.down = false;
			}
			if (e.getKeyCode() == e.VK_RIGHT) {
				Player.player.right = false;
			}
			if (e.getKeyCode() == e.VK_LEFT) {
				Player.player.left = false;
			}
			if (e.getKeyCode() == e.VK_SPACE) {
				Player.player.shoot = false;
			}
		}
		
		public void keyTyped(KeyEvent e) {
		}
	}
	
	public void stop() {
		System.exit(0);
	}
	
	public static void main(String[] args) {
		TinyGame game = new TinyGame();
		game.gameLoop();
	}
}
