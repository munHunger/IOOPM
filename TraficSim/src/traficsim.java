import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class traficsim extends JPanel implements MouseListener{
  
  private JFrame frame = new JFrame("Traficsim");
  
  private Road r1 = new Road(10, 50, 50, 3, 0, null);
  
  public traficsim(){
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    this.addMouseListener(this);
    frame.add(this);
    
    Road r2 = new Road(10, 50, 250, 3, 0, new TraficLight(3,10));
    Road r3 = new Road(10, 75, 250, 3, -1,new TraficLight(3,10));
    r1.addChild(r2);
    r1.addChild(r3);
    
    Road r4 = new Road(7, 100, 450, 2, 0, null);
    Road r5 = new Road(7, 50, 450, 3, 0, null);
    r3.addChild(r4);
    r2.addChild(r5);
    
    Road r6 = new Road(4, 75, 590, 3, -1, new TraficLight(3,10));
    Road r7 = new Road(4, 50, 590, 3, 0, new TraficLight(3,10));
    r5.addChild(r6);
    r5.addChild(r7);
    
    Road r8 = new Road(6, 50, 690, 3, 0, null);
    r7.addChild(r8);
    
    Road r9 = new Road(8, 100, 670, 2, 0, null);
    r6.addChild(r9);
    
    Road r10 = new Road(8, 260, 640, 2, -1, new TraficLight(5,10));
    Road r11 = new Road(8, 260, 670, 2, 0, new TraficLight(5,10));
    Road r12 = new Road(8, 260, 700, 2, 1, new TraficLight(5,10));
    r9.addChild(r10);
    r9.addChild(r11);
    r9.addChild(r12);
    
    Road r13 = new Road(4, 450, 730, 3, 0, null);
    r12.addChild(r13);
    
    Road r14 = new Road(4, 450, 670, 2, 0, null);
    r11.addChild(r14);
    
    Road r15 = new Road(6, 540, 700, 3, 0, null);
    r14.addChild(r15);
    
    Road r16 = new Road(5, 450, 630, 1, 0, null);
    r10.addChild(r16);
    
    Road r17 = new Road(5, 480, 500, 2, 0, null);
    r16.addChild(r17);
    
    Road r18 = new Road(14, 590, 530, 3, 0, null);
    r17.addChild(r18);
    
    
    Road r19 = new Road(8, 240, 390, 2, -1, new TraficLight(5,10));
    Road r20 = new Road(8, 240, 420, 2, -1, new TraficLight(5,10));
    Road r21 = new Road(8, 240, 450, 2, 0, new TraficLight(5,10));
    r4.addChild(r19);
    r4.addChild(r20);
    r4.addChild(r21);
    
    Road r22 = new Road(10, 430, 450, 2, 0, null);
    r21.addChild(r22);
    
    Road r23 = new Road(8, 430, 380, 1, 0, null);
    r19.addChild(r23);
    
    Road r24 = new Road(8, 460, 380, 1, 0, null);
    r20.addChild(r24);
    
    Road r25 = new Road(8, 460, 220, 1, 0, null);
    r23.addChild(r25);
    r24.addChild(r25);
    
    this.repaint();
  }
  
  
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    this.setBackground(Color.BLACK);
    r1.step();
    r1.paint(g);
    
    if((int)(Math.random()*2) == 0)
      r1.addCar(new Car(r1));
    
    try{
      Thread.sleep(200);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    this.repaint();
  }
  
  public static void main(String args[]){
    new traficsim();
  }
  
  
  @Override
  public void mouseClicked(MouseEvent e) {
    r1.onClick(e.getX(), e.getY());
  }
  
  
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  
  
  @Override
  public void mouseExited(MouseEvent e) {
  }
  
  
  @Override
  public void mousePressed(MouseEvent e) {
  }
  
  
  @Override
  public void mouseReleased(MouseEvent e) {
  }
  
  
}
