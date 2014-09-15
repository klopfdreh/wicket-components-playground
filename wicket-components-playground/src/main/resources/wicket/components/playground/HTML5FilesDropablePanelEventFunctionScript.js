/**
 * Handles the dragover and drop event and embeds the callback script. The event
 * are registered to the components with a specific markup id.
 */
$('#%s').on('dragover',function(evt){
	evt.stopPropagation();
	evt.preventDefault();
}).on('drop',function(evt){
	evt.stopPropagation();
	evt.preventDefault();
	// define the guid function it the drop scope
	var guid = (function() {
		  function s4() {
		    return Math.floor((1 + Math.random()) * 0x10000)
		               .toString(16)
		               .substring(1);
		  }
		  return function() {
		    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
		           s4() + '-' + s4() + s4() + s4();
		  };
	})();
	
	%s
});
