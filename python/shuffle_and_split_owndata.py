from random import shuffle

dir = '/home/deniz/Arbeit/Uni Stuttgart/Semester 3/Teamlab/emotionclassifier/data/own'

files = ["implicit.csv", "explicit.csv"]

def shuffle_and_split(filename, train_ratio = 0.7):
    file = open(filename)
    tweets = file.readlines()

    shuffle(tweets)

    num_of_tweets = len(tweets)

    train_samples = int(num_of_tweets * train_ratio)

    train_tweets = tweets[:train_samples]
    eval_tweets = tweets[train_samples:]

    print(len(train_tweets))
    print(len(eval_tweets))

    labels = [i.split("\t")[0] for i in train_tweets]
    import numpy as np

    print(np.unique(labels, return_counts=True))

    for i in train_tweets:
        label = i.split("\t")[0]
        if label.__contains__("#TRIGGER"):
            print(i)
            exit()


#shuffle_and_split(filename=dir+"/"+files[0])
shuffle_and_split(filename=files[0])