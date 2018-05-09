package com.teamlab.ss18.ec;

import java.util.*;

/**
 * Created by deniz on 26.04.18.
 */
public class PerceptronMap_withArgMax {
    HashMap<Integer,HashMap<String,Integer>> W;

    public PerceptronMap_withArgMax(){
    }

    /**
     * trains the model on corpus for given epochs.
     * If shuffle is true the data will be shuffled before each epoch.
     * If verbose is greater 0 average precision, recall and fscore will be printed.
     * @param corpus A HashMap with Tweets as values and UUIDs as key
     * @param epochs number of training epochs
     * @param shuffle true for shuffle after each epoch
     * @param verbose
     * @param printEveryNthEpoch if verbose is bigger 0, evaluation is printed every nth epoch
     */
    public void fit(Corpus corpus, int epochs, boolean shuffle, int verbose, int printEveryNthEpoch){
        W = new HashMap<>();
        for (int epoch = 1; epoch <= epochs; epoch++) {

            long startTime = System.currentTimeMillis();

            ArrayList<String[]> results = train(corpus, shuffle);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.


            Evaluator evaluator = new Evaluator(results.get(0), results.get(1), corpus.getNumberOfLabels());

            if (printEveryNthEpoch > 0 && (epoch % printEveryNthEpoch == 0 || epoch == epochs || epoch == 1)){
                System.out.println("Epoch "+epoch + " ("+duration+"ms)");
                if (verbose > 1)
                    evaluator.printConfusionMatrix();
                if (verbose > 0){
                    System.out.println("\tTrain Precision: "+evaluator.getPrecisionAverage());
                    System.out.println("\tTrain Recall: "+evaluator.getRecallAverage());
                    System.out.println("\tTrain Fscore: "+evaluator.getFScoreAverage());
                }

                System.out.println();
                System.out.println("-----------------");
            }

        }
    }

    /**
     * trains the model for one epoch and updates the weightMatrix
     * @param corpus A HashMap with Tweets as values and UUIDs as key
     * @param shuffle true for shuffle after each epoch
     * @return return a list of String arrays. first array is an array of predicted labels. Second array is an array of gold labels.
     */
    private ArrayList<String[]> train(Corpus corpus, boolean shuffle){
        String[] predictions = new String[corpus.size()]; //used for after-epoch evaluation
        String[] golds = new String[corpus.size()]; //used for after-epoch evaluation

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

                        W.put(classIndex, new HashMap<String, Integer>());
                        continue;
                    }
                    if (classWeights.keySet().contains(feature)){
                        dotResults[classIndex] += classWeights.get(feature); //add featureWeight to class
                    }
                }
            }

            int predictedClass = ArrayMath.argmax(dotResults);
            int goldClass = currentTweet.getGoldLabel().getLabelInt();

            predictions[samplesSeen] = Label.getLabelsMapInt().get(predictedClass);
            golds[samplesSeen] = Label.getLabelsMapInt().get(goldClass);
            samplesSeen++;

            // UPDATE WEIGHTS
            if (predictedClass != goldClass){
                for (String feature : currentTweet.getFeatures()) {

                    //UPDATE weights of predicted class
                    Integer oldWeightPredicted = W.get(predictedClass).get(feature);
                    if (oldWeightPredicted == null)
                        W.get(predictedClass).put(feature, -1 ); //set weight to 0-1
                    else{
                        int newWeight = oldWeightPredicted - 1;

                        if (newWeight == 0)
                            W.get(predictedClass).remove(feature); //remove features with zero weight
                        else
                            W.get(predictedClass).put(feature, newWeight); //decrement feature weight for predcitedClass
                    }
                    //UPDATE weights of gold class
                    Integer oldWeightGold = W.get(goldClass).get(feature);
                    if (oldWeightGold == null)
                        W.get(goldClass).put(feature, 1 ); //set weight to 0+1
                    else{
                        int newWeight = oldWeightGold + 1;

                        if (newWeight == 0)
                            W.get(goldClass).remove(feature);//remove features with zero weight
                        else
                            W.get(goldClass).put(feature, newWeight); //increment feature weight for goldClass
                    }
                }
            }
        }

        ArrayList<String[]> predictionsAndGolds = new ArrayList<>();
        predictionsAndGolds.add(predictions);
        predictionsAndGolds.add(golds);

        return predictionsAndGolds;
    }

    /**
     * predicts classes for each tweet in corpus based on trained models
     * @param corpus A HashMap with Tweets as values and UUIDs as key
     * @return
     */
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
