import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Color;
public class Chess extends JPanel implements MouseListener
{

  Board b = new Board();
  int currX, currY;
  boolean whitesMove = true;
  boolean pieceSelected = false;
  boolean computersTurn = false;
  String display = "";
  char col[] = {'A','B','C','D','E','F','G','H'};
  int pawnling = -1;
  int turnCount = 0;
  boolean choosePiece = false;
  boolean gameOver = false;
  boolean singlePlayer = false;
  boolean compVComp = false;
  double screenHeight, screenWidth;
  Chess(){addMouseListener(this);}
  public static void main(String[] args)
  {
    Chess chess = new Chess();
    JFrame frame = new JFrame();
    frame.setPreferredSize(new Dimension(1000,1040));
    //frame.setPreferredSize(new Dimension(500,520));
    frame.validate();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    frame.setTitle("Chess");
    frame.add(chess);
    while(true)
    {
      Dimension screenSize = frame.getBounds().getSize();
      chess.screenHeight = screenSize.getHeight();
      chess.screenWidth = screenSize.getWidth();
      if(chess.singlePlayer)
      {
        if(chess.computersTurn)
        {
          chess.ai();
        }
        try
        {
          Thread.sleep(1);
        }
        catch(Exception e)
        {
          System.out.println("Error");
        }
      }
    }
  }

  public void printFen()
  {
    return;
  }

  public void ai()
  {
    if(gameOver)
      return;
    int startX, startY, bestX, bestY, value;
    int king = whitesMove ? 0 : 16;
    if(b.test)
      king = whitesMove ? 0 : 1;
    startX = -1;
    startY = -1;
    bestX = -1;
    bestY = -1;
    value = -1;
    Board a = b.clone();
    String compsColor = whitesMove ? "White" : "Black";

    for(int i = 0; i < a.list.size(); i++)
    {
      if(!a.list.get(i).isAlive || !a.list.get(i).color.equals(compsColor))
      {
        continue;
      }
      int tempX = a.list.get(i).getX();
      int tempY = a.list.get(i).getY();
      for(int j = 0; j < a.list.size(); j++)
      {
        if(j == i || !a.list.get(j).isAlive || a.list.get(j).color.equals(compsColor))
          continue;
        if(a.list.get(i).kill(a.list.get(j),a))
        {
          if((turnCount > 0 || b.test) && a.list.get(j).value > value && !a.list.get(king).checkCheck(a,king))
          {
            startX = tempX;
            startY = tempY;
            bestX = a.list.get(i).getX();
            bestY = a.list.get(i).getY();
            a.list.get(i).calculateValue();
            value = a.list.get(j).value;
          }
        }
        a = b.clone();
        System.gc();
      }
    }
    if(value < 0)
    {
      Random gen = new Random();
      int rand = gen.nextInt(a.list.size()-1);
      for(int i = rand+1; i != rand; i++)
      {
        if(i >= a.list.size())
        {
          i = 0;
        }
        int tempX = a.list.get(i).getX();
        int tempY = a.list.get(i).getY();
        boolean breaker = false;
        if(!a.list.get(i).isAlive || !a.list.get(i).color.equals(compsColor))
        {
          continue;
        }
        ArrayList<Integer> moves = a.list.get(i).getMoves();
      	for(int m = 0; m < moves.size(); m++)
      	{
      		int x = (int)moves.get(m) % 8;
      		int y = (int)moves.get(m) / 8;
            if(a.isPiece(x,y) || (a.list.get(i).getX() == x && a.list.get(i).getY() == y))
              continue;
            if(a.list.get(i).move(x,y,a)  && !a.list.get(king).checkCheck(a,king))
            {
              breaker = true;
              startX = tempX;
              startY = tempY;
              bestX = a.list.get(i).getX();
              bestY = a.list.get(i).getY();
              value = 0;
            }
            else
            {
              a = b.clone();
              System.gc();
            }
          }
        if(breaker)
          break;
      }
    }
    if(b.isPiece(startX, startY)){
      if(b.isPiece(bestX, bestY))
      {
        display = b.list.get(b.whichPiece(startX,startY)) + " takes " + b.list.get(b.whichPiece(bestX, bestY));
        b.list.get(b.whichPiece(startX,startY)).kill(b.list.get(b.whichPiece(bestX,bestY)),b);
      }
      else
      {
        display = b.list.get(b.whichPiece(startX,startY)).type + " " + col[startX] + (startY+1) + " to " + col[bestX] + (bestY+1);
        b.list.get(b.whichPiece(startX,startY)).move(bestX,bestY,b);
      }
    }
    turnCount++;
    whitesMove = !whitesMove;
    computersTurn = false;
    repaint();
  }

