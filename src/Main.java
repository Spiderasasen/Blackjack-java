//imports
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Random;

//main code
public class Main {
    public static void main(String[] args) {
        //calling a new scanner
        Scanner scan = new Scanner(System.in);

        //trying to check that the user does not enter a string
        int totalMoney = 1000;
        while(true){
            System.out.println("Welcome to Blackjack\n" +
                    "Please choose a valid option\n" +
                    "1. Play blackjack\n" +
                    "2. How to play blackjack\n" +
                    "3. Exit");
            try{
                int choice = scan.nextInt();
                switch (choice){
                    case 1:
                        //if totalMoney is 0
                        if(totalMoney <= 0){
//                        System.out.println("GET YOUR BROKE ASS OUTTA HERE");
                            System.out.println("You sadly don\'t have any money. " +
                                    "\nMeaning you can't play." +
                                    "\nHave a nice day\n");
                            break;
                        }

                        int money = totalMoney;
                        while(money >= 1){
                            int leftAmonut = blackjack(money);
                            System.out.println("Want to go leave?(y/n)\n");
                            String option = scan.next();
                            if(option.equalsIgnoreCase("y")){
                                totalMoney = leftAmonut;
                                break;
                            }
                            money = leftAmonut;
                        }
                        //telling the player how much they left with
                        System.out.println("You entered with $1000 and left with $" + totalMoney + "\n");
                        break;
                    case 2:
                        System.out.println("The Rules are simple\n" +
                                "Get as close as you can to 21\n" +
                                "Ace\'s can either be 1 or 11\n" +
                                "Jack\'s, Queen\'s, and King\'s are all equal to 10\n" +
                                "who ever is closer to 21 wins\n" +
                                "If you have 21 you automatically win");
                        pressEnter();
                        break;
                    case 3:
                        System.out.println("Leaving the game");
                        System.exit(0);
                    default:
                        System.out.println("Please enter a valid number from 1 - 3");
                        break;
                }
            }
            //user put a string instead of a number
            catch (Exception e){
                System.out.println("Please enter a valid number");
                String exstion = scan.next();
            }
        }

    }

    //functions
    //asking the user to press enter to contuine the segment they are in
    public static void pressEnter(){
        //scanner
        Scanner enter = new Scanner(System.in);

        //asking the player to press enter to contuine
        System.out.println("Please press enter to contuine");
        String exstion = enter.nextLine();
    }

    public static int blackjack(int money){
        //calling the compnets
        blackjack_componets blackjack = new blackjack_componets(money);

        //building the system
        //betting system
        blackjack.betting();

        //players turn
        int player = blackjack.player();
//        pressEnter();

        //if the player got a 21
        if(blackjack.is21(player)){
            blackjack.whoWon(player, 0);
            return blackjack.getMoney();
        }

        //if the player went over 21
        if(blackjack.over21(player)){
            blackjack.whoWon(player,100);
            return blackjack.getMoney();
        }

        int bot = blackjack.bot();
        pressEnter();

        //if the bot got a 21
        if(blackjack.is21(bot)){
            blackjack.whoWon(0, bot);
            return blackjack.getMoney();
        }

        //if nither got 21, then it depends on who got closer to 21
        blackjack.whoWon(player, bot);

        return blackjack.getMoney();
    }
}