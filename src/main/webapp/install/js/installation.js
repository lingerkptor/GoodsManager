const installPage = {
	getMessagePool: function (element) {
		let messageList = [];
		return {
			getCategory: { "category": ["info", "warn", "err"] },
			appendMessage: function (msg) {
				messageList.push(msg);
				msgNode = element.getElementsByClassName('prototype')[0].cloneNode(true);
				element.appendChild(msgNode);
				msgNode.getElementsByClassName('time')[0].innerText = msg.dateTime;
				msgNode.getElementsByClassName('context')[0].innerText = msg.context;
				msgNode.classList.remove('prototype');
				msgNode.classList.add(msg.category);
			},
			beforeMsgRead: function () {
				if (JSON.parse(localStorage.getItem('messageList')) !== null)
					JSON.parse(localStorage.getItem('messageList')).forEach(msg => {
						this.appendMessage(msg);
					});
			},
			afterMsgWriting: function () {
				localStorage.setItem('messageList', JSON.stringify(messageList));
			},
			listening: function () {
				getMessage(this);
			}
		}
	},
	uploadDBFile: function (formObj, updateObj) {
		/** formObj{
				part{
					DBName : String
				}
				part{
					JDBC: file
				}
				part{
					SQLZip : file
				}
			}
		 */
		let uploadDBFileRequest = new XMLHttpRequest();
		uploadDBFileRequest.onreadystatechange = function () {
			if (uploadDBFileRequest.readyState === XMLHttpRequest.DONE) {
				if (uploadDBFileRequest.status === 200) {
					let responce = JSON.parse(uploadDBFileRequest.responseText);
					if (responce.uploadSuccess) {
						console.log('上傳成功');
					} else {
						console.log('上傳失敗');
					}
					if (typeof updateObj != 'undefined')
						updateObj.update(responce.uploadSuccess);
				} else {
					alert('There was a problem with the request(uploadDBFile).');
				}
			}
		};
		uploadDBFileRequest.open('Post', "/GoodsManager/api/UploadDBFiles");
		uploadDBFileRequest.send(formObj);


	},
	getActiveDB: function (updateObj) {
		let request = new XMLHttpRequest();
		request.onreadystatechange = function () {
			if (request.readyState === XMLHttpRequest.DONE) {

				if (request.status === 200) {
					responceObj = JSON.parse(request.responseText);
					if (typeof (responceObj.activedDBList) == 'undefined')
						updateObj.update([]);
					else
						updateObj.update(JSON.parse(request.responseText).activedDBList);
				} else {
					alert('There was a problem with the request(getActiveDB).');
				}
			}
		};
		request.open('Get', "/GoodsManager/api/getActiveDB");
		request.setRequestHeader('Content-Type', 'text/plain');
		request.send();
	},
	installDB: function (installJsonObj, updateObj) {
		/**
			installJsonObj = {
				"customized": boolean,
				"databaseName": String,
				"JDBCName": String,
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
					updateObj.update(JSON.parse(request.responseText));
				} else
					alert('There was a problem with the request(installDB).');
			}
		};
		request.open('Post', "/GoodsManager/api/install");
		request.setRequestHeader('Content-Type', 'application/json');
		request.send(JSON.stringify(installJsonObj));
	}
};