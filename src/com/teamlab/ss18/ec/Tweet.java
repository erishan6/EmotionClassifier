package com.teamlab.ss18.ec;

public class Tweet {
    private String goldLabel = "";
    private String predictedLabel = "";
    private String sentence = "";

    /**
     * This class takes a sentence and a gold label (emotion word)
     * @param goldLabel
     * @param sentence
     */
    public Tweet(String sentence, String goldLabel) {
        this.goldLabel = goldLabel;
        this.sentence = sentence;
    }
    public Tweet(String sentence) {
        this.sentence = sentence;
    }

    public String getGoldLabel() {
        return goldLabel;
    }

    public String getSentence() {
        return sentence;
    }

    public void setPredictedLabel(String predictedLabel) {
        this.predictedLabel = predictedLabel;
    }

    public String getPredictedLabel() {
        return predictedLabel;
    }

}
