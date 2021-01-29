
const GoodsListModel = function () {
    let GoodsList;
    let sumPage;
    let page = 1;
    // let token = [];
    let filterMap = new Map();
    let observer;
    let goodsCount = 10;

    //getGoodsList();


    function getGoodsList() {
        let filterObj;
        if (typeof sumPage != 'undefined') {
            filterObj = getFilterObj(page);
        } else filterObj = getFilterObj();

        let getGoodsListRequest = new XMLHttpRequest();
        getGoodsListRequest.onreadystatechange = function () {
            if (getGoodsListRequest.readyState === XMLHttpRequest.DONE) {
                if (getGoodsListRequest.status === 200) {
                    let responseObj = JSON.parse(getGoodsListRequest.responseText);
                    if (responseObj.Code == "SUCCESS") {
                        page = responseObj.page;
                        sumPage = responseObj.pages;
                        GoodsList = responseObj.goodsList;
                        if (typeof observer != 'undefined')
                            observer.update();

                        // if (!token.includes(responseObj.token))
                        //     token.push(responseObj.token);
                        // if (token.length < page) {
                        //     // console.log("token.length:" + token.length);
                        //     // console.log("page:" + page);
                        //     getGoodsList(page);
                        // } else {
                        //     page = token.indexOf(responseObj.token) + 1;
                        //     sumPage = responseObj.pages;
                        //     GoodsList = responseObj.goodsList;
                        //     if (typeof observer != 'undefined')
                        //         observer.update();
                        // }
                    } else {
                        console.log("Code is" + responseObj.Code);
                    }
                } else
                    console.log('There was problom with request about \'getGoodsList\'');
            }
        };
        getGoodsListRequest.open('Post', '/GoodsManager/api/getGoodsList');

        getGoodsListRequest.send(JSON.stringify(filterObj));
    }


    function getFilterObj(page) {
        let filterObj = {};
        console.log(filterMap);
        filterMap.forEach((v, k) => {
            switch (k) {
                case 'class':
                    filterObj.className = v;
                    break;
                case 'tags':
                    filterObj.tags = [];
                    console.log(v);
                    v.forEach((value) => filterObj.tags.push(value));
                    break;
                case 'date':
                    filterObj.date = v;
                    break;
                case 'keyword':
                    filterObj.keyword = v;
                    break;
                case 'order':
                    filterObj.order = v;
                    break;
                case 'sortIn':
                    filterObj.sortIn = v;
                    break;
            }
        });
        filterObj.count = goodsCount;

        if (typeof page != 'undefined' && page > 1) {
            filterObj.page = page;
            // let index = page - 2;
            // if (token.length > index)
            //     filterObj.token = token[index];
            // else {
            //     filterObj.token = token[token.length - 1];
            // }
        }
        //console.log(filterObj);
        return filterObj;
    }
    function deleteGoods(id) {
        let deleteGoodsRequest = new XMLHttpRequest();
        deleteGoodsRequest.onreadystatechange = function () {
            if (deleteGoodsRequest.readyState === XMLHttpRequest.DONE) {
                if (deleteGoodsRequest.status === 200) {
                    let responseObj = JSON.parse(deleteGoodsRequest.responseText);
                    if (responseObj.code == "SUCCESS") {
                        getGoodsList();
                    } else {
                        console.log("deleteGoodsRequest:code => " + responseObj.code);
                    }
                }
            }
        };
        deleteGoodsRequest.open('Post', '/GoodsManager/api/DeleteGoods');
        deleteGoodsRequest.send(JSON.stringify({ GID: id }));
    }


    return {
        registerObserver: function (ob) {
            observer = ob;
            // token.length = 0;
            getGoodsList();
        },
        getGoodsListData: function () {
            return GoodsList;
        },
        SortingGoodsByClass: function (isASC) {
            filterMap.set("order", "class");
            if (isASC)
                filterMap.set("sortIn", 1);
            //  GoodsList.sort((x, y) => x.date - y.date);
            else
                filterMap.set("sortIn", 0);
            //  return GoodsList.sort((x, y) => x.className - y.className);
            getGoodsList();
        },
        SortingGoodsbyId: function (isASC) {
            filterMap.set("order", "id");
            if (isASC)
                filterMap.set("sortIn", 1);
            //  GoodsList.sort((x, y) => x.date - y.date);
            else
                filterMap.set("sortIn", 0);
            getGoodsList();
        },
        /**
         * 
         * @param {boolean}  
         */
        SortingGoodsbyDate: function (isASC) {
            filterMap.set("order", "date");
            if (isASC)
                filterMap.set("sortIn", 1);
            //  GoodsList.sort((x, y) => x.date - y.date);
            else
                filterMap.set("sortIn", 0);
            getGoodsList();
        },

        setClassFilter: function (value) {
            if (typeof value != 'undefined')
                filterMap.set('class', value);
            else
                filterMap.delete('class');
        },
        setTagsFilter: function (value) {
            filterMap.set('tags', new Set(value));
        },
        setDateFilter: function (value) {
            if (typeof value != 'undefined')
                filterMap.set('date', value);
            else
                filterMap.delete('date');
        },
        setKeywordFilter: function (value) {
            if (typeof value != 'undefined')
                filterMap.set('keyword', value);
            else
                filterMap.delete('keyword');
        },
        searchGoodsList: function () {
            // token.length = 0;
            page = 0;
            sumPage = 0;
            getGoodsList();
        },
        changePage: function (pageNum) {
            if (pageNum <= 0)
                pageNum = 1;

            if (sumPage < pageNum)
                pageNum = sumPage;
            console.log(pageNum);
            if (page == pageNum) {
                observer.update();
                return;
            }
            page = pageNum;
            getGoodsList();
        },
        getSumPage: function () {
            return sumPage;
        },
        getPage: function () {
            return page;
        },
        setGoodCount: function (value) {
            if (typeof value === 'undefined')
                return (typeof goodsCount !== "undefined") ? goodsCount : "";
            if ((!Number.isNaN(Number.parseInt(value))) || typeof goodsCount !== "undefined")
                goodsCount = Number.parseInt(value);
            else
                throw new NaNException("The value is not Number.");
        },
        deleteGoods: function (id) {
            deleteGoods(id);
        }

    };
};
const ClassModel = function () {
    let classList;
    let observer;

    //初始化(建構式)START
    //載入所有分類清單
    // getALLClassData();
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
                        observer.update();
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
        registerObserver: function (ob) {
            observer = ob;
            getALLClassData();
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
    let observer;

    //初始化(建構式)START
    //載入所有分類清單
    //getALLTagData();
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
                        observer.update();
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
        registerObserver: function (ob) {
            observer = ob;
            getALLTagData();
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
