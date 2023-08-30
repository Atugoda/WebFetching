import javax.swing.*;
import java.awt.*;
    public class Main {
public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Applet Example");
             MyFirstApplet applet = new MyFirstApplet();


             applet.init();
             applet.start();
             applet.setPreferredSize(new Dimension(400, 300));

             frame.getContentPane().add(applet);
             frame.pack();
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setVisible(true);
             });
         }
}
