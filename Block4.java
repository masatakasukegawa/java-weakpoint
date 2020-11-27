package ex1123_Q10;

public class Block4 extends Block{
	public Block4() {
		this.type=4;

		Cell cell1 = new Cell(1,1);
		Cell cell2 = new Cell(-1,0);
		Cell cell3 = new Cell(0,0);
		Cell cell4 = new Cell(1,0);
		Cell cell5 = new Cell(-1,-1);

		cells.add(cell1);
		cells.add(cell2);
		cells.add(cell3);
		cells.add(cell4);
		cells.add(cell5);


	}


}
