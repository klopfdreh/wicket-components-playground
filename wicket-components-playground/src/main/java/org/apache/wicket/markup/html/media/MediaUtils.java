package org.apache.wicket.markup.html.media;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;

/**
 * Helper method to provide access to basic media files like subtitles
 * 
 * @author Tobias Soloschenko
 * 
 */
public class MediaUtils
{

	/**
	 * Method that has to be called within the init method of the web application to make
	 * translation files accessible
	 */
	public static void init()
	{
		IPackageResourceGuard packageResourceGuard = Application.get()
			.getResourceSettings()
			.getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard)
		{
			SecurePackageResourceGuard securePackageResourceGuard = (SecurePackageResourceGuard)packageResourceGuard;
			securePackageResourceGuard.addPattern("+*.vtt");
			securePackageResourceGuard.addPattern("+*.srt");
		}
	}

}
