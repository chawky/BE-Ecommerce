package Stream.project.stream.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SUNDAY;

import java.time.Duration;
class  C{
    String name;
}

public class OcpTest {
    public static void main(String[] args) {
        C c1 = new C();
        C c2 = c1;
        c2.name = "c2";
        System.out.println(c1.name);
        c2 = null;
        System.out.println(c2.name);


    }
}
