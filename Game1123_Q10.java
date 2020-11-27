package ex1123_Q10;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Game1123_Q10 extends GameBase {

	public static void main(String[] args) {
		new Game1123_Q10();
	}

	//=============
	// 変数定義
	//=============
	BufferedImage backGroundImage;

//	LinkedList<Cell> cells;

	Block block;


	Stage stage;

	//=============
	// 初期化処理
	//=============
	@Override
	public void initialize() {
		backGroundImage = cg.loadImage("/img/sky.jpg");


//		switch(この中にランダムに数字をつくる式を入れる。ぐぐればでてくる);
//		case1
		block = new Block2();
//		case2



		stage = new Stage();

	}

	//=============
	// 通常処理
	//=============
	@Override
	public void play() {
		//=============
		// 入力
		//=============

//	if(stage.isGetOffStage(block)==true) {
			if (key.isType(KeyEvent.VK_RIGHT)&& stage.isMovableToRight(block)==true ) {
				block.moveRight();
			} else if (key.isType(KeyEvent.VK_LEFT)&& stage.isMovableToLeft(block)==true ) {
				block.moveLeft();
			} else if (key.isType(KeyEvent.VK_DOWN)&& stage.isDownable(block)==true ) {
				block.moveDown();
			} else if (key.isType(KeyEvent.VK_SPACE)) {
				block.moveUp();
			}
//	}
		//=============
		// 処理
		//=============

		//=============
		// 出力（描画）
		//=============
		drawBackGround();
		drawBlock(block);

	}

	//======================================
	// 描画用関数
	//======================================
	private Color getColor(int type) {
		if (type == 1) {
			return Color.BLUE;
		}
		if (type == 2) {
			return Color.RED;
		}
		if (type == 3) {
			return Color.CYAN;
		}
		if (type == 4) {
			return Color.ORANGE;
		}
		if (type == 5) {
			return Color.PINK;
		}
		if (type == 6) {
			return Color.MAGENTA;
		}
		return Color.BLACK;
	}

	private void drawCell(int x, int y, int type) {
		int size = 18;
		cg.setColor(Color.BLACK);
		cg.drawRect(50 + size * x, 30 + size * y, size, size);
		cg.setColor(getColor(type));
		cg.fillRect(50 + size * x + 1, 30 + size * y + 1, size - 1,
				size - 1);
	}

	private void drawBlock(Block block) {
		int type = block.getType();
		for (int i = 0; i < block.getCellSize(); i++) {
			int x = block.getCellX(i);
			int y = block.getCellY(i);
			drawCell(x, y, type);
		}
	}

	private void drawStage(Stage stage) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				int type = stage.getType(x, y);
				if (type > 0) {
					drawCell(x, y, type);
				}
			}
		}
	}

	private void drawBackGround() {
		cg.drawImage(0, 0, backGroundImage);
		cg.setColor(Color.BLACK);
		cg.fillRect(50, 30, 180, 360);
	}

}
