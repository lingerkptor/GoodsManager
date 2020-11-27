const indexPage = function () {
    let workDesk;
    return {
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
        registerWorkDesk: function (element) {
            workDesk = element;
        },
        changeWork: function (page) {
            workDesk.src = page;
        }
        
    };

}();