filename = "runs/predictions/2018-7-29_10-53-8_emb-300_lr-0.001_implicit_predictions.pred"

file = open(filename)

lines = file.readlines()
file.close()
gold = [int(ele.strip()) for ele in lines[0].split(":")[1].replace("[","").replace("]", "").strip().split(",")]
pred = [int(ele.strip()) for ele in lines[1].split(":")[1].replace("[","").replace("]", "").strip().split(",")]


filename = "runs/predictions/2018-7-29_10-53-8_emb-300_lr-0.001_implicit_predictions.pred"
filename = "../data/own//own_implicit_eval.csv"
file = open(filename)

tweets = file.readlines()
file.close()


filename = "joy_joy_confusions.txt"
file = open(filename, "w")


for i in range(len(gold)):
    g = gold[i]
    p = pred[i]
    #if ((g == 3 and p == 4) or (g == 4 and p == 3)):
    if (g == 3 and p == 3): #or (g == 4 and p == 3)):
        print(tweets[i])
        file.write(tweets[i])

file.close()
'''
joy as sad: 619
kill: 9
*n't: 53
bad: 17
feel*: 48

sad_sad: 2026
feel*: 173 = 8.5%

joy_joy: 1462
feel*: 60 = 4.1%
'''

