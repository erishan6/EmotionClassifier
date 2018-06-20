import numpy as np



def load_data(filename, train_ratio = 0.7): #TODO: DENIZ
    '''
    this function reads a file and seperates labels from sentences and creates a train-test-split
    :param filename:
    :return:
    '''

    def label_to_one_hot(array):
        y_one_hot = []
        for label in array:
            label = label.strip()
            one_hot_label = []
            if (label == "anger"):
                one_hot_label = [1, 0, 0, 0, 0, 0]
            elif (label == "disgust"):
                one_hot_label = [0, 1, 0, 0, 0, 0]
            elif (label == "fear"):
                one_hot_label = [0, 0, 1, 0, 0, 0]
            elif (label == "joy"):
                one_hot_label = [0, 0, 0, 1, 0, 0]
            elif (label == "sad"):
                one_hot_label = [0, 0, 0, 0, 1, 0]
            elif (label == "surprise"):
                one_hot_label = [0, 0, 0, 0, 0, 1]
            else:
                one_hot_label = [0,0,0,0,0,0]
            y_one_hot.append(np.array(one_hot_label))

        return np.array(y_one_hot)

    data = np.loadtxt(filename, delimiter="\t", dtype=str, comments=None)

    number_training_instances = int(train_ratio * data.shape[0])

    data_train = data[0:number_training_instances]
    y_train = label_to_one_hot(data_train[:,0])

    x_train = data_train[:,1]

    data_test = data[number_training_instances:]
    y_test = label_to_one_hot(data_test[:,0])
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

def evaluate(y_gold, y_pred, verbose = 0): #TODO: DENIZ
    '''
    returns precision, recall and fscore
    :param y_gold:
    :param y_pred:
    :param verbose: if greater 0 prec, recall and f for all labels are additionally returned
    :return:
    '''

    gold_counts_arrs = np.unique(y_gold, return_counts=True)
    pred_counts_arrs = np.unique(y_pred, return_counts=True)

    number_of_instances = len(y_gold)
    gold_counts_dict = dict(zip(gold_counts_arrs[0], gold_counts_arrs[1]))
    pred_counts_dict = dict(zip(pred_counts_arrs[0], pred_counts_arrs[1]))

#    print(gold_counts_dict)
#    print(pred_counts_dict)

    labels = np.unique(y_gold)
    tp_counts = np.zeros(len(labels))

    tp_dict = dict(zip(labels, tp_counts)) #dictionary that has labels as key and the number of true positives for this label

    for i in range(len(y_gold)):
        if (y_gold[i] == y_pred[i]):
            tp_dict[y_gold[i]] += 1




    def calculate_precisions():
        precision_dict = dict()
        for label in tp_dict:
            number_of_tp_for_current_label = tp_dict[label]
            tp_plus_fp_for_current_label = pred_counts_dict[label]

            if (tp_plus_fp_for_current_label == 0):
                precision_dict[label] = 0
            else:
                precision_dict[label] = number_of_tp_for_current_label / tp_plus_fp_for_current_label
        return precision_dict

    def calculate_recalls():
        recall_dict = dict()
        for label in tp_dict:
            number_of_tp_for_current_label = tp_dict[label]
            tp_plus_fn_for_current_label = gold_counts_dict[label]

            if (tp_plus_fn_for_current_label == 0):
                recall_dict[label] = 0
            else:
                recall_dict[label] = number_of_tp_for_current_label / tp_plus_fn_for_current_label

        return recall_dict


    def calculate_f_scores():
        f_dict = dict()
        precisions = calculate_precisions()
        recalls = calculate_recalls()

        for label in tp_dict:
            precision = precisions[label]
            recall = recalls[label]

            if recall == 0 or precision == 0:
                f = 0
            else:
                f = (2 * precision * recall) / (precision + recall)

            f_dict[label] = f

        return f_dict

    def calculate_macro_precision():
        return np.mean(np.array(list(calculate_precisions().values()), dtype=float))

    def calculate_macro_recall():
        return np.mean(np.array(list(calculate_recalls().values()), dtype=float))

    def calculate_macro_f():
        return np.mean(np.array(list(calculate_f_scores().values()), dtype=float))


    p = round(calculate_macro_precision(),2)
    r = round(calculate_macro_recall(), 2)
    f = round(calculate_macro_f(), 2)
    evaluation = "Macro:\tr: {}\tr: {}\tf: {}".format(p,r,f)


    if (verbose > 0):
        evaluation += "\n"
        ps = calculate_precisions()

        rs = calculate_recalls()
        fs = calculate_f_scores()

        for label in ps:
            p = round(ps[label],2)
            r = round(rs[label],2)
            f = round(fs[label],2)
            evaluation += "{}:\tp: {}\tr: {}\tf: {}\n".format(label, p,r,f)

    return evaluation

def batch_iter(data, batch_size, num_epochs, shuffle=True):
    """
    Generates a batch iterator for a dataset.
    """
    data = np.array(data)
    data_size = len(data)
    num_batches_per_epoch = int((len(data) - 1) / batch_size) + 1
    for epoch in range(num_epochs):
        # Shuffle the data at each epoch
        print("Epoch : ",epoch)
        if shuffle:
            shuffle_indices = np.random.permutation(np.arange(data_size))
            shuffled_data = data[shuffle_indices]
        else:
            shuffled_data = data
        for batch_num in range(num_batches_per_epoch):
            start_index = batch_num * batch_size
            end_index = min((batch_num + 1) * batch_size, data_size)
            yield shuffled_data[start_index:end_index]


if __name__ == "__main__":

    #CNN()
    #RNN()

    filename = "../data/full/data_original"
    load_data(filename)



    y_gold = [1, 2, 2, 1, 1, 1, 3, 1,1, 1, 1, 1, 2]
    y_pred = [1, 2, 1, 1, 3, 3, 1, 2, 2, 2, 2, 1, 2]

    e = evaluate(y_gold, y_pred, verbose = 1)

    print(e)
