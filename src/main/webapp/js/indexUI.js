
window.onload = function () {
    const messageArea = document.getElementById('messageArea');

    const queryGoodsBtn = document.getElementById('queryGoods');
    const addGoodsBtn = document.getElementById('addGoods');
    const classManageBtn = document.getElementById('classManage');
    const tagManageBtn = document.getElementById('tagManage');
    const workDeskele = document.getElementById('workdesk');



    messagePool = indexPage.getMessagePool(messageArea);
    messagePool.beforeMsgRead();
    messagePool.listening();
    indexPage.registerWorkDesk(workDeskele);
    queryGoodsBtn.addEventListener('click', function (e) {
        indexPage.changeWork("./searchGoods.html");
    });
    addGoodsBtn.addEventListener('click', function (e) {
        indexPage.changeWork("./addGoods.html");
    });
    classManageBtn.addEventListener('click', function (e) {
        indexPage.changeWork("./classManage.html");
    });
    tagManageBtn.addEventListener('click', function (e) {
        indexPage.changeWork("./tagManage.html");
    });
};


window.onunload = function () {
    messagePool.afterMsgWriting();
};

