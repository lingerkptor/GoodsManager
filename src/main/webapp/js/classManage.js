const classManagePage = function () {
    let classes = [];
    return {
        getClassList: function (element) {
            let getClassListRequest = new XMLHttpRequest();
            getClassListRequest.onreadystatechange = function () {

            };
            getClassListRequest.open('Get', "/GoodsManager/api/getClassList");
            getClassListRequest.send();

        },
        addClass: function (element) {
            let addClassRequest = new XMLHttpRequest();
            this.getClassList.onreadystatechange = function () {

            };
            addClassRequest.open('Post', "/GoodsManager/api/addClass");
            addClassRequest.send();
        }
    };
};