  public void paintComponent(Graphics g)
  {
   super.paintComponent(g);
   setOpaque(false);
   setVisible(true);
   Graphics2D g2d = (Graphics2D)g;
   Image image;
   int sw = (int)screenWidth;
   int sh = (int)screenHeight - (((int)screenHeight / 520) * 20);
   int swr = sw / 500;
   int shr = sh / 500;
   //bottom left and top right corners should be black
   //top left and bottom right should be white
   g.setColor(new Color(102,51,0));
   int times = 0;
   int add = sw / 10;//50;
   int x = sw / 10;//50;
   int y = sh / 10;//50;
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
       x = sw / 10;//50;
       filled = !filled;
     }
   }

   for(int i = 7; i >= 0; i--)
   {
     for(int j = 0; j < 8; j++)
     {
       for(int k = 0; k < b.list.size(); k++)
       {
         if(b.list.get(k).getX() == j && b.list.get(k).getY() == i && b.list.get(k).isAlive)
         {
           g.setColor(Color.WHITE);
           String file = "images/" + b.list.get(k).toString() + ".png";
           image = new ImageIcon(file).getImage();
           g2d.drawImage(image,(swr*60)+(j * (sh/10)),((sh - (shr * 30))-((shr * 60)+(i*(sh/10)))), swr *  30, shr * 30, null);
         }
       }
     }
   }

   if(choosePiece)
   {
     String selectedColor = whitesMove ? "Black" : "White";
     String queenFile = "images/" + selectedColor + " Queen.png";
     String bishopFile = "images/" + selectedColor + " Bishop.png";
     String rookFile = "images/" + selectedColor + " Rook.png";
     String knightFile = "images/" + selectedColor + " Knight.png";
     Image queenImage = new ImageIcon(queenFile).getImage();
     Image bishopImage = new ImageIcon(bishopFile).getImage();
     Image rookImage = new ImageIcon(rookFile).getImage();
     Image knightImage = new ImageIcon(knightFile).getImage();
     g2d.drawImage(queenImage,(swr * 60)+(8 * (sw / 10)),((sh - (shr * 30))-((shr * 60)+(5*(shr * 50)))), (swr * 30), (shr * 30), null);
     g2d.drawImage(bishopImage,(swr * 60)+(8 * (sw / 10)),((sh - (shr * 30))-((shr * 60)+(4*(shr * 50)))), (swr * 30), (shr * 30), null);
     g2d.drawImage(rookImage,(swr * 60)+(8 * (sw / 10)),((sh - (shr * 30))-((shr * 60)+(3*(shr * 50)))), (swr * 30), (shr * 30), null);
     g2d.drawImage(knightImage,(swr * 60)+(8 * (sw / 10)),((sh - (shr * 30))-((shr * 60)+(2*(shr * 50)))), (swr * 30), (shr * 30), null);
   }

   if(pieceSelected)
   {
     g.setColor(Color.GREEN);
     g.drawRect(currX*(sw/10)+(sw/10), ((7-currY)*(sh/10))+(sh/10), sw/10,sh/10);
   }
   g2d.setColor(Color.BLACK);
   FontMetrics fm = g2d.getFontMetrics();
   int stringX = ((getWidth() - fm.stringWidth(display)) / 2);
   g2d.drawString(display,stringX,(sw / 20));
   g2d.dispose();
  }
  public void mouseClicked(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mousePressed(MouseEvent e)
  {
    if(compVComp)
    {
      computersTurn = true;
      return;
    }
    boolean tempTurn = whitesMove;
    playerTurn(e);
    if(tempTurn != whitesMove)
    {
      computersTurn = true;
    }
  }
  public void playerTurn(MouseEvent e)
  {
    if(gameOver || (singlePlayer && computersTurn))//don't update board if game is over
      return;
    int piecesAlive = 0;
    for(int i = 0; i < b.list.size(); i++)
    {
      if(b.list.get(i).isAlive)
        piecesAlive++;
    }
    int king = whitesMove ? 0 : 16;
    if(b.test)
      king = whitesMove ? 0 : 1;
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
    /*if a pawn has reached the other side of the board
    choose what piece to make them into*/
    if(choosePiece)
    {
      int selectedX = e.getX();
      int selectedY = e.getY();
      selectedX = (selectedX-((int)screenWidth / 10))/((int)screenWidth / 10);
      selectedY = 7-((selectedY-(((int)screenHeight-40)/10))/(((int)screenHeight-40) / 10));

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
      currX = (e.getX()-((int)screenWidth/10))/((int)screenWidth/10);
      currY = 7-((e.getY()-(((int)screenHeight-(((int)screenHeight / 520) * 20))/10))/(((int)screenHeight-(((int)screenHeight / 520) * 20))/10));
      if(b.isPiece(currX,currY) &&
      ((b.list.get(b.whichPiece(currX,currY)).color.equals("White") && whitesMove) ||
      (b.list.get(b.whichPiece(currX,currY)).color.equals("Black") && !whitesMove)))
        pieceSelected = true;
    }
    //if a piece is already selected
    else
    {
      int newX = (e.getX()-((int)screenWidth/10))/((int)screenWidth/10);
      int newY = 7-((e.getY()-(((int)screenHeight-(((int)screenHeight / 520) * 20))/10))/(((int)screenHeight-(((int)screenHeight / 520) * 20))/10));
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
              printFen();
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
                printFen();
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
            display = b.list.get(pieceIndex) + " takes " + b.list.get(b.whichPiece(newX, newY));
            turnComplete = b.list.get(pieceIndex).kill(b.list.get(b.whichPiece(newX,newY)),b);
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
                printFen();
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
                printFen();
            }
          }
        }
        else
        {
          display = "That is not your piece";
        }
      }
      king = whitesMove ? 0 : 16;
      if(b.test)
        king = whitesMove ? 0 : 8;
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
      pieceSelected = false;
    }
    repaint();
  }

}
