package com.newsweb.utils;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class ContentExtractor {

	public static String extractContent(String html) {
		try {
			return ArticleExtractor.getInstance().getText(html);
		} catch (BoilerpipeProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
