package com.teamlab.ss18.ec;

import java.util.HashMap;

public class BayesianClassifier extends AbstractClassifier {
    private HashMap<String, Integer> labelCount = new HashMap<>();
    private HashMap<String, Double> labelProb = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> wordsEmotionMap;
    private Integer vocabTrainingCorpus;
    private Integer emotionCountTotal;

    public BayesianClassifier(Corpus trainingCorpus) {
        super(trainingCorpus);
        initParams();
    }

    private void initParams() {
        this.vocabTrainingCorpus = trainingCorpus.getVocabularySize();
        this.wordsEmotionMap = trainingCorpus.wordEmotionCount();

    }
    /*
     * calculate count for prior prob of each label and store in hashmap
     */
    private void calculateLabelCount() {
        for (Tweet tweet : trainingCorpus.getTweets().values()) {
            String label = tweet.getGoldLabel().getLabelString();
            if (labelCount.containsKey(label)) {
                int count = labelCount.get(label);
                labelCount.put(label, count + 1);
            } else {
                labelCount.put(label, 1);
            }
        }
    }

    /*
     * calculate probability of word given emotion
     */
    private double wordProbGivenEmotion(String word, String emotion) {
        int totalOccurences = vocabTrainingCorpus;
        double emotionCount = vocabTrainingCorpus * 0.0001;
        if (wordsEmotionMap.containsKey(word)) {
//            System.out.println("word found in corpus");
            HashMap<String, Integer> emotionMap = wordsEmotionMap.get(word);
            for (String currEmotion : emotionMap.keySet()) {
                totalOccurences += emotionMap.get(currEmotion);
                if (currEmotion.equals(emotion)) {
                    emotionCount += emotionMap.get(emotion);
                }
            }
        } else {
//            System.out.println("word is not present in corpus");
        }
        return Math.log(emotionCount / totalOccurences);
    }

    /*
     * calculate params like label prob.
     */
    private void calculateLabelProb() {
        calculateLabelCount();
        emotionCountTotal = trainingCorpus.getTweets().size();
        for (String emotion : labelCount.keySet()) {
            labelProb.put(emotion, labelCount.get(emotion).doubleValue() / emotionCountTotal);
        }

    }

    /*
     * Predicts the label for text data ie sentence
     * Label index to label mapping
     */
    private Label predictLabel(String text) {
        text = Utility.convertEmotionToText(text);
        double[] finalLabelProb = new double[labelProb.size()];
        int index = 0;
        double max = Double.NEGATIVE_INFINITY;
        String predictedLabel = "";
        for (String currEmotion : labelProb.keySet()) {
//            System.out.println(currEmotion);
            double currEmotionProb = labelProb.get(currEmotion);
            String[] wordList = text.replaceAll(Utility.ADV_REGEX, " ").split("\\s+");

            for (String word : wordList) {
                currEmotionProb += wordProbGivenEmotion(word, currEmotion);
            }
            finalLabelProb[index] = currEmotionProb;
            double value = currEmotionProb;
            if (value > max) {
                max = value;
                predictedLabel = currEmotion;
            }
//            System.out.println(currEmotionProb);
            index++;
        }
        int pred = ArrayMath.argmax(finalLabelProb);
//        System.out.println(pred + " " + predictedLabel);
        return new Label(predictedLabel);

    }

    /*
     * Public function for support for prediction
     */
    public Label predictLabel_BayesianClassifier(String text) {
        calculateLabelProb();
        return predictLabel(text);
    }


//    public Corpus do_classify() {
//        calculateLabelProb();
//        for (Tweet tweet : trainingCorpus.getTweets().values()) {
//            tweet.setPredictedLabel(predictLabel(tweet.getSentence()));
//        }
//        return trainingCorpus;
//    }


    /*
     * Predicts the label for each sentence in corpus and returns the corpus  with updated value
     */
    public Corpus predictLabel_BayesianClassifier(Corpus testCorpus) {
        calculateLabelProb();
        for (Tweet tweet : testCorpus.getTweets().values()) {
            tweet.setPredictedLabel(predictLabel(tweet.getSentence()));
        }
        return testCorpus;
    }

    private void internalTrain(Corpus corpus) {
        calculateLabelProb();
        for (Tweet tweet : corpus.getTweets().values()) {
            tweet.setPredictedLabel(predictLabel(tweet.getSentence()));
        }
    }

    /*
     * Classifies the label for each sentence in corpus and returns the corpus  with updated value of predicted label
     */

    @Override
    public void train() {
        internalTrain(trainingCorpus);
    }

    @Override
    public void evaluate(Corpus corpus) {
        Evaluator evaluator = new Evaluator(corpus);
        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();
        System.out.println("******************************");
    }

    @Override
    public Corpus predict(Corpus testCorpus) {
        internalTrain(testCorpus);
        return testCorpus;
    }
}

