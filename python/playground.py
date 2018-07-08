import re

regex_pattern = "\\W*(\\@USERNAME)|\\W*(\\[#TRIGGERWORD#])|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\&|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\!|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\""
regex_pattern = "\\W*(\\@USERNAME)|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\&|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\!|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\""

tweet = '''@USERNAME so much violence and sex on regular TV. Its [#TRIGGERWORD#] that this would cause such a reaction in some people.'''
print(tweet)

tweet_clean = re.sub(regex_pattern, " ", tweet)
tweet_clean = re.sub("\\s+", " ", tweet_clean).strip().lower()


print(tweet_clean)
