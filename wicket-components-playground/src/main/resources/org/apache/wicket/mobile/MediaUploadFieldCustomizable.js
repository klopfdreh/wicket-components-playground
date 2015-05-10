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
function readURL(input, image, img, maxHeight, maxWidth) {
	if (input.files && input.files[0]) {
				
		// Initialize the reader
		var reader = new FileReader();
		
		// Starts to load
		reader.onload = function(e) {
			image.load(function(){
		        var ratio = 0;  // Used for aspect ratio
		        var width = this.naturalWidth;    // Current image width
		        var height = this.naturalHeight;  // Current image height
		        
		        var scaledHeight;
		        var scaledWidth;

		        // Check if the current width is larger than the max
		        if(width > maxWidth){
		            ratio = maxWidth / width;   // get ratio for scaling image
		            // Scale height based on ratio
		            scaledHeight = height * ratio
		            // Set new width
		            scaledWidth = maxWidth
		            height = height * ratio;    // Reset height to match scaled image
		            width = width * ratio;    // Reset width to match scaled image
		        }

		        // Check if current height is larger than max
		        if(height > maxHeight){
		            ratio = maxHeight / height; // get ratio for scaling image
		            // Set new height
		            scaledHeight = maxHeight
		            // Scale width based on ratio
		            scaledWidth = width * ratio
		            width = width * ratio;    // Reset width to match scaled image
		            height = height * ratio;    // Reset height to match scaled image
		        }
		        
		        // If the image is smaller - shrink it.
		        if(width < maxWidth){
		            scaledWidth = width
		        }
		        if(height < maxHeight){
		            scaledHeight = height
		        }
				
		        // Adjust the width / height
				image.css("height", scaledHeight);
				image.css("width", scaledWidth);
				
				// Set image to be visible
				image.css({
					"visibility":"visible"
				});
			});
		}
		
		// Finished loading
		reader.onloadend = function(e){
			if (e.target.readyState == FileReader.DONE) {
				// Set the image to be invisible (no flickering)
				image.css({
					"visibility":"hidden"
				});
				img.src = e.target.result;
			}
		}
		reader.readAsDataURL(input.files[0]);
	}
}
$(function() {
	var image = $('#%(imageid)');
	var img = image.get(0);
	image.load(function(){
		if(!image.data("resize_dimensions")){
			image.data("resize_dimensions",{
				height:img.height, 
				width:img.width
			});
		}
		$("#%(mediauploadfieldid)").change(function() {
			readURL(this, image, img, image.data("resize_dimensions").height, image.data("resize_dimensions").width);
		});
	});
});