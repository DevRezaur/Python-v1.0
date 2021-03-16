package python.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    
    private ImageIcon snakeBackground;
    private ImageIcon leftMouth;
    private ImageIcon rightMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private ImageIcon snakeImage;
    private ImageIcon foodImage;
    
    private Timer time;
    private int delay = 100;
    
    private int[] snakeXlenght = new int[750];
    private int[] snakeYlenght = new int[750];
    private int snakeLenght = 3;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private int move = 0;
    private int score = 0;
    private int highScore = 0;
    boolean lock = false;
    
    private int[] foodX = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,
                            475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] foodY = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
                            525,550,575,600,625};
    
    private Random rand = new Random();
    private int Xpos = rand.nextInt(34);
    private int Ypos = rand.nextInt(23);
    
    public GamePlay() {
        time = new Timer(delay, this);
        time.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        highScore = Integer.parseInt(getHighScore()); 
    }
    
    public void paint(Graphics X) {
        if(move==0) {
            snakeXlenght[0] = 100;
            snakeYlenght[0] = 325;
            
            snakeXlenght[1] = 75;
            snakeYlenght[1] = 325;
            
            snakeXlenght[2] = 50;
            snakeYlenght[2] = 325;
        }
        
        X.setColor(Color.black);
        X.fillRect(0, 0, 905, 700);
        
        X.setColor(Color.white);
        X.drawRect(25, 74, 851, 577);
        snakeBackground = new ImageIcon("data/background.jpg");
        snakeBackground.paintIcon(this, X, 26, 75);
        
        X.drawRect(24, 10, 210, 55);
        X.setFont(new Font("Arial", Font.BOLD, 30));
        X.drawString("P Y T H O N", 47, 40);
        X.setFont(new Font("Arial", Font.PLAIN, 15));
        X.drawString("Version 1.0", 92, 60);
        
        X.drawRect(260, 10, 275, 55);
        X.setFont(new Font("arial", Font.BOLD, 20));
        X.drawString("Developed By", 275, 35);
        X.drawString("Rezaur Rahman", 370, 55);
               
        X.drawRect(560, 10, 160, 55);
        X.setFont(new Font("arial", Font.BOLD, 15));
        X.drawString("High Score: " +highScore, 580, 43);
        
        X.drawRect(745, 10, 130, 55);
        X.setFont(new Font("arial", Font.PLAIN, 14));
        X.drawString("Score: " +score, 780, 35);
        X.drawString("Lenght: " +snakeLenght, 780, 50);
        
        rightMouth = new ImageIcon("data/rightmouth.png");
        rightMouth.paintIcon(this, X, snakeXlenght[0], snakeYlenght[0]);
        
        for(int i=0; i<snakeLenght; i++) {
            if(i==0 && right) {
                rightMouth = new ImageIcon("data/rightmouth.png");
                rightMouth.paintIcon(this, X, snakeXlenght[0], snakeYlenght[0]);
            }
            if(i==0 && left) {
                leftMouth = new ImageIcon("data/leftmouth.png");
                leftMouth.paintIcon(this, X, snakeXlenght[0], snakeYlenght[0]);
            }
            if(i==0 && up) {
                upMouth = new ImageIcon("data/upmouth.png");
                upMouth.paintIcon(this, X, snakeXlenght[0], snakeYlenght[0]);
            }
            if(i==0 && down) {
                downMouth = new ImageIcon("data/downmouth.png");
                downMouth.paintIcon(this, X, snakeXlenght[0], snakeYlenght[0]);
            }
            if(i!=0) {
                snakeImage = new ImageIcon("data/snakeimage.png");
                snakeImage.paintIcon(this, X, snakeXlenght[i], snakeYlenght[i]);
            }
        }
        
        foodImage = new ImageIcon("data/food.png");
        foodImage.paintIcon(this, X, foodX[Xpos], foodY[Ypos]);
        
        if(foodX[Xpos] == snakeXlenght[0] && foodY[Ypos] == snakeYlenght[0]) {
            score++;
            snakeLenght++;
            Xpos = rand.nextInt(34);
            Ypos = rand.nextInt(23);
        }
        
        for(int i=1; i<snakeLenght; i++) {
            if(snakeXlenght[0] == snakeXlenght[i] && snakeYlenght[0] == snakeYlenght[i]) {
                
                lock = true;
                left = false;
                right = false;
                up = false;
                down = false;
                move = 0;
                snakeLenght = 3;
                
                X.setColor(Color.white);
                X.setFont(new Font("arial", Font.BOLD, 50));
                X.drawString("GAME OVER", 300, 300);
                X.setFont(new Font("arial", Font.BOLD, 30));
                X.drawString("Press ENTER to restart", 285, 350);
                
                if(score> highScore)setHighScore();
            }
        }
        
        X.dispose();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT && lock == false) {
            move++;
            if(!left) right = true;
            up = false;
            down = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT && lock == false && move!=0) {
            move++;
            if(!right) left = true;
            up = false;
            down = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_UP && lock == false) {
            move++;
            if(!down) up = true;
            left = false;
            right = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_DOWN && lock == false) {
            move++;
            if(!up) down = true;
            left = false;
            right = false;
        }
        if(ke.getKeyCode() == KeyEvent.VK_ENTER && lock == true) {
            lock = false;
            score = 0;
            highScore = Integer.parseInt(getHighScore()); 
            repaint();
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(right) {
            for(int i = snakeLenght-1; i>0; i--) {
                snakeYlenght[i] = snakeYlenght[i-1];
            }
            for(int i = snakeLenght-1; i>=0; i--) {
                if(i==0) {
                    snakeXlenght[i] = snakeXlenght[i]+25;
                }else {
                    snakeXlenght[i] = snakeXlenght[i-1];
                }
                if(snakeXlenght[i] > 850) {
                    snakeXlenght[i] = 25;
                }
            }
            repaint();
        }
        if(left) {
            for(int i = snakeLenght-1; i>0; i--) {
                snakeYlenght[i] = snakeYlenght[i-1];
            }
            for(int i = snakeLenght-1; i>=0; i--) {
                if(i==0) {
                    snakeXlenght[i] = snakeXlenght[i] - 25;
                }else {
                    snakeXlenght[i] = snakeXlenght[i-1];
                }
                if(snakeXlenght[i] < 25) {
                    snakeXlenght[i] = 850;
                }
            }
            repaint();
        }
        if(up) {
            for(int i = snakeLenght-1; i>0; i--) {
                snakeXlenght[i] = snakeXlenght[i-1];
            }
            for(int i = snakeLenght-1; i>=0; i--) {
                if(i==0) {
                    snakeYlenght[i] = snakeYlenght[i] - 25;
                }else {
                    snakeYlenght[i] = snakeYlenght[i-1];
                }
                if(snakeYlenght[i] < 75) {
                    snakeYlenght[i] = 625;
                }
            }
            repaint();
        }
        if(down) {
            for(int i = snakeLenght-1; i>0; i--) {
                snakeXlenght[i] = snakeXlenght[i-1];
            }
            for(int i = snakeLenght-1; i>=0; i--) {
                if(i==0) {
                    snakeYlenght[i] = snakeYlenght[i] + 25;
                }else {
                    snakeYlenght[i] = snakeYlenght[i-1];
                }
                if(snakeYlenght[i] > 625) {
                    snakeYlenght[i] = 75;
                }
            }
            repaint();
        }
    }
    
    public String getHighScore() {
        FileReader read = null;
        BufferedReader reader = null;
        try{
            read = new FileReader("data/score.dat");
            reader = new BufferedReader(read);
            return reader.readLine();
        }
        catch(IOException ex) {
            return "0";
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void setHighScore() {
        FileWriter write = null;
        BufferedWriter writer = null;
        try{
            write = new FileWriter("data/score.dat");
            writer = new BufferedWriter(write);
            String xp = Integer.toString(score);
            writer.write(xp);
            if(writer!=null) writer.close();
        }
        catch(Exception ex) {
            //Do nothing
        }
  
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {}
    
    @Override
    public void keyTyped(KeyEvent ke) {}
}
