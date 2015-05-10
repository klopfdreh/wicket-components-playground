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
		image.css({
			"visibility":"hidden"
		});
		var reader = new FileReader();
		reader.onload = function(e) {
			image.load(function(){
		        var ratio = 0;  // Used for aspect ratio
		        var width = this.naturalWidth;    // Current image width
		        var height = this.naturalHeight;  // Current image height

		        // Check if the current width is larger than the max
		        if(width > maxWidth){
		            ratio = maxWidth / width;   // get ratio for scaling image
		            $(this).css("width", maxWidth); // Set new width
		            $(this).css("height", height * ratio);  // Scale height based on ratio
		            height = height * ratio;    // Reset height to match scaled image
		            width = width * ratio;    // Reset width to match scaled image
		        }

		        // Check if current height is larger than max
		        if(height > maxHeight){
		            ratio = maxHeight / height; // get ratio for scaling image
		            $(this).css("height", maxHeight);   // Set new height
		            $(this).css("width", width * ratio);    // Scale width based on ratio
		            width = width * ratio;    // Reset width to match scaled image
		            height = height * ratio;    // Reset height to match scaled image
		        }
		        
		        // If the image is smaller - shrink it.
		        if(width < maxWidth){
		        	$(this).css("width", width);
		        }
		        if(height < maxHeight){
		        	$(this).css("height", height);
		        }
			});
			img.src = e.target.result;
		}
		reader.readAsDataURL(input.files[0]);
		image.css({
			"visibility":"visible"
		});
	}
}
$(function() {
	var image = $('#%(imageid)');
	var img = image.get(0);
	var %(imageid)_space = {};
	image.load(function(){
		if(!%(imageid)_space.height){
			%(imageid)_space.height = img.height;
			%(imageid)_space.width = img.width;
		}
		$("#%(mediauploadfieldid)").change(function() {
			readURL(this, image, img, %(imageid)_space.height, %(imageid)_space.width);
		});
	});
});