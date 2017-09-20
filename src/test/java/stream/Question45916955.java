package stream;

import one.util.streamex.StreamEx;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Question45916955 {

    @Test
    public void test() {
        List<Integer> originalList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 1, 1, 2, 3, 1, 2, 3, 4, 5, 6));

        List<List<Integer>> streamExList = StreamEx.of(originalList)
                .groupRuns((first, second) -> second != 1)
                .collect(Collectors.toList());

        List<List<Integer>> finalList = new ArrayList<>();
        List<Integer> currentList = new ArrayList<>();
        for (Integer current : originalList) {
            if (current == 1) {
                if (!currentList.isEmpty()) {
                    finalList.add(currentList);
                }
                currentList = new ArrayList<>();
            }
            currentList.add(current);
        }
        finalList.add(currentList);

        Assert.assertEquals(streamExList, finalList);

    }

    @Test
    public void testDay() {
        Assert.assertEquals(LocalDate.of(2015, Month.FEBRUARY, 25).getDayOfYear(), daysOfYear(25, 1, 2015));
        Assert.assertEquals(LocalDate.of(2014, Month.MARCH, 25).getDayOfYear(), daysOfYear(25, 2, 2014));
        Assert.assertEquals(LocalDate.of(2013, Month.MAY, 25).getDayOfYear(), daysOfYear(25, 4, 2013));
        Assert.assertEquals(LocalDate.of(2012, Month.JUNE, 25).getDayOfYear(), daysOfYear(25, 5, 2012));
        Assert.assertEquals(LocalDate.of(2011, Month.DECEMBER, 25).getDayOfYear(), daysOfYear(25, 11, 2011));
    }

    public static int[] days = new int[]{0,
            31,
            31 + 28,
            31 + 28 + 31,
            31 + 28 + 31 + 30,
            31 + 28 + 31 + 30 + 31,
            31 + 28 + 31 + 30 + 31 + 30,
            31 + 28 + 31 + 30 + 31 + 30 + 31,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30};

    public int daysOfYear(int day, int month, int year) {
        if (month == 0) {
            return day;
        }

        int leapOffset = 0;
        if (month == 2) {
            if (Year.of(year).isLeap()) {
                leapOffset = 1;
            }
        }
        return day + days[month] + leapOffset;
    }


}
