import java.util.ArrayList;
public class Knight extends Piece
{
  Knight(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Knight";
    this.moveDistance = 0;
    this.value = 3;
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

  protected ArrayList<Integer> getMoves()
  {
    ArrayList<Integer> moves = new ArrayList<Integer>();
    int x = this.getX();
    int y = this.getY();

    if(y < 6)
    {
      if(x > 0)
        moves.add((y+2)*8+x-1);
      if(x < 7)
        moves.add((y+2)*8+x+1);
    }
    if(y < 7)
    {
      if(x < 6)
        moves.add((y+1)*8+x+2);
      if(x > 1)
        moves.add((y+1)*8+x-2);
    }
    if(y > 1)
    {
      if(x > 0)
        moves.add((y-2)*8+x-1);
      if(x < 7)
        moves.add((y-2)*8+x+1);
    }
    if(y > 0)
    {
      if(x > 1)
        moves.add((y-1)*8+x-2);
      if(x < 6)
        moves.add((y-1)*8+x+2);
    }
    // int[] arr = new int[moves.size()];
    // for(int i = 0; i < moves.size(); i++)
    // {
    //   arr[i] = moves.get(i);
    // }
    // return arr;
    return moves;
  }

}
