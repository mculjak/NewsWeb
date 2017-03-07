package com.newsweb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleUtils {
	/*
	 * the CASE_INSENSITIVE flag accounts for sites that use uppercase title
	 * tags. the DOTALL flag accounts for sites that have line feeds in the
	 * title text
	 */
	private static final Pattern TITLE_TAG = Pattern.compile(
			"\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * @param content
	 *            the HTML page
	 * @return title text (null if document isn't HTML or lacks a title tag)
	 */
	public static String getPageTitle(String content) {
		Matcher matcher = TITLE_TAG.matcher(content);
		if (matcher.find()) {
			/*
			 * replace any occurrences of whitespace (which may include line
			 * feeds and other uglies) as well as HTML brackets with a space
			 */
			return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
		} else
			return null;
	}
}
