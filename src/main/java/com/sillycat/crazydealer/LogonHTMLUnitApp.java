package com.sillycat.crazydealer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlEmailInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class LogonHTMLUnitApp {

	private final static Logger logger = LoggerFactory.getLogger(LogonHTMLUnitApp.class);

	private final static String LOGIN_URL = "https://m.costco.com/LogonForm";
	
	public static void main(String[] args) {
		logger.info("Start to init the HTTP Client---------");
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setTimeout(300000);

		//open the login form
		try {
			HtmlPage page = (HtmlPage) webClient.getPage(LOGIN_URL);
			final HtmlForm form = page.getFormByName("LogonForm");
			HtmlEmailInput textUserName = form.getInputByName("logonId"); 
	        textUserName.setText("luohuazju@gmail.com");
	        HtmlPasswordInput txtPwd = form.getInputByName("logonPassword");
	        txtPwd.setText("kaishi117A");
	        HtmlSubmitInput submitInput = form.getInputByValue("Sign In");
	        HtmlPage loginAfterPage = submitInput.click();
			
	        logger.info("*************************************************************************************");
	        logger.info(loginAfterPage.getWebResponse().getContentAsString());
	        logger.info("*************************************************************************************");
	        logger.info("Cookies : " + webClient.getCookieManager().getCookies().toString());
	        
		} catch (FailingHttpStatusCodeException e) {
			logger.error("error:", e);
		} catch (MalformedURLException e) {
			logger.error("error:", e);
		} catch (IOException e) {
			logger.error("error:", e);
		} finally{
			webClient.close();
		}
	}

}
