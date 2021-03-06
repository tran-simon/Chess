package Controller;

import Data.Board;
import Data.Point;

import static Data.Board.*;

/**
 * Created by simon on 06/12/16.
 */
public class Piece{
    
    
    public Point position;
    public int type;//tochange
    public boolean isTeamWhite;
    public boolean selected = false;
    public boolean isPossibleDestination = false;
    boolean isDead = false;
    
    public Point originalPosition;
    
    public Piece(int theType, boolean isTeamWhite){
        type = theType;
        this.isTeamWhite = isTeamWhite;
    }
    
    
    public Piece(){
    }
    
    public static Piece createEmptyAt(Point position){
        Piece piece = new Piece(EMPTY, false);
        piece.setPosition(position);
        return piece;
    }
    
    public Point[] getPossibleDirection(Board theBoard){
        Point[] result;// = new Point[0];
        switch(type){
            case PAWN:
                result = getPossiblePawnDirection(theBoard);
                break;
            case ROOK:
                result = getPossibleRookDirection(theBoard);
                break;
            case KNIGHT:
                result = getPossibleKnightDirection(theBoard);
                break;
            case BISHOP:
                result = getPossibleBishopDirection(theBoard);
                break;
            case QUEEN:
                result = getPossibleQueenDirection(theBoard);
                break;
            case KING:
                result = getPossibleKingDirection(theBoard);
                break;
            default:
                System.out.println("ERROR " + getType() + " ]");
                result = new Point[0];
                break;
        }
        
        
        return result;
    }
    
    public Point[] getPossiblePawnDirection(Board theBoard){
        Point[] result = new Point[4];
        
        int directionY = 1;
        if(!isTeamWhite){
            directionY = -1;
        }
        Point relativePoint = new Point(-1, directionY);
        Point absolutePoint;
        Piece object;
        int possibilityNb = 0;
        int i = 0;
        for(i = 0; i < 3; i++){
            absolutePoint = relativePoint.addPoint(position);
            if(absolutePoint.isInRect(theBoard.getWidth(), theBoard.getHeight())){
                object = theBoard.objectAt(absolutePoint);
                if(object.type == EMPTY){
                    if(relativePoint.x == 0){
                        result[possibilityNb] = absolutePoint;
                        possibilityNb++;
                    }
                }
                else if(object.isTeamWhite != this.isTeamWhite && relativePoint.x != 0){
                    result[possibilityNb] = absolutePoint;
                    possibilityNb++;
                }
            }
            relativePoint.x++;
        }
        if(position.equals(originalPosition)){
            relativePoint.x = 0;
            relativePoint.y = directionY * 2;
            absolutePoint = relativePoint.addPoint(position);
            object = theBoard.objectAt(absolutePoint);
            if(object.type == EMPTY){
                result[possibilityNb] = absolutePoint;
                possibilityNb++;
            }
        }
        
        if(result.length != possibilityNb){
            Point[] result1 = new Point[possibilityNb];
            for(int j = 0; j < possibilityNb; j++){
                result1[j] = result[j];
            }
            return result1;
        }
        return result;
    }
    
    public Point[] getPossibleRookDirection(Board theBoard){
        
        Point[] result = new Point[64];
        
        int i = 0;
        Point point = new Point(1, 0);
        
        int limitsReached = 0; //Stop when reaches 4 limits (end of board or pieces)
        Piece object;
        
        int nbToAddToX = 1;
        int nbToAddToY = 0;
        boolean checkNextDirection = false;
        Point absolutePoint;
        while(limitsReached < 4){
            absolutePoint = point.addPoint(position);
            if(absolutePoint.isInRect(theBoard.getWidth(), theBoard.getHeight())){
                object = theBoard.objectAt(absolutePoint);
                if(object.type == EMPTY){
                    result[i] = absolutePoint;
                    i++;
                }
                else{
                    if(object.isTeamWhite != this.isTeamWhite){
                        result[i] = absolutePoint;
                        i++;
                    }
                    checkNextDirection = true;
                }
            }
            else{
                checkNextDirection = true;
            }
            if(checkNextDirection){
                checkNextDirection = false;
                limitsReached++;
                switch(limitsReached){
                    case 1:
                        nbToAddToX = -1;
                        nbToAddToY = 0;
                        break;
                    case 2:
                        nbToAddToX = 0;
                        nbToAddToY = 1;
                        break;
                    case 3:
                        nbToAddToX = 0;
                        nbToAddToY = -1;
                        break;
                }
                point.x = 0;
                point.y = 0;
            }
            point.x += nbToAddToX;
            point.y += nbToAddToY;
        }
        Point[] result1 = new Point[i];
        for(int j = 0; j < i; j++){
            result1[j] = result[j];
        }
        return result1;
    }
    
