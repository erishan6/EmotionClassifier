import numpy as np



def load_data(filename, train_ratio = 0.7): #TODO: DENIZ
    '''
    this function reads a file and seperates labels from sentences and creates a train-test-split
    :param filename:
    :return:
    '''

    data = np.loadtxt(filename, delimiter="\t", dtype=str, comments=None)

    number_training_instances = int(train_ratio * data.shape[0])

    data_train = data[0:number_training_instances]
    y_train = data_train[:,0]
    x_train = data_train[:,1]

    data_test = data[number_training_instances:]
    y_test = data_test[:,0]
    x_test = data_test[:,1]

    return x_train, y_train, x_test, y_test

def create_embeddings(x_train): #TODO: ISHAN
    '''
    this function creates a lexicon (embedding) from x (tweets)
    :param x: a list of tweets
    :return: word embedding matrix
    '''
    matrix = []
    return matrix

def get_embedding_for(sentence): #TODO: ISHAN
    embedding = [] #matrix
    return embedding

def CNN(filename): #TODO: ISHAN
    x_train, y_train, x_test, y_test = load_data(filename)
    embedding = create_embeddings(x_train)


    y_gold = []
    y_pred = []
    return y_gold, y_pred

def RNN(filename): #TODO: DENIZ
    x_train, y_train, x_test, y_test = load_data(filename)
    embedding = create_embeddings(x_train)

    y_gold = []
    y_pred = []
    return y_gold, y_pred

def evaluate(y_gold, y_pred): #TODO: DENIZ

    gold_counts_dict = np.unique(y_gold, return_counts=True)
    pred_counts_dict = np.unique(y_pred, return_counts=True)

    print(gold_counts_dict)

    evaluation = ""
    return evaluation

if __name__ == "__main__":

    #CNN()
    #RNN()

    #filename = "../data/full/data_original"
    #load_data(filename)



    y_gold = [1, 2, 2, 1, 1, 1, 3]

    evaluate(y_gold, y_pred)
