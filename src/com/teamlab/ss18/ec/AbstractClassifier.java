package com.teamlab.ss18.ec;

public abstract class AbstractClassifier {
    protected Corpus trainingCorpus;

    public AbstractClassifier(Corpus trainingCorpus) {
        this.trainingCorpus = trainingCorpus;
    }

    public abstract void train();

    public abstract void evaluate(Corpus corpus);

    public abstract Corpus predict(Corpus testCorpus);

}
