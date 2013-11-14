package arch.mips8.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import arch.mips8.Globals;
import arch.mips8.Register;

public class DrawPanel extends JPanel {

	private Color stage1 = new Color(125, 167, 116);
	private Color stage2 = new Color(125, 167, 116);
	private Color stage3 = new Color(125, 167, 116);
	private Color stage4 = new Color(125, 167, 116);
	private Color stage5 = new Color(125, 167, 116);
	private Color stage6 = new Color(125, 167, 116);
	private Color stage7 = new Color(125, 167, 116);
	private Color stage8 = new Color(125, 167, 116);
	private ArrayList<Line> lines;
	private Font font = new Font("Dialog", Font.PLAIN, 12);
	Font Instfont = new Font("Courier", Font.BOLD, 14);
	private ArrayList<StageClockCycle> Stages = null;
	ArrayList<ArrayList<StageClockCycle>> table=null;
	private Color stall_color = new Color(125, 167, 116);
	private ArrayList<String> stageName = new ArrayList<String>();
	private String InputToDraw;
	private boolean forwarding;
	private boolean drawEnable;
	private int CurrentCycleX;
	private int CurrentCycleY;
	private RegisterPanel registersGui = new RegisterPanel();

	private MemoryPanel memoryGui = new MemoryPanel();

	public RegisterPanel getRegistersGui() {
		return registersGui;
	}

	public void setRegistersGui(RegisterPanel registersGui) {
		this.registersGui = registersGui;
	}

	public int getCurrentCycleX() {
		return CurrentCycleX;
	}

	public void setCurrentCycleX(int currentCycleX) {
		CurrentCycleX = currentCycleX;
	}

	public int getCurrentCycleY() {
		return CurrentCycleY;
	}

	public void setCurrentCycleY(int currentCycleY) {
		CurrentCycleY = currentCycleY;
	}

	public boolean isDrawEnable() {
		return drawEnable;
	}

	public void setDrawEnable(boolean drawEnable) {
		this.drawEnable = drawEnable;
	}

	public String getInputToDraw() {
		return InputToDraw;
	}

	public void setInputToDraw(String inputToDraw) {
		InputToDraw = inputToDraw;
	}

	public DrawPanel() {
		InputToDraw = "";
		drawEnable = false;
		init();
		stageName.add("IF");
		stageName.add("IS");
		stageName.add("ID");
		stageName.add("EX");
		stageName.add("DF");
		stageName.add("DS");
		stageName.add("TC");
		stageName.add("WB");

	}

	public void init() {
		Globals.instructionPipeLine.setInitialX(200);
		Globals.instructionPipeLine.setInitialY(10);
		CurrentCycleX = 200;
		CurrentCycleY = 10;

	}

	public Color getStage1() {
		return stage1;
	}

	public void setStage1(Color stage1) {
		this.stage1 = stage1;
	}

	public Color getStage2() {
		return stage2;
	}

	public void setStage2(Color stage2) {
		this.stage2 = stage2;
	}

	public Color getStage3() {
		return stage3;
	}

	public void setStage3(Color stage3) {
		this.stage3 = stage3;
	}

	public Color getStage4() {
		return stage4;
	}

	public void setStage4(Color stage4) {
		this.stage4 = stage4;
	}

	public Color getStage5() {
		return stage5;
	}

	public void setStage5(Color stage5) {
		this.stage5 = stage5;
	}

	public Color getStage6() {
		return stage6;
	}

	public void setStage6(Color stage6) {
		this.stage6 = stage6;
	}

	public Color getStage7() {
		return stage7;
	}

	public void setStage7(Color stage7) {
		this.stage7 = stage7;
	}

	public Color getStage8() {
		return stage8;
	}

	public void setStage8(Color stage8) {
		this.stage8 = stage8;
	}

	public Color getStall_color() {
		return stall_color;
	}

	public void setStall_color(Color stall_color) {
		this.stall_color = stall_color;
	}

	public boolean isForwarding() {
		return forwarding;
	}

	public void setForwarding(boolean forwarding) {
		this.forwarding = forwarding;
	}

	private void drawAll(Graphics2D g2d) {
		draw(g2d);

	}

	private void draw(Graphics2D g2d) {
		if (Stages != null && Stages.size() != 0) {
			g2d.setFont(font);
			int size = Stages.size();
			CurrentCycleX = Stages.get(size - 1).getStartX();
			// System.out.println("Current X in rect " + CurrentCycleX);
			CurrentCycleY = Stages.get(size - 1).getStartY();
			g2d.setColor(new Color(200, 200, 200));
			g2d.drawRect(CurrentCycleX - 2, 0, 42,
					Math.max(700, CurrentCycleY + 100));
			g2d.setColor(new Color(200, 200, 0));
			g2d.fillRect(CurrentCycleX, 0, 40,
					Math.max(700, CurrentCycleY + 100));
			g2d.setColor(new Color(255, 255, 255));
			g2d.fillRect(0, 0, 200, Math.max(700, CurrentCycleY + 100));
			for(ArrayList<StageClockCycle> l : table)
				for (StageClockCycle x : l) {
					int X = x.getStartX();
					int Y = x.getStartY();
					String Stage_Name;
					int stage = x.getStage();
					String TypeOfStall = x.getTypeOfStalls();
					if (TypeOfStall.equals("N")) {
						Stage_Name = stageName.get(stage - 1);
					} else {
						Stage_Name = TypeOfStall;
					}
					drawOneClockCycle(g2d, X, Y, x.getColor(), Stage_Name);
				}

			for (Line l : lines) {
				
				drawArrow(g2d, l.getP1().x, l.getP1().y, l.getP2().x,
						l.getP2().y);
			}
			drawInstruction(g2d);
			registersGui.repaint();
			memoryGui.repaint();
		}
		Globals.instructionPipeLine.getForwarding().clear();

	}

