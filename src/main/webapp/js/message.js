/**
	obj.appendMessage(String message);
	obj.getCategory = {"category" : ["info","warn","err"]};
 */

const getMessage = function (messagePool) {
	getMessageRequest = new XMLHttpRequest();
	getMessageRequest.onreadystatechange = function () {
		if (getMessageRequest.readyState === XMLHttpRequest.DONE) {
			if (getMessageRequest.status === 200) {
				console.log(JSON.parse(getMessageRequest.responseText));
				JSON.parse(getMessageRequest.responseText).messageList.forEach(msg =>
					messagePool.appendMessage(msg)
				);
				getMessage(messagePool);
			} else {
				console.log('There was a problem with the request(getMessage).');
				console.log('http state: ' + getMessageRequest.readyState);
			}
		}
	};
	getMessageRequest.open('Post', "/GoodsManager/api/getMessages");
	getMessageRequest.setRequestHeader('Content-Type', 'application/json');
	getMessageRequest.send(JSON.stringify(messagePool.getCategory));
};
