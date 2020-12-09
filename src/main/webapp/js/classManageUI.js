


window.onload = function () {
    const className = document.getElementById("className");
    const createClass = document.getElementById("createClassBtn");
    const report = document.getElementById("report");
    createClass.addEventListener("click", function (e) {
        classManagePage.addClass(
            { classificationName: className.value },
            {
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
                        default: break;
                    }
                }
            });
    }
    );
    const deleteClasses = document.getElementsByClassName("deleteClassBtn");
    const classTree = document.getElementById("classTree");

    const updateClassList = function () {
        let classificationList = [];
        let prototype = classTree.getElementsByClassName("prototype")[0].cloneNode(true);
        function setClassifiction() {
            classTree.innerHTML = "";//清空內容
            classificationList.forEach((classification) => {//逐一填入
                newNode = prototype.cloneNode(true);
                newNode.children[1].innerText = classification.classificationName;
                if (!typeof (classification.subClassificationList) === "undefined")
                    newNode.children[2].innerText = classification.subClassificationList.length;
                else
                    newNode.children[2].innerText = 0;
                newNode.classList.remove("prototype");
                classTree.appendChild(newNode);
            });
        };
        return {
            update: function (list) {
                classificationList.length = 0;
                list.forEach(classification => { classificationList.push(classification) });
                console.log(classificationList);
                setClassifiction();
            }
        };
    }();
    classManagePage.getClassList(updateClassList);



    // deleteClasses.forEach(delbtn => {
    //     delbtn.addEventListener("click", function (e) {
    //         parent = delbtn.parentElement;

    //     });
    // });
};
window.unonload = function () { };