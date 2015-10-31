package org.apache.wicket.markup.html.media;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.media.video.Video;

public class FileSystemExamplePage extends WebPage
{

	private static final long serialVersionUID = 1L;
	
	public FileSystemExamplePage() throws IOException, URISyntaxException
	{
//		Path path = FileSystemResourceReference.getPath(URI.create("file:///Users/klopfdreh/Documents/Eclipse%20Workspace/myproject/src/main/java/com/mycompany/video1.mp4"));
		Path path = FileSystemResourceReference.getPath(URI.create("jar:file:///Users/klopfdreh/Documents/Eclipse%20Workspace/myproject/src/main/java/com/mycompany/test.zip!/test/video1.mp4"));
		add(new Video("video", new FileSystemResourceReference(path){
			private static final long serialVersionUID = 1L;

			@Override
			protected String getMimeType()
			{
				return "video/mp4";
			}
		}));
	}

}
