var %sObserve = {};
$("#%s").bind("%s",function(event){
	function simpleKeys (original) {
	  return Object.keys(original).reduce(function (obj, key) {
	    obj[key] = typeof original[key] === 'object' ? '{ }' : original[key];
	    return obj;
	  }, {});
	}
	var selfRef = $(this);
	%sObserve.event = event;
	if(!%sObserve.registered){
		Object.observe(%sObserve,function(changes){
			changes.forEach(function(change){
				var jsonData = window.JSON.stringify({
					value: selfRef.text(),
					html: selfRef.html(),
					oldEvent: change.oldValue ? simpleKeys(change.oldValue):"", 
					newEvent: simpleKeys(%sObserve.event)
				});
				Wicket.Ajax.post({'u':'%s',ep:{data: jsonData}});
			});
		});
		%sObserve.registered=true;
	}
});

