package com.teamlab.ss18.ec;

import java.util.*;

/**
 * Created by deniz on 26.04.18.
 */
public class PerceptronMap {
    HashMap<Integer,HashMap<String,Integer>> W;

    public PerceptronMap(){
    }

    public void fit(Corpus corpus, int epochs, boolean shuffle, int verbose){
        W = new HashMap<>();
        for (int epoch = 0; epoch < epochs; epoch++) {
            ArrayList<String[]> results = train(corpus, shuffle);


            Evaluator evaluator = new Evaluator(results.get(0), results.get(1), corpus.getNumberOfLabels());

            if (epoch % 10 == 0){
                System.out.println("Epoch "+epoch);
                if (verbose>0){
                    System.out.println("\tPrecision: "+evaluator.getPrecisionAverage());
                    System.out.println("\tRecall: "+evaluator.getRecallAverage());
                    System.out.println("\tFscore: "+evaluator.getFScoreAverage());
                }
            }

        }
    }

    private ArrayList<String[]> train(Corpus corpus, boolean shuffle){
        String[] predictions = new String[corpus.size()]; //used for after-epoch evaluation
        String[] golds = new String[corpus.size()];


        ArrayList<UUID> ids = new ArrayList<>();
        ids.addAll(corpus.getTweets().keySet());

        if (shuffle)
            Collections.shuffle(ids);

        int samplesSeen = 0;
        for (UUID tweetID : ids) {
            Tweet currentTweet = corpus.getTweets().get(tweetID);

            int[] dotResults = new int[corpus.getNumberOfLabels()]; //stores results from "vectorMultiplication".

            for (Integer classIndex : Label.getLabelsMapInt().keySet()) {

                ArrayList<String> currentFeatureVector = currentTweet.getFeatures();

                for (String feature : currentFeatureVector) {

                    HashMap<String, Integer> classWeights = W.get(classIndex);

                    if (classWeights == null){ //class has not been seen yet
                        HashMap<String, Integer> newClassWeights = new HashMap<>();
                        newClassWeights.put(feature,0); //Init weight to zero for feature
                        W.put(classIndex, newClassWeights);
                        continue;
                    }
                    if (classWeights.keySet().contains(feature)){
                        dotResults[classIndex] += classWeights.get(feature); //add featureWeight to class
                    }
                    else{
                        classWeights.put(feature,0); //If feature is not in weightVector for this class, then add and init with zero
                    }
                }
            }

            int predictedClass = ArrayMath.argmax(dotResults);
            int goldClass = currentTweet.getGoldLabel().getLabelInt();

            predictions[samplesSeen] = Label.getLabelsMapInt().get(predictedClass);
            golds[samplesSeen] = Label.getLabelsMapInt().get(goldClass);

            // UPDATE WIEGHTS
            if (predictedClass != goldClass){
                for (String feature : currentTweet.getFeatures()) {
                    int oldWeightPredicted = W.get(predictedClass).get(feature);
                    W.get(predictedClass).put(feature, oldWeightPredicted - 1 ); //decrement feature weight for predcitedClass

                    int oldWeightGold = W.get(goldClass).get(feature);
                    W.get(goldClass).put(feature, oldWeightGold + 1 ); //increment feature weight for goldClass
                }
            }
            samplesSeen++;
        }

        ArrayList<String[]> predictionsAndGolds = new ArrayList<>();
        predictionsAndGolds.add(predictions);
        predictionsAndGolds.add(golds);

        return predictionsAndGolds;
        //TODO: add epochs
        //TODO: shuffle input data before every epoch

    }

    public Corpus predict(Corpus corpus){

        LinkedHashMap<UUID, Tweet> tweets = corpus.getTweets();

        for (UUID id : corpus.getTweets().keySet()) {
            Tweet currentTweet = tweets.get(id);

            int[] dotResults = new int[corpus.getNumberOfLabels()]; //stores results from "vectorMultiplication".

            for (Integer classIndex : Label.getLabelsMapInt().keySet()) {
                ArrayList<String> currentFeatureVector = currentTweet.getFeatures();

                for (String feature : currentFeatureVector) {
                    Integer weight = W.get(classIndex).get(feature);

                    if (weight != null){
                        dotResults[classIndex] += weight;
                    }
                }
            }

            //Set tweet's predictedLabel
            int y_pred = ArrayMath.argmax(dotResults);
            String y_predString =Label.getLabelsMapInt().get(y_pred);
            currentTweet.setPredictedLabel(y_predString);
        }
        return corpus;
    }
}
