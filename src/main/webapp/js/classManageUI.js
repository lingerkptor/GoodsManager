window.onload = function () {
    const className = document.getElementById("className");
    const createClass = document.getElementById("createClassBtn");
    createClass.addEventListener("click", function (e) {
        classManagePage.addClass({ classificationName: className.value }, addClassUpdateobj);
    });
    const classTree = document.getElementById("classTree");

    const report = document.getElementById("report");
    
    const updateClassList = function () {
        let prototype = classTree.getElementsByClassName("prototype")[0].cloneNode(true);
        function setClassifiction(parent, classificationList, parentName) {
            classificationList.forEach((classification) => {//逐一填入
                newNode = prototype.cloneNode(true);
                newNode.classList.remove("prototype");
                parent.appendChild(newNode);
                let input = newNode.children[2].getElementsByTagName('input');
                let addClassInput = input[0];
                //addClassBtn
                input[1].addEventListener('click', function (e) {
                    classManagePage.addClass({
                        classificationName: addClassInput.value,
                        parentClassificationName: classification.classificationName
                    }, addClassUpdateobj);
                });
                //updateClassBtn
                input[2].addEventListener('click', function (e) {
                    classManagePage.updateClass(
                        {
                            classificationName: classification.classificationName,
                            classificationNewName: addClassInput.value
                        },
                        updateClassupdateObj);
                });
                //deleteClassBtn
                input[3].addEventListener('click', function (e) {
                    classManagePage.deleteClass(
                        { classificationName: classification.classificationName },
                        deleteClassupdateObj);
                });
                // set Classification Name
                newNode.children[0].getElementsByTagName("div")[0].innerText = classification.classificationName;

                // count sub Classification
                if (!(typeof (classification.subClassificationList) === "undefined")) {
                    newNode.children[0].classList.add("caret");
                    newNode.children[0].addEventListener('click', function (e) {
                        e.currentTarget.parentElement.querySelector(".nested").classList.toggle("active");
                        e.currentTarget.classList.toggle("caret-down");
                    });
                    newNode.children[1].getElementsByTagName("div")[0].innerText = classification.subClassificationList.length;
                    setClassifiction(newNode.getElementsByTagName("ul")[0], classification.subClassificationList, classification.classificationName);
                } else
                    newNode.children[1].getElementsByTagName("div")[0].innerText = 0;


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
                    classManagePage.getClassList(updateClassList());
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
                case "SQLFILEERROR":
                    console.log("SQLFile error");
                    break;
                case "SQLEXCEPTION":
                    console.log("SQLException error");
                    break;
                case "CLASSIFICATIONNONEXIST":
                    console.log("分類不存在");
                    report.innerText = "分類不存在";
                    break;
                case "DELETESUCESS":
                    console.log("刪除成功");
                    report.innerText = "刪除成功";
                    classManagePage.getClassList(updateClassList());
                    break;
                case "DELETEFAILURE":
                    console.log("刪除失敗");
                    report.innerText = "刪除失敗";
                default: break;
            }
        }
    };
    const updateClassupdateObj = {
        update: function (code) {
            switch (code) {
                case "SQLFILEERROR":
                    console.log("SQLFile error");
                    break;
                case "SQLEXCEPTION":
                    console.log("SQLException error");
                    break;
                case "NONUPDATECLASSIFICATION":
                    console.log("沒有要更新");
                    report.innerText = "沒有要更新";
                    break;
                case "OTHEREXCEPTION":
                    console.log("其他例外");
                    report.innerText = "其他例外";
                    break;
                case "UPDATENAMEFAILURE":
                    console.log("更新名稱失敗");
                    report.innerText = "更新名稱失敗";
                    break;
                case "UPDATEPARENTFAILURE":
                    console.log("更新上層分類失敗");
                    report.innerText = "更新上層分類失敗";
                    break;
                case "NEWNAMEISEXIST":
                    console.log("新名稱已存在");
                    report.innerText = "新名稱已存在";
                    break;
                case "UPDATESUCESS":
                    console.log("更新成功");
                    report.innerText = "更新成功";
                    classManagePage.getClassList(updateClassList());
                default: break;
            }

        }

    };


  


    classManagePage.getClassList(updateClassList());


};


window.unonload = function () { };