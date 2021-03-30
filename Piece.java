public abstract class Piece
{
  Piece(int x, int y, String color)
  {
    this.x = x;
    this.y = y;
    this.color = color;
    this.hasMoved = false;
    this.hasMovedAgain = false;
    this.isAlive = true;
    this.workAround = false;
  }
  public boolean moveDiag(int x, int y, Board b){return false;}//implement for king, queen, pawn(for attack), bishop
  public boolean moveStraight(int x, int y, Board b){return false;}//implement for King, queen, rook, pawn
  public boolean moveKnight(int x, int y, Board b){return false;}
  public boolean canKillStraight(int x, int y, Board b){return false;}//identical to moveStraight except changing x and y
  public boolean canKillDiag(int x, int y, Board b){return false;}//
  public boolean canKillKnight(int x, int y, Board b){return false;}
  public boolean canKillPawn(int x, int y, Board b){return false;}
  public boolean killDirection(int deltaX, int deltaY){return false;}//called by canKill is the difference of xs and ys within this pieces legal kill moves
  public boolean checkCheck(Board b, int king){return false;}
  public void setXandY(int x, int y){this.x = x; this.y = y;}
  public boolean castle(Piece p, Board b){return false;}
  public boolean checkMate(Board b, int king){return false;}
  public void upgrade(Board b, int pawn, int piece){}


  public boolean checkBounds(int x, int y, Board b)
  {
    if(x < 0 || x > 7 || y < 0 || y > 7)
    {
      return false;
    }
    return true;
  }
  public boolean clearPath(int x, int y, Board b)
  {
    int minX, maxX, minY, maxY;
    boolean straightX = false;
    boolean straightY = false;
    int yOff = this.y > y ? 1 : -1;
    int xOff = this.x > x ? 1 : -1;
    straightX = this.x == x;
    straightY = this.y == y;
    minX = this.x < x ? this.x : x;//4,0  //3,1  minX = 3
    maxX = this.x > x ? this.x : x;//maxX = 4
    minY = this.y < y ? this.y : y;//minY = 0
    maxY = this.y > y ? this.y : y;//maxY = 1
    if(straightX)
    {
      minY++;
      for(; minY < maxY; minY++)
      {
        if(b.isPiece(x,minY))
          return false;
      }
    }
    if(straightY)
    {
      minX++;
      for(; minX < maxX; minX++)
      {
        if(b.isPiece(minX, y))
          return false;
      }
    }
    if(!straightX && !straightY)
    {
      y+=yOff;
      x+=xOff;
      while(x != this.x && y != this.y)
      {
        if(b.isPiece(x,y))
          return false;
        y+=yOff;
        x+=xOff;
      }
    }
    return true;
  }

  public boolean move(int x, int y, Board b)
  {
    if(this.type.equals("Knight"))
    {
      return this.moveKnight(x,y,b);
    }
    if(this.x == x || this.y == y)
    {
      return this.moveStraight(x,y,b);
    }
    if(Math.abs(this.x - x) == Math.abs(this.y - y))
    {
      return this.moveDiag(x,y,b);
    }
    return false;
  }


  public boolean kill(Piece p,Board b)
  {
    if(!this.color.equals(p.color) && this.canKill(p.getX(),p.getY(),b))
    {
      p.isAlive = false;
      this.x = p.x;
      this.y = p.y;
      b.lastKilled = p;
      if(!this.hasMovedAgain && this.hasMoved)
      {
        this.hasMovedAgain = true;
      }
      if(!this.hasMoved)
      {
        this.hasMoved = true;
      }
      return true;
    }
    return false;
  }

  public boolean canKill(int x, int y, Board b)
  {
    if(this.type.equals("Knight"))
    {
      return this.canKillKnight(x,y,b);
    }
    if(this.type.equals("Pawn"))
    {
      return this.canKillPawn(x,y,b);
    }
    if(this.x == x || this.y == y)
    {
      return this.canKillStraight(x,y,b);
    }
    if(Math.abs(this.x - x) == Math.abs(this.y - y))
    {
      return this.canKillDiag(x,y,b);
    }
    return false;
  }




  //do not need to implement in child classes
  public int getX(){return this.x;}
  public int getY(){return this.y;}
  public String toString(){return this.color + " " + this.type;}
  public boolean isAlive;
  public int x;
  public int y;
  public int moveDistance;
  public String type;
  public String color;
  public char symbol;
  public boolean hasMoved;
  public boolean hasMovedAgain;
  public boolean workAround;
  //public abstract boolean checkBounds(int x, int y){return false;}
}
