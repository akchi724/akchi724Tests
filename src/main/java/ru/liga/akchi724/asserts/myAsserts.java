package ru.liga.akchi724.asserts;

import ru.liga.akchi724.FontColor;

public class myAsserts {

    public static void assertEquals(Object first, Object second) {
        if (!first.equals(second))
            FontColor.soutRed("Тест завален:\nОжидалось:" +second+", Результат: "+first);
        else
            FontColor.soutGreen("Тест пройден!");
    }

    public static void assertTrue(Object obj) {
        if (!obj.equals(true))
            FontColor.soutRed("Тест завален:\nОжидалось:" +true+", Результат: "+false);
        else
            FontColor.soutGreen("Тест пройден!");
    }

    public static void assertNotNull(Object obj) {
        if (obj == null)
            FontColor.soutRed("Тест завален:\nОбъект равен null");
        else
            FontColor.soutGreen("Тест пройден!");
    }
}
