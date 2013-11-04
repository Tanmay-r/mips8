package guiClass;

import java.awt.Color;
import java.awt.Point;

public class Line {
	private Point p1;
	private Point p2;
	private Color color;
	Line(){
		color=new Color(255,0,0);
	}
	public Point getP1() {
		return p1;
	}
	public void setP1(Point p1) {
		this.p1 = p1;
	}
	public Point getP2() {
		return p2;
	}
	public void setP2(Point p2) {
		this.p2 = p2;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	

}
