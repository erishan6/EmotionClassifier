import pandas as pd
import re

# for i in range(6):
#     print(i)

folder = "../data/new/"
# path = "../data/new/temp"

def createBaseFile():
	cols = ["label","label_words","time","sha","nan", "user", "lang", "chanel", "text","gibber", "gibber2"]
	trainfile = pd.DataFrame()
	df_list = []
	filelist = ["data1.csv", "data2.csv", "data3.csv","data4.csv","data5.csv","data6.csv","data7.csv",]
	for i in filelist:
		path = folder+i
		# if (i==0):
		# 	df = pd.read_csv(path, sep = "\t", names= cols)
		# else :
		# 	df = pd.read_csv(path, sep = "\t")
		df = pd.read_csv(path, sep = "\t", names= cols)
		df = df[df["text"].str.contains("RT")==False]
		df = df[(df["text"].str.contains("angry")==True) | (df["text"].str.contains("happy")==True) | (df["text"].str.contains("disgusting")==True) | (df["text"].str.contains("scared")==True) | (df["text"].str.contains("sad")==True) | (df["text"].str.contains("surprised")==True)]
		df_list.append(df)
		# print(len(a))
	# print(len(df_list))
	trainfile=pd.concat(df_list)
	trainfile = trainfile[["label","text"]]
	trainfile.to_csv(folder+"train.csv", sep="\t",index=False, header=False)
	print(len(trainfile))


def cleanTweet(text, flag):
	text = text.lower()
	text = re.sub(r'@[A-Za-z0-9\_]+',' @USERNAME',text)
	text = re.sub(r'https?://[A-Za-z0-9./]+',' http://url.removed',text)
	if flag=="implicit":
		text = re.sub(r'\W*(angry)|\W*(disgusting)|\W*(scared)|\W*(happy)|\W*(sad)|\W*(surprised)+',' [#TRIGGERWORD#]',text,flags=re.IGNORECASE)
	return text
	# print(text)



def updatefile(flag= "explicit"):
	filename = "train.csv"
	df = pd.read_csv(folder+filename,sep="\t",names=["label","text"])
	a = df["text"]
	df["text"] = [cleanTweet(x,flag) for x in a]
	df.to_csv(folder+flag+"_"+filename, sep="\t",index=False, header=False)


# cleanTweet("@jennifervo88 @Btsjin120492 @FIFAWorldCup Listen honey. I’m not unhappy or aNgry that they won or anything like tha… https://t.co/gNL5wIs6mj")
createBaseFile()
updatefile()
updatefile("implicit")
