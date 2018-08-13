'''Trains an LSTM model on the IMDB sentiment classification task.

The dataset is actually too small for LSTM to be of any advantage
compared to simpler, much faster methods such as TF-IDF + LogReg.

# Notes

- RNNs are tricky. Choice of batch size is important,
choice of loss and optimizer is critical, etc.
Some configurations won't converge.

- LSTM loss decrease patterns during training can be quite different
from what you see with CNNs/MLPs/etc.
'''
from __future__ import print_function

from keras.preprocessing import sequence
from keras.models import Sequential
from keras.layers import Dense, Embedding
from keras.layers import LSTM
from keras.datasets import imdb
from keras.utils import to_categorical

total_number_of_words = 20000
max_sentence_length = 80  # cut texts after this number of words (among top max_features most common words)
batch_size = 32
epochs = 2

number_of_labels = 2
length_of_dense_embedding = 128
dropout = 0.2
recurrent_dropout = 0.2

print('Loading data...')
(x_train, y_train), (x_test, y_test) = imdb.load_data(num_words=total_number_of_words)
print(x_train)

print(len(x_train), 'train sequences')
print(len(x_test), 'test sequences')

print('Pad sequences (samples x time)')

num_of_samples = 25000
num_of_samples = 500
x_train = sequence.pad_sequences(x_train[0:num_of_samples], maxlen=max_sentence_length)
y_train = y_train[0:num_of_samples]

x_test = sequence.pad_sequences(x_test[0:num_of_samples], maxlen=max_sentence_length)
y_test = y_test[0:num_of_samples]
print('x_train shape:', x_train.shape)
print('x_test shape:', x_test.shape)

y_train = to_categorical(y_train, num_classes=number_of_labels)
y_test = to_categorical(y_test, num_classes=number_of_labels)
print(y_train)

#exit()


print('Build model...')
model = Sequential()
model.add(Embedding(total_number_of_words, length_of_dense_embedding))
model.add(LSTM(length_of_dense_embedding, dropout=dropout, recurrent_dropout=recurrent_dropout))
model.add(Dense(2, activation='softmax'))


model.compile(loss='binary_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

print('Train...')
model.fit(x_train, y_train,
          batch_size=batch_size,
          epochs=epochs,
          validation_data=(x_test, y_test),
          shuffle=True,
          verbose = 2)



print('Test...')
score, acc = model.evaluate(x_test, y_test,
                            batch_size=batch_size)

#predictions = model.predict(x_test)

#TODO store score and acc in vecs to plot later
print('Test score:', score)
print('Test accuracy:', acc)

#TODO: add history