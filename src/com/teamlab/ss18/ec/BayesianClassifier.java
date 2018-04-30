package com.teamlab.ss18.ec;

import java.util.HashMap;

public class BayesianClassifier {
    private Corpus trainingCorpus;
    private HashMap<String, Integer> labelCount = new HashMap<>();
    private HashMap<String, Double> labelProb = new HashMap<>();
    private Integer vocabTrainingCorpus;
    private Integer emotionCountTotal;

    public BayesianClassifier(Corpus trainingCorpus) {
        this.trainingCorpus = trainingCorpus;
        this.vocabTrainingCorpus = trainingCorpus.getVocabularySize();
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
        if (trainingCorpus.wordEmotionCount().containsKey(word)) {
//            System.out.println("word found in corpus");
            HashMap<String, Integer> emotionMap = trainingCorpus.wordEmotionCount().get(word);
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
    public void calculateLabelProb() {
        calculateLabelCount();
        emotionCountTotal = trainingCorpus.getTweets().size();
        for (String emotion : labelCount.keySet()) {
            labelProb.put(emotion, labelCount.get(emotion).doubleValue() / emotionCountTotal);
        }

    }


    public void do_classify() {
//        TODO add functionality for label classification
        //classify(feat1,...,featN) = argmax(P(cat)*PROD(P(featI|cat)

    }

    /*
     * Predicts the label index for text data ie sentence
     * Label index to label mapping
     */
    public void predictLabel_BayesianClassifier(String text) {
//        TODO add functionality of label as enum so that we can use it to predict or use something more appropriate (WIP)
        double[] finalLabelProb = new double[labelProb.size()];
        int index = 0;
        calculateLabelProb();
        for (String currEmotion : labelProb.keySet()) {
            double currEmotionProb = labelProb.get(currEmotion);
            String[] wordList = text.split("\\s+");
            for (String word : wordList) {
                currEmotionProb += wordProbGivenEmotion(word, currEmotion);
            }
            finalLabelProb[index] = currEmotionProb;
            System.out.println(currEmotionProb);
            index++;
        }
        int pred = ArrayMath.argmax(finalLabelProb);
        System.out.println(pred);
    }

    public void predictLabel_BayesianClassifier(Corpus testCorpus) {
//        TODO add functionality for label prediction
    }
}

