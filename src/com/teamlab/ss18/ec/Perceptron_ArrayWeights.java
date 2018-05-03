package com.teamlab.ss18.ec;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by deniz on 19.04.18.
 */
public class Perceptron_ArrayWeights {

    int[][] W;

    public Perceptron_ArrayWeights(){
    }

    public void train(Corpus corpus, int numberOfFeatures){

        //TODO: add epochs
        //TODO: shuffle input data before every epoch
        System.out.println("start training");

        W = new int[corpus.getNumberOfLabels()][numberOfFeatures]; //TODO: change numberOfFeatures to be taken from corpus

        LinkedHashMap<UUID, Tweet> tweets = corpus.getTweets();

        for (UUID id : tweets.keySet()) {
            Tweet currentTweet = tweets.get(id);
            //int[] x = currentTweet.getFeatures();
            int[] x = {};
            Label goldLabel = currentTweet.getGoldLabel();
            int y_gold = goldLabel.getLabelInt();

            int[] dotResults = new int[corpus.getNumberOfLabels()];

            for (int classIndex = 0; classIndex < W.length; classIndex++) {
                int[] classWeight = W[classIndex];

                dotResults[classIndex] = ArrayMath.dot(classWeight, x);
            }
            int y_pred = ArrayMath.argmax(dotResults);


            if (y_gold == y_pred){
                //W[y_gold] = ArrayMath.add(W[y_gold], x);
            } else{
                //System.out.println("W_length: "+W.length);
                //System.out.println(y_gold);
                W[y_gold] = ArrayMath.add(W[y_gold], x);
                W[y_pred] = ArrayMath.subtract(W[y_pred], x);
            }
/*
            System.out.println("---------");
            System.out.println(currentTweet.getSentence());
            System.out.println("gold: "+currentTweet.getGoldLabel().getLabelString());
            System.out.println("pred: "+currentTweet.getPredictedLabel().getLabelString());
            for (int val : currentTweet.getFeatures()) {
                System.out.print(val+", ");
            }
            System.out.println();
            System.out.println("WEIGHT");
            for (int[] ints : W) {
                for (int anInt : ints) {
                    System.out.print(anInt+" ");
                }
                System.out.println();
            }
            System.out.println("\n---------");
*/
        }
    }

    /**
     * method is just for dev
     */
    public void train(){

        int numberOfFeatures = 10;
        int numberOfClasses = 4;

        int[] x = {0,1,0,0,0,1,0,0,0,1};
        int y_gold = 1;

        W = new int[numberOfClasses][numberOfFeatures];

        int[] dotResults = new int[numberOfClasses];
        for (int classIndex = 0; classIndex < W.length; classIndex++) {
            int[] classWeight = W[classIndex];

            dotResults[classIndex] = ArrayMath.dot(classWeight, x);
        }

        int y_pred = ArrayMath.argmax(dotResults);

        if (y_gold == y_pred){
            W[y_gold] = ArrayMath.add(W[y_gold], x);
        } else{
            W[y_gold] = ArrayMath.add(W[y_gold], x);
            W[y_pred] = ArrayMath.subtract(W[y_pred], x);
        }

        /*
        for (int[] ints : W) {
            for (int anInt : ints) {
                System.out.print(anInt+" ");
            }
            System.out.println();
        }
        */




    }

    public Corpus predict(Corpus corpus){

        LinkedHashMap<UUID, Tweet> tweets = corpus.getTweets();

        for (UUID id : corpus.getTweets().keySet()) {
            Tweet currentTweet = tweets.get(id);
            //int[] x = currentTweet.getFeatures();
            int[] x = {};

            int[] dotResults = new int[corpus.getNumberOfLabels()];

            for (int classIndex = 0; classIndex < W.length; classIndex++) {
                int[] classWeight = W[classIndex];

                dotResults[classIndex] = ArrayMath.dot(classWeight, x);
            }
            int y_pred = ArrayMath.argmax(dotResults);
            currentTweet.setPredictedLabel(Label.getLabelsMapInt().get(y_pred));

        }

        return corpus;
    }

    public void saveModel(String filePath){

    }

    public Object loadModel(String filePath){
        return null;
    }

    public static void main(String[] args) {
        new Perceptron_ArrayWeights().train();
    }
}
