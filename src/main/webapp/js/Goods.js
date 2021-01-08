/** 建立GoodsModel
 * @param {string} GID 商品ID
 */
const GoodsModel = function (GID) {
    /** goods 物件結構
     * @type { {GID: string,
     * goodsName:string , 
     *  L:number,
     *  W:number,
     *  H:number,
     *  price:number,
     *  count:number,
     *  className:string,
     *  tags:Set<string>,
     *  picturesKey:Set<string>}}
     */
    let goods;
    const observers = [];

    /** 初始化 start */
    getGoodsData(GID);
    /** 初始化 end */

    /** 載入資料
     * @param {string} GID
     */
    function getGoodsData(GID) {
        if (typeof GID === "undefined") {
            goods = {};
            notifyUpdate();
        } else {
            let getGoodsRequest = new XMLHttpRequest();
            getGoodsRequest.onreadystatechange = function () {
                if (getGoodsRequest.readyState == XMLHttpRequest.DONE) {
                    if (getGoodsRequest.status == 200) {
                        let responseObj = JSON.parse(getGoodsRequest.responseText);
                        if (typeof responseObj.code == "SUCCESS") {
                            goods = responseObj;
                            notifyUpdate();
                        }
                    } else
                        console.log("There was a problem with the request(getGoodsRequest).");
                }
            };
            getGoodsRequest.open('Get', "/GoodsManager/api/GetGoods?GID=" + GID);
            getGoodsRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
            getGoodsRequest.send();
        }
    }

    /** 上傳圖片的請求函式
     * @param {FormData} formObj 請求參數，為FormData物件．
     */
    function uploadPicture(formObj) {
        let uploadPictureRequest = new XMLHttpRequest();
        uploadPictureRequest.onreadystatechange = function () {
            if (uploadPictureRequest.readyState === XMLHttpRequest.DONE) {
                if (uploadPictureRequest.status === 200) {
                    /**
                     * {
                     * code:String,
                     * hash:Array<String>
                     * }
                     */
                    let json = JSON.parse(uploadPictureRequest.responseText);
                    if (json.code == "SECCESS") {
                        if (typeof goods.picturesKey === "undefined")
                            goods.picturesKey = new Set();
                        json.hash.forEach(code => {
                            goods.picturesKey.add(code);
                        });
                        notifyUpdate();
                    } else {
                        console.log("Upload Picture Failure, Message is " + json.code);
                    }
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
    /** Not a Number Exception 這不是數字的例外 
     * @param {string} message 訊息
     */
    function NaNException(message) {
        this.message = message;
    }
    /** 新增商品
     * 
     * @param {string} JSONString 
     */
    function increateGoods(JSONString) {
        let increateGoodsRequest = new XMLHttpRequest();
        increateGoodsRequest.onreadystatechange = function () {
            if (increateGoodsRequest.readyState == XMLHttpRequest.DONE) {
                if (increateGoodsRequest.status == 200) {
                    let responseObj = JSON.parse(increateGoodsRequest.responseText);
                    if (typeof responseObj.code == "SUCCESS") {
                        window.location.href = "";
                    } else {
                        console.log(responseObj.code);
                    }
                } else
                    console.log("There was a problem with the request(increateGoodsRequest).");
            }
        };
        increateGoodsRequest.open('Post', "/GoodsManager/api/increateGoods");
        increateGoodsRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        increateGoodsRequest.send(JSONString);
    }
    /** 修改商品
     * 
     * @param {string} JSONString 
     */
    function modifyGoods(JSONString) {
        let modifyGoodsRequest = new XMLHttpRequest();
        modifyGoodsRequest.onreadystatechange = function () {
            if (modifyGoodsRequest.readyState == XMLHttpRequest.DONE) {
                if (modifyGoodsRequest.status == 200) {
                    let responseObj = JSON.parse(modifyGoodsRequest.responseText);
                    if (typeof responseObj.code == "SUCCESS") {
                        window.location.href = "";
                    } else {
                        console.log(responseObj.code);
                    }

                } else
                    console.log("There was a problem with the request(modifyGoodsRequest).");
            }
        };
        modifyGoodsRequest.open('Post', "/GoodsManager/api/modifyGoods");
        modifyGoodsRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        modifyGoodsRequest.send(JSONString);
    }

    return {
        isInited: function () {
            return (typeof goods) !== "undefined";
        },
        /**
         * 
         * @param {{update:function}} observer 
         */
        register: function (observer) {
            observers.push(observer);
        },
        /**
         * 
         * @param {{update:function}} observer 
         */
        deRegister: function (observer) {
            observers.splice(observers.indexOf(observer), 1);
        },
        increateGoods: function () {
            let JSONString = JSON.stringify(goods, (key, value) => {
                if (value instanceof Set) {
                    return Array.from(value);
                }
                return value;
            });
            increateGoods(JSONString);
        },
        modifyGoods: function () {
            let JSONString = JSON.stringify(goods, (key, value) => {
                if (value instanceof Set) {
                    return Array.from(value);
                }
                return value;
            });
            modifyGoods(JSONString);
        },
        GID: function () {
            return (typeof goods.GID !== "undefined") ? goods.GID : "";
        },
        /** 商品名稱(setter & getter)
         * @param {number} value (setter) 商品名稱
         * @returns {number} (getter) 商品名稱 
         */
        goodsName: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.goodsName !== 'undefined') ? goods.goodsName : "";
            goods.goodsName = value;
        },
        /** 商品長度(setter & getter)
         * @param {number} value (setter) 商品長度值 單位為mm 
         * @returns {number} (getter) 商品長度值 單位為mm 
         */
        L: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.L !== "undefined") ? goods.L : "";
            if ((!Number.isNaN(Number.parseFloat(value)) || typeof goods.L !== "undefined")) {
                goods.L = Number.parseFloat(value);
            } else {
                console.log(value);
                throw new NaNException("The value is not Number.");
            }
        },
        /** 商品寬度(setter & getter)
        * @param {number} value (setter) 商品寬度 單位為mm 
        * @returns {number} (getter) 商品寬度 單位為mm 
        */
        W: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.W !== "undefined") ? goods.W : "";
            if ((!Number.isNaN(Number.parseFloat(value))) || typeof goods.W !== "undefined")
                goods.W = Number.parseFloat(value);
            else
                throw new NaNException("The value is not Number.");

        },
        /** 商品高度或厚度(setter & getter)
        * @param {number} value (setter) 商品高度或厚度 單位為mm 
        * @returns {number} (getter) 商品高度或厚度 單位為mm 
        */
        H: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.H !== "undefined") ? goods.H : "";
            if ((!Number.isNaN(Number.parseFloat(value))) || typeof goods.H !== "undefined")
                goods.H = Number.parseFloat(value);
            else
                throw new NaNException("The value is not Number.");

        },
        /** 商品價格(setter & getter)
        * @param {number} value (setter) 商品價格
        * @returns {number} (getter) 商品價格 
        */
        price: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.price !== "undefined") ? goods.price : "";
            if ((!Number.isNaN(Number.parseInt(value))) || typeof goods.price !== "undefined")
                goods.price = Number.parseInt(value);
            else
                throw new NaNException("The value is not Number.");
        },
        /** 商品數量(setter & getter)
         * @param {number} value (setter) 商品數量
         * @returns {number} (getter) 商品數量
         * @throws NaNException 參數不是一個數值
         */
        count: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.count !== "undefined") ? goods.count : "";
            if ((!Number.isNaN(Number.parseInt(value))) || typeof goods.count !== "undefined")
                goods.count = Number.parseInt(value);
            else
                throw new NaNException("The value is not Number.");
        },
        className: function (value) {
            if (typeof value === 'undefined')
                return (typeof goods.className !== "undefined") ? goods.className : "";
            goods.className = value;
        },
        /** 取得標籤的Iterator
         * @returns {Iterable} tags的Iterator
         */
        getTags: function () {
            if (typeof goods.tags === "undefined")
                goods.tags = new Set([]);
            return goods.tags.values();
        },
        addTag: function (tagName) {
            if (typeof goods.tags === "undefined")
                goods.tags = new Set([]);
            goods.tags.add(tagName);
        },
        removeTag: function (tagName) {
            if (typeof goods.tags === "undefined")
                goods.tags = new Set([]);
            goods.tags.delete(tagName);
        },
        /**
         * @returns {Map<string,string>} key is key ,value is src
         */
        getPicturesKey: function () {
            if (typeof goods.picturesKey === "undefined")
                goods.picturesKey = new Set([]);
            let pictureMap = new Map();
            for (let key of goods.picturesKey.values()) {
                pictureMap.set(key, "/GoodsManager/api/img?key=" + key);
            }
            return pictureMap;
        },
        addPicture: function (files) {
            let formData = new FormData();
            for (let i = 0; i < files.length; i++) {
                formData.append("picture" + i, files[i], files[i].name);
            }
            uploadPicture(formData);
        },
        removePicture: function (picturesKey) {
            goods.picturesKey.splice(goods.picturesKey.indexOf(picturesKey), 1);
        }

    };

};

