public class Queen extends Piece
{
  Queen(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Queen";
    this.moveDistance = 8;
    if(color.equals("White"))
    {
      this.symbol = Character.toUpperCase(this.type.charAt(0));
    }
    else
    {
      this.symbol = Character.toLowerCase(this.type.charAt(0));
    }
  }
  public boolean moveDiag(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((this.x - x == this.y - y || this.x - x == -1 * (this.y - y)))
    {
      this.x = x;
      this.y = y;
      return true;
    }
    return false;
  }
  public boolean moveStraight(int x, int y, Board b)
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
      return true;
    }
    return false;
  }

  public boolean canKillDiag(int x, int y, Board b)
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

  public boolean canKillStraight(int x, int y, Board b)
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
}
