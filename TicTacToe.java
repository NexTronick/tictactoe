/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package game;

import java.util.Scanner;
/**
 *
 * @author NexTronick
 */
public class TicTacToe {
    public static Scanner in = new Scanner(System.in);
    public static enum  Players {X,O}
	
    public static void main(String[] args) {
        
        Players[] player = new Players[9];
        String[] placed = new String[9];
        String[] valuePlace= {
            "A1","B1","C1","A2","B2","C2","A3","B3","C3"
        };
        
        int len = placed.length -1;
        int running = 0;
        String whoWon = "";
        updatePrint(player);
        while (running <9){
            
            System.out.println("Player 1 Place X(eg, A1 or C3):");
            String xPlace = (in.nextLine()).trim();

            while (checkTaken(valuePlace, player, xPlace) ==true) {
                System.out.println("Player 1 Place X again: ");
                xPlace = (in.nextLine()).trim();
             
            }
            
            xPlace = fixPosition(xPlace);
            
            for (int i = 0; i < valuePlace.length; i++) {
                if(valuePlace[i].equals(xPlace)){                  
                    player[i] = Players.X;
                    placed[i] = xPlace;  
                    running++;
                } 
            }
            
            updatePrint(player);
            //check if there is a winner
            whoWon = checkWins(player, Players.X);
            if(!whoWon.equals("")) {
                System.out.println(whoWon);
                break;
            }
            if (running == 8) {
                //check if it won or not if not then output TIE
                System.out.println("Player X and Player O TIED");
                break;
            }
            System.out.println("Player 2 Place O(eg, A2 or C1)");
            String oPlace = (in.nextLine()).trim();

            while (checkTaken(valuePlace, player, oPlace)==true) {
                System.out.println("Player 2 Place O again: ");
                oPlace = (in.nextLine()).trim();
            }
            
            oPlace = fixPosition(oPlace);
            
            for (int i = 0; i < valuePlace.length; i++) {
                if(valuePlace[i].equals(oPlace)){                  
                    player[i] = Players.O;
                    placed[i] = oPlace;
                    running++;
                } 
            }
            updatePrint(player);
            
            whoWon = checkWins(player, Players.O);
            if(!whoWon.equals("")) {
                System.out.println(whoWon);
                break;
            }
        }
    } 
    public static void updatePrint(Players[] player) { //prints the output
        
        /* Printing methond would be usng */
        String printValue = "  A B C \n";
        
        for (int i = 0,j=1; i < player.length; i++, j++) {
           
            printValue += (i==0) ? 1+" " :"";
            printValue += (i==3) ? 2+" " :"";
            printValue += (i==6) ? 3+" " :"";
           if(player[i] !=null){
               printValue += player[i]==Players.X ? "X":"O";
		if(j%3 != 0){
		   printValue += "|";
		} else {
		   printValue +="\n";
		}
           }else {
		printValue += " ";
		if(j%3 != 0){
		 printValue += "|";
		} else {
		  printValue +="\n";
		}
	    }
           
        }
      
        System.out.println(printValue);
    }
	public static boolean checkIsRunning(Players[] player) {
        
            for (int i = 0; i < player.length; i++) {
                if (player[i] == null) {
                   return true;
                }
            }
            return false;
        }
	public static boolean checkTaken(String[] valuePlace,Players[] player,String place) {
            //check if which value is place, so we need to seperate check it.
            // either a-c or A-C can be used and either 1a or A1 can be used
            // first to check if the first 2 value is in range
            
            String fixedPlace = fixPosition(place);
            if(fixedPlace.equals("")) {return true;}
            
            
            for (int i=0; i<player.length; i++) {
                if(valuePlace[i].equals(fixedPlace)&& player[i] != null) {
                    return true;
                }
            }
            return false;//if its false then correct input
	}
        public static String fixPosition(String place) { //fixes the postion to be A1 or C3 letter and number
            if (place.length() >2 || place.length() <2) {
                return "";
            }
            char firstPlace = place.charAt(0);
            char secondPlace = place.charAt(1);
            if (firstPlace >= 'a' && firstPlace <= 'c' || firstPlace >= 'A' && firstPlace <= 'C') {
                if (secondPlace >= '1' && secondPlace <= '3') {
                    if(firstPlace >= 'a' && firstPlace <= 'c' ) {
                        place = place.substring(0,1).toUpperCase() +""+secondPlace;
                    }
                    return place;
                }
            } else if(firstPlace >= '1' && firstPlace <= '3') {
                if (secondPlace >= 'a' && secondPlace <= 'c' || secondPlace >= 'A' && secondPlace <= 'C') {
                    place = secondPlace +""+ firstPlace;
                    if(secondPlace >= 'a' && secondPlace <= 'c' ) {
                        place = place.substring(0,1).toUpperCase() +""+place.charAt(1);
                    }
                    return place;
                }
            }
            return "";
        }
  /*
    first look for sides:  
        a1,c1,a3,c3 if the remainder is 0 when divisible by 2 
    second look for +: b1, a2, c2, b3
    third look for the middle: 
        b2
    
    */
    public static String checkWins(Players[] player, Players check) {
    
        //firstCheckCorner check every 3 ways
        int switchNum;
        String won="";
        String give="";
        //there are 3 cases of checking horizontally
        
        for (int i = 0, j = 1; i < player.length; i++, j++) {
            if (player[i] == check) {
                if(j%3 == 0) {
                    switchNum = 3;
                    won = checkRowAndColum(player, i,switchNum);
                    
                }
                else if(j == 2 || j==5 || j==8) {
                    switchNum = 2;
                    won = checkRowAndColum(player, i,switchNum);
                    
                } 
                else { 
                    switchNum = 1;
                    won = checkRowAndColum(player, i,switchNum);
                   
                }
                
            }
        }
        if (won.equals("X")) {
            return "Player 1 (or X) Won!";
        } else if (won.equals("O")) {
            return "Player 2 (or O) Won!";
        }
        
        //checks for cross
        for (int i = 0, j=1; i < player.length; i++, j++) {
            //not divisible by 2 that means its uneven (its needed for doing cross check)  
            if (player[i] == check && j%2 != 0) {
                   
                if (j < 4) { //
                    if ((i == 0) && player[i] == player[i+4] && player[i] == player[i+8]) {
                        if (player[i] == Players.X) {
                            give =  "Player 1 (or X) Won!";
                            return give;
                        } else if(player[i] == Players.O) {
                            give = "Player 2 (or O) Won!";
                            return give;
                        }
                    }else if (i == 3 && player[i] == player[i+2] && player[i] == player[i+4]){
                         if (player[i] == Players.X) {
                            give =  "Player 1 (or X) Won!";
                            return give;
                        } else if(player[i] == Players.O) {
                            give = "Player 2 (or O) Won!";
                            return give;
                        }
                    }
                    
                }else if (j == 5) {
                    if(player[i] == player[i-2] && player[i]== player[i+2]) {
                        if (player[i] == Players.X) {
                            give =  "Player 1 (or X) Won!";
                            return give;
                        } else if(player[i] == Players.O) {
                            give = "Player 2 (or O) Won!";
                            return give;
                        }
                    } else if(player[i] == player[i-4] && player[i] == player[i+4]) {
                        if (player[i] == Players.X) {
                                give =  "Player 1 (or X) Won!";
                                return give;
                            } else if(player[i] == Players.O) {
                                give = "Player 2 (or O) Won!";
                                return give;
                            }
                        }
                } else {
                    if (j==7 && player[i] == player[i-2] && player[i] == player[i-4]) {
                        if (player[i] == Players.X) {
                            give =  "Player 1 (or X) Won!";
                            return give;
                        } else if(player[i] == Players.O) {
                            give = "Player 2 (or O) Won!";
                            return give;
                        }
                    } else if (j==9 && player[i] == player[i-4] && player[i] == player[i-8]) {
                        if (player[i] == Players.X) {
                            give =  "Player 1 (or X) Won!";
                            return give;
                        } else if(player[i] == Players.O) {
                            give = "Player 2 (or O) Won!";
                            return give;
                        }
                    }
                }
             
            }
        }
        return give; //no one won
    }
    
