package com.newsweb.utils;

import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class Downloader {
	
	public static String dowloadPage(String url) throws IOException {		
		String html = IOUtils.toString((new URL(url)).openStream(), "utf-8");
		return html;
	}
}
