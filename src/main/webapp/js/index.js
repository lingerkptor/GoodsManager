const indexPage = function () {
    let workDesk;
    return {
        getMessagePool: function (element) {
            let messageList = [];
            return {
                getCategory: { "category": ["info", "warn", "err"] },
                appendMessage: function (msg) {
                    console.log(messageList);
                    console.log(msg);
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
                    localStorage.setItem('messageList', JSON.stringify(this.messageList));
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

};