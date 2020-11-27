package ex1123_Q10;

import java.util.LinkedList;

public class Block extends Character {

	public Block() {
		this.x= 5;
		this.y= 18;


	}

	//------------------------
	// 属性
	//------------------------
	protected int type = 0;
	LinkedList<Cell> cells = new LinkedList<Cell>();
	//------------------------
	// アクセッサ
	//------------------------
	public int getType() {
		return type;
	}

	//========================
	// メソッド（セルの値取得）
	//========================

	public void moveRight() {
//			for(int i=0;i<cells.size();i++) {
//				Cell cell =cells.get(i);
//				cell.x +=1;
//				int cellx=1+cell.getX();
//				cells.add(cell);
		this.x +=1;


	}

	public void moveLeft() {
		this.x-=1;
	}

	public void moveDown() {
		this.y+=-1;
	}

	public void moveUp() {
		this.y+=1;
	}



	public int getCellSize() {
		int size = cells.size();
		return size;
	}

	public int getCellX(int i) {
		Cell cell =cells.get(i);
		int cellx=x+cell.getX();
		return cellx;
	}

	public int getCellY(int i) {
		Cell cell =cells.get(i);
		int celly=y+cell.getY();
		return celly;
	}
}
