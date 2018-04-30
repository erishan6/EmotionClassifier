package com.teamlab.ss18.ec;

import java.util.HashMap;

public class BayesianClassifier {
    private Corpus trainingCorpus;
    private HashMap<String, Integer> labelCount = new HashMap<>();
    private HashMap<String, Double> labelProb = new HashMap<>();
    private HashMap<String, HashMap<String, Integer>> wordsEmotionMap;
    private Integer vocabTrainingCorpus;
    private Integer emotionCountTotal;

    public BayesianClassifier(Corpus trainingCorpus) {
        this.trainingCorpus = trainingCorpus;
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
    public void calculateLabelProb() {
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
    public Label predictLabel_BayesianClassifier(String text) {
//        Long stime = System.currentTimeMillis();
        calculateLabelProb();
//        Long etime = System.currentTimeMillis();
//        System.out.println("time " + (etime - stime));
//        stime = etime;
        double[] finalLabelProb = new double[labelProb.size()];
        int index = 0;
        double max = Double.NEGATIVE_INFINITY;
        String predictedLabel = "";
        for (String currEmotion : labelProb.keySet()) {
//            System.out.println(currEmotion);
            double currEmotionProb = labelProb.get(currEmotion);
            //TODO update regex precedence for appropraite results
            String regex = "\\W*(\\@USERNAME)|\\W*(\\[#TRIGGERWORD#])|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\"";
            String[] wordList = text.replaceAll(regex, " ").split("\\s+");

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
//        etime = System.currentTimeMillis();
//        System.out.println("time2 " + (etime - stime));
//        stime = etime;
        int pred = ArrayMath.argmax(finalLabelProb);
        System.out.println(pred + " " + predictedLabel);
        return new Label(predictedLabel);
    }

    public Corpus do_classify() {
//        TODO add functionality for label classification
        return null;
    }

    public Corpus predictLabel_BayesianClassifier(Corpus testCorpus) {
//        TODO add functionality for label prediction

        return null;
    }
}

