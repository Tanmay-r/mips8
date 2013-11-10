package arch.mips8.gui;

import java.awt.Color;

public class StageClockCycle {
	private String TypeOfStalls;
	private int StartX;
	private int StartY;
	private Color color;
	private int stage;
	private int instNo;
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getInstNo() {
		return instNo;
	}
	public void setInstNo(int instNo) {
		this.instNo = instNo;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getTypeOfStalls() {
		return TypeOfStalls;
	}
	public void setTypeOfStalls(String typeOfStalls) {
		TypeOfStalls = typeOfStalls;
	}
	public int getStartX() {
		return StartX;
	}
	public void setStartX(int startX) {
		StartX = startX;
	}
	public int getStartY() {
		return StartY;
	}
	public void setStartY(int startY) {
		StartY = startY;
	}

		

}
