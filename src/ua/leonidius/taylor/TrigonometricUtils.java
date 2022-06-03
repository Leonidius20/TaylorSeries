package ua.leonidius.taylor;

public class TrigonometricUtils {

    public static double simplifyAngle(double angle) {
        return Math.atan2(Math.sin(angle), Math.cos(angle)); // normalize\wrap between -pi and pi
    }

}
