/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Application;
import org.apache.wicket.Component;

/**
 * The ReplacingResourceModel
 * 
 * @author Tobias Soloschenko
 */
public class ReplacingResourceModel extends AbstractReadOnlyModel<String> implements
	IComponentAssignedModel<String>
{
	private static final long serialVersionUID = 1L;

	private final String resourceKey;


	public ReplacingResourceModel(String resourceKey)
	{
		this.resourceKey = resourceKey;
	}

	/**
	 * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
	 */
	@Override
	public String getObject()
	{
		// this shouldn't be called always wrapped!
		return getReplacedResourceString(null);
	}

	/**
	 * @see org.apache.wicket.model.IComponentAssignedModel#wrapOnAssignment(org.apache.wicket.Component)
	 */
	@Override
	public IWrapModel<String> wrapOnAssignment(final Component component)
	{
		return new AssignmentWrapper(component);
	}

	/**
	 * 
	 */
	private class AssignmentWrapper extends LoadableDetachableModel<String> implements
		IWrapModel<String>
	{
		private static final long serialVersionUID = 1L;

		private final Component component;

		/**
		 * Construct.
		 * 
		 * @param component
		 */
		public AssignmentWrapper(Component component)
		{
			this.component = component;
		}

		/**
		 * @see org.apache.wicket.model.IWrapModel#getWrappedModel()
		 */
		@Override
		public IModel<String> getWrappedModel()
		{
			return ReplacingResourceModel.this;
		}

		@Override
		protected String load()
		{
			return getReplacedResourceString(component);
		}

		@Override
		protected void onDetach()
		{
			ReplacingResourceModel.this.detach();
		}

	}

	// The pattern to find URLs in CSS resources
	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{(.*)\\}");

	private String getReplacedResourceString(Component component)
	{
		String resourceKeyValue = Application.get()
			.getResourceSettings()
			.getLocalizer()
			.getString(resourceKey, component, null, null, null, (IModel<String>)null);

		StringBuffer output = new StringBuffer();

		Matcher matcher = ReplacingResourceModel.PLACEHOLDER_PATTERN.matcher(resourceKeyValue);
		// Search for placeholder to replace
		while (matcher.find())
		{
			String replacedPlaceHolder = Application.get()
				.getResourceSettings()
				.getLocalizer()
				.getString(matcher.group(1), component, null, null, null, (IModel<String>)null);
			matcher.appendReplacement(output, replacedPlaceHolder);
		}
		matcher.appendTail(output);
		return output.toString();
	}
}
