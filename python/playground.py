tweet = """"@tedcruz And, #HandOverTheServer she wiped clean + 30k deleted emails, explains dereliction of duty/lies re #Benghazi,etc #tcot",Hillary Clinton,AGAINST,"1.  The tweet explicitly expresses opinion about the target, a part of the target, or an aspect of the target.",neg"""


split_line = tweet.split("\",")
tweet = split_line[0]+"\""

split_line = split_line[1].split(",")

print(tweet)
for i in split_line:
    print(i)
