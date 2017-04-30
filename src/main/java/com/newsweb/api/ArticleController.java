package com.newsweb.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsweb.model.Article;
import com.newsweb.model.InvalidDataException;
import com.newsweb.service.ArticleService;
 
@RestController
@RequestMapping(value="/api")
public class ArticleController {
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value = "/articles/{id:.+}", method = RequestMethod.GET)
	public Article getArticle(@PathVariable("id") Long id) {
		logger.debug("get() - id {}", id);
		return articleService.get(id);
	}
	
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public Collection<Article> listArticles(@RequestParam(value="categories", required=false) String[] categories,
			@RequestParam(value="page",required=false) Integer page,
			@RequestParam(value="size",required=false) Integer size) {
		if (page == null) {
			page = 1; //default value
		}
		if (size == null) {
			size = 10; //default value
		}
		if (categories != null && categories.length > 0) {
		    List<String> categoriesList = Arrays.asList(categories);
			return articleService.getArticlesForCategories(categoriesList, page, size);
		} else {
            return articleService.list(page, size);
        }
    }
	
	@RequestMapping(value = "/articles", method = RequestMethod.PUT)
	public void save(@RequestBody Article article) throws InvalidDataException {
		if (article.getId() == null) {
			throw new InvalidDataException("Can't save transient object!");
		}
		articleService.save(article);
	}
	
	@RequestMapping(value = "/articles/{id:.+}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) throws InvalidDataException {
		articleService.delete(id);
	}

	@RequestMapping(value = "/articles", method = RequestMethod.POST)
	public boolean create(@RequestBody Article article) throws InvalidDataException {
		if (article.getUrl() == null || article.getHtml() == null) {
			throw new InvalidDataException("Article must have URL and HTML!");
		}
		return articleService.addArticle(article.getUrl(), article.getHtml());
	}
}
