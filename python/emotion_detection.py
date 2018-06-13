def load_data(filename, train_ratio = 0.7): #TODO: DENIZ
    '''
    this function reads a file and seperates labels from sentences and creates a train-test-split
    :param filename:
    :return:
    '''
    x_train = []
    y_train = []
    x_test = []
    y_test = []



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
    evaluation = ""
    return evaluation

if __name__ == "__main__":

    #CNN()
    #RNN()

    filename = "..data/full/data_original"
    load_data(filename)