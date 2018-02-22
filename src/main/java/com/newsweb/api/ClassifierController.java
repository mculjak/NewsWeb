package com.newsweb.api;

import com.newsweb.model.Article;
import com.newsweb.service.ClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/classifier")
public class ClassifierController {

    private ClassifierService classifierService;

    @Autowired
    public ClassifierController(ClassifierService classifierService) {
        this.classifierService = classifierService;
    }

    @RequestMapping(value = "/train", method = RequestMethod.GET)
    public void trainClassifier() {
        classifierService.train();
    }
}
