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
package org.apache.wicket.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IOUtils;

public class MediaUploadFieldCustomizable extends MediaUploadField
{
	private static final long serialVersionUID = 1L;

	private Image image;

	private FormComponentLabel formComponentLabel;

	public MediaUploadFieldCustomizable(String id, Type type)
	{
		this(id, null, type);
	}

	public MediaUploadFieldCustomizable(String id, IModel<? extends List<FileUpload>> model,
		Type type)
	{
		super(id, model, type);
	}

	public void setImage(Image image)
	{
		if (type != Type.IMAGE)
		{
			throw new WicketRuntimeException("The type of the MediaUploadField is not IMAGE");
		}
		image.setOutputMarkupId(true);
		this.image = image;
	}

	public void setLabel(FormComponentLabel formComponentLabel)
	{
		formComponentLabel.setOutputMarkupId(true);
		this.formComponentLabel = formComponentLabel;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		if (type == Type.IMAGE && image != null)
		{
			response.render(JavaScriptReferenceHeaderItem.forReference(Application.get()
				.getJavaScriptLibrarySettings()
				.getJQueryReference()));

			InputStream javaScriptStream = null;
			InputStream cssStream = null;
			try
			{
				// javascript
				javaScriptStream = MediaUploadField.class.getResourceAsStream(this.getClass()
					.getSimpleName() + ".js");
				String javaScript = IOUtils.toString(javaScriptStream);
				javaScript = javaScript.replaceAll("%\\(imageid\\)", image.getMarkupId());
				javaScript = javaScript.replaceAll("%\\(mediauploadfieldid\\)", getMarkupId());
				response.render(JavaScriptReferenceHeaderItem.forScript(javaScript, "script_" +
					getMarkupId()));


				// css
				if (formComponentLabel != null)
				{
					cssStream = MediaUploadField.class.getResourceAsStream(this.getClass()
						.getSimpleName() + ".css");
					String css = IOUtils.toString(cssStream);
					css = css.replaceAll("%\\(mediauploadfieldid\\)", getMarkupId());
					css = css.replaceAll("%\\(mediauploadfieldlabelid\\)",
						formComponentLabel.getMarkupId());
					response.render(CssHeaderItem.forCSS(css, "css_" + getMarkupId()));
				}
			}
			catch (IOException ioe)
			{
				throw new WicketRuntimeException("Error while reading the MediaUploadField.js", ioe);
			}
			finally
			{
				IOUtils.closeQuietly(javaScriptStream);
				IOUtils.closeQuietly(cssStream);
			}
		}
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
	}
}
