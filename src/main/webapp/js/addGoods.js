const addGoodsPage = {
    getClassIdList: function (element) {
        let getClassIdListRequest = new XMLHttpRequest();
        getClassIdListRequest.onreadystatechange = function () {
            if (getClassIdListRequest.readyState === XMLHttpRequest.DONE) {
                if (getClassIdListRequest.status === 200) {
                    element.update(JSON.parse(getClassIdListRequest.responseText));
                } else {
                    console.log("There was a problem with the request(getClassIdList).");
                }
            }

        };
        getClassIdListRequest.open("Get", "/GoodsManager/api/getClassIDLIst");
        getClassIdListRequest.send();
    },
    sendNewGoods: function (formObj, element) {
        let sendGoodsRequest = new XMLHttpRequest();
        sendGoodsRequest.onreadystatechange = function () {
            if (sendGoodsRequest.readyState === XMLHttpRequest.DONE) {
                if (sendGoodsRequest.status === 200) {
                    element.update(JSON.parse(sendGoodsRequest.responseText));
                } else
                    console.log("There was a problem with the request(sendNewGoods).");
            }
        };
        sendGoodsRequest.open('Post', '/GoodsManager/api/addGoods');
        sendGoodsRequest.send(formObj);
    }

};
