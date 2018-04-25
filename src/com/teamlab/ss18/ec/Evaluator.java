package com.teamlab.ss18.ec;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by deniz on 11.04.18.
 */
public class Evaluator {

    private int numOfLabels;
    private HashMap<String, Integer> labelMap = new HashMap<>(); //for mapping labels onto distinct int values

    private String[] labels;
    private double[][] confusionMatrix = null;


    public Evaluator(String[] yPred, String[] yGold, int numOfLabels){
        this.numOfLabels = numOfLabels;
        this.labels = new String[numOfLabels];


        fillMatrix(yPred, yGold);
    }

    public Evaluator(Corpus corpus){
        this.labels = new String[corpus.getNumberOfLabels()];
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


            String currentPredictionLabel = currentTweet.getPredictedLabel();
            String currentGoldLabel = currentTweet.getGoldLabel();

            //add unseen labels to labelMap
            if (!labelMap.keySet().contains(currentPredictionLabel)){
                System.out.println(labels.length);
                labels[labelsSeen] = currentPredictionLabel;
                labelMap.put(currentPredictionLabel, labelsSeen++);
            }

            if (!labelMap.keySet().contains(currentGoldLabel)){
                labels[labelsSeen] = currentGoldLabel;
                labelMap.put(currentGoldLabel,labelsSeen++);
            }

            int predIndex = labelMap.get(currentPredictionLabel);
            int goldIndex = labelMap.get(currentGoldLabel);

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

            //add unseen labels to labelMap
            if (!labelMap.keySet().contains(currentPredictionLabel)){
                labels[labelsSeen] = currentPredictionLabel;
                labelMap.put(currentPredictionLabel, labelsSeen++);
            }

            if (!labelMap.keySet().contains(currentGoldLabel)){
                labels[labelsSeen] = currentGoldLabel;
                labelMap.put(currentGoldLabel,labelsSeen++);
            }

            int predIndex = labelMap.get(currentPredictionLabel);
            int goldIndex = labelMap.get(currentGoldLabel);

            //incement cells in confusionMatrix
            this.confusionMatrix[predIndex][goldIndex]++;
            this.confusionMatrix[numOfLabels][goldIndex]++; //increment number of gold labels for currentGoldLabel
            this.confusionMatrix[predIndex][numOfLabels]++; //increment number of predictions for currentPredictionLabel
            this.confusionMatrix[numOfLabels][numOfLabels]++; //increment total amount of predictions
        }
    }

    /**
     * @return labels
     */
    public String[] getLabels(){
        return this.labels;
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
        String currentLabel = classLabel;
        int labelIndex = labelMap.get(classLabel);
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
    public double getPrecisionAverage(){
        double sum = 0;
        for (String label : this.getLabels()) {
            sum += getPrecisionFor(label);
        }
        return sum/this.getLabels().length;
    }

    /**
     * calculates and returns the recall for classLabel
     * @param classLabel
     * @return recall
     */
    public double getRecallFor(String classLabel){
        String currentLabel = classLabel;
        int labelIndex = labelMap.get(classLabel);
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
    public double getRecallAverage(){
        double sum = 0;
        for (String label : this.getLabels()) {
            sum += getRecallFor(label);
        }
        return sum/this.getLabels().length;
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

    public double getFScoreAverage(){
        double sum = 0;
        for (String label : this.getLabels()) {
            sum += getFScoreFor(label);
        }
        return sum/this.getLabels().length;
    }

    public void printEvalResults() {

        String[] labels = {"joy", "surprise","sad", "fear","anger", "disgust"};

        for (String label : labels) {
            System.out.println(label+" \tP = " +getPrecisionFor(label)+" R = " +getRecallFor(label)+" F-Score = " +getFScoreFor(label));
        }
        System.out.println("***************");
        System.out.println("for corpus , P = "+getPrecisionAverage()+" R = "+getRecallAverage()+ "  F-Score = "+getFScoreAverage());


    }

}
