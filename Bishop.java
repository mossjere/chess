public class Bishop extends Piece
{
  Bishop(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Bishop";
    this.moveDistance = 8;
    this.value = 3;
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
}
