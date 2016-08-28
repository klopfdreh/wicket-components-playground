package org.apache.wicket.security;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;

public class SecurityApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return null;
	}

	@Override
	protected void init()
	{
		super.init();
		getRequestCycleListeners().add(new AbstractRequestCycleListener(){
			@Override
			public void onEndRequest(RequestCycle cycle)
			{
				((WebResponse)cycle.getResponse()).setHeader("X-XSS-Protection", "1; mode=block");
				((WebResponse)cycle.getResponse()).setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
				((WebResponse)cycle.getResponse()).setHeader("X-Content-Type-Options", "nosniff");
				((WebResponse)cycle.getResponse()).setHeader("X-Frame-Options", "DENY");
				((WebResponse)cycle.getResponse()).setHeader("Content-Security-Policy", "default-src https:");
				
				/*
				 * Also add this to your web.xml
				 * 	<!-- Force SSL for entire site -->
				 *	<security-constraint>
				 *		<web-resource-collection>
				 *			<web-resource-name>Entire Application</web-resource-name>
				 *			<url-pattern>/*</url-pattern>
				 *		</web-resource-collection>
				 *		<user-data-constraint>
				 *			<transport-guarantee>CONFIDENTIAL</transport-guarantee>
				 *		</user-data-constraint>
				 *	</security-constraint>
				 */
			}
		});
	}
}
