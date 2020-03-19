package ru.liga.akchi724.test;

import ru.liga.akchi724.Censor;
import ru.liga.akchi724.annatations.After;
import ru.liga.akchi724.annatations.Before;
import ru.liga.akchi724.annatations.Test;
import ru.liga.akchi724.asserts.myAsserts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CensorTest {
    Censor cen;
    @Before
    public void before(){
        System.out.println("Before");
        String str="Вот ты, конечно, редиска, Александр. Таких, как ты, убивать надо!";
        HashMap<String,String> badWords= new HashMap<>();
        badWords.put("редиска","нехороший человек");
        badWords.put("убивать","любить");
        cen= new Censor(str,badWords);
    }
    @Test
    public void getSentencesTest(){

            myAsserts.assertEquals(cen.getSentences().get(0),"Вот ты, конечно, редиска, Александр");
            myAsserts.assertEquals(cen.getSentences().get(1),"Таких, как ты, убивать надо!");

    }
    @Test
    public void getEditedSentencesTest(){
        List<String> list= new ArrayList<>();
        list.add("гречка");
        HashMap<String,String> map = new HashMap<>();
        map.put("гречка","рис");
        myAsserts.assertTrue(cen.getEditedSentences(list,map).get(0).equals("рис"));
    }
    @Test
    public void replacementOfUnacceptableWordsTest(){
        HashMap<String,String> badWords2= new HashMap<>();
        badWords2.put("Синица","Журавль");
        myAsserts.assertNotNull(badWords2);
        myAsserts.assertEquals(cen.replacementOfUnacceptableWords("Синица",badWords2),"Журавль");
        myAsserts.assertTrue(cen.replacementOfUnacceptableWords("Синица",badWords2).equals("Журавль"));
    }
    @Test
    public void getEditedMessageTest(){
        myAsserts.assertEquals(cen.getEditedMessage(cen.getEditedSentences(cen.getSentences(),cen.getForbiddenWords())),"Вот ты, конечно, нехороший человек, Александр. Таких, как ты, любить надо!. ");
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
