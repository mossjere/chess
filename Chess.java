import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class Chess extends JPanel implements MouseListener
{

  Board b = new Board();
  int currX, currY;
  boolean whitesMove = true;
  boolean pieceSelected = false;
  String display = "";
  char col[] = {'A','B','C','D','E','F','G','H'};
  int pawnling = -1;
  boolean choosePiece = false;
  boolean gameOver = false;
  Chess(){addMouseListener(this);}
  public static void main(String[] args)
  {
    Chess chess = new Chess();
    JFrame frame = new JFrame();
    frame.setPreferredSize(new Dimension(500,520));
    frame.validate();
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.pack();
    frame.setTitle("Chess");
    frame.add(chess);
  }



  public void paintComponent(Graphics g)
  {
   super.paintComponent(g);
   setOpaque(false);
   setVisible(true);
   Graphics2D g2d = (Graphics2D)g;
   Image image;
   //bottom left and top right corners should be black
   //top left and bottom right should be white
   g.setColor(new Color(102,51,0));
   int times = 0;
   int add = 50;
   int x = 50;
   int y = 50;
   int y2 = 65;
   boolean filled = false;
   while(times < 64)
   {
     if(filled)
     {
       g.fillRect(x,y,add,add);
     }
     else
     {
       g.drawRect(x,y,add,add);
     }
     times++;
     x+=add;
     filled = !filled;
     if(times % 8 == 0)
     {
       y+=add;
       x = 50;
       filled = !filled;
     }
   }


   //g.setColor(Color.BLACK);
   for(int i = 7; i >= 0; i--)
   {
     for(int j = 0; j < 8; j++)
     {
       boolean taken = false;
       for(int k = 0; k < b.list.size(); k++)
       {
         if(b.list.get(k).getX() == j && b.list.get(k).getY() == i && b.list.get(k).isAlive)
         {
           // if(b.list.get(k).color.equals("White"))
           // {
           //   g.setColor(new Color(153,102,0));
           // }
           // else
           // {
           //   g.setColor(Color.BLACK);
           // }
           //g.fillOval(60+(j * 50),(470-(60+(i*50))),30,30);
           g.setColor(Color.WHITE);
           String file = b.list.get(k).toString() + ".png";
           image = new ImageIcon(file).getImage();
           g2d.drawImage(image,60+(j * 50),(470-(60+(i*50))), 30, 30, null);
         }
       }
     }
   }

   if(choosePiece)
   {
     String selectedColor = whitesMove ? "Black" : "White";
     String queenFile = selectedColor + " Queen.png";
     String bishopFile = selectedColor + " Bishop.png";
     String rookFile = selectedColor + " Rook.png";
     String knightFile = selectedColor + " Knight.png";
     Image queenImage = new ImageIcon(queenFile).getImage();
     Image bishopImage = new ImageIcon(bishopFile).getImage();
     Image rookImage = new ImageIcon(rookFile).getImage();
     Image knightImage = new ImageIcon(knightFile).getImage();
     g2d.drawImage(queenImage,60+(8 * 50),(470-(60+(5*50))), 30, 30, null);
     g2d.drawImage(bishopImage,60+(8 * 50),(470-(60+(4*50))), 30, 30, null);
     g2d.drawImage(rookImage,60+(8 * 50),(470-(60+(3*50))), 30, 30, null);
     g2d.drawImage(knightImage,60+(8 * 50),(470-(60+(2*50))), 30, 30, null);
   }

   if(pieceSelected)
   {
     g.setColor(Color.GREEN);
     g.drawRect(currX*50+50, (7-currY)*50+50, 50,50);
   }
   g2d.setColor(Color.BLACK);
   FontMetrics fm = g2d.getFontMetrics();
   int stringX = ((getWidth() - fm.stringWidth(display)) / 2);
   g2d.drawString(display,stringX,25);
   g2d.dispose();
  }
  public void mouseClicked(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mousePressed(MouseEvent e)
  {
    if(gameOver)//don't update board if game is over
      return;
    /*if a pawn has reached the other side of the board
    choose what piece to make them into*/
    int piecesAlive = 0;
    for(int i = 0; i < b.list.size(); i++)
    {
      if(b.list.get(i).isAlive)
        piecesAlive++;
    }
    int king = whitesMove ? 0 : 16;
    if(!b.list.get(king).checkCheck(b,king) && b.list.get(king).checkMate(b,king))
    {
      display = "Stalemate!";
      pieceSelected = false;
      gameOver = true;
      repaint();
      return;
    }
    if(piecesAlive == 2)
    {
      display = "Stalemate!";
      repaint();
      gameOver = true;
      return;
    }
    if(choosePiece)
    {
      int selectedX = e.getX();
      int selectedY = e.getY();
      selectedX = (selectedX-50)/50;
      selectedY = 7-((selectedY-50)/50);
      if(selectedX == 8 && selectedY > 1 && selectedY < 6)
      {
        b.list.get(pawnling).upgrade(b, pawnling, selectedY);
        choosePiece = !choosePiece;
        repaint();
      }
      return;
    }
    //If it is a click where no piece is already selected select piece at x, y
    if(!pieceSelected)
    {
      currX = e.getX();
      currY = e.getY();
      currX = (currX-50)/50;
      currY = 7-((currY-50)/50);
      if(b.isPiece(currX,currY) &&
      ((b.list.get(b.whichPiece(currX,currY)).color.equals("White") && whitesMove) ||
      (b.list.get(b.whichPiece(currX,currY)).color.equals("Black") && !whitesMove)))
        pieceSelected = true;
    }
    //if a piece is already selected
    else
    {
      int newX = (e.getX()-50)/50;
      int newY = 7-((e.getY()-50)/50);
      if(currX >= 0 && currX < 8 && currY >= 0 && currY < 8)
      {
        int pieceIndex = b.whichPiece(currX,currY);
        boolean tempMoved = b.list.get(pieceIndex).hasMoved;
        boolean tempMovedAgain = b.list.get(pieceIndex).hasMovedAgain;
        boolean turnComplete = false;
        if(pieceIndex < 0)
          return;
        //Make sure it is the turn of the piece selected
        if((b.list.get(pieceIndex).color.equals("White") && whitesMove)||
           (b.list.get(pieceIndex).color.equals("Black") && !whitesMove))
        {
          boolean inCheck = b.list.get(king).checkCheck(b,king);
          if(inCheck)
          {
            if(b.list.get(king).checkMate(b,king))
            {
              String winner = whitesMove ? "Black" : "White";
              display = "Check Mate! " + winner + " wins the game!";
              gameOver = true;
              pieceSelected = false;
              repaint();
              System.out.println(b.fen());
              return;
            }
          }
          if(b.isPiece(newX,newY))
          {
            int tempIndex = b.whichPiece(newX, newY);
            boolean tempOtherMoved = b.list.get(tempIndex).hasMoved;
            boolean tempOtherMovedAgain = b.list.get(tempIndex).hasMovedAgain;
            if(b.list.get(pieceIndex).color.equals(b.list.get(b.whichPiece(newX,newY)).color)
            && b.list.get(pieceIndex).type.equals("King") && b.list.get(b.whichPiece(newX,newY)).type.equals("Rook"))
            {
              if(b.list.get(pieceIndex).castle(b.list.get(b.whichPiece(newX,newY)), b))
              {
                whitesMove = !whitesMove;
                turnComplete = true;
                pieceSelected = false;
                if(b.list.get(king).checkCheck(b,king))
                {
                  whitesMove = !whitesMove;
                  b.list.get(pieceIndex).setXandY(currX,currY);
                  b.list.get(tempIndex).setXandY(newX,newY);
                  b.list.get(pieceIndex).hasMoved = tempMoved;
                  b.list.get(pieceIndex).hasMovedAgain = tempMovedAgain;
                  b.list.get(tempIndex).hasMoved = tempOtherMoved;
                  b.list.get(tempIndex).hasMovedAgain = tempOtherMovedAgain;
                  if(inCheck)
                  {
                    display = "You cannot move there.  You are in check";
                  }
                  else
                  {
                    display = "You cannot move into check.";
                  }
                }
                System.out.println(b.fen());
                repaint();
                return;
              }
              else
              {
                currX = newX;
                currY = newY;
                pieceSelected = true;
                repaint();
                return;
              }
            }
            else if(b.list.get(pieceIndex).color.equals(b.list.get(b.whichPiece(newX,newY)).color))
            {
              currX = newX;
              currY = newY;
              pieceSelected = true;
              repaint();
              return;
            }
            //String tempDisplay = display;
            display = b.list.get(pieceIndex) + " takes " + b.list.get(b.whichPiece(newX, newY));
            turnComplete = b.list.get(pieceIndex).kill(b.list.get(b.whichPiece(newX,newY)),b);
            // if(b.list.get(pieceIndex).type.equals("Pawn") && ((whitesMove && b.list.get(pieceIndex).getY() == 7) || !whitesMove && b.list.get(pieceIndex).getY() == 0))
            // {
            //   b.list.get(pieceIndex).queenMe(b,pieceIndex);
            //   repaint();
            // }
            if(!turnComplete)
            {
              display = "You cannot move the " + b.list.get(pieceIndex) + " there";
            }
            else
            {
              //if pawn makes it to the opposite side of the board
              if(b.list.get(pieceIndex).type.equals("Pawn") && ((whitesMove && b.list.get(pieceIndex).getY() == 7) || !whitesMove && b.list.get(pieceIndex).getY() == 0))
              {
                display = "Select your piece";
                choosePiece = true;
                pawnling = pieceIndex;
                // b.list.get(pieceIndex).queenMe(b,pieceIndex);
                repaint();
              }
              whitesMove = !whitesMove;
              if(b.list.get(king).checkCheck(b,king))
              {
                whitesMove = !whitesMove;
                b.list.get(b.whichPiece(newX,newY)).setXandY(currX,currY);
                b.list.get(pieceIndex).hasMoved = tempMoved;
                b.list.get(pieceIndex).hasMovedAgain = tempMovedAgain;
                if(inCheck)
                {
                  display = "You cannot move there.  You are in check";
                }
                else
                {
                  display = "You cannot move into check.";
                }
                if(b.lastKilled.x == newX && b.lastKilled.y == newY)
                {
                  b.lastKilled.isAlive = true;
                  b.lastKilled = null;
                }
              }
              else
                System.out.println(b.fen());
            }
          }
          else
          {
            turnComplete = b.list.get(pieceIndex).move(newX,newY,b);
            if(!turnComplete)
              display = "You cannot move the " + b.list.get(pieceIndex) + " there";
            else
            {
              b.lastMoved = b.list.get(pieceIndex);
              if(b.list.get(pieceIndex).type.equals("Pawn") && ((whitesMove && b.list.get(pieceIndex).getY() == 7) || !whitesMove && b.list.get(pieceIndex).getY() == 0))
              {
                display = "Select your piece";
                choosePiece = true;
                pawnling = pieceIndex;
                // b.list.get(pieceIndex).queenMe(b,pieceIndex);
                repaint();
              }
              display = b.list.get(pieceIndex).type + " " + col[currX] + (currY+1) + " to " + col[newX] + (newY+1);
              if(choosePiece)
                display = "Select your piece";
              whitesMove = !whitesMove;
              if(b.list.get(king).checkCheck(b,king))
              {
                whitesMove = !whitesMove;
                b.list.get(b.whichPiece(newX,newY)).setXandY(currX,currY);
                b.list.get(pieceIndex).hasMoved = tempMoved;
                b.list.get(pieceIndex).hasMovedAgain = tempMovedAgain;
                if(inCheck)
                {
                  display = "You cannot move there.  You are in check";
                }
                else
                {
                  display = "You cannot move into check.";
                }
              }
              else
                System.out.println(b.fen());
            }
          }
        }
        else
        {
          display = "That is not your piece";
        }
        // if(b.list.get(pieceIndex).type.equals("Pawn") && ((whitesMove && b.list.get(pieceIndex).getY() == 7) || !whitesMove && b.list.get(pieceIndex).getY() == 0))
        // {
        //   b.list.get(pieceIndex).queenMe(b,pieceIndex);
        //   repaint();
        // }
      }
      king = whitesMove ? 0 : 16;
      if(b.list.get(king).checkCheck(b,king))
      {
        if(b.list.get(king).checkMate(b,king))
        {
          String winner = whitesMove ? "Black" : "White";
          display = "Check Mate! " + winner + " wins the game!";
          gameOver = true;
          repaint();
          pieceSelected = false;
          return;
        }
        display = "Check";
      }
      if(choosePiece)
        display = "Select your piece";
      // System.out.println(display);
      //pieceSelected = !pieceSelected;
      pieceSelected = false;
    }
    repaint();
  }

}
