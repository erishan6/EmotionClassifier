package com.teamlab.ss18.ec;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;

public class Utility {
    public static final String ADV_REGEX = "\\W*(\\@USERNAME)|\\W*(\\[#TRIGGERWORD#])|\\W*(\\[NEWLINE\\])|\\W*(http://url.removed)\\W*|\\W*(#)\\W*|\\-|\\%|\\,|\\.|\\[|\\^|\\$|\\\\|\\?|\\*|\\+|\\(|\\)|\\|\\;|\\:|\\<|\\>|\\_|\\\"";

    public static String convertEmotionToText(String text) {
        List<String> emotionList = EmojiParser.extractEmojis(text);
        for (String emotion : emotionList) {
            Emoji emoji = EmojiManager.getByUnicode(emotion);
            List<String> tags = emoji.getTags();
            List<String> alias = emoji.getAliases();
            if (!tags.isEmpty()) {
                text = text.replaceAll(emotion, " " + tags.get(0) + " ");
            } else if (!alias.isEmpty()) {
                text = text.replaceAll(emotion, " " + alias.get(0) + " ");
            } else if (!emoji.getDescription().equals("")) {
                text = text.replaceAll(emotion, " " + emoji.getDescription() + " ");
            }
        }
        return text;
    }
}
