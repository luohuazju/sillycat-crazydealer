package com.sillycat.crazydealer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlEmailInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class ItemHTMLUnitApp {

	private final static Logger logger = LoggerFactory.getLogger(ItemHTMLUnitApp.class);

	private final static String HOME_URL = "https://www.costco.com/";
	private final static String ITEM_URL = "https://www.costco.com/Xbox-Live-24-Month-Gold-Card.product.100243703.html";

	public static void main(String[] args) {
		logger.info("Start to init the HTTP Client---------");
		// BEST_SUPPORTED, CHROME, EDGE, FIREFOX_45, FIREFOX_52,
		// INTERNET_EXPLORER
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setTimeout(300000);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setActiveXNative(true);
		webClient.getOptions().setAppletEnabled(true);
		webClient.getOptions().setDoNotTrackEnabled(true);
		webClient.getOptions().setDownloadImages(true);
		webClient.getOptions().setGeolocationEnabled(true);
		webClient.getCookieManager().setCookiesEnabled(true);
		// webClient.setAjaxController(new AjaxController() {
		// private static final long serialVersionUID = -5849069892969682176L;
		//
		// public boolean processSynchron(HtmlPage page, WebRequest request,
		// boolean async) {
		// return true;
		// }
		// });

		try {
			webClient.getCookieManager().setCookiesEnabled(true);
			webClient.getPage(HOME_URL);
			HtmlPage itemPage = webClient.getPage(ITEM_URL);
			webClient.getCookieManager().setCookiesEnabled(true);
			final HtmlForm itemForm = itemPage.getFormByName("OrderItemAddForm");
			HtmlElement signButton = itemForm.getInputByName("sign-in-to-buy");
			webClient.getCookieManager().setCookiesEnabled(true);
			HtmlPage logonPage = signButton.click();
			webClient.getCookieManager().setCookiesEnabled(true);
			final HtmlForm form = logonPage.getFormByName("LogonForm");
			HtmlEmailInput textUserName = form.getInputByName("logonId");
			textUserName.setText("luohuazju@gmail.com");
			HtmlPasswordInput txtPwd = form.getInputByName("logonPassword");
			txtPwd.setText("kaishi117A");
			HtmlSubmitInput submitInput = form.getInputByValue("Sign In");
			webClient.getCookieManager().setCookiesEnabled(true);
			HtmlPage loginAfterPage = submitInput.click();
			webClient.getCookieManager().setCookiesEnabled(true);
			logger.info("*************************************************************************************");
			logger.info(loginAfterPage.getWebResponse().getContentAsString());
			byte data[] = loginAfterPage.getWebResponse().getContentAsString().getBytes();
			FileOutputStream out = new FileOutputStream("/Users/carl/Downloads/picture/index.html");
			out.write(data);
			out.close();
			logger.info("*************************************************************************************");
			logger.info("Cookies : " + webClient.getCookieManager().getCookies().toString());

		} catch (FailingHttpStatusCodeException e) {
			logger.error("error:", e);
		} catch (MalformedURLException e) {
			logger.error("error:", e);
		} catch (IOException e) {
			logger.error("error:", e);
		} finally {
			webClient.close();
		}
	}

}
