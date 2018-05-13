package com.teamlab.ss18.ec;

import java.util.*;

/**
 * Created by deniz on 26.04.18.
 */
public class Perceptron extends AbstractClassifier{
    HashMap<String,HashMap<String,Double>> W;

    public Perceptron(Corpus trainingCorpus){
        super(trainingCorpus);
    }

    /**
     * trains the model on trainCorpus for given epochs.
     * If shuffle is true the data will be shuffled before each epoch.
     * If verbose is greater 0 average precision, recall and fscore will be printed.
     * @param evalCorpus Corpus object containing test data
     * @param epochs number of training epochs
     * @param shuffle true for shuffle after each epoch
     * @param verbose
     * @param printEveryNthEpoch if verbose is bigger 0, evaluation is printed every nth epoch
     */
    public void fit(Corpus evalCorpus, int epochs, boolean shuffle, int verbose, int printEveryNthEpoch){
        W = new HashMap<>();
        for (int epoch = 1; epoch <= epochs; epoch++) {

            long startTime = System.currentTimeMillis();

            train();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

            if (printEveryNthEpoch > 0 && (epoch % printEveryNthEpoch == 0 || epoch == epochs || epoch == 1)){

                System.out.println("Epoch "+epoch + " ("+duration+"ms)");
                this.predict(evalCorpus);
                Evaluator testEvaluator = new Evaluator(evalCorpus);

                if (verbose > 1){
                    System.out.println("Confusion Matrix Test");
                    testEvaluator.printConfusionMatrix();
                }
                if (verbose > 0){
                    System.out.println("\tTest Precision: "+testEvaluator.getPrecisionAverage());
                    System.out.println("\tTest Recall: "+testEvaluator.getRecallAverage());
                    System.out.println("\tTest Fscore: "+testEvaluator.getFScoreAverage());
                }

                System.out.println();
                System.out.println("-----------------");
            }

        }
    }


    /**
     * trains the model for one epoch and updates the weightMatrix
     * @return return a list of String arrays. first array is an array of predicted labels. Second array is an array of gold labels.
     */
    @Override
    public void train(){

        Corpus corpus = this.trainingCorpus;
        boolean shuffle = true;

        ArrayList<UUID> ids = new ArrayList<>();

        ids.addAll(corpus.getTweets().keySet());

        if (shuffle)
            Collections.shuffle(ids);

        for (UUID tweetID : ids) {
            Tweet currentTweet = corpus.getTweets().get(tweetID);

            String predictedClass = "";

            double maxDotProduct = 0;
            double currentDotProduct = 0;
            for (String currentClass : Label.getLabelsMap().keySet()) {

                ArrayList<String> currentFeatureVector = currentTweet.getFeatures();
                for (String feature : currentFeatureVector) {
                    HashMap<String, Double> classWeights = W.get(currentClass);

                    if (classWeights == null){ //class has not been seen yet

                        W.put(currentClass, new HashMap<String, Double>());
                        continue;
                    }
                    if (classWeights.keySet().contains(feature)){
                        currentDotProduct += classWeights.get(feature);//add featureWeight to class
                    }
                }

                if (currentDotProduct >= maxDotProduct){
                    maxDotProduct = currentDotProduct;
                    predictedClass = currentClass;
                }
            }

            String goldClass = currentTweet.getGoldLabel().getLabelString();


            // UPDATE WEIGHTS
            if (!predictedClass.equals(goldClass)){
                for (String feature : currentTweet.getFeatures()) {
                    //UPDATE weights of predicted class
                    Double oldWeightPredicted = W.get(predictedClass).get(feature);
                    if (oldWeightPredicted == null)
                        W.get(predictedClass).put(feature, -1.0 ); //set weight to 0-1
                    else{
                        double newWeight = oldWeightPredicted - 1;

                        if (newWeight == 0)
                            W.get(predictedClass).remove(feature); //remove features with zero weight
                        else
                            W.get(predictedClass).put(feature, newWeight); //decrement feature weight for predcitedClass
                    }
                    //UPDATE weights of gold class
                    Double oldWeightGold = W.get(goldClass).get(feature);
                    if (oldWeightGold == null)
                        W.get(goldClass).put(feature, 1.0 ); //set weight to 0+1
                    else{
                        double newWeight = oldWeightGold + 1;

                        if (newWeight == 0)
                            W.get(goldClass).remove(feature);//remove features with zero weight
                        else
                            W.get(goldClass).put(feature, newWeight); //increment feature weight for goldClass
                    }
                }
            }
        }
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

            double maxDotProduct = 0;
            double currentDotProduct = 0;
            String predictedLabel = "";
            for (String currentClass : Label.getLabelsMap().keySet()) {
                ArrayList<String> currentFeatureVector = currentTweet.getFeatures();

                for (String feature : currentFeatureVector) {
                    Double weight = W.get(currentClass).get(feature);

                    if (weight != null){
                        currentDotProduct += weight;
                    }
                }
                if (currentDotProduct >= maxDotProduct){
                    maxDotProduct = currentDotProduct;
                    predictedLabel = currentClass;
                }
            }

            //Set tweet's predictedLabel
            currentTweet.setPredictedLabel(predictedLabel);
        }
        return corpus;
    }


    @Override
    public void evaluate(Corpus corpus) {

    }
}
