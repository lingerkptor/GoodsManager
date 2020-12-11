


window.onload = function () {
    const className = document.getElementById("className");
    const createClass = document.getElementById("createClassBtn");
    const report = document.getElementById("report");
    const addClassUpdateobj = {
        update: function (code) {
            switch (code) {
                case "SQLFile":
                    console.log("SQLFile error");
                    break;
                case "SQLException":
                    console.log("SQLException error");
                    break;
                case "PARENTCLASSNONEXIST":
                    console.log("上層分類不存在");
                    report.innerText = "上層分類不存在";
                    break;
                case "CLASSISEXIST":
                    console.log("分類已存在");
                    report.innerText = "分類已存在";
                    break;
                case "CREATECLASSSUCESS":
                    console.log("新增成功");
                    report.innerText = "新增成功";
                    break;
                case "LOSTCLASS":
                    console.log("新增失敗");
                    report.innerText = "新增失敗";
                    break;
                case "DATAERROR":
                    console.log("資料內容錯誤");
                    report.innerText = "資料內容錯誤";
                default: break;
            }
        }
    };
    const deleteClassupdateObj = {
        update: function (code) {
            switch (code) {
                case "SQLFile":
                    console.log("SQLFile error");
                    break;
                case "SQLException":
                    console.log("SQLException error");
                    break;
                case "CLASSIFICATIONNONEXIST":
                    console.log("分類不存在");
                    report.innerText = "分類不存在";
                    break;
                case "DELETESUCESS":
                    console.log("刪除成功");
                    report.innerText = "刪除成功";
                    break;
                case "DELETEFAILURE":
                    console.log("刪除失敗");
                    report.innerText = "刪除失敗";
                default: break;
            }
        }
    };


    createClass.addEventListener("click", function (e) {
        classManagePage.addClass({ classificationName: classificationName }, addClassUpdateobj);
        classManagePage.getClassList(updateClassList());
    });
    const classTree = document.getElementById("classTree");

    const updateClassList = function () {
        let prototype = classTree.getElementsByClassName("prototype")[0].cloneNode(true);
        function setClassifiction(parent, classificationList) {
            classificationList.forEach((classification) => {//逐一填入
                newNode = prototype.cloneNode(true);
                newNode.classList.remove("prototype");
                parent.appendChild(newNode);
                let addClassInput = newNode.children[0].children[0];
                //addClassBtn
                newNode.children[0].children[1].addEventListener('click', function (e) {
                    classManagePage.addClass({
                        classificationName: addClassInput.value,
                        parentClassificationName: classification.classificationName
                    }, addClassUpdateobj);
                    classManagePage.getClassList(updateClassList());
                });
                //deleteClassBtn
                newNode.children[0].children[2].addEventListener('click', function (e) {
                    classManagePage.deleteClass(
                        { classificationName: classification.classificationName },
                        deleteClassupdateObj);
                    classManagePage.getClassList(updateClassList());
                });
                // set Classification Name
                newNode.children[1].innerText = classification.classificationName;
                // count sub Classification
                if (!(typeof (classification.subClassificationList) === "undefined")) {
                    newNode.children[2].innerText = classification.subClassificationList.length;
                    setClassifiction(parent, classification.subClassificationList);
                } else
                    newNode.children[2].innerText = 0;

            });
        };
        return {
            update: function (list) {
                let prototype = classTree.getElementsByClassName("prototype")[0].cloneNode(true);
                classTree.innerText = "";
                classTree.appendChild(prototype);
                setClassifiction(classTree, list);
            }
        };
    };
    classManagePage.getClassList(updateClassList());


};
window.unonload = function () { };