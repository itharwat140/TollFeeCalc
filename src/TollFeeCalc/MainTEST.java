package TollFeeCalc;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DisplayName("Running Tests")
public class MainTEST {

    private final Main testingMain = new Main();

    @Test
    @DisplayName("Free of Charges Times ")
    void isTollFreeDate(){

        LocalDateTime FirstDate = LocalDateTime.parse("2020-07-17 08:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        LocalDateTime SecondDate = LocalDateTime.parse("2020-02-23 18:50",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        LocalDateTime ThirdDate = LocalDateTime.parse("2020-10-03 04:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertTrue(Main.CostFreeDay(FirstDate));
        assertTrue(Main.CostFreeDay(SecondDate));
        assertTrue(Main.CostFreeDay(ThirdDate));
    }
}
