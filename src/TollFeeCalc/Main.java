package TollFeeCalc;
//Mudasser Abbass & Islam Mohamed
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {

    public static String TollFeeCalc(String inputFile) throws DateTimeParseException{
        String errorMessage = "";
        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] Vehicles = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[Vehicles.length];
            for(int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(Vehicles[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("The Total Payment for the current Vehicle: " + GetTotalCost(dates));
            System.out.println("Getting Input From New Vehicle, Please Wait");
            System.out.println("*******************************************");
        } catch(FileNotFoundException e) {
            System.err.println("Can not open or read Vehicle");
            System.err.println("**** Loading New Vehicle ****");
            errorMessage = "Can not open or read Vehicle\n" + "**** Loading New Vehicle ****";
        } catch (DateTimeParseException e){
            System.err.println("The File Contains a Format Which Cannot Be Identified");
            System.err.println("**** Loading New Vehicle ****");
            errorMessage = "The File Contains a Format Which Cannot Be Identified\n" + "**** Loading New Vehicle ****";
        }
        return errorMessage;
    }

    public static int GetTotalCost (LocalDateTime[] dates) {
        int TotalCost = 0;
        int TotalDayCost = 0;
        LocalDateTime Cal = dates[0];

        for(LocalDateTime date: dates) {
            long Min = Cal.until(date, ChronoUnit.MINUTES);
            long Days = Cal.until(date, ChronoUnit.DAYS);
            System.out.println(date.toString());
            if(Min > 60 || Days > 0 || Cal.equals(date) ) {
                Cal = date;
                TotalDayCost += GetTollCostPerPassing(date);
                System.out.println("+ " + GetTollCostPerPassing(date) + " To the daily Cost");
                if (GetTollCostPerPassing(date) == 0) {
                    System.out.println("Free Hour/Day");
                }
                if (Days > 0) {
                    System.out.println("This is the end of the day, Your current totalDayCost is: "+ TotalDayCost);
                    TotalCost += Math.min(60,TotalDayCost);
                    TotalDayCost = 0;
                }
            } else {
                System.out.println("You already been charged for this passage ");
            }
            System.out.println(" ");
        }
        TotalCost += Math.min(60,TotalDayCost);
        return TotalCost;
    }

    public static int GetTollCostPerPassing(LocalDateTime date) {
        if (CostFreeDay(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean CostFreeDay(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args)  {
        System.out.println("**** Loading New Vehicle ****");
        TollFeeCalc("src/Vehicle1");
        TollFeeCalc("src/Vehicle2");
        TollFeeCalc("src/Vehicle3");
        TollFeeCalc("src/Vehicle4");
    }
}

