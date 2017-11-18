package com.sillycat.crazydealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class LogonHTMLUnitApp {

	private final static Logger logger = LoggerFactory.getLogger(LogonHTMLUnitApp.class);

	public static void main(String[] args) {
		logger.info("Start to init the HTTP Client---------");
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);

	}

}
