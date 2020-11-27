package ex1123_Q10;

public class Block1 extends Block{
	public Block1() {
		this.type=1;

		Cell cell1 = new Cell(0,1);
		Cell cell2 = new Cell(0,0);
		Cell cell3 = new Cell(0,-1);
		Cell cell4 = new Cell(0,-2);

		cells.add(cell1);
		cells.add(cell2);
		cells.add(cell3);
		cells.add(cell4);


	}


}
