package ru.liga.akchi724;

public class FontColor {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void soutGreen(String str){
        System.out.println(ANSI_GREEN+str+ANSI_RESET);
    }
    public static void soutRed(String str){
        System.out.println(ANSI_RED+str+ANSI_RESET);
    }
    public static void soutBlue(String str){
        System.out.println(ANSI_BLUE+str+ANSI_RESET);
    }
}
