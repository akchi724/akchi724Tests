package ru.liga.akchi724;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Censor {
    private String message;
    private  HashMap<String, String> forbiddenWords;

    public HashMap<String, String> getForbiddenWords() {
        return forbiddenWords;
    }

    public String getMessage() {
        return message;
    }

    public Censor(String message, HashMap<String, String> forbiddenWords) {
        this.message = message;
        this.forbiddenWords = forbiddenWords;
    }

    public List<String> getSentences() {
        return Arrays.stream(message.split("\\.")).map(String::trim).collect(Collectors.toList());
    }

    public List<String> getEditedSentences(List<String> sentences, HashMap<String, String> lForbiddenWords) {
        return sentences.stream()
                .map(sentence ->
                        replacementOfUnacceptableWords(sentence, lForbiddenWords))
                .collect(Collectors.toList());
    }

    public String replacementOfUnacceptableWords(String sentence, HashMap<String, String> lForbiddenWords) {
        for (Map.Entry entry : lForbiddenWords.entrySet()) {
            if (sentence.contains((String) entry.getKey())) {
                sentence = sentence.replace((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return sentence;
    }
    public String getEditedMessage(List<String> list){
        String sum="";
        for(String str:list){
            sum+=str+". ";
        }
        return sum;
    }
}
