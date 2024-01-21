import java.util.ArrayList;
public class Queen extends Piece
{
  Queen(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Queen";
    this.moveDistance = 8;
    this.value = 9;
    if(color.equals("White"))
    {
      this.symbol = Character.toUpperCase(this.type.charAt(0));
    }
    else
    {
      this.symbol = Character.toLowerCase(this.type.charAt(0));
    }
  }
  protected boolean moveDiag(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((this.x - x == this.y - y || this.x - x == -1 * (this.y - y)))
    {
      this.x = x;
      this.y = y;
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
  protected boolean moveStraight(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((((this.x == x && (this.y - y < this.moveDistance && this.y - y > -1 * this.moveDistance)) ||
    (this.y == y && (this.x - x < this.moveDistance && this.x - x > -1 * this.moveDistance)))))
    {
      this.x = x;
      this.y = y;
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

  protected boolean canKillDiag(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((this.x - x == this.y - y || this.x - x == -1 * (this.y - y)))
    {
      return true;
    }
    return false;
  }

  protected boolean canKillStraight(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((((this.x == x && (this.y - y < this.moveDistance && this.y - y > -1 * this.moveDistance)) ||
    (this.y == y && (this.x - x < this.moveDistance && this.x - x > -1 * this.moveDistance)))))
    {
      return true;
    }
    return false;
  }

  protected ArrayList<Integer> getMoves()
  {
    ArrayList<Integer> moves = new ArrayList<Integer>();
    int x = this.getX();
    int y = this.getY();
    for(int i = 0; i < 8; i++)
    {
      if(y+i < 8)
      {
        if(x+i < 8)
          moves.add((y+i)*8 + (x + i));
        if(x-i >= 0)
          moves.add((y+i)*8 + (x - i));
        moves.add((y+i)*8 + x);
      }
      if(y-i >= 0)
      {
        if(x+i < 8)
          moves.add((y-i)*8 + (x + i));
        if(x-i >= 0)
          moves.add((y-i)*8 + (x - i));
        moves.add((y-i)*8 + x);
      }
      if(x+i < 8)
        moves.add(y*8 + (x + i));
      if(x-i >= 0)
        moves.add(y*8 + (x - i));
    }
    return moves;
  }
}
