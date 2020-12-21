/**  
 * TagUI邏輯，包含UI的變化行為．
 * */
const tagUIView = function () {
    let tagTable = document.getElementById("tagTable");
    let prototype = document.getElementsByClassName("prototype")[0].cloneNode(true);
    let tagName = document.getElementById("tagName");
    let tagDescription = document.getElementById("tagDescription");
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
                {
                    tagName: tagName.value,
                    tagDescription: tagDescription.value
                }, {
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

            // modify button
            newTag.children[0].children[0].addEventListener('click', function (e) {
                let tagNameElement = e.currentTarget.parentElement.children[1];
                let tagDescriptionElement = e.currentTarget.parentElement.children[2];
                if (e.currentTarget.locked == "true") {
                    e.currentTarget.value = "修改確認";
                    e.currentTarget.locked = "false";
                    tagNameElement.removeAttribute("readonly");
                    tagDescriptionElement.removeAttribute("readonly");
                } else {
                    let selectedTag = data.find((tag) => {
                        tag.newTagName = tagNameElement.tagName;
                    });
                    (functionMap.get("modifyTag"))(
                        {//sendObj
                            tagName: selectedTag.tagName,
                            newTagName: selectedTag.newTagName,
                            tagDescription: selectedTag.tagDescription
                        }
                        , {//updateObj
                            update: function (responseObj) {
                                if (!(typeof (responseObj.result) === "undefined")) {
                                    result(responseObj.result);
                                }
                            }
                        });
                    e.currentTarget.value = "修改";
                    e.currentTarget.locked = "true";
                    tagNameElement.setAttribute("readonly", "readonly");
                    tagDescriptionElement.setAttribute("readonly", "readonly");
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
            function updateTag() {
                let selectedtag = data.find((tag) => {
                    tag.tagName = newTag.children[1].tagName;
                });
                selectedtag.newTagName = newTag.children[1].value;
                selectedtag.tagDescription = newTag.children[2].value;
                (functionMap.get("updateTag"))(
                    selectedtag,//sendObj
                    {//updateObj
                        update: function (responseObj) {
                            if (!(typeof (responseObj.result) === "undefined")) {
                                result(responseObj.result);
                            }
                        }
                    }
                );
            };
            // tageName input (view)
            console.log("tagName=>" + element.tagName);
            newTag.children[2].value = element.tagName;
            newTag.children[2].tagName = element.tagName;
            newTag.children[2].addEventListener('input', updateTag);
            newTag.children[4].value = element.tagName;
            newTag.children[4].tagName = element.tagName;
            newTag.children[4].addEventListener('input',
                updateTag
                // function (e) {
                //     data.find((tag) => {
                //         tag.tagName = e.currentTarget.tagName;
                //     }).tagDescription = newTag.children[2].value;
                //     (functionMap.get("updateTag"))(
                //         { tagName: selectedTag.tagName },//sendObj
                //         {//updateObj
                //             update: function (responseObj) {
                //                 if (!(typeof (responseObj.result) === "undefined")) {
                //                     result(responseObj.result);
                //                 }
                //             }
                //         }
                //     );
                // }
            );

            newTag.children[6].innerText = element.count;
            newTag.classList.remove("prototype");
            tagTable.appendChild(newTag);
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