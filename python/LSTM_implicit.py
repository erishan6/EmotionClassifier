import numpy as np
import emoji
import re

from tensorflow.contrib import learn

from keras.models import Sequential
from keras.layers import Dense, Embedding
from keras.layers import LSTM
import sys
from keras.optimizers import Adam

np.set_printoptions(linewidth=100,)

global_vocab_processor = None
global_max_document_length = 0

def load_data(filename, training = True):
    '''
    this function reads a file and seperates labels from sentences and creates a train-test-split
    :param test_filename:
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

    if training:
        print(">>> loading training data...")
    else:
        print(">>> loading test data...")

    data_train = np.loadtxt(filename, delimiter="\t", dtype=str, comments=None)

    y = label_to_one_hot(data_train[:,0])
    x = data_train[:,1]
    x = [preprocess_tweet(x) for x in x]
    number_of_train_instances = len(x)
    #print(number_of_train_instances)

    x = create_vocabmapping(x, training=training)


    return x, y

def create_vocabmapping(tweets, training = True):
    '''
    this function creates a lexicon (embedding) from tweet (tweets)
    each tweet is cut or padded to a uniform length
    uniform length is calculated by adding the mean tweet length to the standard dev of document lengths (following Neumann and Vu 2017)
    :param tweets: a list of tweets
    :return: word embedding matrix
    '''
    global global_vocab_processor
    global global_max_document_length

    if training:
        print(">>> creating training vocabulary mapping...")
    else:
        print(">>> creating test vocabulary mapping...")


    if(global_vocab_processor == None and not training):
        print(">>> ERROR: trying to create embedding for testset before creating dictionary using training data")
        print(">>> SOLUTION: call create_vocabmapping() with training data first")
        print(">>> EXIT: program terminated")
        exit(0)

    if training: #create new vocab_processor using training data

        doc_lengths = np.array([len(tweet.split(" ")) for tweet in tweets])


        mean_doc_length = np.mean(doc_lengths)

        std_doc_length = np.std(doc_lengths)

        meanlength_plus_stdlength = mean_doc_length + std_doc_length



        global_max_document_length = int(meanlength_plus_stdlength)
        global_vocab_processor = learn.preprocessing.VocabularyProcessor(global_max_document_length)

        matrix = np.array(list(global_vocab_processor.fit_transform(tweets)))


    else: #use existing
        vocab_dict = global_vocab_processor.vocabulary_._mapping

        #replace words with indices
        matrix = [[vocab_dict[word] if word in vocab_dict else 0 for word in tweet.split(" ") ] for tweet in tweets]

        padded_tweets = []
        for tweet in matrix:
            tweet_length = len(tweet)

            if tweet_length > global_max_document_length:
                tweet_padded = np.array(tweet[:global_max_document_length])
            else:
                zero = np.zeros(global_max_document_length, dtype=int)
                zero[:tweet_length] = tweet
                tweet_padded = zero

            padded_tweets.append(tweet_padded)
        matrix = np.array(padded_tweets)


    return matrix


def CNN(filename): #TODO: ISHAN
    x_train, y_train, x_test, y_test = load_data(filename)
    embedding = create_vocabmapping(x_train)


    y_gold = []
    y_pred = []
    return y_gold, y_pred


def evaluate(y_gold, y_pred, verbose = 0, filename = None):
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

    for i in range(len(gold_counts_arrs[0])):
        if(not i in pred_counts_dict.keys()):
            pred_counts_dict[i] = 0



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
            #print(label)
            #print(pred_counts_dict)
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
    evaluation = "Macro:\tp: {}\tr: {}\tf: {}".format(p,r,f)


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


    if(filename != None):
        eval_file = open(filename, "w")
        eval_file.write(evaluation)
        eval_file.close()

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

def preprocess_tweet(text):
    #regex removing "everything" except ! and &
    regex_pattern = "\W*(\@USERNAME)|\W*(\[#TRIGGERWORD#\])|\W*(\[NEWLINE\])|\W*(http:\/\/url.removed)\W*|\W*(#)\W*|\-|\%|\,|\.|\[|\^|\$|\\|\?|\*|\+|\(|\)|\|\;|\:|\<|\>|\_|\""

    #regex removing "everything" except TRIGGERWORD
    regex_pattern = "\\W*(\\@USERNAME)|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\&|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\!|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\""
    text = emoji.demojize(text)
    text = re.sub(regex_pattern, " ", text)
    text = re.sub(r"\s+", " ", text).strip().lower()
    return text

def get_current_time():

    import datetime
    separator = "-"

    now = datetime.datetime.now()

    year = str(now.year)
    month = str(now.month)
    day = str(now.day)
    hour = str(now.hour)
    min = str(now.minute)
    second = str(now.second)

    time = year + separator + month + separator + day + "_" + hour + separator + min + separator + second
    return time

def RNN(train_filename, test_filename, current_time, embedding_depth=128, learning_rate=0.001):


    def get_callback_list():
        from keras import callbacks  # import ModelCheckpoint


        filepath = "{}_weights_bestmodel.hdf5".format(current_time)
        callback_model_checkpoint = callbacks.ModelCheckpoint(filepath, monitor='val_acc', verbose=0,
                                                              save_best_only=True,
                                                              mode='max')
        callback_stop_training = callbacks.EarlyStopping(monitor='loss', min_delta=0, patience=3, verbose=2,
                                                         mode='auto')
        filename = "{}_logger.csv".format(current_time)
        callback_csv_logger = callbacks.CSVLogger(filename, separator=',', append=False)

        #return [callback_model_checkpoint, callback_stop_training, callback_csv_logger]
        return [callback_model_checkpoint, callback_csv_logger]


    ##########################
    ### SET GENERAL PARAMS ###
    ##########################

    batch_size = 32
    max_epochs = 50

    ##########################
    ### SET NETWORK PARAMS ###
    ##########################

    number_of_labels = 6
    length_of_dense_embedding = int(embedding_depth)
    dropout = 0.2
    recurrent_dropout = 0.2
    learning_rate = float(learning_rate)

    #################
    ### LOAD DATA ###
    #################

    x_train, y_train = load_data(train_filename, training=True)
    x_test, y_test = load_data(test_filename, training=False)

    total_number_of_words = len(global_vocab_processor.vocabulary_._mapping)

    #############
    ### MODEL ###
    #############



    print('>>> building model...')
    model = Sequential()
    model.add(Embedding(total_number_of_words, length_of_dense_embedding))
    model.add(LSTM(length_of_dense_embedding, dropout=dropout, recurrent_dropout=recurrent_dropout))
    model.add(Dense(number_of_labels, activation='softmax'))

    optimizer = Adam(lr=learning_rate)
    model.compile(loss='binary_crossentropy',
                  optimizer=optimizer,
                  metrics=['accuracy'])

    print('>>> training...')
    model.fit(x_train, y_train,
              batch_size=batch_size,
              epochs=max_epochs,
              validation_data=(x_test, y_test),
              shuffle=True,
              verbose=2,
              callbacks=get_callback_list())

    print('>>> evaluate...')
    score, acc = model.evaluate(x_test, y_test,
                                batch_size=batch_size)

    print("final acc: ")
    print(acc)
    predictions = model.predict(x_test, verbose=1, batch_size=batch_size)
    #print(predictions)
    y_pred = [np.argmax(pred) for pred in predictions]
    y_gold = [np.argmax(gold) for gold in y_test]

    #print()
    #print(y_pred)
    #print(y_gold)

    return y_gold, y_pred


if __name__ == "__main__":

    args = sys.argv

    if len(args) > 1:
        embedding_depth = args[1]
    else:
        embedding_depth = 100
    if len(args) > 2:
        learning_rate = args[2]
    else:
        learning_rate = 0.001

    #filename_train = "../data/iest/full/02_dev/iest_dev.csv"
    #filename_test = "../data/iest/full/03_eval/iest_eval.csv"

    #filename_train = "../data/own_explicit_train.csv"
    #filename_test = "../data/own_explicit_eval.csv"


   # filename_train = "../data/full/02_dev/iest_dev.csv"
   # filename_test = "../data/full/03_eval/iest_eval.csv"



    #filename_train = "../data/own_explicit_train.csv"
    #filename_test = "../data/own_explicit_eval.csv"

    #filename_train = "../data/iest_train.csv"
    #filename_test = "../data/iest_eval.csv"

    #filename_train = "../data/own_explicit_train.csv"
    #filename_test = "../data/own_explicit_eval.csv"

    filename_train = "../data/own_implicit_train.csv"
    filename_test = "../data/own_implicit_eval.csv"

    current_time = get_current_time()
    y_gold, y_pred = RNN(filename_train, filename_test,
                         current_time=current_time,
                         embedding_depth=embedding_depth, learning_rate=learning_rate)


    filename_prefix = "{}_emb-{}_lr-{}_implicit".format(current_time, embedding_depth, learning_rate)


    pred_file = open(filename_prefix + "_predictions.pred", "w")

    pred_file.write("y_gold: "+str(y_gold)+"\n")
    pred_file.write("y_pred: "+str(y_pred)+"\n")


    evaluation = evaluate(y_gold, y_pred, verbose = 2, filename = filename_prefix + "_evaluation.eval")
    print()
    print(evaluation)







