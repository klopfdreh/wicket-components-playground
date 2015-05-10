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

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;

public class MediaUploadField extends FileUploadField
{

	private static final long serialVersionUID = -1852799458979511170L;

	public enum Type
	{
		IMAGE("image/*", "camera"), VIDEO("video/*", "camcorder"), AUDIO("audio/*", "microphone");

		private String accept;

		private String capture;

		private Type(String accept, String capture)
		{
			this.accept = accept;
			this.capture = capture;
		}

		public String getAccept()
		{
			return accept;
		}

		public String getCapture()
		{
			return capture;
		}
	}

	protected Type type;


	public MediaUploadField(String id, Type type)
	{
		this(id, null, type);
	}

	public MediaUploadField(String id, IModel<? extends List<FileUpload>> model, Type type)
	{
		super(id, model);
		this.type = type;
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		// Default handling for component tag
		super.onComponentTag(tag);

		// Capture attribute must be set
		tag.put("capture", type.getCapture());

		// The accept type to get the representation of
		tag.put("accept", type.getAccept());
	}
}
