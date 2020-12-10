const classManagePage = function () {
    let classifictionList = [];
    return {
        getClassList: function (updateObj) {
            let getClassListRequest = new XMLHttpRequest();
            getClassListRequest.onreadystatechange = function () {
                if (getClassListRequest.readyState == XMLHttpRequest.DONE) {
                    if (getClassListRequest.status == 200) {
                        let responseObj = JSON.parse(getClassListRequest.responseText);
                        if (typeof (responseObj.classificationList) != "undefined") {
                            classifictionList = responseObj.classificationList;
                            updateObj.update(responseObj.classificationList);
                        }
                    }
                }
            };
            getClassListRequest.open('Get', "/GoodsManager/api/GetClassificationList");
            getClassListRequest.setRequestHeader('Content-Type', 'text/plain');
            getClassListRequest.send();

        },
        addClass: function (sendObj, updateObj) {
            let addClassRequest = new XMLHttpRequest();
            addClassRequest.onreadystatechange = function () {
                if (addClassRequest.readyState == XMLHttpRequest.DONE) {
                    if (addClassRequest.status == 200) {
                        let responseObj = JSON.parse(addClassRequest.responseText);
                        if (typeof (responseObj.Code) != "undefined")
                            updateObj.update(responseObj.Code);
                    }
                }
            };

            addClassRequest.open('Post', "/GoodsManager/api/IncreateClassification");
            addClassRequest.setRequestHeader('Content-Type', 'application/json');
            addClassRequest.send(JSON.stringify(sendObj));
        }
    };
}();