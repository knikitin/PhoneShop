package phonesshop.util;

public class DebugMode {
    private static boolean DebugState = false;

    public static void setDebugModeOn() {
        DebugState = true;
    }

    public static void setDebugModeOff() {
        DebugState = false;
    }

    public static boolean isDebug(){
        return DebugState;
    }

}
