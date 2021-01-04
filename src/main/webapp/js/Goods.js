const GoodsModel = function (data) {
    let goods = data;
    let observers = [];
    /** 上傳圖片的請求函式
     * @param {Object} formObj 請求參數，為FormData物件．
     * @param {Object} viewObj 請求完成後，要呼叫view更新的物件
     */
    function uploadPicture(formObj) {
        let uploadPictureRequest = new XMLHttpRequest();
        uploadPictureRequest.onreadystatechange = function () {
            if (uploadPictureRequest.readyState === XMLHttpRequest.DONE) {
                if (uploadPictureRequest.status === 200) {
                    console.log(uploadPictureRequest.responseText);
                    //goods.picturesKey.push(JSON.parse(uploadPictureRequest.responseText));
                    //notifyUpdate();
                } else {
                    console.log("There was a problem with the request(uploadPictureRequest).");
                }
            }
        };
        uploadPictureRequest.open('Post', "/GoodsManager/api/UploadPicture");
        // uploadPictureRequest.setRequestHeader('Content-Type','Multipart'); 不用設定給瀏覽器自己產生
        uploadPictureRequest.send(formObj);
    }
    /**   通知有註冊的觀察者(Observer)更新
     */
    function notifyUpdate() {
        observers.forEach(observer => {
            observer.update();
        });
    }

    return {
        register: function (observer) {
            observers.push(observer);
        },
        deregister: function (observer) {
            observers.splice(observers.indexOf(observer), 1);
        },
        GID: function () {
            return goods.GID;
        },
        goodsName: function (value) {
            if (typeof value === 'undefined')
                return goods.goodsName;
            goods.goodsName = value;
            notifyUpdate();
        },
        L: function (value) {
            if (typeof value === 'undefined')
                return goods.L;
            goods.L = value;
            notifyUpdate();
        },
        W: function (value) {
            if (typeof value === 'undefined')
                return goods.W;
            goods.W = value;
            notifyUpdate();
        },
        H: function (value) {
            if (typeof value === 'undefined')
                return goods.H;
            goods.H = value;
            notifyUpdate();
        },
        price: function (value) {
            if (typeof value === 'undefined')
                return goods.price;
            goods.price = value;
            notifyUpdate();
        },
        count: function (value) {
            if (typeof value === 'undefined')
                return goods.count;
            goods.count = value;
            notifyUpdate();
        },
        className: function (value) {
            if (typeof value === 'undefined')
                return goods.className;
            goods.className = value;
            notifyUpdate();
        },
        getTags: function () {
            return goods.tags;
        },
        addTag: function (tagName) {
            goods.tags.push(tagName);
            notifyUpdate();
        },
        removeTag: function (tagName) {
            goods.tags.splice(goods.tags.indexOf(tagName), 1);
            notifyUpdate();
        },
        getPicturesKey: function () {
            return goods.getPicturesKey;
        },
        addPicture: function (files) {
            let formData = new FormData();
            for (i = 0; i < files.length; i++) {
                formData.append("picture" + i, files[i], files[i].name);
            }
            uploadPicture(formData);
        },
        removePicture: function (picturesKey) {
            goods.picturesKey.splice(goods.picturesKey.indexOf(picturesKey), 1);
            notifyUpdate();
        }

    };

};

const ClassModel = function (data) {
    let classList = data;
    return {
        getClassList: function (className) {
            /** 找尋指定分類名稱的下一階分類清單
             * @param {String} className 分類名稱
             * @param {Array} list 分類清單
             */
            function searchClassList(className, list) {
                for (i = 0; i < list.length; i++) {
                    if (className == list[i].classificationName) {
                        let result = [];
                        list[i].subClassificationList.forEach(ele => {
                            result.push(ele.classificationName);
                        });
                        return result;
                    }
                    if ((typeof list[i].subClassificationList) !== "undefined") {
                        let result = searchClassList(className, list[i].subClassificationList);
                        if (result !== null)
                            return result;
                    }
                }
                return null;
            }
            if ((typeof className) === "undefined") {
                return classList;
            } else {
                return searchClassList(className, classList);
            }
        },
        getClassChain: function (className) {
            /**查詢分類鏈(從主分類到該分類名稱經過的途徑)
             * @param {String} className Search Condition with Classifiction Name
             * @param {Object} classTreeNode sub Classifiction Node
             */
            function classSearchChain(className, classTreeNode) {//tree search
                if (className == classTreeNode.classificationName)
                    return classTreeNode.classificationName;
                if ((typeof classTreeNode.subClassificationList) == "undefined") {
                    return null;
                } else {
                    for (i = 0; i < classTreeNode.subClassificationList.length; i++) {
                        let result = classSearchChain(className, classTreeNode.subClassificationList[i]);
                        if (result === null) {
                            continue;
                        } else {
                            return classTreeNode.classificationName + "|" + result;
                        }
                    }
                }
            }
            let classChain;
            for (i = 0; i < classList.length; i++) {
                let result = classSearchChain(className, classList[i]);
                if (result === null)
                    continue;
                else {
                    classChain = result.splite("|");
                }
            }
            return classChain;
        },
    };
};

