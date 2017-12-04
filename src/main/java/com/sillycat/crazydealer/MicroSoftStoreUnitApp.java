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
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MicroSoftStoreUnitApp {

	private final static Logger logger = LoggerFactory.getLogger(MicroSoftStoreUnitApp.class);

	private final static String HOME_URL = "https://www.microsoft.com";
	private final static String ITEM_URL = "https://www.microsoft.com/en-us/store/d/surface-mouse/8qbtdr3q4rpw/935j?activetab=pivot:overviewtab";

	public static void main(String[] args) {
		logger.info("Start to init the HTTP Client---------");
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

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
		webClient.setAjaxController(new AjaxController() {
			private static final long serialVersionUID = -5849069892969682176L;

			public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
				return true;
			}
		});
		logger.info("Start to init the HTTP Client---------done");

		try {
			webClient.getPage(HOME_URL);
			HtmlPage itemPage = webClient.getPage(ITEM_URL);
			HtmlButton addToCartButton = itemPage.getHtmlElementById("PhysicalGoodIdentityBuyButton");
			logger.info("addToCartButton:" + addToCartButton.asText());
			HtmlPage cartPage = addToCartButton.click();

			logger.info("*************************************************************************************");
			logger.debug(cartPage.asXml());
			byte data[] = cartPage.asXml().getBytes();
			FileOutputStream out = new FileOutputStream("/Users/carl/Downloads/picture/cartPage.html");
			out.write(data);
			out.close();
			logger.info("*************************************************************************************");

		} catch (FailingHttpStatusCodeException e) {
			logger.error("MicroSoftStore Error:", e);
		} catch (MalformedURLException e) {
			logger.error("MicroSoftStore Error:", e);
		} catch (IOException e) {
			logger.error("MicroSoftStore Error:", e);
		} finally{
			webClient.close();
		}

		// try {
		// webClient.getPage(HOME_URL);
		// HtmlPage logonPage = webClient.getPage(LOGIN_URL);
		// final HtmlForm form = logonPage.getFormByName("LogonForm");
		// HtmlEmailInput textUserName = form.getInputByName("logonId");
		// textUserName.setText("luohuazju@gmail.com");
		// HtmlPasswordInput txtPwd = form.getInputByName("logonPassword");
		// txtPwd.setText("kaishi117A");
		// HtmlSubmitInput submitInput = form.getInputByValue("Sign In");
		// HtmlPage loginAfterPage = submitInput.click();
		//
		// logger.trace("*************************************************************************************");
		// logger.trace(loginAfterPage.getWebResponse().getContentAsString());
		// logger.trace("*************************************************************************************");
		// logger.trace("Cookies : " +
		// webClient.getCookieManager().getCookies().toString());
		//
		// HtmlPage itemPage = webClient.getPage(ITEM_URL);
		// webClient.waitForBackgroundJavaScript(6000);
		//
		// logger.info("*************************************************************************************");
		// logger.info(itemPage.getWebResponse().getContentAsString());
		// logger.info("*************************************************************************************");
		// logger.info("Cookies : " +
		// webClient.getCookieManager().getCookies().toString());
		//
		// } catch (FailingHttpStatusCodeException e) {
		// logger.error("error:", e);
		// } catch (MalformedURLException e) {
		// logger.error("error:", e);
		// } catch (IOException e) {
		// logger.error("error:", e);
		// } finally {
		// webClient.close();
		// }
	}

}
