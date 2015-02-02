package org.wicketstuff.resources;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.IComponentInitializationListener;
import org.apache.wicket.css.ICssCompressor;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * The css url replacer is used to replace url within css files with resources that belongs to their corresponding classes. The compress method is not
 * compressing any content, but replacing the urls with Wicket representatives.<br>
 * <br>
 * 
 * Usage (Add the following line to the init method of the application):<br>
 * <br>
 * 
 * this.getResourceSettings().setCssCompressor(new CssUrlReplacer(this));
 * 
 * @author Tobias Soloschenko
 * 
 */
public class CssUrlReplacer implements ICssCompressor {

	// Holds the names of pages
	private List<String> pageNames = new ArrayList<String>();

	// The pattern to find URLs in CSS resources
	private Pattern urlPattern = Pattern.compile("url\\(['|\"](.*)['|\"]\\)");

	public CssUrlReplacer(Application application) {

		// Create an instantiation listener which filters only pages.
		application.getComponentInitializationListeners().add(new IComponentInitializationListener() {

			@Override
			public void onInitialize(Component component) {
				if (Page.class.isAssignableFrom(component.getClass())) {
					CssUrlReplacer.this.pageNames.add(component.getClass().getName());
				}
			}
		});
	}

	/**
	 * Replaces the URLs of CSS resources with Wicket representatives.
	 */
	@Override
	public String compress(String original) {
		Matcher matcher = this.urlPattern.matcher(original);
		// Search for urls
		while (matcher.find()) {
			for (String pageName : this.pageNames) {
				try {
					Class<Page> pageClass = (Class<Page>) Class.forName(pageName);
					String url = matcher.group(1);
					URL urlResource = pageClass.getResource(url);
					// If the resource is not found for a page skip it
					if (urlResource != null) {
						PackageResourceReference packageResourceReference = new PackageResourceReference(pageClass, url);
						String replacedUrl = RequestCycle.get().urlFor(packageResourceReference, null).toString();
						StringBuilder urlBuilder = new StringBuilder();
						urlBuilder.append("url('");
						urlBuilder.append(replacedUrl);
						urlBuilder.append("')");
						original = matcher.replaceFirst(urlBuilder.toString());
					}
				} catch (Exception e) {
					StringWriter stringWriter = this.printStack(e);
					throw new WicketRuntimeException(stringWriter.toString());
				}
			}

		}
		return original;
	}

	/**
	 * Prints the stack trace to a print writer
	 * 
	 * @param exception
	 *            the exception
	 * @return the string writer containing the stack trace
	 */
	private StringWriter printStack(Exception exception) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		return stringWriter;
	}
}