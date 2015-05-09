function readURL(input, image, img, maxHeight, maxWidth) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			img.src = e.target.result;
			img.onload = function(){
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
			};
		}
		reader.readAsDataURL(input.files[0]);
	}
}
$(function() {
	var image = $('#%(imageid)');
	var img = $('#%(imageid)').get(0);
	var maxHeight = img.height;
	var maxWidth = img.width;
	$("#%(mediauploadfieldid)").change(function() {
		readURL(this, image, img, maxHeight, maxWidth);
	});
});