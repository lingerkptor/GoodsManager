var installPage = {
	getMessagePool: function (element) {
		messageList = [];
		return {
			getCategory: { "category": ["info", "warn", "err"] },
			appendMessage: function (msg) {
				messageList.push(msg);
				console.log(msg.dateTime);
				console.log(msg.category);
				console.log(msg.context);
				msgNode = element.getElementsByClassName('prototype')[0].cloneNode(true);
				element.appendChild(msgNode);
				msgNode.getElementsByClassName('time')[0].innerText += msg.dateTime;
				msgNode.getElementsByClassName('context')[0].innerText += msg.context;
				msgNode.style.display = '';
				msgNode.classList.remove('prototype');
				msgNode.classList.add(msg.category);
			},
			beforeMsgRead: function () {
				messageList = JSON.parse(localStorage.getItem('messageList'));
			},
			afterMsgWriting: function () {
				localStorage.setItem('messageList', JSON.stringify(messageList));
			},
			listening: function () {
				getMessage(this);
			}
		}
	},
	uploadDBFile: function (formObj) {
		/**
			part{
				DBName : String
			}
			part{
				JDBC: file
			}
			part{
				SQLZip : file
			}
		 */
		request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (request.readyState === XMLHttpRequest.DONE) {
				if (request.status === 200) {
					let responce = JSON.parse(request.responceText);
					if (responce.uploadSuccess) {
						console.log('上傳成功');
					} else {
						console.log('上傳失敗');
					}
					console.log(request.responseText);
				} else {
					alert('There was a problem with the request.');
				}
			}
		};
		request.open('Post', "/GoodsManager/api/UploadDBFiles");
		request.send(formObj);


	},
	getActiveDB: function (updateObj) {
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (request.readyState === XMLHttpRequest.DONE) {
				if (request.status === 200) {
					let responce = JSON.parse(request.responseText);
					if (typeof responce.activedDBList != 'undefined') {
						updateObj.update(responce.activedDBList);
					}
				}
			}
		};
		request.open('Get', "/api/getActiveDB");
		request.setRequestHeader('Content-Type', 'text/plain');
		request.send();

	},
	installDB: function (installJsonObj) {
		/**
			installJsonObj = {
				"customized": boolean,
				"databaseName": String,
				"JDBC": String,
				"URL": String,
				"account": String,
				"password": String,
				"maxConnection": Int,
			};
		 */
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (request.readyState === XMLHttpRequest.DONE) {
				if (request.status === 200) {
					console.log('install sucess');
					console.log(request.responceText);
				} else {
					console.log('install failure');
					console.log(request.responceText);
				}

			}
		};
		request.open('Post', "/GoodsManager/api/install");
		request.setRequestHeader('Content-Type', 'application/json');
		request.send(JSON.stringify(installJsonObj));
	}
};