package com.teamlab.ss18.ec;

import java.util.ArrayList;
import java.util.UUID;

public class Tweet {
    private UUID id;
    private Label goldLabel = null;
    private Label predictedLabel = null;
    private String sentence = "";
    private ArrayList<String> features;

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

    public void setPredictedLabel(Label predictedLabel) {
        this.predictedLabel = predictedLabel;
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

    public ArrayList<String> getFeatures() {
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
        TODO: other possible features: top k most frequent words
        no stopwords
        adjective valency
        emojis
        punctuation (??!)
        other users mentioned?
        link?
        hashTags?
        etc


         */
        this.features = new ArrayList<>();



        String sentence = Utility.convertEmotionToText(this.sentence);

/*
        sentence = Utility.convertEmotionToText(sentence);
        if(!sentence.equals(this.sentence)){
            System.out.println("-------------------");
            System.out.println(this.sentence);
            System.out.println(sentence);
        }
*/


        //String[] splitTweet = this.sentence.split(" ");
        String[] splitTweet = sentence.split(" ");


        for (String token : splitTweet) {
            token = token.toLowerCase().trim();
            String punctuationRegex = "[.,;:\\-\\\\\\/!?&\"']";


            token = token.replaceAll(punctuationRegex, "");

            String newFeature = "w="+token;
            if ( ! this.features.contains(newFeature))
                if (!newFeature.equals("w=")) //ignore empty features (might be created when removing punctuation)
                    this.features.add(newFeature);
        }

    }


}
