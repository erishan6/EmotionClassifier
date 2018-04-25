package com.teamlab.ss18.ec;

import java.util.UUID;

public class Tweet {
    private UUID id;
    private Label goldLabel = null;
    private Label predictedLabel = null; //TODO: update for final version
    private String sentence = "";
    private int[] features;

    /**
     * This class takes a sentence and a gold label (emotion word)
     * @param goldLabel
     * @param sentence
     */
    public Tweet(String sentence, String goldLabel) {
        this.id = UUID.randomUUID();
        this.goldLabel = new Label(goldLabel);
        this.sentence = sentence;
        extractFeatures();
    }
    public Tweet(String sentence) {
        this.id = UUID.randomUUID();
        this.sentence = sentence;
        extractFeatures();
    }

    public Label getGoldLabel() {
        return goldLabel;
    }

    public String getSentence() {
        return sentence;
    }

    public void setPredictedLabel(String predictedLabel) {
        this.predictedLabel = new Label(predictedLabel);
    }

    public Label getPredictedLabel() {
        if (predictedLabel == null)
            return new Label();
        return predictedLabel;
    }

    public UUID getId() {
        return id;
    }

    public int[] getFeatures() {
        return features;
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

        /*
        tmp random generation
         */
        int gold = getGoldLabel().getLabelInt();

        int[] featureVector = new int[10];

        for (int i = 0; i < featureVector.length; i++) {
            if (gold == 0)
                gold = 7;
            if (i%gold == 0)
                featureVector[i] = 1;
        }

        this.features = featureVector;
    }
}
