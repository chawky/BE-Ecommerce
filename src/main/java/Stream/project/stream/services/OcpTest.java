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
 record Song(String title, Duration duration) {
     public Song(String title, int seconds) {
         this(title,Duration.ofSeconds(seconds));
     }
}


public class OcpTest {
    public static void main(String[] args) {
        Song song = new Song("Imagine", 106);
        System.out.println(song);
    }
}