const GoodsPageModel = function () {
    let classList;
    let tagList;
    let goods;

    // 請求函式 start
    /** 上傳圖片的請求函式
     * @param {Object} formObj 請求參數，為FormData物件．
     * @param {Object} viewObj 請求完成後，要呼叫view更新的物件
     */
    function uploadPicture(formObj, viewObj) {
        let uploadPictureRequest = new XMLHttpRequest();
        uploadPictureRequest.onreadystatechange = function () {
            if (uploadPictureRequest.readyState === XMLHttpRequest.DONE) {
                if (uploadPictureRequest.status === 200) {
                    viewObj.update(JSON.parse(uploadPictureRequest.responseText));
                } else {
                    console.log("There was a problem with the request(uploadPictureRequest).");
                }
            }
        };
        uploadPictureRequest.open('Post', "/GoodsManager/api/UploadPicture");
        // uploadPictureRequest.setRequestHeader('Content-Type','Multipart'); 不用設定給瀏覽器自己產生
        uploadPictureRequest.send(formObj);
    }
    /** 取得分類清單的請求函式
     */
    function getClassList() {
        let getClassListRequest = new XMLHttpRequest();
        getClassListRequest.onreadystatechange = function () {
            if (getClassListRequest.readyState === XMLHttpRequest.DONE) {
                if (getClassListRequest.status === 200) {
                    classList = JSON.parse(getClassListRequest.responseText);
                } else {
                    console.log("There was a problem with the request(getClassListRequest).");
                }
            }
        };
        getClassListRequest.open('Get', "/GoodsManager/api/GetClassificationList");
        getClassListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        getClassListRequest.send();
    }
    /** 取得標籤清單的請求函式
     */
    function getTagList() {
        let getTagListRequest = new XMLHttpRequest();
        getTagListRequest.onreadystatechange = function () {
            if (getTagListRequest.readyState === XMLHttpRequest.DONE) {
                if (getTagListRequest.status === 200) {
                    tagList = JSON.parse(getTagListRequest.responseText);
                } else
                    console.log("There was a problem with the request(getTagListRequest).");
            }
        };
        getTagListRequest.open('Get', "/GoodsManager/api/getTagList");
        getTagListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        getTagListRequest.send();
    }
    /** 建立新商品  的請求函式
     * @param {Object} goodsObj 請求參數，為goods物件，
     * @param {Object} viewObj 請求完成後，要呼叫view更新的物件
     */
    function sendNewGoods(goodsObj, viewObj) {
        let sendGoodsRequest = new XMLHttpRequest();
        sendGoodsRequest.onreadystatechange = function () {
            if (sendGoodsRequest.readyState === XMLHttpRequest.DONE) {
                if (sendGoodsRequest.status === 200) {
                    viewObj.update(JSON.parse(sendGoodsRequest.responseText));
                } else
                    console.log("There was a problem with the request(sendNewGoods).");
            }
        };
        sendGoodsRequest.open('Post', '/GoodsManager/api/increateGoods');
        sendGoodsRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        sendGoodsRequest.send(JSON.stringify(goodsObj));
    }
    /** 更新商品資料 的請求的函式
     * @param {Object} goodsObj 請求參數，為goods物件，
     * @param {Object} viewObj 請求完成後，要呼叫view更新的物件
     */
    function modifyGoods(goodsObj, viewObj) {
        let modifyGoodsRequest = new XMLHttpRequest();
        modifyGoodsRequest.onreadystatechange = function () {
            if (modifyGoodsRequest.readyState === XMLHttpRequest.DONE) {
                if (modifyGoodsRequest.status === 200) {
                    viewObj.update(JSON.parse(modifyGoodsRequest.responseText));
                } else
                    console.log("There was a problem with the request(modifyGoods).");
            }
        };
        modifyGoodsRequest.open('Post', '/GoodsManager/api/modifyGoods');
        modifyGoodsRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        modifyGoodsRequest.send(JSON.stringify(goodsObj));
    }
    /** 取得指定Id的商品 的請求函式 
     * @param {int} goodsId 請求參數，商品ID
     */
    function getGoods(goodsId) {
        let getGoodsRequest = new XMLHttpRequest();
        getGoodsRequest.onreadystatechange = function () {
            if (getGoodsRequest.readyState === XMLHttpRequest.DONE) {
                if (getGoodsRequest.status === 200) {
                    goods = JSON.parse(sendGoodsRequest.responseText);
                } else
                    console.log("There was a problem with the request(getGoods).");
            }
        };
        getGoodsRequest.open('Post', '/GoodsManager/api/getGoods');
        getGoodsRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        getGoodsRequest.send(JSON.stringify({ goodsName: goodsNameStr }));
    }
    //  請求函式end  


    return {
        init: function (goodsID, viewObj) {
            let count = 2;
            getClassList();
            getTagList();
            if (typeof (goodsID) !== 'undefined') {
                getGoods(goodsID);
                count = 3;
            }
            if (typeof viewObj !== 'undefined')
                for (i = 0; i < count;) {
                    if (typeof classList !== 'undefined')
                        i++;
                    if (typeof tagList !== 'undefined')
                        i++;
                    if (count > 2 && typeof goods !== 'undefined')
                        i++;
                    viewObj.update(i, count);
                    if (i < count)
                        i = 0;
                }
        },
        getGoodsFun: function () {
            return {
                getGoods: function () {
                    return goods;
                },
                increateGoods: function (viewObj) {
                    sendNewGoods(goods, viewObj);
                },
                modifyGoods: function (viewObj) {
                    modifyGoods(goods, viewObj);
                }
            };
        },
        getClassList: function (className) {
            /** 找尋指定分類名稱的下一階分類清單
             * @param {String} className 分類名稱
             * @param {Array} list 分類清單
             */
            function searchClassList(className, list) {
                for (i = 0; i < list.length; i++) {
                    if (className == list[i].classificationName) {
                        return list[i].subClassificationList;
                    }
                    if ((typeof list[i].subClassificationList) !== "undefined") {
                        let result = searchClassList(className, list[i].subClassificationList);
                        if (result !== null)
                            return result;
                    }
                }
                return null;
            }
            if ((typeof className) === "undefined") {
                return classList;
            } else {
                return searchClassList(className, classList);
            }
        },
        getClassChain: function (className) {
            /**查詢分類鏈(從主分類到該分類名稱經過的途徑)
             * @param {String} className Search Condition with Classifiction Name
             * @param {Object} classTreeNode sub Classifiction Node
             */
            function classSearchChain(className, classTreeNode) {//tree search
                if (className == classTreeNode.classificationName)
                    return classTreeNode.classificationName;
                if ((typeof classTreeNode.subClassificationList) == "undefined") {
                    return null;
                } else {
                    for (i = 0; i < classTreeNode.subClassificationList.length; i++) {
                        let result = classSearchChain(className, classTreeNode.subClassificationList[i]);
                        if (result === null) {
                            continue;
                        } else {
                            return classTreeNode.classificationName + "|" + result;
                        }
                    }
                }
            }
            let classChain;
            for (i = 0; i < classList.length; i++) {
                let result = classSearchChain(className, classList[i]);
                if (result === null)
                    continue;
                else {
                    classChain = result.splite("|");
                }
            }
            return classChain;
        },
        getTagList: function () {
            return tagList;
        },
        uploadPicture: function (files, viewObj) {
            let formObj = new FormData();
            for (i = 0; i < files.length; i++) {
                formObj.append('picture' + i, files[i]);
            }
            uploadPicture(formObj, viewObj);
        }
    };
};