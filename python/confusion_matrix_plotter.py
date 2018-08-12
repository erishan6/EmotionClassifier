'''
Plots the confusion matrix of the predictions and gold labels
'''

import matplotlib.pyplot as plt
import seaborn as sn
import pandas as pd
import numpy as np


filename = "/home/deniz/Arbeit/Uni Stuttgart/Semester 3/Teamlab/emotionclassifier/python/runs/predictions/2018-7-29_10-53-8_emb-300_lr-0.001_implicit_predictions.pred"
#filename = "/home/deniz/Arbeit/Uni Stuttgart/Semester 3/Teamlab/emotionclassifier/python/runs/predictions/2018-7-30_8-10-40_emb-300_lr-0.001_iest_predictions.pred"
predictions_file = open(filename)

predictions = predictions_file.readlines()

def to_list(y_):
    '''
    turns a str from a custom .pred file into a list
    :param y_:
    :return:
    '''
    y_ = str(y_)
    i = y_.index("[")+1
    y_ = y_[i:-2].split(",")
    y_ = [int(y.strip()) for y in y_]
    return y_

Y_pred = to_list(predictions[1])
Y_gold = to_list(predictions[0])


count = 0
confusion = np.zeros(36).reshape(6, 6)
for index, y_gold in enumerate(Y_gold):
    y_pred = Y_pred[index]

    if y_pred == 0 and y_gold == 1:
        count += 1


    confusion[y_pred][y_gold] += 1


confusion_precision = np.zeros(36).reshape(6, 6)
for i, row in enumerate(confusion):
    num_of_preds = sum(row)

    for j, cell in enumerate(row):
        precision = cell/num_of_preds
        confusion_precision[i][j] = precision * 100

values = confusion_precision
index = ['0', '1', '2', '3', '4', '5']
index = ['anger', 'disgust', 'fear', 'joy', 'sad', 'surprise']
columns = index
df = pd.DataFrame(data=values, index=index, columns=columns)
#print(df)

heat_map = sn.heatmap(df, annot=True, cbar=False,cmap="YlGnBu")
heat_map.set_yticklabels(heat_map.get_yticklabels(), rotation = 0, fontsize = 8)
plt.title("Precision")

plt.show()

confusion_transposed = np.transpose(confusion)

print(confusion)
print(confusion_transposed)

confusion_recall = np.zeros(36).reshape(6, 6)
for i, row in enumerate(confusion_transposed):
    num_of_preds = sum(row)

    for j, cell in enumerate(row):
        recall = cell/num_of_preds
        confusion_recall[i][j] = recall * 100


confusion_recall = np.transpose(confusion_recall)

print(confusion_recall)





values = confusion_recall
index = ['0', '1', '2', '3', '4', '5']
index = ['anger', 'disgust', 'fear', 'joy', 'sad', 'surprise']
columns = index
df = pd.DataFrame(data=values, index=index, columns=columns)
#print(df)

heat_map = sn.heatmap(df, annot=True, cbar=False,cmap="YlGnBu")
heat_map.set_yticklabels(heat_map.get_yticklabels(), rotation = 0, fontsize = 8)
plt.title("Recall")

plt.show()
