file = open("implicit.csv")
den = file.readlines()
file.close()
file = open("implicit_train.csv")
ish = file.readlines()
file.close()

den = set(den)
ish = set(ish)

inter = list(ish - den)

for i in inter[0:6]:
    print(i)

print(len(den))
print(len(ish))
print(len(inter))