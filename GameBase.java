package ex1123_Q10;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class GameBase extends TimerTask {

	protected static String TITLE = "GameTest";
	protected static int X_LENGTH = 600;
	protected static int Y_LENGTH = 400;
	protected static int Y_INSETS = 0;

	protected static int X_OFFSET = 0;
	protected static int Y_OFFSET = 0;

	protected JFrame frame;
	protected BufferStrategy bs;

	protected Key key;
	protected Mouse mouse;
	protected CG cg;

	// ======================================
	// 初期設定
	// ======================================
	public GameBase() {
		key = new Key();
		mouse = new Mouse();
		cg = new CG();

		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		Y_INSETS = insets.top;
		frame.setSize(X_LENGTH + insets.left + insets.right, Y_LENGTH
				+ insets.top + insets.bottom);
		frame.setLocationRelativeTo(null);
		frame.setIgnoreRepaint(true);

		frame.addKeyListener(key);
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);

		frame.createBufferStrategy(2);
		bs = frame.getBufferStrategy();
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Timer t = new Timer();
		t.scheduleAtFixedRate(this, 0, 30);
	}

	// ======================================
	// タイマーによる繰り返し
	// ======================================
	public void run() {
		mouse.clear();

		cg.g = bs.getDrawGraphics();

		try {
			play();
		} catch (Exception e) {
		}

		cg.g.dispose();
		bs.show();
	}

	// ======================================
	// ゲーム用オーバーライド関数
	// ======================================
	protected void initialize() throws Exception {
	}

	protected void play() throws Exception {
	}

	// #######################################################
	// VIEW: KEY クラス
	// #######################################################
	public class Key implements KeyListener {
		private final int KEY_SIZE = 256;
		private boolean pressFlag = false;
		private boolean releaseKey = true;
		private int currentCode = 0;
		private boolean[] codes = new boolean[KEY_SIZE];

		private void press(KeyEvent ev) {
			currentCode = ev.getKeyCode();
			codes[ev.getKeyCode()] = true;
			if (!pressFlag) {
				pressFlag = true;
			}
		}

		private void release(KeyEvent ev) {
			pressFlag = false;
			codes[ev.getKeyCode()] = false;
			for (int i = 0; i < codes.length; i++) {
				if (codes[i]) {
					currentCode = i;
					return;
				}
			}
			currentCode = 0;
			return;
		}

		public int getCode() {
			return currentCode;
		}

		public boolean isPress(int code) {
			return codes[code];
		}

		public boolean isType(int code) {
			if (currentCode != 0) {
				if (releaseKey == true) {
					boolean result = codes[code];
					if (result) {
						releaseKey = false;
						return result;
					}
				}
			} else {
				releaseKey = true;
			}

			return false;
		}

		// --------------------------------------
		// 以下、リスナーインタフェース用
		// --------------------------------------
		public void keyPressed(KeyEvent ev) {
			press(ev);
		}

		public void keyReleased(KeyEvent ev) {
			release(ev);
		}

		public void keyTyped(KeyEvent ev) {
		}
	}

	//	// #######################################################
	//	// VIEW: KeyInput クラス
	//	// #######################################################
	//	public class KeyInput extends Key implements KeyListener {
	//		private int oldKey=0;
	//		private boolean releaseKey = true;
	//
	//		public boolean isType(int code) {
	//			if(getCode() != 0) {
	//				if(releaseKey==true) {
	//					releaseKey=false;
	//					return getCode() == code;
	//				}
	//				releaseKey=false;
	//			}else {
	//				oldKey=0;
	//				releaseKey =true;
	//			}
	//
	//			return false;
	//		}
	//
	//	}

	// #######################################################
	// VIEW: MOUSE クラス
	// #######################################################
	protected class Mouse implements MouseListener, MouseMotionListener {
		private int rcount = 0;
		private int lcount = 0;
		private int x = 0;
		private int y = 0;
		private int r = 0;
		private int l = 0;

		private void clear() {
			rcount--;
			if (rcount < 0) {
				r = 0;
			}
			lcount--;
			if (lcount < 0) {
				l = 0;
			}
		}

		private void move(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		private void click(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				lcount = 1;
				l = e.getClickCount();
				break;
			case MouseEvent.BUTTON3:
				rcount = 1;
				r = e.getClickCount();
				break;
			}
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getRightClick() {
			return r;
		}

		public int getLeftClick() {
			return l;
		}

		// --------------------------------------
		// 以下、リスナーインタフェース用
		// --------------------------------------
		public void mouseDragged(MouseEvent e) {
			mouse.move(e);
		}

		public void mouseMoved(MouseEvent e) {
			mouse.move(e);
		}

		public void mouseClicked(MouseEvent e) {
			mouse.click(e);
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {

		}

	}

	// #######################################################
	// VIEW: CGクラス
	// #######################################################
	public class CG {
		protected Graphics g;

		// ======================================
		// 画像読み込み用関数
		// ======================================
		public BufferedImage loadImage(String fileName) {
			try {
				return ImageIO.read(getClass().getResource(fileName));
			} catch (IOException e) {
				return null;
			}
		}

		// ======================================
		// 描画用ラップ関数
		// ======================================

		// 色
		protected void setColor(Color color) {
			g.setColor(color);
		}

		protected Color getColor() {
			return g.getColor();
		}

		// フォント
		protected void setFont(Font font) {
			g.setFont(font);
		}

		protected Font getFont() {
			return g.getFont();
		}

		protected void setStroke(float stroke) {
			Graphics2D g2 = (Graphics2D) g;
			BasicStroke wideStroke = new BasicStroke(stroke);
			g2.setStroke(wideStroke);
		}

		// y座標変換
		protected int translate(int y, int yLength) {
			return Y_LENGTH + Y_INSETS - yLength - y;
		}

		// 直線
		protected void drawLine(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawLine(x, y, xLength, yLength);
		}

		// 曲線
		protected void drawArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawArc(x, translate(y, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		// 曲線（塗りつぶし）
		protected void fillArc(int x, int y, int xLength, int yLength,
				int startAngle, int arcAngle) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.fillArc(x, translate(y, yLength), xLength, yLength, startAngle,
					arcAngle);
		}

		// 長方形
		protected void drawRect(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawRect(x, translate(y, yLength), xLength, yLength);
		}

		// 長方形（塗り潰し）
		protected void fillRect(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.fillRect(x, translate(y, yLength), xLength, yLength);
		}

		// 長方形（白に塗りつぶし）
		protected void clearRect(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.clearRect(x, translate(y, yLength), xLength, yLength);
		}

		// （角の丸い）長方形
		protected void drawRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawRoundRect(x, translate(y, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		// （角の丸い）長方形（塗りつぶし）
		protected void fillRoundRect(int x, int y, int xLength, int yLength,
				int arcxLength, int arcyLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.fillRoundRect(x, translate(y, yLength), xLength, yLength,
					arcxLength, arcyLength);
		}

		// 円
		protected void drawOval(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawOval(x, translate(y, yLength), xLength, yLength);
		}

		// 円（塗りつぶし）
		protected void fillOval(int x, int y, int xLength, int yLength) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.fillOval(x, translate(y, yLength), xLength, yLength);
		}

		// 文字列（左寄せ表示）
		protected void drawStringLeft(int x, int y, String str) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawString(str, x, translate(y, 0));
		}

		// 文字列（右寄せ表示）
		protected void drawStringRight(int x, int y, String str) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawString(str, x - getStringWidth(str),
					translate(y, 0));
		}

		// 文字列（センタリング表示）
		protected void drawStringCenter(int x, int y, String str) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawString(str, x - getStringWidth(str) / 2,
					translate(y, 0));
		}

		// 文字列の横幅（ドット数）取得
		protected int getStringWidth(String str) {
			return g.getFontMetrics().stringWidth(str);
		}

		// 文字列の縦幅（ドット数）取得
		protected int getStringHeight(String str) {
			return g.getFontMetrics().getHeight();
		}

		// 画像
		protected void drawImage(int x, int y, BufferedImage img) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawImage(img, x, translate(y, img.getHeight()), frame);
		}

		// 画像（サイズ指定）
		protected void drawImage(int x, int y, int xLength, int yLength,
				BufferedImage img) {
			x += X_OFFSET;
			y += Y_OFFSET;
			g.drawImage(img, x, translate(y, yLength), xLength, yLength, frame);
		}
	}

}
