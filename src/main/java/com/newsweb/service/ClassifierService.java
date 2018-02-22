package com.newsweb.service;

import java.util.List;

import com.newsweb.model.Article;
import com.newsweb.utils.Classifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassifierService {

    private ArticleService articleService;

    @Autowired
    public ClassifierService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void train() {
        List<Article> allArticles = articleService.findAllCategorized();
        Classifier.buildClassifier(allArticles);
    }

}
