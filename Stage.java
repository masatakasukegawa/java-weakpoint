package ex1123_Q10;

public class Stage {

	private int xsize = 10;
	private int ysize = 20;
	//=======================================
	// メソッド（座標（x,y)のセルの値・取得）
	//=======================================
	public int getType(int x, int y) {
		return 0;
	}

	protected boolean isGetOffStage(Block block) {

		for(int i=0;i< block.getCellSize();i++) {
			if(0>block.getCellX(i) || block.getCellX(i)>9) {
				return true;
			}
			if(0>block.getCellY(i) || block.getCellY(i)>19) {
				return true;
			}

		}
		return false;

//		if( 0<=block.x && block.x<xsize && 0<=block.y && block.y<ysize) {
//					return true;
//				}else {
//					return false;
//				}
	}

	public boolean isDownable(Block block){

		block.moveDown();
		if(isGetOffStage(block)) {
			block.moveUp();
			return false;
		}else {
			block.moveUp();
			return true;
		}

	}

	public boolean isMovableToLeft(Block block){
		block.moveLeft();
		if(isGetOffStage(block)) {
			block.moveRight();
			return false;
		}else {
			block.moveRight();
			return true;
		}
	}
	public boolean isMovableToRight(Block block){

		block.moveRight();
		if(isGetOffStage(block)) {
			block.moveLeft();
			return false;
		}else {
			block.moveLeft();
			return true;
		}
	}
}
