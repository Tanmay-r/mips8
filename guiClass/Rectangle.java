package guiClass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Rectangle extends JPanel{
	
	
	
	private calculateCordinate instructionPipeLine=new calculateCordinate();
	
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
	private ArrayList<StageClockCycle> Stages;
	private Color stall_color=new Color(125, 167, 116);
	private ArrayList<String> stageName =new  ArrayList<String>();
	private boolean forwarding;
	Rectangle(){
		instructionPipeLine.setInitialX(10);
		instructionPipeLine.setInitialY(10);
		stageName.add("IF1");
		stageName.add("IF2");
		stageName.add("ID");
		stageName.add("EX");
		stageName.add("M1");
		stageName.add("M2");
		stageName.add("M3");
		stageName.add("WB");

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

	public int getY_Cord() {
		return y_Cord;
	}

	
	
	
	
	
	private int x_Cord;
	private int y_Cord;
	public int getX_Cord() {
		return x_Cord;
	}

	public void setX_Cord(int x_Cord) {
		this.x_Cord = x_Cord;
	}

	public int getY_cord() {
		return y_Cord;
	}

	public void setY_Cord(int y_Cord) {
		this.y_Cord = y_Cord;
	}

	private void drawAll(Graphics2D g2d){
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("/home/alok29/cs305/8_STAGE_MIPS_PIPELINE/src/guiClass/input.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				draw(g2d,sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void draw(Graphics2D g2d,String str){
		drawRectangles(str);
		System.out.println("Here");
		int offset=0;
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
    	   System.out.println(X+"::"+Y);
    	   int NoOfCycle=x.getNumberOfClockCycle();
    	   for(int i=0;i<NoOfCycle;i++){
    		   drawOneClockCycle(g2d,X,Y,x.getColor(),Stage_Name);
    		   offset++;
    	   }
       }
		for( Line l : lines){
			System.out.println("X1 :"+l.getP1().x+" Y1 :"+l.getP1().y+" X2 :"+l.getP2().x+" Y2:"+l.getP2().y);
			drawArrow(g2d,l.getP1().x,l.getP1().y,l.getP2().x,l.getP2().y);
		}
      // instructionPipeLine.getClockPerStage().clear();
		
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
	private void drawRectangles(String TwoInstructionLines){
		instructionPipeLine.readInput(TwoInstructionLines);
		//instructionPipeLine.Calculate();
		lines=instructionPipeLine.getLines();
		Stages=instructionPipeLine.getClockPerStage();
		
		
	}
	
	private void drawFirstInst(Graphics2D g2d){

		
	}
	
	private void doDrawing(Graphics g) {
	        g.setFont(font);
	        Graphics2D g2d = (Graphics2D) g;

	        drawAll(g2d);
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        
	        super.paintComponent(g);
	        doDrawing(g);
	    }
}
