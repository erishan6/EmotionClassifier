from random import shuffle
import numpy as np

dir = '/home/deniz/Arbeit/Uni Stuttgart/Semester 3/Teamlab/emotionclassifier/data/own'

files = ["implicit.csv", "explicit.csv"]

def shuffle_and_split(filename, train_ratio = 0.7):


    file = open(filename)
    tweets = file.readlines()
    file.close()

    shuffle(tweets)

    num_of_tweets = len(tweets)

    train_samples = int(num_of_tweets * train_ratio)

    train_tweets = tweets[:train_samples]
    eval_tweets = tweets[train_samples:]

    print(len(train_tweets))
    print(len(eval_tweets))

    labels = [i.split("\t")[0] for i in train_tweets]


    print(np.unique(labels, return_counts=True))

    filename = filename.replace(".csv", "")

    file = open("{}_train.csv".format(filename), "w")
    for tweet in train_tweets:
        file.write(tweet)
    file.close()

    file = open("{}_eval.csv".format(filename), "w")
    for tweet in eval_tweets:
        file.write(tweet)
    file.close()


for file in files:
    shuffle_and_split(filename=dir+"/"+file)
