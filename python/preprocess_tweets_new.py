import pandas as pd
import re

def cleanTweet(text, flag):
    text = text.lower()
    text = re.sub(r'@[A-Za-z0-9\_]+',' @USERNAME',text)
    text = re.sub(r'https?://[A-Za-z0-9./]+',' http://url.removed',text)
    text = re.sub('\\s+',' ',text)


    if flag=="implicit":
        text = re.sub(r'\W*(angry)|\W*(disgusting)|\W*(scared)|\W*(happy)|\W*(sad)|\W*(surprised)+',' [#TRIGGERWORD#]',text,flags=re.IGNORECASE)

    text = text.strip()
    return text



implicit_list = list()
explicit_list = list()

def preprocess(filename, flag="implicit"):

    text_index = 8
    label_index = 0
    infile = open(filename)

    lines = infile.readlines()
    infile.close()

    for tweet in lines:
        split = tweet.split("\t")
        label = split[label_index]

        text = split[text_index]



        if text.startswith("RT"):

            continue

        text = cleanTweet(text, flag=flag)

        if not text.__contains__("angry") \
                and not text.__contains__("happy") \
                and not text.__contains__("disgusting") \
                and not text.__contains__("scared") \
                and not text.__contains__("sad") \
                and not text.__contains__("surprised") \
                and not text.__contains__("#TRIGGERWORD#"):

            continue


        global implicit_list
        global explicit_list




        res = label + "\t" + text

        if flag == "implicit":
            implicit_list.append(res)
        else:
            explicit_list.append(res)



dir = "/home/deniz/Desktop/twittercrawler/data"
filelist = ["data1.csv", "data2.csv", "data3.csv","data4.csv","data5.csv","data6.csv","data7.csv"]

outfile_name_implicit = "implicit.csv"
outfile_name_explicit = "explicit.csv"

for filename in filelist:
    infile_path = dir+"/"+filename

    preprocess(infile_path, flag="explicit")

    preprocess(infile_path, flag="implicit")

for i in range(len(implicit_list)):
    pass
    #print(implicit_list[i])
    #print(explicit_list[i])
    #print("--------------------------")

print(len(implicit_list))
print(len(explicit_list))

out_impl = open(outfile_name_implicit, "w")
for i in implicit_list:
    out_impl.write(i+"\n")
out_impl.close()
out_expl = open(outfile_name_explicit, "w")

for i in explicit_list:
    out_expl.write(i+"\n")
out_expl.close()
