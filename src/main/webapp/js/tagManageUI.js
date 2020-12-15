/**  
 * TagUI邏輯，包含UI的變化行為．
 * */
const tagUIView = function () {
    let tagTable = document.getElementById("tagTable");
    let prototype = document.getElementsByClassName("prototype")[0].cloneNode(true);
    let tagName = document.getElementById("tagName");
    let addtag = document.getElementById("addtag");
    let data = [];
    let functionMap = new Map();

    let result = function (resultObj) {
        let resultElement = document.getElementById("result");
        switch (resultObj) {
            case "sucess":
                resultElement.innerText;
                functionMap.get("")
                break;
            case "failure":
                break;
            default:
                break;
        }
    }


    addtag.addEventListener('click', function (e) {
        let selectedTag = data.find((tag) => {
            tag.tagName = tagName.value;
        });
        if (typeof (selectedTag) === "undefined") {
            (functionMap.get("addTag"))(
                {}, {
                update: function (responseObj) {
                    result(responseObj.result);
                }
            }
            );
        }
    });

    //updateTagsUI
    function updateTagsUI() {
        data.forEach(element => {
            let newTag = prototype.cloneNode(true);
            tagTable.appendChild(newTag);
            // modify button
            newTag.children[0].children[0].addEventListener('click', function (e) {
                tagNameElement = e.currentTarget.parentElement.children[1];
                if (tagNameElement.hasAttribute("readonly")) {
                    e.currentTarget.value = "確認";
                    tagNameElement.removeAttribute("readonly");
                } else {
                    let selectedTag = data.find((tag) => {
                        tag.newTagName = tagNameElement.tagName;
                    });
                    (functionMap.get("modifyTag"))(
                        { tagName: selectedTag.tagName, newTagName: selectedTag.newTagName }//sendObj
                        , {//updateObj
                            update: function (responseObj) {
                                if (!(typeof (responseObj.result) === "undefined")) {
                                    result(responseObj.result);
                                }
                            }
                        });
                    e.currentTarget.value = "修改";
                    tagNameElement.setAttribute("readonly", "readonly");
                }
            });
            // delete button
            newTag.children[0].children[1].addEventListener('click', function (e) {
                if (confirm("確定要刪除?")) {
                    let tagNameElement = e.currentTarget.parentElement.children[1];
                    let selectedTag = data.find((tag) => {
                        tag.tagName = tagNameElement.tagName;
                    });
                    (functionMap.get("deleteTag"))(
                        { tagName: selectedTag.tagName },//sendObj
                        {//updateObj
                            update: function (responseObj) {
                                if (!(typeof (responseObj.result) === "undefined")) {
                                    result(responseObj.result);
                                }
                            }
                        }
                    );
                    data.splice(data.indexOf(selectedTag), 1);
                }
            });
            // tageName input (view)
            newTag.children[1].value = element.tagName;
            newTag.children[1].tagName = element.tagName;
            newTag.children[1].addEventListener('input', function (e) {
                data.find((tag) => {
                    tag.tagName = e.currentTarget.tagName;
                }).newTagName = newTag.children[1].value;
                (functionMap.get("addTag"))(
                    { tagName: selectedTag.tagName },//sendObj
                    {//updateObj
                        update: function (responseObj) {
                            if (!(typeof (responseObj.result) === "undefined")) {
                                result(responseObj.result);
                            }
                        }
                    }
                );
            });
            newTag.children[2].innerText = element.count;
        });
    }
    
    function updateTagSource() {
        let dataSource = functionMap.get("updatetagList");
        if (typeof (dataSource) === "undefined") {
            console.log("沒有設定資料來源");
        } else {
            dataSource(
                {//udpateObj
                    update: function (responseObj) {
                        data = responseObj.tagList;
                        updateTagsUI();
                    }
                });
        }
    }

    return {
        setAddTagFunc: function (addTagFunction) {
            functionMap.set("addTag", addTagFunction);
        },
        setModifyTagFunc: function (addTagFunction) {
            functionMap.set("modifyTag", addTagFunction);
        },
        setDeleteTagFunc: function (addTagFunction) {
            functionMap.set("deleteTag", addTagFunction);
        },
        setUpdateDataSource: function (dataSource) {
            functionMap.set("updatetagList", dataSource);
        },
        updateUI: function () {
            updateTagSource();
        }

    }
};