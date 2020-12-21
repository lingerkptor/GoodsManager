const searchGoodsPage = function () {
    let GoodsList = [];
    let filterList = [];
    return {
        getclassList: function (element) {
            let getclassListRequest = new XMLHttpRequest();
            getclassListRequest.onreadystatechange = function () {
                if (getclassListRequest.readyState === XMLHttpRequest.DONE) {
                    if (getclassListRequest.status === 200) {
                        element.update(JSON.parse(getclassListRequest.responseText));
                    } else {
                        console.log('There was a problem with request about \'getClassList\'');
                    }
                }
            };
            getclassListRequest.open('Get', '/GoodsManager/api/GetClassificationList');
            getclassListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
            getclassListRequest.send();
        },
        getTagList: function () {
            let getTagListRequest = new XMLHttpRequest();
            getTagListRequest.onreadystatechange = function () {
                if (getTagListRequest.onreadystatechange === XMLHttpRequest.DONE) {
                    if (getTagListRequest.status === 200) {
                        element.update(JSON.parse(getTagListRequest.responseText));
                    } else
                        console.log('There was a porblem with request about \'getTagList\'');
                }
            };
            getTagListRequest.open('Get', '/GoodsManager/api/getTagList');
            getTagListRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
            getTagListRequest.send();
        },


        SortingGoodsByClass: function (element) {
            element.update(common.sorting(GoodsList, (x, y) => x.classId - y.classId));
        },
        SortingGoodsbyId: function (element) {
            element.update(common.sorting(GoodsList, (x, y) => x.goodsId - y.goodsId));
        },
        SortingGoodsbyDate: function (element) {
            element.update(common.sorting(GoodsList, (x, y) => Date.parse(x.date) - Date.parse(y.date)));
        },
        addfilter: function (key, value) {
            for (i = 0; i < filterList.length; i++) {
                if (filterList[i].key = key) {
                    filterList.splice(i, 1);
                    break;
                }
            }
            filterList.push({ key: key, value: value });
        },
        removefilter: function (key) {
            for (i = 0; i < filterList.length; i++) {
                if (filterList[i].key = key)
                    filterList.splice(i, 1);
            }
        },
        getGoodsList: function (element) {
            let updateGoodsListRequest = new XMLHttpRequest();
            updateGoodsListRequest.onreadystatechange = function () {
                if (updateGoodsListRequest.readyState === XMLHttpRequest.DONE) {
                    if (updateGoodsListRequest.status === 200) {
                        GoodsList = JSON.parse(updateGoodsListRequest.responseText);
                        element.update(GoodsList);
                    } else
                        console.log('There was problom with request about \'getGoodsList\'');
                }
            };
            if (filterList.length > 0) {
                let paraStr = '?';
                for (i = 0; i < filterList.length - 1; i++) {
                    paraStr += filterList[i].key + "=" + filterList[i].value + '&';
                }
                updateGoodsListRequest.open('Get', '/GoodsManager/api/getGoodsList' + paraStr + filterList[filterList.length - 1]);
            } else
                updateGoodsListRequest.open('Get', '/GoodsManager/api/getGoodsList');

            updateGoodsListRequest.send();

        }

    };
}();