const tagManageModel = {
    addTag: function (tag, element) {
        /**
         * tag
         * {
         *  tagName: string
         * }
         */
        let addTagRequest = new XMLHttpRequest();
        addTagRequest.onreadystatechange = function () {
            if (addTagRequest.readyState === XMLHttpRequest.DONE) {
                if (renameTagRequest.status === 200) {
                    element.update(JSON.parse(addTagRequest.responseText));
                }
            } else
                console.log('There was problem with request about addTag.');
        };
        addTagRequest.open('Post', "/GoodsManager/api/addTag");
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
                if (renameTagRequest.status === 200) {
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
    renameTag: function (tag, element) {
        /**
         * tag
         * {
         *  tagName: string
         *  newTagName : string
         * }
         */
        let renameTagRequest = new XMLHttpRequest();
        renameTagRequest.onreadystatechange = function () {
            if (renameTagRequest.readyState === XMLHttpRequest.DONE) {
                if (renameTagRequest.status === 200) {
                    element.update(JSON.parse(renameTagRequest.responseText));
                }
                else
                    console.log('There was problem with request about renameTag.');
            }
        };
        renameTagRequest.open('Post', "/GoodsManager/api/renameTag");
        renameTagRequest.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        renameTagRequest.send(JSON.stringify(tag));

    },
    getTagList: function (element) {
        let updateTagsRequest = new XMLHttpRequest();
        updateTagsRequest.onreadystatechange = function () {
            if (updateTagsRequest.readyState === XMLHttpRequest.DONE) {
                if (renameTagRequest.status === 200) {
                    tags = JSON.parse(updateTagsRequest.responseText);
                    element.update(tags);
                }
            } else
                console.log('There was a problem with request about getTagList.')

        };
        updateTagsRequest.open('Get', "/GoodsManager/api/getTagList");
        updateTagsRequest.setRequestHeader('Content-Type', 'text/plain;charset=UTF-8');
        updateTagsRequest.send();
    }
};
