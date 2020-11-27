const tagManagePage = function () {
    let tags = [];
    return {
        addTag: function (tag, element) {
            let addTagRequest = new XMLHttpRequest();
            addTagRequest.onreadystatechange = function () {
                if (addTagRequest.readyState === XMLHttpRequest.DONE) {
                    element.update(JSON.parse(addTagRequest.responseText));
                } else
                    console.log('There was problem with request about addTag.');
            };
            addTagRequest.open('Post', "/GoodsManager/api/addTag");
            addTagRequest.send(JSON.stringify(tag));
        },
        removeTag: function (tag) {
            let removeTagRequest = new XMLHttpRequest();
            removeTagRequest.onreadystatechange = function () {
                if (removeTagRequest.readyState === XMLHttpRequest.DONE) {
                    element.update(JSON.parse(removeTagRequest.responseText));
                } else
                    console.log('There was problem with request about addTag.');
            };
            removeTagRequest.open('Post', "/GoodsManager/api/addTag");
            removeTagRequest.send(JSON.stringify(tag));
        },
        getTagList: function (element) {
            let updateTagsRequest = new XMLHttpRequest();
            updateTagsRequest.onreadystatechange = function () {
                if (updateTagsRequest.readyState === XMLHttpRequest.DONE) {
                    tags = JSON.parse(updateTagsRequest.responseText);
                    element.update(tags);
                } else
                    console.log('There was a problem with request about getTagList.')

            };
            updateTagsRequest.open('Get', "/GoodsManager/api/getTagList");
            updateTagsRequest.send();
        }

    };
};