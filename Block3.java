package ex1123_Q10;

public class Block3 extends Block{
	public Block3() {
		this.type=3;

		Cell cell1 = new Cell(0,1);
		Cell cell2 = new Cell(-2,0);
		Cell cell3 = new Cell(-1,0);
		Cell cell4 = new Cell(0,0);

		cells.add(cell1);
		cells.add(cell2);
		cells.add(cell3);
		cells.add(cell4);


	}


}
