import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Board
{
  boolean grid[][] = new boolean[8][8];
  ArrayList<Piece> list = new ArrayList<Piece>();
  Piece lastKilled;
  Piece lastMoved;
  Board()
  {
    lastKilled = null;
    King whiteKing = new King(4,0,"White");
    list.add(whiteKing);
    Queen whiteQueen = new Queen(3,0,"White");
    list.add(whiteQueen);
    Bishop whiteQueenBishop = new Bishop(2,0,"White");
    list.add(whiteQueenBishop);
    Bishop whiteKingBishop = new Bishop(5,0,"White");
    list.add(whiteKingBishop);
    Knight whiteQueenKnight = new Knight(1,0,"White");
    list.add(whiteQueenKnight);
    Knight whiteKingKnight = new Knight(6,0,"White");
    list.add(whiteKingKnight);
    Rook whiteQueenRook = new Rook(0,0,"White");
    list.add(whiteQueenRook);
    Rook whiteKingRook = new Rook(7,0,"White");
    list.add(whiteKingRook);
    Pawn whitePawn1 = new Pawn(0,1,"White");
    list.add(whitePawn1);
    Pawn whitePawn2 = new Pawn(1,1,"White");
    list.add(whitePawn2);
    Pawn whitePawn3 = new Pawn(2,1,"White");
    list.add(whitePawn3);
    Pawn whitePawn4 = new Pawn(3,1,"White");
    list.add(whitePawn4);
    Pawn whitePawn5 = new Pawn(4,1,"White");
    list.add(whitePawn5);
    Pawn whitePawn6 = new Pawn(5,1,"White");
    list.add(whitePawn6);
    Pawn whitePawn7 = new Pawn(6,1,"White");
    list.add(whitePawn7);
    Pawn whitePawn8 = new Pawn(7,1,"White");
    list.add(whitePawn8);




    King blackKing = new King(4,7,"Black");
    list.add(blackKing);
    Queen blackQueen = new Queen(3,7,"Black");
    list.add(blackQueen);
    Bishop blackQueenBishop = new Bishop(2,7,"Black");
    list.add(blackQueenBishop);
    Bishop blackKingBishop = new Bishop(5,7,"Black");
    list.add(blackKingBishop);
    Knight blackQueenKnight = new Knight(1,7,"Black");
    list.add(blackQueenKnight);
    Knight blackKingKnight = new Knight(6,7,"Black");
    list.add(blackKingKnight);
    Rook blackQueenRook = new Rook(0,7,"Black");
    list.add(blackQueenRook);
    Rook blackKingRook = new Rook(7,7,"Black");
    list.add(blackKingRook);
    Pawn blackPawn1 = new Pawn(0,6,"Black");
    list.add(blackPawn1);
    Pawn blackPawn2 = new Pawn(1,6,"Black");
    list.add(blackPawn2);
    Pawn blackPawn3 = new Pawn(2,6,"Black");
    list.add(blackPawn3);
    Pawn blackPawn4 = new Pawn(3,6,"Black");
    list.add(blackPawn4);
    Pawn blackPawn5 = new Pawn(4,6,"Black");
    list.add(blackPawn5);
    Pawn blackPawn6 = new Pawn(5,6,"Black");
    list.add(blackPawn6);
    Pawn blackPawn7 = new Pawn(6,6,"Black");
    list.add(blackPawn7);
    Pawn blackPawn8 = new Pawn(7,6,"Black");
    list.add(blackPawn8);
    for(int i = 0; i < 8; i++)
    {
      for(int j = 0; j < 8; j++)
      {
        grid[i][j] = false;
      }
    }
    for(int k = 0; k < list.size(); k++)
    {
      int i = list.get(k).getX();
      int j = list.get(k).getY();
      grid[i][j] = true;
    }
  }
  public boolean isPiece(int x, int y)
  {
    for(int k = 0; k < list.size(); k++)
    {
      if(list.get(k).getX() == x && list.get(k).getY() == y && list.get(k).isAlive)
        return true;
    }
    return false;
  }
  public int whichPiece(int x, int y)
  {
    for(int k = 0; k < list.size(); k++)
    {
      if(list.get(k).getX() == x && list.get(k).getY() == y && list.get(k).isAlive)
        return k;
    }
    return -1;
  }
  public static void main(String[] args)
  {
    Board b = new Board();
    Scanner scnr = new Scanner(System.in);
    int x, y, newX, newY;;
    newX = 0;
    boolean whitesMove = true;
    while(newX >= 0)
    {
      boolean turnComplete = false;
      while(!turnComplete)
      {
        System.out.println("Enter x value of piece: ");
        x = scnr.nextInt();
        System.out.println("Enter y value of piece: ");
        y = scnr.nextInt();
        System.out.println("Enter new X value of piece: ");
        newX = scnr.nextInt();
        System.out.println("Enter new Y value of piece: ");
        newY = scnr.nextInt();
        int pieceIndex = b.whichPiece(x,y);
        if((b.list.get(pieceIndex).color.equals("White") && whitesMove)||
          (b.list.get(pieceIndex).color.equals("Black") && !whitesMove))
        {
          if(b.isPiece(newX,newY))
          {
              turnComplete = b.list.get(pieceIndex).kill(b.list.get(b.whichPiece(newX,newY)),b);
              if(!turnComplete)
                System.out.println("You cannot move the " + b.list.get(pieceIndex) + " there");
              else
                whitesMove = !whitesMove;
          }
          else{
            turnComplete = b.list.get(pieceIndex).move(newX,newY,b);
            if(!turnComplete)
              System.out.println("You cannot move the " + b.list.get(pieceIndex) + " there");
            else
              whitesMove = !whitesMove;
          }
        }
        else
        {
          System.out.println("That is not your piece");
        }
      }

      for(int i = 7; i >= 0; i--)
      {
        for(int j = 0; j < 8; j++)
        {
          boolean taken = false;
          for(int k = 0; k < b.list.size(); k++)
          {
            if(b.list.get(k).getX() == j && b.list.get(k).getY() == i && b.list.get(k).isAlive)
            {
              System.out.print(b.list.get(k).symbol + " ");
              taken = true;
            }
          }
          if(!taken)
          {
            System.out.print("0 ");
          }
        }
        System.out.print("\n");
      }
    }
  }
}
