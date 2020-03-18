package com.xiaobos.test.mode;

public class Dictionary {

    public static final String TYPE_BOTH = "BOTH";
    public static final String TYPE_PUBLIC = "PUBLIC";
    public static final String TYPE_PRIVATE = "PRIVATE";

    private Long id;

    private String[] words;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        StringBuffer wordsz = new StringBuffer();
        for (String wrd : words) {
            wordsz.append(wrd).append(",");
        }
        return "[id:, type:, words: " + wordsz.toString().substring(0, wordsz.length() - 1) + "]";
    }
}
