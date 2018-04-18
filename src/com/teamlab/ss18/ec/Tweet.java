package com.teamlab.ss18.ec;

import java.util.UUID;

public class Tweet {
    private UUID id;
    private String goldLabel = "";
    private String predictedLabel = "joy"; //TODO: update for final version
    private String sentence = "";
    //TODO: add featurevector

    /**
     * This class takes a sentence and a gold label (emotion word)
     * @param goldLabel
     * @param sentence
     */
    public Tweet(String sentence, String goldLabel) {
        this.id = UUID.randomUUID();
        this.goldLabel = goldLabel;
        this.sentence = sentence;
    }
    public Tweet(String sentence) {
        this.id = UUID.randomUUID();
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

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                //", goldLabel='" + goldLabel + '\'' +
                //", predictedLabel='" + predictedLabel + '\'' +
                //", sentence='" + sentence + '\'' +
                '}';
    }

    public void extractFeatures(){
        //TODO: implement extractFeatures() method
    }
}
