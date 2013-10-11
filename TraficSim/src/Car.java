import java.awt.*;
import java.util.*;

public class Car{
  
  private Color fgColor;
  private Color bgColor;
  private Stack<Integer> dest;
  private int time;
  
  public Car(Road root){
    int r = (int)(Math.random()*200)+55;
    int g = (int)(Math.random()*200)+55;
    int b = (int)(Math.random()*200)+55;
    fgColor = new Color(r,g,b);
    bgColor = new Color(r-20, g-20, b-20);
    
    dest = getPath(root, new Stack<Integer>());
  }
  
  public int getTime(){
    return time;
  }
  
  public void step(){
    time++;
  }
  
  public int getNext(){
    return (int)dest.pop();
  }
    
  public void addDest(int d) {
    dest.push(d);
  }
  
  public Stack<Integer> getPath(Road r, Stack<Integer> s){
    ArrayList<Road> children = r.getChildren();
    if(children.size() == 0)
      return getRevStack(s, new Stack<Integer>());
    
    Road n = children.get((int)(Math.random()*children.size()));
    s.push(n.getLaneType());
    return getPath(n,s);
  }
    
  public Stack<Integer> getRevStack(Stack<Integer> s, Stack<Integer> rev){
    if(s.isEmpty())
      return rev;
    else{
      rev.push(s.pop());
      return getRevStack(s, rev);
    }
  }
  
  public void paint(Graphics g, int x, int y, int size){
    int sizeDiv = size/5;
    g.setColor(fgColor);
    g.fillRect(x, y, size, size);
    
    g.setColor(bgColor);
    g.fillRect(x+sizeDiv, y+sizeDiv, size-(2*sizeDiv), size-(2*sizeDiv));
    
    g.setColor(fgColor);
    g.fillRect(x+(2*sizeDiv), y+(2*sizeDiv), sizeDiv, sizeDiv);
  }
}
