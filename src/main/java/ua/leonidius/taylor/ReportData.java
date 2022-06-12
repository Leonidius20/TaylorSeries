package ua.leonidius.taylor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportData {

    public ArrayList<SingleReport> runs = new ArrayList<>();

    public static class SingleReport {
        public int iterations;
        public Map<String, Long> titlesAndTimes = new HashMap<>();
        public double speedup;
    }

}
