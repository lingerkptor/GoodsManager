/**
	obj.appendMessage(String message);
	obj.getCategory = {"category" : ["info","warn","err"]};
 */

var getMessage = function (messagePool) {
	request = new XMLHttpRequest();
	request.onreadystatechange = function () {
		if (request.readyState === XMLHttpRequest.DONE) {
			if (request.status === 200) {
				JSON.parse(request.responseText).messageList.forEach(msg => {
					messagePool.appendMessage(msg);
				});
				getMessage(messagePool);
			} else {
				console.log('http state: ' + request.readyState);
			}
		}
	};
	request.open('Post', "/GoodsManager/api/getMessages");
	request.setRequestHeader('Content-Type', 'application/json');
	request.send(JSON.stringify(messagePool.getCategory));
};