    public Point[] getPossibleKnightDirection(Board theBoard){
        Point[] result = new Point[64];
        int i = 0;
        
        int x = 2;
        int y = 1;
        
        for(int j = 0; j < 8; j++){
            Point point = new Point(x + position.x, y + position.y);
            if(point.isInRect(theBoard.getWidth(), theBoard.getHeight())){
                Piece object = theBoard.objectAt(point);
                if(object.type == EMPTY || object.isTeamWhite != this.isTeamWhite){
                    result[i] = point;
                    i++;
                }
            }
            if(y <= 0){
                x += (x < 0) ? 1 : -1;
                y--;
                
            }
            y *= -1;
            if(x == 0){
                y = 1;
                x = -2;
            }
        }
        Point[] result1 = new Point[i];
        for(int j = 0; j < i; j++){
            result1[j] = result[j];
        }
        return result1;
        
    }
    
    public Point[] getPossibleBishopDirection(Board theBoard){
        Point[] result = new Point[64];
        
        int i = 0;
        Point point = new Point(1, 1);
        
        int limitsReached = 0; //Stop when reaches 4 limits (end of board or pieces)
        Piece object;
        
        int nbToAddToX = 1;
        int nbToAddToY = 1;
        boolean checkNextDirection = false;
        Point absolutePoint;
        while(limitsReached < 4){
            absolutePoint = point.addPoint(position);
            if(absolutePoint.isInRect(theBoard.getWidth(), theBoard.getHeight())){
                object = theBoard.objectAt(absolutePoint);
                if(object.type == EMPTY){
                    result[i] = absolutePoint;
                    i++;
                }
                else{
                    if(object.isTeamWhite != this.isTeamWhite){
                        result[i] = absolutePoint;
                        i++;
                    }
                    checkNextDirection = true;
                }
            }
            else{
                checkNextDirection = true;
            }
            if(checkNextDirection){
                checkNextDirection = false;
                limitsReached++;
                nbToAddToX *= -1;
                if(nbToAddToX < 0){
                    nbToAddToY *= -1;
                }
                point.x = 0;
                point.y = 0;
            }
            point.x += nbToAddToX;
            point.y += nbToAddToY;
        }
        Point[] result1 = new Point[i];
        for(int j = 0; j < i; j++){
            result1[j] = result[j];
        }
        return result1;
    }
    
    public Point[] getPossibleQueenDirection(Board theBoard){
        
        Point[] directionsStraight = getPossibleRookDirection(theBoard);
        Point[] directionsDiagonal = getPossibleBishopDirection(theBoard);
        
        Point[] result = new Point[directionsStraight.length + directionsDiagonal.length];
        int i = 0;
        for(Point point : directionsStraight){
            result[i] = point;
            i++;
        }
        for(Point point : directionsDiagonal){
            result[i] = point;
            i++;
        }
        return result;
        
    }
    
    public Point[] getPossibleKingDirection(Board theBoard){
        Point[] result = new Point[8];
        
        Point relativePosition = new Point(-1, -1);
        Point absolutePosition;
        int possibilityNb = 0;
        Piece pieceAt;
        for(int i = 0; i < 8; i++){
            absolutePosition = position.addPoint(relativePosition);
            if(absolutePosition.isInRect(theBoard.getWidth(), theBoard.getHeight())){
                pieceAt = theBoard.objectAt(absolutePosition);
                if(pieceAt.isTeamWhite != this.isTeamWhite || pieceAt.type == EMPTY){
                    result[possibilityNb] = absolutePosition;
                    possibilityNb++;
                }
            }
            if(relativePosition.y == 0){
                if(relativePosition.x == 1){
                    relativePosition.x = 0;
                }
                else{
                    relativePosition.x = 1;
                }
                relativePosition.y = -1;
            }
            else if(relativePosition.y == -1){
                relativePosition.y = 1;
            }
            else if(relativePosition.y == 1){
                relativePosition.y = 0;
            }
        }
        if(result.length != possibilityNb){
            Point[] result1 = new Point[possibilityNb];
            for(int j = 0; j < possibilityNb; j++){
                result1[j] = result[j];
            }
            return result1;
        }
        return result;
        
    }
    
    public int getType(){
            return type;
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    public String getStringType(){
        char character = (char) getType();
        switch(getType()){
            case EMPTY:
                character = ' ';
                break;
            default:
                character += '0';
                break;
            
            
        }
        return Character.toString(character);
    }
    
    
    public void setPosition(Point point){
        position = point;
    }
    public void setOriginalPosition(Point point){
        originalPosition = point;
    }
    
    public String getName(){
        String result = "";
        switch(type){
            case EMPTY:
                result = "EMPTY";
                break;
            case PAWN:
                result = "PAWN";
                break;
            case ROOK:
                result = "ROOK";
                break;
            case KNIGHT:
                result = "KNIGHT";
                break;
            case BISHOP:
                result = "BISHOP";
                break;
            case QUEEN:
                result = "QUEEN";
                break;
            case KING:
                result = "KING";
                break;
            
            default:
                result = "EMPTY ERROR";
                break;
            
        }
        return result;
    }
}
