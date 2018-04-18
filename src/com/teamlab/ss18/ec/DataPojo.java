package com.teamlab.ss18.ec;

public class DataPojo {
    private String tag = "";
    private String sentence = "";

    public DataPojo(String tag, String sentence) {
        this.tag = tag;
        this.sentence = sentence;
    }

    public String getTag() {
        return tag;
    }

    public String getSentence() {
        return sentence;
    }
}
