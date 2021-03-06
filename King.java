import java.util.ArrayList;
public class King extends Piece
{
  King(int x, int y, String color)
  {
    super(x,y,color);
    this.moveDistance = 1;
    this.type = "King";
    this.value = 999999999;
    if(color.equals("White"))
    {
      this.symbol = Character.toUpperCase(this.type.charAt(0));
    }
    else
    {
      this.symbol = Character.toLowerCase(this.type.charAt(0));
    }
  }
  protected boolean checkCheck(Board b, int king)
  {
    for(int i = 0; i < b.list.size(); i++)
    {
      if(b.list.get(i).color.equals(b.list.get(king).color) || !b.list.get(i).isAlive)
        continue;
      if(b.list.get(i).clearPath(b.list.get(king).getX(),b.list.get(king).getY(),b))
      {
        if(b.list.get(i).canKill(b.list.get(king).getX(),b.list.get(king).getY(),b))
        {
          return true;
        }
      }
    }
    return false;
  }
  protected boolean checkMate(Board b, int king)
  {
    for(int piece = 0; piece < b.list.size(); piece++)
    {
      boolean tempHasMoved = b.list.get(piece).hasMoved;
      boolean tempHasMovedAgain = b.list.get(piece).hasMovedAgain;
      if(!b.list.get(piece).color.equals(b.list.get(king).color) || !b.list.get(piece).isAlive)
      {
        continue;
      }
      for(int i = 0; i < 8; i++)
      {
        for(int j = 0; j < 8; j++)
        {
          int newX = b.list.get(piece).getX();
          int newY = b.list.get(piece).getY();
          if(b.isPiece(i,j))
          {
            // if(b.list.get(piece).type.equals("King") && b.list.get(b.whichPiece(i,j)).type.equals("Rook")
            // && b.list.get(piece).color.equals(b.list.get(b.whichPiece(i,j))) && this.color.equals(b.list.get(piece).color))
            // {
            //   int tempX = this.x;
            //   int tempY = this.y;
            //   int tempPieceIndex = b.whichPiece(i,j);
            //   int rookX = b.list.get(tempPieceIndex).getX();
            //   int rookY = b.list.get(tempPieceIndex).getY();
            //   if(this.castle(b.list.get(tempPieceIndex),b))
            //   {
            //     this.x = tempX;
            //     this.y = tempY;
            //     b.list.get(i).setXandY(rookX,rookY);
            //     this.hasMoved = false;
            //     b.list.get(i).hasMoved = false;
            //     return false;
            //   }
            // }
            if(b.list.get(piece).kill(b.list.get(b.whichPiece(i,j)),b))
            {
              if(!this.checkCheck(b,king))
              {
                // System.out.println("You can kill " + b.lastKilled + " with " + b.list.get(piece));
                b.list.get(piece).setXandY(newX,newY);
                b.list.get(piece).hasMoved = tempHasMoved;
                b.list.get(piece).hasMovedAgain = tempHasMovedAgain;
                b.lastKilled.isAlive = true;
                b.lastKilled = null;
                return false;
              }
              b.list.get(piece).setXandY(newX,newY);
              b.lastKilled.isAlive = true;
              b.lastKilled = null;
            }
          }
          else
          {
            if(b.list.get(piece).move(i,j,b))
            {
              if(!this.checkCheck(b,king))
              {
                // System.out.println("You can move " + b.list.get(piece) + " to " + i + " " + j);
                b.list.get(piece).setXandY(newX,newY);
                b.list.get(piece).hasMoved = tempHasMoved;
                b.list.get(piece).hasMovedAgain = tempHasMovedAgain;
                return false;
              }
              b.list.get(piece).setXandY(newX,newY);
            }
          }
        }
      }
      b.list.get(piece).hasMoved = tempHasMoved;
      b.list.get(piece).hasMovedAgain = tempHasMovedAgain;
    }
    return true;
  }

  // protected boolean move(int x, int y, Board b)
  // {
  //   if(x == this.x && y == this.y)
  //   {
  //     System.out.println("the piece didn't move");
  //     return false;
  //   }
  //   if(this.checkBounds(x,y,b))
  //     {
  //       this.x = x;
  //       this.y = y;
  //       if(this.hasMoved)
  //       {
  //         this.hasMovedAgain = true;
  //       }
  //       this.hasMoved = true;
  //     }
  // }
  // protected boolean checkBounds(int x, int y, Board b)
  // {
  //   if((x+this.moveDistance == this.x || x == this.x || x-this.moveDistance == this.x)&&
  //   (y+this.moveDistance == this.y || y == this.y || y-this.moveDistance == this.y))
  //   {
  //     return true;
  //   }
  //   return false;
  // }
  protected boolean castle(Piece r, Board b)
  {
    //check if left rook
    if(r.x == 0 && this.y == r.y)
    {
      //check for pieces in the way
      for(int i = 1; i < this.x; i++)
      {
        if(b.isPiece(i,this.y))
        {
          return false;
        }
      }
      if(this.hasMoved || r.hasMoved)
      {
        return false;
      }
      int tempX = this.x;
      int pieceIndex = b.whichPiece(this.x,this.y);
      --this.x;
      for(;this.x > tempX-2; this.x--)
      {
        if(this.checkCheck(b,pieceIndex))
        {
          this.x = tempX;
          return false;
        }
      }
      //this.x-=2;
      r.x+=3;
      r.hasMoved = true;
      this.hasMoved = true;
      return true;
    }
    if(r.x==7  && this.y == r.y)
    {
      for(int i = 6; i > this.x; i--)
      {
        if(b.isPiece(i,this.y))
        {
          return false;
        }
      }
      if(this.hasMoved || r.hasMoved)
      {
        return false;
      }
      int tempX = this.x;
      int pieceIndex = b.whichPiece(this.x,this.y);
      this.x++;
      for(;this.x < tempX+2; this.x++)
      {
        if(this.checkCheck(b,pieceIndex))
        {
          this.x = tempX;
          return false;
        }
      }
      r.x-=2;
      r.hasMoved = true;
      this.hasMoved = true;
      return true;
    }
    return false;
  }
  protected boolean moveDiag(int x, int y, Board b)
  {
    if(!this.checkBounds(x,y,b) || !this.clearPath(x,y,b))
    {
      return false;
    }
    if((this.x - 1 == x || this.x + 1 == x) && (this.y - 1 == y || this.y + 1 == y))
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
    if(((this.x == x && (this.y == y+1 || this.y == y-1)) || (this.y == y && (this.x == x-1 || this.x == x+1))))
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
    if(((this.x == x && (this.y == y+1 || this.y == y-1)) || (this.y == y && (this.x == x-1 || this.x == x+1))))
    {
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
    if((this.x - 1 == x || this.x + 1 == x) && (this.y - 1 == y || this.y + 1 == y))
    {
      return true;
    }
    return false;
  }

  protected ArrayList getMoves()
  {
    ArrayList<Integer> moves = new ArrayList<Integer>();
    int x = this.getX();
    int y = this.getY();
    if(x < 7)
    {
      if(y < 7)
        moves.add((y+1)*8 + x+1);
      if(y > 0)
        moves.add((y-1)*8 + x+1);
      moves.add(y*8+x+1);
    }
    if(x > 0)
    {
      if(y < 7)
        moves.add((y+1)*8+x-1);
      if(y > 0)
        moves.add((y-1)*8+x-1);
      moves.add(y*8+x-1);
    }
    if(y > 0)
      moves.add((y-1)*8+x);
    if(y < 7)
    {
      moves.add((y+1)*8+x);
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
