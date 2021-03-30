public class Pawn extends Piece
{
  Pawn(int x, int y, String color)
  {
    super(x,y,color);
    this.type = "Pawn";
    this.moveDistance = 2;
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
    if(((this.color.equals("White") && this.y == 4) || (this.color.equals("Black") && this.y == 3)) && b.isPiece(x,this.y))
    {
      this.workAround = true;
      if(b.list.get(b.whichPiece(x,this.y)).type.equals("Pawn")
        && kill(b.list.get(b.whichPiece(x,this.y)), b))
      {
        this.workAround = false;
        this.x = x;
        this.y = y;
        return true;
      }
      this.workAround = false;
    }
    return false;//implement for attacking and that onpassont crap
  }
  public boolean moveStraight(int x, int y, Board b)
  {
    if(this.y == y)
      return false;
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if(!this.hasMoved && ((this.y + 2 == y && this.color.equals("White"))
    || (this.y - 2 == y && this.color.equals("Black"))
    || (this.y + 1 == y && this.color.equals("White"))
    || (this.y - 1 == y && this.color.equals("Black"))) && !b.isPiece(x,y))
    {
      this.y = y;
      this.hasMoved = true;
      return true;
    }
    if(this.hasMoved && ((this.y + 1 == y && this.color.equals("White"))
    || (this.y - 1 == y && this.color.equals("Black"))) && !b.isPiece(x,y))
    {
      this.x = x;
      this.y = y;
      this.hasMovedAgain = true;
      return true;
    }
    return false;
  }

  public boolean canKillPawn(int x, int y, Board b)
  {
    if(((this.y+1 == y && (this.x+1 == x || this.x-1 == x) && this.color.equals("White"))
    || (this.y-1 == y && (this.x+1 == x || this.x-1 ==x) && this.color.equals("Black"))))
    {
      return true;
    }
    else if(b.list.get(b.whichPiece(x,y)).hasMoved && !b.list.get(b.whichPiece(x,y)).hasMovedAgain)
    {
      if(this.y == y && (this.x-1 == x || this.x+1 == x) && (y == 3 || y == 4)
        && b.list.get(b.whichPiece(x,y)).type.equals("Pawn") && this.workAround)
      {
        // if(y == 3)
        //   this.y = 2;
        // else
        //   this.y = 5;
        // this.x = x;
        return true;
      }
    }
    return false;
  }

  public void upgrade(Board b, int pawn, int piece)
  {
    int newX = b.list.get(pawn).getX();
    int newY = b.list.get(pawn).getY();
    String newColor = b.list.get(pawn).color;
    b.list.get(pawn).isAlive = false;
    switch(piece)
    {
    case 5:
      Queen newQueen = new Queen(newX, newY, newColor);
      b.list.add(pawn,newQueen);
      break;
    case 4:
      Bishop newBishop = new Bishop(newX, newY, newColor);
      b.list.add(pawn,newBishop);
      break;
    case 3:
      Rook newRook = new Rook(newX, newY, newColor);
      b.list.add(pawn,newRook);
      break;
    case 2:
      Knight newKnight = new Knight(newX, newY, newColor);
      b.list.add(pawn,newKnight);
    }
    pawn++;
    b.list.remove(pawn);
  }
}
