package org.apache.wicket.markup.html.media;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.media.video.Video;

public class FileSystemExamplePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public FileSystemExamplePage() throws IOException, URISyntaxException
	{
		Path path1 = FileSystemResourceReference.getPath(URI.create("jar:file:///Users/klopfdreh/Documents/Eclipse%20Workspace/myproject/src/main/java/com/mycompany/test.zip!/test/video1.mp4"));
		add(new Video("video", new FileSystemResourceReference("name1", path1)));


		Path path2 = FileSystemResourceReference.getPath(URI.create("file:///Users/klopfdreh/Documents/Eclipse%20Workspace/myproject/src/main/java/com/mycompany/video1.mp4"));
		final FileSystemResource fileSystemResource = new FileSystemResource(path2)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String getMimeType() throws IOException
			{
				return "video/mp4";
			}
		};

		add(new Video("video", new FileSystemResourceReference("name2", path2)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected FileSystemResource getFileSystemResource()
			{
				return fileSystemResource;
			}
		}));
	}

}
