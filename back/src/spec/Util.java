package spec;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    public static int compareDate(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);

        return localDate1.compareTo(localDate2);
    }

    public static void main(String[] args) {
        System.out.println(compareDate("2023-10-31", "2023-10-10"));
    }
}
