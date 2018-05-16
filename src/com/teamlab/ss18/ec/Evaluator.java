package com.teamlab.ss18.ec;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by deniz on 11.04.18.
 */
public class Evaluator {

    private int numOfLabels;
    private double[][] confusionMatrix = null;

    public Evaluator(String[] yPred, String[] yGold, int numOfLabels){
        this.numOfLabels = numOfLabels;
        fillMatrix(yPred, yGold);
    }

    public Evaluator(Corpus corpus){
        this.numOfLabels = corpus.getNumberOfLabels();
        fillMatrix(corpus);
    }



    /**
     * this method creates and fills a confusion matrix
     * @param corpus
     */
    private void fillMatrix(Corpus corpus) {
        confusionMatrix = new double[corpus.getNumberOfLabels()+1][corpus.getNumberOfLabels()+1]; //initialize confusion matrix

        int labelsSeen = 0;

        for (UUID id : corpus.getTweets().keySet()) {
            Tweet currentTweet = corpus.getTweets().get(id);

            String currentPredictionLabel = currentTweet.getPredictedLabel().getLabelString();
            String currentGoldLabel = currentTweet.getGoldLabel().getLabelString();

            int predIndex = Label.getLabelsMap().get(currentPredictionLabel);
            int goldIndex = Label.getLabelsMap().get(currentGoldLabel);

            //incement cells in confusionMatrix
            this.confusionMatrix[predIndex][goldIndex]++;
            this.confusionMatrix[numOfLabels][goldIndex]++; //increment number of gold labels for currentGoldLabel
            this.confusionMatrix[predIndex][numOfLabels]++; //increment number of predictions for currentPredictionLabel
            this.confusionMatrix[numOfLabels][numOfLabels]++; //increment total amount of predictions
        }


    }

    /**
     * this method creates and fills a confusion matrix
     * @param yPred
     * @param yGold
     */
    private void fillMatrix(String[] yPred, String[] yGold) {
        confusionMatrix = new double[numOfLabels+1][numOfLabels+1]; //initialize confusion matrix

        int labelsSeen = 0;

        for (int i = 0; i < yPred.length; i++) {
            String currentPredictionLabel = yPred[i];
            String currentGoldLabel = yGold[i];


            int predIndex = Label.getLabelsMap().get(currentPredictionLabel);
            int goldIndex = Label.getLabelsMap().get(currentGoldLabel);

            //incement cells in confusionMatrix
            this.confusionMatrix[predIndex][goldIndex]++;
            this.confusionMatrix[numOfLabels][goldIndex]++; //increment number of gold labels for currentGoldLabel
            this.confusionMatrix[predIndex][numOfLabels]++; //increment number of predictions for currentPredictionLabel
            this.confusionMatrix[numOfLabels][numOfLabels]++; //increment total amount of predictions
        }
    }

    public void printEvalResults() {

        //Label.getLabelsMap()
        //String[] labels = {"joy", "surprise", "sad", "fear", "anger", "disgust"};
        ArrayList<String> labels = new ArrayList<>();
        labels.addAll(Label.getLabelsMap().keySet());

        System.out.println("***Class results");
        for (String label : labels) {
            System.out.println(label + " \tP = " + getPrecisionFor(label) + " R = " + getRecallFor(label) + " F-Score = " + getFScoreFor(label));
        }
        System.out.println("***Average results");

        String macroResults = getPrecisionMacroAverage() +"/"
                + getRecallMacroAverage() +"/"
                + getFScoreMacroAverage();

        String microResults = getPrecisionMicroAverage() +"/"
                + getRecallMicroAverage() +"/"
                + getFScoreMicroAverage();

        System.out.println("Macro averages (P/R/F): "+macroResults);
        System.out.println("Micro averages (P/R/F): "+microResults);
    }

    public void printConfusionMatrix(){
        //String[] labels = {"joy", "surprise", "sad", "fear", "anger", "disgust"};

        ArrayList<String> labels = new ArrayList<>();
        labels.addAll(Label.getLabelsMap().keySet());
        System.out.println();
        System.out.println("\tConfusion Matrix:");
        ArrayMath.printArrayAsAligned(labels, this.confusionMatrix);
        System.out.println();
    }

    /**
     * @return this object's confusionMatrix
     */
    public double[][] getConfusionMatrix() {
        return confusionMatrix;
    }

    /**
     * calculates and returns the precision for classLabel
     * @param classLabel
     * @return precision
     */
    public double getPrecisionFor(String classLabel){
        int labelIndex = Label.getLabelsMap().get(classLabel);
        double truePositives = this.confusionMatrix[labelIndex][labelIndex];
        double numOfClassPredictions = this.confusionMatrix[labelIndex][numOfLabels]; //number of times currentLabel has been predicted
        if(numOfClassPredictions == 0)
            return 0; //TODO: zero or n/a?
        return truePositives/numOfClassPredictions;
    }

    /**
     * calculates average precision of all classes
     * @return
     */
    public double getPrecisionMacroAverage(){
        double sum = 0;
        for (String label : Label.getLabelsMap().keySet()) {
            sum += getPrecisionFor(label);
        }
        return sum/numOfLabels;
    }

    /**
     * calculates and returns the recall for classLabel
     * @param classLabel
     * @return recall
     */
    public double getRecallFor(String classLabel){
        String currentLabel = classLabel;
        int labelIndex = Label.getLabelsMap().get(classLabel);
        double truePositives = this.confusionMatrix[labelIndex][labelIndex];
        double numOfClassGold = this.confusionMatrix[numOfLabels][labelIndex]; //number of times currentLabel occurs in gold
        if(numOfClassGold == 0)
            return 0; //TODO: zero or n/a?
        return truePositives/numOfClassGold;
    }

    /**
     * calculates average recall of all classes
     * @return
     */
    public double getRecallMacroAverage(){
        double sum = 0;
        for (String label : Label.getLabelsMap().keySet()) {
            sum += getRecallFor(label);
        }
        return sum/numOfLabels;
    }

    /**
     * calculates fscore
     * @param classLabel
     * @return
     */
    public double getFScoreFor(String classLabel){

        double precision = getPrecisionFor(classLabel);
        double recall = getRecallFor(classLabel);
        if ((precision+recall) == 0)
            return 0;
        return 2*(precision*recall)/(precision+recall);
    }

    public double getFScoreMacroAverage(){
        double sum = 0;
        for (String label : Label.getLabelsMap().keySet()) {
            sum += getFScoreFor(label);
        }
        return sum/numOfLabels;
    }

    public double getPrecisionMicroAverage(){
        double numerator = 0;
        double denominator = 0;
        for (int i = 0; i < numOfLabels; i++) {
            numerator += confusionMatrix[i][i];
            denominator += confusionMatrix[i][numOfLabels];
        }
        return numerator/denominator;
    }

    public double getRecallMicroAverage(){
        double numerator = 0;
        double denominator = 0;
        for (int i = 0; i < numOfLabels; i++) {
            numerator += confusionMatrix[i][i];
            denominator += confusionMatrix[numOfLabels][i];
        }
        return numerator/denominator;

    }

    public double getFScoreMicroAverage(){
        // (2ab)/(a+b)
        double precision = getPrecisionMicroAverage();
        double recall = getRecallMicroAverage();

        return (precision * recall *2) / (precision + recall);
    }


}
