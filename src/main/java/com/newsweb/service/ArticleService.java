package com.newsweb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsweb.model.Article;
import com.newsweb.model.ArticleRepository;
import com.newsweb.model.InvalidDataException;
import com.newsweb.utils.ContentExtractor;
import com.newsweb.utils.Downloader;
import com.newsweb.utils.Summarization;
import com.newsweb.utils.TitleUtils;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	
	public ArticleService() {
	}
	
	public Collection<Article> list(int currentPageNum, int elementsPerPage) {
		return articleRepository.list(currentPageNum, elementsPerPage);
	}
	
	public Article get(Long id) {
		return articleRepository.getById(id);
	}
	
	public void save(Article article) throws InvalidDataException {
		articleRepository.save(article);
	}
	
	public void delete(Article article) throws InvalidDataException {
		articleRepository.delete(article);
	}
	
	public void delete(Long id) throws InvalidDataException {
		articleRepository.delete(id);
	}
	
	public Collection<Article> getArticlesForCategories(List<String> categories, int currentPageNum, int elementsPerPage) {
		return articleRepository.getArticlesForCategories(categories,currentPageNum,elementsPerPage);
	}
	
	/**
	 * Add and process a new article.
	 * 
	 * @param url Original URL of an article.
	 * @param html HTML content of an article.
	 * @return True if article was added successfully.
	 */
	public boolean addArticle(String url, String html) {
		Article a = new Article();
		a.setHtml(html);
		a.setUrl(url);
		a.setDate(new Date());
		a.setTitle(TitleUtils.getPageTitle(html));
		String content = ContentExtractor.extractContent(html);
		if (content == null) {
			return false;
		}
		a.setText(content);
		a.setSummary(Summarization.summarize(content));
		
		try {
			save(a);
			return true;	
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Add and process a new article.
	 * 
	 * @param url Original URL of an article.
	 * @return True if article was added successfully.
	 */
	public boolean addArticle(String url) {
		try {
			String html = Downloader.dowloadPage(url);
			return addArticle(url, html);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
