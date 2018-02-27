import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Simulation2 {

    Scanner scanner = new Scanner(System.in);
    public static final double START_TIME = 6.0;
    public static final double CLOSE_TIME = 20.0;

    public void gameInterface(Simulation gameWorld, double time){

        System.out.println("Welcome to "+gameWorld.getRestaurant().getName());

        System.out.print("What would you like to do? : ");
        String command = scanner.nextLine();
        command.toLowerCase();

        String[] commandList = command.split(" ");
        ArrayList<String> checkList = new ArrayList<>(Arrays.asList("time", "add", "remove", "list")) ;

        String actionVariable = commandList[0];
        if (checkList.contains(commandList[1])){
            actionVariable = commandList[0] + " " + commandList[1];
        }

        switch (actionVariable){

            case "wealth":
                if (commandList.length != 1){
                    System.out.println("I don't understand");
                    break;
                }

                System.out.println(gameWorld.getRestaurant().getWealth());
                break;

            case "time":
                if (commandList.length != 1){
                    System.out.println("I don't understand");
                    break;
                }

                System.out.println("The current time is "+time);
                break;

            case "pass time":
                String timeChange = commandList[2];
                passTime(time, timeChange);
                break;

            case ""
        }

    }

    public double passTime(double time, String timeChange){

        timeChange = timeChange.substring(1, timeChange.length() - 1);
        for (int i = 0; i < timeChange.length(); i++){
            if (!Character.isDigit(timeChange.charAt(i))){
                System.out.println("Invalid time entry");
                return time;
            }
        }
        if (Integer.parseInt(timeChange) < 0){
            System.out.println("Invalid Entry");
            return time;
        }

        time = Integer.parseInt(timeChange) % 16 + time;

        if (time > 20){
            time = (time - 20) + 6;
        }

        return time;
    }






    public static void main (String[] args){


        FileLoading fileLoader = new FileLoading();
        Simulation gameWorld = fileLoader.loadFile();
        double time = START_TIME;


    }

}
