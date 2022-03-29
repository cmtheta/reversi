
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Dimension;

import static constant.ReversiConst.*;

public class GameBoard extends JPanel implements MouseListener, Runnable{
    private static int boardSize = GAMEBOARD_WIDTH - GAMEBOARD_WIDTH/100 * 2;
    private static int boardEdge = GAMEBOARD_WIDTH/100;
    private static int cellSize = boardSize / 8;
    private static Color green = new Color(10,150,50);
    private BufferedImage brdImg;
    private Graphics gc;
    private int animationCounter;
    private int blinkingCounter;
    private ReversiModel model;
    private ArrayList<Point> pointList;
    private ArrayList<Point> revList;
    private Point putStone;
    private GameInfo gi;

    public GameBoard(ReversiModel model, GameInfo gi){
        super();
        this.model = model;
        this.gi = gi;

        setPreferredSize(new Dimension(GAMEBOARD_WIDTH, GAMEBOARD_HEIGHT));
        addMouseListener(this);

        animationCounter = ANIMATION_TURN_OFF;
        blinkingCounter = ANIMATION_BLINKING_OFF;
        putStone = new Point(-1,-1);

        brdImg = new BufferedImage(GAMEBOARD_WIDTH, GAMEBOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        gc = brdImg.createGraphics();
        
        pointList = model.isThereCanPut();
        
        drawBoard();
        drawMarks();
        repaint();

        new Thread(this).start();
    }

    public void run(){
        while(true){
            if(blinkingCounter != ANIMATION_BLINKING_OFF)
                blinkingPutStone();

            if(animationCounter != ANIMATION_TURN_OFF) 
                reverseAnimation();

            try{
                Thread.sleep(ANIMATION_SLEEP_TIMER);
            } catch(InterruptedException e){
                System.err.println(e);
            }
        }
    }

    public synchronized void reverseAnimation(){
        Color stoneColor = null;
        if(animationCounter > 4){
            if(model.getTurn() == BLACK) stoneColor = Color.WHITE;
            else stoneColor = Color.BLACK;
        } else {
            if(model.getTurn() == BLACK) stoneColor = Color.BLACK;
            else stoneColor = Color.WHITE;
        }
        for(Point p : revList){
            int x = p.getX(), y = p.getY();
            int px = x*cellSize + boardEdge;
            int py = y*cellSize + boardEdge;

            drawStone(px, py, animationCounter, stoneColor);
        }

        animationCounter--;
        if(animationCounter == ANIMATION_TURN_OFF){
            drawBoard();
            changePlayer();
            drawMarks();
        }
        repaint();
    }

    public synchronized void drawStone(int x, int y, int time, Color stoneColor){
        gc.setColor(green);
        gc.fillRect(x,y, cellSize, cellSize);

        gc.setColor(stoneColor);
        int t;
        if(time > 4){ t = ANIMATION_COUNT_REVERSE - time+1; } 
        else { t = time - 1;}
        int ovalSize = cellSize / 4 * t;
        gc.fillOval(x + 12*t,y,cellSize - ovalSize, cellSize);

        gc.setColor(Color.BLACK);
        gc.drawRect(x, y, cellSize, cellSize);
    }

    public synchronized void drawMarks(){
        if(model.getTurn() == BLACK){ gc.setColor(Color.BLACK); } 
        else { gc.setColor(Color.WHITE); }
        for(Point p : pointList){
            int x = p.getX() * cellSize + cellSize/100 * 52;
            int y = p.getY() * cellSize + cellSize/100 * 52;
            gc.fillRect(x, y, cellSize/5, cellSize/5);
        } 
        repaint();
    }

    public synchronized void eraseMarks(){
        for(Point p : pointList){
            int x = p.getX() * cellSize + boardEdge;
            int y = p.getY() * cellSize + boardEdge;

            gc.setColor(green);
            gc.fillRect(x, y, cellSize, cellSize);

            gc.setColor(Color.BLACK);
            gc.drawRect(x, y, cellSize, cellSize);
        } 
        repaint();
    }

    public synchronized void drawBoard(){
        gc.setColor(green);
        gc.fillRect(0,0,GAMEBOARD_WIDTH, GAMEBOARD_HEIGHT);
        
        gc.setColor(Color.BLACK);
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                int px = x*cellSize + boardEdge;
                int py = y*cellSize + boardEdge;

                gc.setColor(green);
                gc.fillRect(px, py, cellSize, cellSize);
                if(model.getCell(x, y) == BLACK){
                    gc.setColor(Color.BLACK);
                    gc.fillOval(px,py,cellSize-1,cellSize-1);
                } else if(model.getCell(x, y) == WHITE){
                    gc.setColor(Color.WHITE);
                    gc.fillOval(px,py,cellSize-1,cellSize-1);
                }
                gc.setColor(Color.BLACK);
                gc.drawRect(px, py, cellSize, cellSize);

            }
        }
        repaint();
    }

    public synchronized void blinkingPutStone(){
        int x = putStone.getX();
        int y = putStone.getY();
        int px = x * cellSize + boardEdge; 
        int py = y * cellSize + boardEdge;
        int turn = (blinkingCounter / 3) % 2;
        if(blinkingCounter == ANIMATION_COUNT_BLINKING){
            eraseMarks();
        }

        gc.setColor(green);
        gc.fillRect(px, py, cellSize, cellSize);

        if(turn == 1) {
            drawPutStone(x, y);
        }

        gc.setColor(Color.BLACK);
        gc.drawRect(px, py, cellSize, cellSize);

        blinkingCounter++;
        repaint();

        if(blinkingCounter == ANIMATION_BLINKING_OFF)
            this.animationCounter = ANIMATION_COUNT_REVERSE;
        

    }
    
    public synchronized void drawPutStone(int x, int y){
        int px = x * cellSize + boardEdge;
        int py = y * cellSize + boardEdge;
        gc.setColor(green);
        gc.fillRect(px, py, cellSize, cellSize);
        if(model.getCell(x, y) == BLACK){
            gc.setColor(Color.BLACK);
            gc.fillOval(px, py,cellSize-1,cellSize-1);
        } else if(model.getCell(x, y) == WHITE){
            gc.setColor(Color.WHITE);
            gc.fillOval(px, py,cellSize-1,cellSize-1);
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(brdImg,0,0,this);
    }

    public void mouseClicked(MouseEvent e){
        if(animationCounter != ANIMATION_TURN_OFF) return ;
        if(blinkingCounter != ANIMATION_BLINKING_OFF) return;
        int x = e.getX();
        int y = e.getY();
        if(x < boardEdge || y < boardEdge) return ;
        if(x >= boardEdge+boardSize || y >= boardEdge+boardSize) return;

        x = (x - boardEdge) / cellSize;
        y = (y - boardEdge) / cellSize;
        // System.out.println(x + " " + y);

        revList = model.put(x,y);
        if(revList.size() == 0) return;

        putStone.setX(x);
        putStone.setY(y);

        this.blinkingCounter = ANIMATION_COUNT_BLINKING;
    }

    public void mousePressed(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

    public void changePlayer(){
        model.changePlayer();
        pointList = model.isThereCanPut();
        if(pointList.size() == 0){
            model.changePlayer();
            pointList = model.isThereCanPut();
            if(pointList.size() == 0){
                // Display Game finish infomatioin//
            }
        }
        gi.setStoneNum(model.getStoneNum(BLACK), model.getStoneNum(WHITE));
    }

}
