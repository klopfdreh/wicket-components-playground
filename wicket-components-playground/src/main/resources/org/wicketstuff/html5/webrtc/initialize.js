$(function(){
	var webrtc = new SimpleWebRTC({
	    // the id/element dom element that will hold "our" video
	    localVideoEl: '%(localvideoid)',
	    // the id/element dom element that will hold remote videos
	    remoteVideosEl: '',
	    // immediately ask for camera access
	    autoRequestMedia: true,
	    detectSpeakingEvents: true,
	    autoAdjustMic: false,
	    // url for the socket.io server
	    url: '%(socketiourl)',
	    socketio: { 'force new connection':true }
	});
	
	// show local volume bar
	if(%(volumebars)){
		var localvolumebar = document.createElement('div');
		localvolumebar.id="localvolume";
		localvolumebar.className="volumebar";
		var localvideo = document.getElementById('%(localvideoid)');
		localvideo.parentElement.appendChild(localvolumebar);
	}
	
	//we have to wait until it's ready
	webrtc.on('readyToCall', function () {
	    // you can name it anything
	    webrtc.joinRoom('%(roomname)');
	}); 
	
	// Shows the volume bar in the video elements
	function showVolume(el, volume) {
	    if (!el) return;
	    if (volume < -45) { // vary between -45 and -20
	        el.style.height = '0px';
	    } else if (volume > -20) {
	        el.style.height = '100%';
	    } else {
	        el.style.height = '' + Math.floor((volume + 100) * 100 / 25 - 220) + '%';
	    }
	}
	
	// If a message is received the volume is going to be refreshed
	webrtc.on('channelMessage', function (peer, label, data) {
	    if (data.type == 'volume') {
	        showVolume(document.getElementById('volume_' + peer.id), data.volume);
	    }
	});
	
	// If a new user joins another video element is added and a volume bar is created for the video element
	webrtc.on('videoAdded', function (video, peer) {
		var remotes = document.getElementById('%(markupid)');
	    if (remotes) {
	        var d = document.createElement('div');
	        d.className = 'localvideo';
	        d.id = 'container_' + webrtc.getDomId(peer);
	        d.appendChild(video);
	        
	        if(%(volumebars)){
		        // Creating the volume element for remote videos
		        var vol = document.createElement('div');
		        vol.id = 'volume_' + peer.id;
		        vol.className = 'volumebar';
		        video.onclick = function () {
		            video.style.width = video.videoWidth + 'px';
		            video.style.height = video.videoHeight + 'px';
		        };
		        d.appendChild(vol);
	        }
	        
	        remotes.appendChild(d);
	    }
	});
	
	// If a user left the room the video is going to be removed
	webrtc.on('videoRemoved', function (video, peer) {
	    var remotes = document.getElementById('%(markupid)');
	    var el = document.getElementById('container_' + webrtc.getDomId(peer));
	    if (remotes && el) {
	        remotes.removeChild(el);
	    }
	});
	
	// If the volume changed the current volume should be displayed
	webrtc.on('volumeChange', function (volume, treshold) {
	    showVolume(document.getElementById('localvolume'), volume);
	});

});