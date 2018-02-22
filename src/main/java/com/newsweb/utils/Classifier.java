package com.newsweb.utils;


import java.io.*;
import java.util.List;

import com.aliasi.classify.*;
import com.aliasi.util.AbstractExternalizable;
import com.newsweb.model.Article;

public class Classifier {

    private static String[] CATEGORIES = {
            "business",
            "AI",
            "computer vision",
            "security",
            "finance",
            "science",
            "programming",
            "work"
    };
    private static int NGRAM_SIZE = 6;

    public static void buildClassifier(List<Article> classifiedArticles) {
        DynamicLMClassifier classifier = DynamicLMClassifier.createNGramBoundary(CATEGORIES, NGRAM_SIZE);
        for (Article article : classifiedArticles) {
            List<String> articleCategories = article.getCategories();
            if (articleCategories == null || articleCategories.size() == 0) {
                continue;
            }
            Classification classification = new Classification(articleCategories.get(0));
            Classified<CharSequence> classified = new Classified<CharSequence>(article.getText(),classification);
            classifier.handle(classified);
        }
        try {
            File file = new File("classifier_output");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutput o = new ObjectOutputStream(fos);
            classifier.compileTo(o);
            o.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String classify(String text) {
        try {
            File file = new File("classifier_output");
            LMClassifier classifier = (LMClassifier) AbstractExternalizable.readObject(file);
            JointClassification jc = classifier.classify(text);
            return jc.bestCategory();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
