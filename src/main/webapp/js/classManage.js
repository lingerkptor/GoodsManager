const classManagePage = function () {
    let classifictionList = [];
    return {
        getClassList: function (updateObj) {
            /**
             *  updateObj
             * {
             *  update: function(){ // doUpdate  }
             * }
             */
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
            /**
             * sendObj
             * {
             *  classificationName : String
             *  parentClassificationName : String
             * }
             * updateObj
             * {
             *  update: function(){ // doUpdate  }
             * }
             */
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
        },
        updateClass: function (sendObj, updateObj) {
            /**
             * sendObj
             * {
             *  classificationName : String
             *  classificationNewName : String
             * parentClassificationName :String
             * }
             * updateObj
             * {
             *  update: function(){ // doUpdate  }
             * }
             */
            let updateClassRequest = new XMLHttpRequest();
            updateClassRequest.onreadystatechange = function () {
                if (updateClassRequest.readyState == XMLHttpRequest.DONE) {
                    if (updateClassRequest.status == 200) {
                        let responseObj = JSON.parse(updateClassRequest.responseText);
                        if (typeof (responseObj.Code) != "undefined")
                            updateObj.update(responseObj.Code);
                    }
                }
            };
            updateClassRequest.open('Post', "/GoodsManager/api/UpdateClassification");
            updateClassRequest.setRequestHeader('Content-Type', 'application/json');
            updateClassRequest.send(JSON.stringify(sendObj));
        },
        deleteClass: function (sendObj, updateObj) {
            /**
             * sendObj
             * {
             *  classificationName: String
             * }
             * updateObj
             * {
             *  update: function(){ // doUpdate  }
             * }
             */
            let deleteClassRequest = new XMLHttpRequest();
            deleteClassRequest.onreadystatechange = function () {
                if (deleteClassRequest.readyState == XMLHttpRequest.DONE) {
                    if (deleteClassRequest.status == 200) {
                        let responseObj = JSON.parse(deleteClassRequest.responseText);
                        if (typeof (responseObj.Code) != "undefined")
                            updateObj.update(responseObj.Code);
                    }
                }
            };
            deleteClassRequest.open('Post', "/GoodsManager/api/DeleteClassification");
            deleteClassRequest.setRequestHeader('Content-Type', 'application/json');
            deleteClassRequest.send(JSON.stringify(sendObj));
        }
    };
}();