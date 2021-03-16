package python.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class PythonGame extends JFrame{

    public static void main(String[] args) {
        PythonGame object1 = new PythonGame();
        GamePlay object2 = new GamePlay();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        
        //0, 0, 905, 700
        object1.setBounds((width-905)/2, 0, 905, 700);
        object1.setResizable(false);
        object1.setVisible(true);
        object1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        object1.add(object2);
    }
    
}