    public static String checkRowAndColum(Players[] player, int index, int switchNum) {
        /*
        Rows:
            uses the switchNum to ditermind which are should we check 
            if switchNum is 3 then its 3rd right one in row;
            2nd will be middle and 1 will be the rest that is left\
        Colums:
            similar to rows 
            but this time it will plus or minus 3 to check if its the same or not
            
        */
        String wins ="";
        switch(switchNum) {
            case 1:
                if(player[index] == player[index+1] && player[index] == player[index+2]) {
                    wins = ""+player[index];
                    return wins;
                } else {
                    if (index <3 ) {
                        if (player[index] == player[index+3] && player[index] == player[index+6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                        
                    }else if(index>=3 && index <6) {
                        if (player[index] == player[index-3] && player[index] == player[index+3]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    } else{
                        if (player[index] == player[index-3] && player[index] == player[index-6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    }
                }
                break;
            case 2:
                if (player[index] == player[index-1] && player[index] == player[index+1]) {
                    wins = ""+player[index];
                    return wins;
                } else {
                    if (index <3 ) {
                        if (player[index] == player[index+3] && player[index] == player[index+6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                        
                    }else if(index>=3 && index <6) {
                        if (player[index] == player[index-3] && player[index] == player[index+3]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    } else{
                        if (player[index] == player[index-3] && player[index] == player[index-6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    }
                }
                break;
            case 3: 
                if (player[index] == player[index-1] && player[index] == player[index-2]) {
                    wins = ""+player[index];
                    return wins;
                } else {
                    if (index <3 ) {
                        if (player[index] == player[index+3] && player[index] == player[index+6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                        
                    }else if(index>=3 && index <6) {
                        if (player[index] == player[index-3] && player[index] == player[index+3]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    } else{
                        if (player[index] == player[index-3] && player[index] == player[index-6]) {
                            wins = ""+player[index];
                            return wins;
                        }
                    }
                }
                break;
        }      
        return wins;
    }     
}

