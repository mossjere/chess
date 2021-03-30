public class Knight extends Piece
{
  Knight(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Knight";
    this.moveDistance = 0;
    if(color.equals("White"))
    {
      this.symbol = Character.toUpperCase(this.type.charAt(1));
    }
    else
    {
      this.symbol = Character.toLowerCase(this.type.charAt(1));
    }
  }
  protected boolean moveKnight(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b))
    {
      return false;
    }
    if(((this.x == x+2 && (this.y == y+1 || this.y == y - 1)) ||
        (this.x == x-2 && (this.y == y+1 || this.y == y - 1)) ||
        (this.y == y+2 && (this.x == x+1 || this.x == x - 1)) ||
         (this.y == y-2 && (this.x == x+1 || this.x == x - 1))))
    {
      this.x = x;
      this.y = y;
      if(this.hasMoved)
        this.hasMovedAgain = true;
      this.hasMoved = true;
      return true;
    }
    return false;
  }

  protected boolean clearPath(int x, int y, Board b)
  {
    return true; //lol
  }

  protected boolean canKillKnight(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b))
    {
      return false;
    }
    if(((this.x == x+2 && (this.y == y+1 || this.y == y - 1)) ||
        (this.x == x-2 && (this.y == y+1 || this.y == y - 1)) ||
        (this.y == y+2 && (this.x == x+1 || this.x == x - 1)) ||
         (this.y == y-2 && (this.x == x+1 || this.x == x - 1))))
    {
      return true;
    }
    return false;
  }
}
