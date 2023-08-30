
import javax.swing.*;
 import java.awt.*;

public class MyFirstApplet extends JComponent {
public void paint(Graphics g){
    super.paint(g);
    g.drawString("hello world",50,30);
}

    public void init() {
    }

    public void start() {
    }
}