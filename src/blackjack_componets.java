//imports
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class blackjack_componets {
    //private vars
    private int money = 1000;
    private int amount = 0;

    public blackjack_componets(int money){
        setMoney(money);
    }

    public  blackjack_componets(){
        setMoney(1000);
    }

    //scanner
    Scanner scan = new Scanner(System.in);

    //bringing functions from the main
    Main press = new Main();

    //betting amount
    public void betting(){
        while(true) {
            System.out.println("You have, $"+ money + " available\n" +
                    "How much will you want to bet");
            try{
                int amount = scan.nextInt();
                if(amount > money){
                    System.out.println("You don\'t have that much money ");
                    continue;
                }
                this.amount = amount;
                break;
            }
            catch (Exception e){
                System.out.println("That is not a valid number\n");
                String express = scan.next();
            }
        }
    }

    //checking who won
    public void whoWon(int player, int bot){
        //if the player won,m they get the money
        if(player > bot){
            //telling the player how much they won
            System.out.println("You won $" + this.amount);
            //doing the math
            this.money += this.amount;
            this.amount = 0;
        }
        //if the bot won, the player loses via amount
        else if(player < bot){
            //teling the player how much they lost
            System.out.println("You lost $" + this.amount);
            //doing the math
            this.money -= this.amount;
            this.amount = 0;
        }
    }

    //this is the players code
    public int player(){
        int playerTotal = 0;

        //array that holds all the cards
        ArrayList<Object> cardList = new ArrayList<Object>();

        //the player gets there cards
        Random rand = new Random();
        //loop to set the cards
        for(int i = 0; i < 2; i++){
            int card = rand.nextInt(1,13);
            //if the number is null
            if(prettyPrintCard(card) == null){
                cardList.add(card);
            }
            else{
                cardList.add(prettyPrintCard(card));
            }
        }
        while(true){
            System.out.println(cardList);
            //checks if your number is not over 21
            int check = addAll(cardList);
            if(over21(check)){
                System.out.println("You sadly lost before starting\nBetter luck next time");
                playerTotal = check;
                break;
            }
            //checks if the player already won
            if(is21(check)){
                System.out.println("YOU WON BEFORE EVEN STARTING!!!");
                playerTotal = check;
                break;
            }
            //asks the player if they want to hit the deck
            System.out.println("Do you want to hit?(y/n)");
            String choice = scan.next();
            //the player hits
            if(choice.equalsIgnoreCase("y")){
                cardList.add(hitCard());
                System.out.println("You got: " + cardList.get(cardList.size() - 1) + "\n");
                //adds the total amount the player has
                check = addAll(cardList);
                //checks if the total amount is not over 21
                if(over21(check)){
                    System.out.println("You Lost\nBetter luck next time!");
                    playerTotal = check;
                    break;
                }
                if(is21(check)){
                    System.out.println("YOU WON");
                    playerTotal = check;
                    break;
                }
                continue;
            }
            //adding all and later checking if you are at 21
            check = addAll(cardList);
            if(is21(check)){
                System.out.println("YOU WON!");
                break;
            }
            playerTotal = check;
            break;
        }
        return playerTotal;
    }

    //this is the bots code
    public int bot(){
        int totalBot = 0;

        //card list
        ArrayList<Object> cardList = new ArrayList<Object>();

        //gettig the 2 cards for the bot
        Random rand = new Random();
        //loop to set the cards
        for(int i = 0; i < 2; i++){
            int card = rand.nextInt(1,13);
            //if the number is null
            if(prettyPrintCard(card) == null){
                cardList.add(card);
            }
            else{
                cardList.add(prettyPrintCard(card));
            }
        }

        //telling the player that its the bots turn
        System.out.println("Bot\'s turn\n");
        while(true){
            System.out.println(cardList);
            int check = addAll(cardList);
            //checking if the bot went over 21
            if(over21(check)){
                System.out.println("YOU WON!!");
                break;
            }
            //checking if the bot got 21
            if(is21(check)){
                System.out.println("You lost on the bots first turn. Better luck next time");
                totalBot = check;
                break;
            }
            press.pressEnter();

            //checking if the bot will hit depending on the amount of cards it has
            int chance = rand.nextInt(1,10);
            boolean hit = false;

            //bot will hit if its value is less then or equal to 10 and chance is greater than 2
            if(check <= 10 && chance >= 2){
                System.out.println("Bot hits");
                int card = rand.nextInt(1,13);
                //if the number is null
                if(prettyPrintCard(card) == null){
                    cardList.add(card);
                }
                else{
                    cardList.add(prettyPrintCard(card));
                }
                System.out.println("Bot gets a " + cardList.get(cardList.size() -1));
                hit = true;
            }
            //checks if chacnce is greater than 5
            else if(chance >= 5){
                System.out.println("Bot hits");
                int card = rand.nextInt(1,13);
                //if the number is null
                if(prettyPrintCard(card) == null){
                    cardList.add(card);
                }
                else{
                    cardList.add(prettyPrintCard(card));
                }
                System.out.println("Bot gets a " + cardList.get(cardList.size() -1));
                hit = true;
            }

            //if hit is true then it will check if the bot got 21 or is over 21
            if(hit){
                //adding all values together
                check = addAll(cardList);

                //checking if the value is over 21
                if(over21(check)){
                    System.out.println("YOU WON!!!");
                    break;
                }
                //checking if the bot got 21
                if(is21(check)){
                    System.out.println("You lost, better luck next time!!");
                    totalBot = check;
                    break;
                }
                continue;
            }

            System.out.println("Bot stands");

            //adding all values together
            check = addAll(cardList);

            //checking if the value is over 21
            if(over21(check)){
                System.out.println("YOU WON!!!");
            }
            //checking if the bot got 21
            if(is21(check)){
                System.out.println("You lost, better luck next time!!");
                totalBot = check;
            }

            totalBot = check;

            break;
        }

        return totalBot;
    }

    //checking if you won with 21
    public boolean is21(int num){
        if(num == 21){
            return true;
        }
        return false;
    }

    //checking if everything is over 21
    public boolean over21(int num){
        if(num > 21){
            return true;
        }
        return false;
    }

    //adding everything up
    public int addAll(ArrayList<Object> list){
        int sum = 0;

        //loop for all the object in the list
        for(Object index: list){
            //if the player has a face card it adds 10 to itself
            if(index.equals("Jack") || index.equals("Queen") || index.equals("King")){
                sum += 10;
            }
            //add all the numbers together
            else if(!index.equals("Ace") && index instanceof Integer){
                sum += (Integer)index;
            }
            //if the player has an ace, its either 11 or 1
            else if(index.equals("Ace"))
            {
                if(sum <= 10){
                    sum += 11;
                }
                else{
                    sum += 1;
                }
            }
        }

        return sum;
    }

    //hit card
    public Object hitCard(){
        Random hit = new Random();
        int card = hit.nextInt(1,13);

        //checking if the card returns null
        if(prettyPrintCard(card) == null){
            return card;
        }
        return prettyPrintCard(card);
    }

    //pretty print cards
    public String prettyPrintCard(int card){
        if(card == 11){
            return "Jack";
        }
        else if(card == 12){
            return "Queen";
        }
        else if(card == 13){
            return "King";
        }
        else if(card == 1){
            return "Ace";
        }
        return null;
    }

    //getters

    public int getAmount() {
        return amount;
    }

    public int getMoney() {
        return money;
    }
    //setters
    public void setMoney(int money){
        if(money <= 0){
            this.money = 1000;
        }
        this.money = money;
    }
}