const ClassModel = function () {
    let classList;

    //初始化(建構式)START
    //載入所有分類清單
    getALLClassData();
    //初始化 END

    /** 抓資料 */
    function getALLClassData() {
        let getClassListRequest = new XMLHttpRequest();
        getClassListRequest.onreadystatechange = function () {
            if (getClassListRequest.readyState == XMLHttpRequest.DONE) {
                if (getClassListRequest.status == 200) {
                    let responseObj = JSON.parse(getClassListRequest.responseText);
                    if (typeof (responseObj.classificationList) !== "undefined") {
                        classList = responseObj.classificationList;
                    }
                } else
                    console.log("There was a problem with the request(getClassListRequest).");
            }
        };
        getClassListRequest.open('Get', "/GoodsManager/api/GetClassificationList");
        getClassListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        getClassListRequest.send();
    }

    /** 找尋指定分類名稱的下一階分類清單
     * @param {String} className 分類名稱
     * @param {Array} list 分類清單
     */
    function searchClassList(className, list) {
        let result = [];
        for (let i = 0; i < list.length; i++) {
            if (className == list[i].classificationName) {
                if ((typeof list[i].subClassificationList) !== "undefined")
                    list[i].subClassificationList.forEach(ele => {
                        result.push(ele.classificationName);
                    });
                break;
            }
            if ((typeof list[i].subClassificationList) !== "undefined") {
                result = searchClassList(className, list[i].subClassificationList);
                if (result.length > 0)
                    break;
            }
        }

        return result;
    }

    /**查詢分類鏈(從主分類到該分類名稱經過的途徑)
     * @param {String} className Search Condition with Classifiction Name
     * @param {Object} classTreeNode sub Classifiction Node
     */
    function classSearchChain(className, classTreeNode) {//tree search
        if (className == classTreeNode.classificationName)
            return [classTreeNode.classificationName];
        if ((typeof classTreeNode.subClassificationList) === "undefined") {
            return null;
        }

        for (let i = 0; i < classTreeNode.subClassificationList.length; i++) {
            let result = classSearchChain(className, classTreeNode.subClassificationList[i]);
            if (result !== null) {
                return [classTreeNode.classificationName].concat(result);
            }
        }
        return null;
    }

    return {
        isInited: function () {
            return (typeof classList) !== "undefined";
        },
        getClassList: function (className) {

            if ((typeof className) === "undefined") {
                let result = [];
                classList.forEach(ele => {
                    result.push(ele.classificationName);
                });
                return result;
            } else {
                return searchClassList(className, classList);
            }
        },
        getClassChain: function (className) {
            if (className == "")
                return [];
            for (let i = 0; i < classList.length; i++) {
                let result = classSearchChain(className, classList[i]);
                if (result !== null)
                    return result;
            }
            return [];
        },
    };
};

const TagModel = function () {
    /**
     * @type {Set<string>}
     */
    let tagList;

    //初始化(建構式)START
    //載入所有分類清單
    getALLTagData();
    //初始化 END

    /** 抓資料
     * 
     */
    function getALLTagData() {
        let getTagListRequest = new XMLHttpRequest();
        getTagListRequest.onreadystatechange = function () {
            if (getTagListRequest.readyState == XMLHttpRequest.DONE) {
                if (getTagListRequest.status == 200) {
                    let responseObj = JSON.parse(getTagListRequest.responseText);
                    if (typeof (responseObj.tagList) !== "undefined") {
                        tagList = new Set(responseObj.tagList);
                    }
                } else
                    console.log("There was a problem with the request(getTagListRequest).");
            }
        };
        getTagListRequest.open('Get', "/GoodsManager/api/getTagList");
        getTagListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        getTagListRequest.send();
    }

    return {
        /**
         * @field 回傳是否成功初始化
         */
        isInited: function () {
            return (typeof tagList) !== "undefined";
        },
        /**
         * @field 回傳TagList的Iterator
         * @returns {Iterable<string>} TagList的Iterator
         */
        getTagIterator: function () {
            return tagList.values();
        }

    }
};