	private void drawInstruction(Graphics2D g2d) {
		ArrayList<String> instructionList = Globals.instructionPipeLine
				.getInstructionList();
		int YOfInst = 35;
		g2d.setFont(Instfont);
		g2d.setPaint(Color.black);
		g2d.drawString("INSTRUCTIONS", 40, 20);
		g2d.setColor(new Color(0, 0, 0));
		g2d.setPaint(Color.blue);
		for (String instr : instructionList) {
			g2d.drawString(instr, 20, YOfInst + 17);
			YOfInst = YOfInst + 25;

		}

	}

	private void drawOneClockCycle(Graphics2D g2d, int X, int Y, Color color,
			String Stage) {
		g2d.setColor(new Color(212, 212, 212));
		g2d.drawRect(X, Y, 40, 20);
		g2d.setColor(color);
		g2d.fillRect(X, Y, 40, 20);
		g2d.setColor(new Color(0, 0, 0));
		g2d.drawString(Stage, X, Y + 16);

	}

	void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - 5, len - 5, len }, new int[] { 0,
				-5, 5, 0 }, 4);
	}

	public void calculateUpdate() {

		lines = Globals.instructionPipeLine.getLines();
		Stages = Globals.instructionPipeLine.getClockPerStage();
		table= Globals.instructionPipeLine.getTable();

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Math.max(1150, CurrentCycleX + 100), Math.max(700,
				CurrentCycleY + 100));
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		drawAll(g2d);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (drawEnable) {
			calculateUpdate();
			drawEnable = !drawEnable;
		}
		doDrawing(g);
	}

	public void resetAll() {
		init();
		if (Stages != null && lines != null) {
			Stages.clear();
			lines.clear();
		}
		Globals.instructionPipeLine.init();
		registersGui.repaint();
		memoryGui.repaint();
		repaint();
	}

	public class RegisterPanel extends JPanel {

		RegisterPanel() {

		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			drawRegister(g);
		}

		public void drawRegister(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			int YOfInst = 35;
			g2d.setFont(Instfont);
			String registersNames[] = "$zero, $at, $v0, $v1, $a0, $a1, $a2, $a3, $t0, $t1, $t2, $t3, $t4, $t5, $t6, $t7, $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7, $t8, $t9, $k0, $k1, $gp, $sp, $fp, $ra, pc, hi, lo"
					.split(",");
			for (String reg : registersNames) {
				Register r = Globals.getRegister(reg.trim());
				g2d.setPaint(Color.black);
				g2d.drawString(r.name, 20, YOfInst + 17);
				g2d.setPaint(Color.blue);
				int a =(int) r.content;
				g2d.drawString("0x" + Integer.toHexString(a), 80, YOfInst + 17);

				YOfInst = YOfInst + 25;

			}

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, Math.max(950, CurrentCycleY + 100));
		}
	}

	public class MemoryPanel extends JPanel {

		
		private int StartIndex;
		private int EndIndex;
		private boolean HexOrDec;
		private String StackOrData;
		
		

		MemoryPanel() {
			Border b = BorderFactory.createLoweredBevelBorder();
			this.setBorder(b);
			StartIndex=0;
			EndIndex=31;
			HexOrDec=true;
			StackOrData="D";
		}
		public int getStartIndex() {
			return StartIndex;
		}

		public void setStartIndex(int startIndex) {
			StartIndex = startIndex;
		}

		public int getEndIndex() {
			return EndIndex;
		}

		public void setEndIndex(int endIndex) {
			EndIndex = endIndex;
		}

		public boolean getHexOrDec() {
			return HexOrDec;
		}

		public void setHexOrDec(boolean hexOrDec) {
			HexOrDec = hexOrDec;
		}

		public String getStackOrData() {
			return StackOrData;
		}

		public void setStackOrData(String stackOrData) {
			StackOrData = stackOrData;
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			drawMemory(g);

		}
		
		public void drawMemory(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(Instfont);
			g2d.setPaint(Color.black);
			g2d.setColor(new Color(0, 0, 0));
			g2d.drawString("ADDRESS", 20, 20);
			for (int i = 0; i < 8; i++) {
				g2d.drawString("ADDRESS+" + i, 150 + i * 110, 20);

			}
			printValue(g2d);


		}

		public void printValue(Graphics2D g2d){
			
			g2d.setPaint(Color.blue);
			if(StackOrData.equals("D")){
				System.out.println("Data memory");
				for(int i=StartIndex;i<=EndIndex;i++){
					Byte b = Globals.memory.dataMemory.memory.get(i);
					int x=i/8;
					int y=i%8;
					if(y==0){
						g2d.drawString(format(i), 20, 40+(x-StartIndex/8)*20);
					}
					g2d.drawString(format(b.intValue()), 150 + y * 110, 40+(x-StartIndex/8)*20);
				}
			}
			else{
				for(int i=StartIndex;i<=EndIndex;i++){
					Byte b = Globals.memory.stackMemory.memory.get(i);
					int x=i/8;
					int y=i%8;
					if(y==0){
						g2d.drawString(format(i), 20, 40+(x-StartIndex/8)*20);
					}
					g2d.drawString(format(b.intValue()), 150 + y * 110, 40+(x-StartIndex/8)*20);
				}
			}
			
		}
		public String format(int i){
			String value=""; 
			if(HexOrDec)
				value="0x"+Integer.toHexString(i);
			else
				value=Integer.toString(i);
			return value;
			
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, 200);
		}
	}

	public MemoryPanel getMemoryGui() {
		return memoryGui;
	}

	public void setMemoryGui(MemoryPanel memoryGui) {
		this.memoryGui = memoryGui;
	}

}
