import java.awt.*;
public class TraficLight{
  
  private boolean green = true;
  private int period;
  private int stepCount = 0;
  private int greenTime;
  
  public void stepCount(){
    stepCount++;
    if(stepCount%period == 0)
      green = true;
    if(stepCount%period == greenTime)
      green = false;
  }
  
  
  
  public boolean isGreen(){
    return green;
  }
  
  public TraficLight(int greenTime, int period){
    this.greenTime = greenTime;
    this.period = period;
  }
  
  public void paint(Graphics g, int x, int y, int size){
    Color fgColor;
    Color bgColor;
    if(green){
      fgColor = new Color(50,255,50);
      bgColor = new Color(10, 205, 10);
    }
    else{
      fgColor = new Color(255,50,50);
      bgColor = new Color(205, 10, 10);
    }
    int sizeDiv = size/5;
    g.setColor(fgColor);
    g.fillOval(x, y, size, size);
    
    g.setColor(bgColor);
    g.fillOval(x+sizeDiv, y+sizeDiv, size-(2*sizeDiv), size-(2*sizeDiv));
    
    g.setColor(fgColor);
    g.fillOval(x+(2*sizeDiv), y+(2*sizeDiv), sizeDiv, sizeDiv);
  }
  
}
