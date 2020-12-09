
window.onload = function () {
    const messageArea = document.getElementById('messageArea');

    const queryGoodsBtn = document.getElementById('queryGoods');
    const addGoodsBtn = document.getElementById('addGoods');
    const classManageBtn = document.getElementById('classManage');
    const tagManageBtn = document.getElementById('tagManage');
    const workDeskele = document.getElementById('workdesk');


    const main = indexPage();

    const messagePool = main.getMessagePool(messageArea);
    messagePool.beforeMsgRead();
    messagePool.listening();
    main.registerWorkDesk(workDeskele);
    queryGoodsBtn.addEventListener('click', function (e) {
        main.changeWork("./searchGoods.html");
    });
    addGoodsBtn.addEventListener('click', function (e) {
        main.changeWork("./addGoods.html");
    });
    classManageBtn.addEventListener('click', function (e) {
        main.changeWork("./classificationManage.html");
    });
    tagManageBtn.addEventListener('click', function (e) {
        main.changeWork("./tagManage.html");
    });
};


window.onunload = function () {
    messagePool.afterMsgWriting();
};

