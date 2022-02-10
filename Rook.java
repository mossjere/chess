public class Rook extends Piece
{
  Rook(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Rook";
    this.moveDistance = 8;
    this.value = 5;
    if(color.equals("White"))
    {
      this.symbol = Character.toUpperCase(this.type.charAt(0));
    }
    else
    {
      this.symbol = Character.toLowerCase(this.type.charAt(0));
    }
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
}
