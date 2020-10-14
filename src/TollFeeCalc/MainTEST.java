package TollFeeCalc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing Main")
public class MainTEST {

    @Test  //1
    @DisplayName("July")
    void isTollFreeDate(){
        LocalDateTime date1 = LocalDateTime.parse("2020-07-17 08:30"
                ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date2 = LocalDateTime.parse("2020-07-23 18:50"
                ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date3 = LocalDateTime.parse("2020-07-03 04:00"
                ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Main.CostFreeDay(date1));
        assertTrue(Main.CostFreeDay(date2));
        assertTrue(Main.CostFreeDay(date3));
    }

    @Test  //2
    @DisplayName("NewDate Cost > 60")
    void GetMaximumCost() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-10-05 06:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-10-05 07:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-10-05 08:28", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[3] = LocalDateTime.parse("2020-10-05 09:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[4] = LocalDateTime.parse("2020-10-05 15:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[5] = LocalDateTime.parse("2020-10-05 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(60, Main.GetTotalCost(date));
    }

    @Test  //3
    @DisplayName("NewDate Cost < 60")
    void CostUnder60() {
        LocalDateTime[] date = new LocalDateTime[5];
        date[0] = LocalDateTime.parse("2020-10-05 06:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-10-05 06:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-10-05 08:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[3] = LocalDateTime.parse("2020-10-05 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[4] = LocalDateTime.parse("2020-10-05 17:07", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(42, Main.GetTotalCost(date));
    }

    @Test  //4
    void testSpecificTimes() {
        LocalDateTime date = LocalDateTime.parse("2020-10-05 07:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(18, Main.GetTollCostPerPassing(date));
        LocalDateTime date1 = LocalDateTime.parse("2020-10-05 15:28", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(13, Main.GetTollCostPerPassing(date1));
    }

    @Test //5
    void FreeInTheSameHour() {
        LocalDateTime[] date = new LocalDateTime[3];
        date[0] = LocalDateTime.parse("2020-10-06 12:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-10-06 12:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-10-06 13:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Main.GetTotalCost(date));
    }

    @Test //6
    void WeekEnd() {
        LocalDateTime date = LocalDateTime.parse("2020-10-03 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Main.CostFreeDay(date));
        LocalDateTime date2 = LocalDateTime.parse("2020-10-04 06:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(Main.CostFreeDay(date2));
    }

    @Test //7
    @DisplayName("TwoTimesSameHour") //first of them cost the other is free 18:00 (8KR), 18:55(0)
    void TwoTimeSecondIsFree() {
        LocalDateTime[] date = new LocalDateTime[2];
        date[0] = LocalDateTime.parse("2020-10-06 18:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-10-06 18:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(8, Main.GetTotalCost(date));
    }

    @Test  //8
    @DisplayName("TwoTimesSameHour") //first of them is free the other cost 05:30 (0), 06:10(8)
    void TwoTimeFirstIsFree() {
        LocalDateTime[] date = new LocalDateTime[2];
        date[0] = LocalDateTime.parse("2020-10-06 05:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-10-06 06:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(0, Main.GetTotalCost(date));
    }

    @Test //9
    @DisplayName("File Don't Exist")
    void DontExistFile () {
        String dontexist = "Can not open or read Vehicle\n" + "**** Loading New Vehicle ****";
        assertEquals(dontexist,Main.TollFeeCalc("src/Don'tExistFile"));
    }

    @Test //10
    @DisplayName("File Have Wrong Format")
    void WrongFormat() {
        String wrong_format = "The File Contains a Format Which Cannot Be Identified\n" +
                "**** Loading New Vehicle ****";
        assertEquals(wrong_format,Main.TollFeeCalc("src/NewDate"));
    }
}