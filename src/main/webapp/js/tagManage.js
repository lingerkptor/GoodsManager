const tagManageModel = {
    addTag: function (tag, element) {
        /**
         * tag
         * {
         *  tagName: string
         *  tagDescription:string
         * }
         */
        let addTagRequest = new XMLHttpRequest();
        addTagRequest.onreadystatechange = function () {
            if (addTagRequest.readyState === XMLHttpRequest.DONE) {
                if (addTagRequest.status === 200) {
                    element.update(JSON.parse(addTagRequest.responseText));
                }
            } else
                console.log('There was problem with request about IncreateTag.');
        };
        addTagRequest.open('Post', "/GoodsManager/api/IncreateTag");
        addTagRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        addTagRequest.send(JSON.stringify(tag));
    },
    removeTag: function (tag, element) {
        /**
         * tag
         * {
         *  tagName: string
         * }
         */
        let removeTagRequest = new XMLHttpRequest();
        removeTagRequest.onreadystatechange = function () {
            if (removeTagRequest.readyState === XMLHttpRequest.DONE) {
                if (removeTagRequest.status === 200) {
                    element.update(JSON.parse(removeTagRequest.responseText));
                }
                else
                    console.log('There was problem with request about removeTag.');
            }
        };
        removeTagRequest.open('Post', "/GoodsManager/api/removeTag");
        removeTagRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        removeTagRequest.send(JSON.stringify(tag));
    },
    updateTag: function (tag, element) {
        /**
         * tag
         * {
         *  tagName: string
         *  newTagName : string
         *  tagDescription:string
         * }
         */
        let updateTagRequest = new XMLHttpRequest();
        updateTagRequest.onreadystatechange = function () {
            if (updateTagRequest.readyState === XMLHttpRequest.DONE) {
                if (updateTagRequest.status === 200) {
                    element.update(JSON.parse(updateTagRequest.responseText));
                }
                else
                    console.log('There was problem with request about updateTag.');
            }
        };
        updateTagRequest.open('Post', "/GoodsManager/api/updateTag");
        updateTagRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        updateTagRequest.send(JSON.stringify(tag));

    },
    getTagList: function (element) {
        let getTagsRequest = new XMLHttpRequest();
        getTagsRequest.onreadystatechange = function () {
            if (getTagsRequest.readyState === XMLHttpRequest.DONE) {
                if (getTagsRequest.status === 200) {
                    tags = JSON.parse(getTagsRequest.responseText);
                    element.update(tags);
                }
            } else
                console.log('There was a problem with request about getTagList.')

        };
        getTagsRequest.open('Get', "/GoodsManager/api/getTagList");
        getTagsRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        getTagsRequest.send();
    }
};
