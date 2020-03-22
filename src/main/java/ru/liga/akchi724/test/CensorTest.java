package ru.liga.akchi724.test;

import ru.liga.akchi724.Censor;
import ru.liga.akchi724.annatations.After;
import ru.liga.akchi724.annatations.Before;
import ru.liga.akchi724.annatations.Test;
import ru.liga.akchi724.asserts.MyAsserts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CensorTest {
    Censor cen;
    MyAsserts ma;
    @Before
    public void before(){
        System.out.println("Before");
        String str="Вот ты, конечно, редиска, Александр. Таких, как ты, убивать надо!";
        HashMap<String,String> badWords= new HashMap<>();
        badWords.put("редиска","нехороший человек");
        badWords.put("убивать","любить");
        cen= new Censor(str,badWords);
        ma= new MyAsserts();
    }
    @Test
    public void getSentencesTest(){

            ma.assertEquals(cen.getSentences().get(0),"Вот ты, конечно, редиска, Александр");
            ma.assertEquals(cen.getSentences().get(1),"Таких, как ты, убивать надо!");

    }
    @Test
    public void getEditedSentencesTest(){
        List<String> list= new ArrayList<>();
        list.add("гречка");
        HashMap<String,String> map = new HashMap<>();
        map.put("гречка","рис");
        ma.assertTrue(cen.getEditedSentences(list,map).get(0).equals("рис"));
    }
    @Test
    public void replacementOfUnacceptableWordsTest(){
        HashMap<String,String> badWords2= new HashMap<>();
        badWords2.put("Синица","Журавль");
        ma.assertNotNull(badWords2);
        ma.assertEquals(cen.replacementOfUnacceptableWords("Синица",badWords2),"Журавль");
        ma.assertTrue(cen.replacementOfUnacceptableWords("Синица",badWords2).equals("Журавль"));
    }
    @Test
    public void getEditedMessageTest(){
        ma.assertEquals(cen.getEditedMessage(cen.getEditedSentences(cen.getSentences(),cen.getForbiddenWords())),"Вот ты, конечно, нехороший человек, Александр. Таких, как ты, любить надо!. ");
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
