package guiClass;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class calculateCordinate {
	private ArrayList<Line> lines= new ArrayList<Line>();
	private ArrayList<StageClockCycle> ClockPerStage=new ArrayList<StageClockCycle>();
	private int [] Instruction_1=new int[8];
	private ArrayList<String> InstructionStalls_1=new ArrayList<String>();
	private int [] Instruction_2=new int[8];
	private ArrayList<String> InstructionStalls_2=new ArrayList<String>();
	private ArrayList<String>Forwarding=new ArrayList<String>();
	private ArrayList<Color> StageColors=new ArrayList<Color>();
	private ArrayList<ArrayList<StageClockCycle>> table = new ArrayList<ArrayList<StageClockCycle> >();
	
	
	calculateCordinate(){
		StageColors.add(new Color(125, 167, 116));
		StageColors.add(new Color(76, 153, 0));
		StageColors.add(new Color(255, 153, 31));
		StageColors.add(new Color(204, 0, 0));
		StageColors.add(new Color(102, 255, 102));
		StageColors.add(new Color(255, 255, 102));
		StageColors.add(new Color(153, 204, 255));
		StageColors.add(new Color(51, 51, 255));
		
	}
	public ArrayList<String> getInstructionStalls_2() {
		return InstructionStalls_2;
	}
	public void setInstructionStalls_2(ArrayList<String> instructionStalls_2) {
		InstructionStalls_2 = instructionStalls_2;
	}

	public ArrayList<StageClockCycle> getClockPerStage() {
		return ClockPerStage;
	}
	
	public void setClockPerStage(ArrayList<StageClockCycle> clockPerStage) {
		ClockPerStage = clockPerStage;
	}
	
	public int getInitialX() {
		return initialX;
	}
	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}
	public int getInitialY() {
		return initialY;
	}
	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}
	private int initialX;
	private int initialY;
	public ArrayList<Line> getLines() {
		return lines;
	}
	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public ArrayList<String> getInstructionStalls_1() {
		return InstructionStalls_1;
	}
	public void setInstructionStalls_1(ArrayList<String> instructionStalls_1) {
		InstructionStalls_1 = instructionStalls_1;
	}
	public ArrayList<String> getForwarding() {
		return Forwarding;
	}
	public void setForwarding(ArrayList<String> forwarding) {
		Forwarding = forwarding;
	}
	public void readInput(String file){
		
			System.out.println(file);
			String[] ClockInfo=file.split("FWD");
			String[] ToParse = ClockInfo[0].split(",");
			String [] Forward;
			if(ClockInfo.length>1){
				Forward=ClockInfo[1].split(",");
				for (String x: Forward){
					Forwarding.add(x);
				}
			}
			int InstNum;
			int stage;
			String Stall;
			String [] OneClockCycleOfInst;
			for(String x : ToParse){
				OneClockCycleOfInst = x.split(":");
				InstNum=Integer.parseInt(OneClockCycleOfInst[0]);
				stage=Integer.parseInt(OneClockCycleOfInst[1]);
				Stall= OneClockCycleOfInst[1];
				if(table.size()<InstNum){
					ArrayList<StageClockCycle> list  = new ArrayList<StageClockCycle>();
					table.add(list);
				}
				StageClockCycle s= new StageClockCycle();
				s.setNumberOfClockCycle(1);
				s.setStartX(initialX);
				s.setStartY(initialY+25*InstNum);
				s.setStage(stage);
				s.setInstNo(InstNum);
				s.setColor(StageColors.get(stage-1));
				s.setTypeOfStalls(OneClockCycleOfInst[2]);			
				table.get(InstNum-1).add(s);
				ClockPerStage.add(s);
				
			}
			initialX=initialX+45;
			CalculateLine();
				
	}
	
	public void CalculateLine(){
	
		
		
		for (String x : Forwarding){
				String ForwardingBetween[]=x.split(":");
				int FromInstNum = Integer.parseInt(ForwardingBetween[0]);
				int FromStage	= Integer.parseInt(ForwardingBetween[1]);
				int ToInstNum	= Integer.parseInt(ForwardingBetween[2]);
				int ToStage	= Integer.parseInt(ForwardingBetween[3]);
				ArrayList<StageClockCycle> FirstInst=table.get(FromInstNum-1);
				int len1=FirstInst.size();
				ArrayList<StageClockCycle> SecondInst=table.get(ToInstNum-1);
				int len2=SecondInst.size();
				int X1=0,X2=0,Y1=0,Y2=0;
				for(int i=len1-1;i>=0;i--){
					StageClockCycle s =FirstInst.get(i);
					if(s.getStage()==FromStage){
						X1=s.getStartX()+20;
						Y1=s.getStartY()+10;
					}
				}
				for(int i=len2-1;i>=0;i--){
					StageClockCycle s =SecondInst.get(i);
					if(s.getStage()==ToStage){
						X2=s.getStartX()+15;
						Y2=s.getStartY()+7;
					}
				}
				Line l =new Line();
				Point p1=new Point(X1,Y1);
				Point p2=new Point(X2,Y2);
				l.setP1(p1);
				l.setP2(p2);
				lines.add(l);

		}

	}
}
