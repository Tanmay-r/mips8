package arch.mips8.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{
	
	
	
	private CalculateCordinate instructionPipeLine=new CalculateCordinate();
	
	private Color stage1=new Color(125, 167, 116);
	private Color stage2=new Color(125, 167, 116);
	private Color stage3=new Color(125, 167, 116);
	private Color stage4=new Color(125, 167, 116);
	private Color stage5=new Color(125, 167, 116);
	private Color stage6=new Color(125, 167, 116);
	private Color stage7=new Color(125, 167, 116);
	private Color stage8=new Color(125, 167, 116);
	private ArrayList<Line> lines;
	private Font font=new Font("Dialog", Font.PLAIN, 12);
	private ArrayList<StageClockCycle> Stages=null;
	private Color stall_color=new Color(125, 167, 116);
	private ArrayList<String> stageName =new  ArrayList<String>();
	private String InputToDraw;
	private boolean forwarding;
	private boolean drawEnable;
	private int CurrentCycleX;
	private int CurrentCycleY;
	
	
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
	public DrawPanel(){
		InputToDraw="";
		drawEnable=false;
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
	public void init(){
		instructionPipeLine.setInitialX(200);
		instructionPipeLine.setInitialY(10);
		CurrentCycleX=200;
		CurrentCycleY=10;
		
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
	private void drawAll(Graphics2D g2d){
		draw(g2d);
	
	}
	private void draw(Graphics2D g2d){
		if(Stages!=null && Stages.size()!=0){
				int size=Stages.size();
				CurrentCycleX = Stages.get(size-1).getStartX();
				System.out.println("Current X in rect "+CurrentCycleX);
				CurrentCycleY = Stages.get(size-1).getStartY();
				g2d.setColor(new Color(200, 200, 200));
			    g2d.drawRect(CurrentCycleX-2, 0, 42, 600);
			    g2d.setColor(new Color(200, 200, 0));
			    g2d.fillRect(CurrentCycleX, 0, 40, 600);
			    g2d.setColor(new Color(255, 255, 255));
			    g2d.fillRect(0, 0, 175, 600);
				for(StageClockCycle x : Stages){
		    	   int X =x.getStartX();
		    	   int Y=x.getStartY();
		    	   String Stage_Name;
		    	   int stage = x.getStage();
		    	   String TypeOfStall=x.getTypeOfStalls();
		    	   if(TypeOfStall.equals("N")){
		    		   Stage_Name=stageName.get(stage-1);
		    	   }
		    	   else{
		    		   Stage_Name=TypeOfStall;
		    	   }
		    	   int NoOfCycle=x.getNumberOfClockCycle();
		    	   for(int i=0;i<NoOfCycle;i++){
		    		   drawOneClockCycle(g2d,X,Y,x.getColor(),Stage_Name);
		    	   }
		       }
				
				for( Line l : lines){
					//System.out.println("X1 :"+l.getP1().x+" Y1 :"+l.getP1().y+" X2 :"+l.getP2().x+" Y2:"+l.getP2().y);
					drawArrow(g2d,l.getP1().x,l.getP1().y,l.getP2().x,l.getP2().y);
				}
		}
    instructionPipeLine.getForwarding().clear();
		
	}
	
	private void drawOneClockCycle(Graphics2D g2d,int X ,int Y, Color color,String Stage){
	       g2d.setColor(new Color(212, 212, 212));
	       g2d.drawRect(X, Y, 40, 20);
	       g2d.setColor(color);
	       g2d.fillRect(X, Y, 40, 20);
	       g2d.setColor(new Color(0,0,0));
	       g2d.drawString(Stage,X,Y+20);
	    
		
	}
    void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-5, len-5, len},
                      new int[] {0, -5, 5, 0}, 4);
    }
    
	public void calculateUpdate(String TwoInstructionLines){
		instructionPipeLine.readInput(TwoInstructionLines);
		//instructionPipeLine.Calculate();
		lines=instructionPipeLine.getLines();
		Stages=instructionPipeLine.getClockPerStage();
		
		
	}
	
	@Override
    public Dimension getPreferredSize() {
          return new Dimension(Math.max(800,CurrentCycleX+100), Math.max(600,CurrentCycleY+100));
      }
	private void doDrawing(Graphics g) {
	        g.setFont(font);
	        Graphics2D g2d = (Graphics2D) g;

	        drawAll(g2d);
	}

	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if(drawEnable){
        	calculateUpdate(InputToDraw);
        	drawEnable=!drawEnable;
        }
    	doDrawing(g);
    }
	public void resetAll (){
		init();
		if(Stages!=null && lines!=null ){
			Stages.clear();
			lines.clear();
		}
		instructionPipeLine.init();
		repaint();
	}
	
}
