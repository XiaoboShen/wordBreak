package service;

import com.xiaobos.test.mode.Dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WordToolServiceImpl implements WordToolService {

    // Test Dictionary
    private Dictionary publicDictionary = new Dictionary();
    private Dictionary privateDictionary = new Dictionary();


    public void setPrivateDictionary(Dictionary dictionary) {
        privateDictionary.setWords(dictionary.getWords());
        privateDictionary.setType(Dictionary.TYPE_PRIVATE);
    }

    public void setPublicDictionary(Dictionary dictionary) {
        publicDictionary.setWords(dictionary.getWords());
        publicDictionary.setType(Dictionary.TYPE_PUBLIC);
    }

    /**
     * break sentence with dictionary words
     *
     * @param sentence senctence without split sign
     * @param type     type
     * @return result sentence
     */
    public ArrayList<String> breakWord(String sentence, String type) {
        if (sentence == null || "".equals(sentence)) {
            return new ArrayList<>();
        }
        String sentenceCopy = sentence;
        Set<String> words = new HashSet<String>();

        if (Dictionary.TYPE_PRIVATE.equals(type)) {
            for (String word : privateDictionary.getWords()) {
                words.add(word);
            }
        } else if (Dictionary.TYPE_PUBLIC.equals(type)) {
            for (String word : publicDictionary.getWords()) {
                words.add(word);
            }
        } else {
            for (String word : privateDictionary.getWords()) {
                words.add(word);
            }
            for (String word : publicDictionary.getWords()) {
                words.add(word);
            }
        }

//        System.out.println("input Sentence: " + sentenceCopy);

        ArrayList<String> resultSentences = new ArrayList<>();
        for (int i = 1; i <= sentenceCopy.length(); i++) {
            String snap = sentenceCopy.substring(0, i);
            if (words.contains(snap)) {
//                System.out.println("snap: " + snap);
                ArrayList<String> tmpSentences = this.breakWord(sentenceCopy.substring(i), type);
                if (tmpSentences.size() > 0) {
                    for (String stc : tmpSentences) {
                        String optionalResult = snap + " " + stc;
                        resultSentences.add(optionalResult);
//                        System.out.println("optionalResult:" + optionalResult);
                    }
                } else {
//                    System.out.println("last word");
                    resultSentences.add(snap);
                }
            }
        }

        resultSentences = (ArrayList<String>) resultSentences.stream().filter(item -> sentence.endsWith(item.split(" ")[item.split(" ").length - 1])).collect(Collectors.toList());
        return resultSentences;
    }

    public static void main(String[] args) {
        Dictionary publicDictionary = new Dictionary();
        publicDictionary.setWords(new String[]{"i", "like", "sam", "sung", "samsung", "mobile", "ice", "cream", "man go", "and"});
        Dictionary privateDictionary = new Dictionary();
        privateDictionary.setWords(new String[]{"i", "like", "sam", "sung", "mobile", "icecream", "man go", "mango"});

        WordToolServiceImpl svc = new WordToolServiceImpl();

        svc.setPrivateDictionary(privateDictionary);
        svc.setPublicDictionary(publicDictionary);
        String sentence_1 = "ilikesamsungmobile";

        System.out.println("****** stage 1. public dictionary\ninput: " + sentence_1 + "\noutput:");
        ArrayList<String> sentences = svc.breakWord(sentence_1, Dictionary.TYPE_PUBLIC);
        for (String stc : sentences) {
            System.out.println(stc);
        }

        System.out.println("****** stage 2. private dictionary\ninput: " + sentence_1 + "\noutput:");
        sentences = svc.breakWord(sentence_1, Dictionary.TYPE_PRIVATE);
        for (String stc : sentences) {
            System.out.println(stc);
        }

        String sentence_2 = "ilikeicecreamandmango";
        System.out.println("****** stage 3. both dictionary\ninput: " + sentence_2 + "\noutput:");
        sentences = svc.breakWord(sentence_2, Dictionary.TYPE_BOTH);
        for (String stc : sentences) {
            System.out.println(stc);
        }
    }
}
