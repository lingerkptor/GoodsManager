window.onload = function () {
    const className = document.getElementById("className");
    const createClass = document.getElementById("createClassBtn");
    const report = document.getElementById("report");
    createClass.addEventListener("click", function (e) {
        classManagePage().addClass(
            {
                className: className.value
            },
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
    deleteClasses.forEach(delbtn => {
        delbtn.addEventListener("click", function (e) {
            parent = delbtn.parentElement;

        });
    });
};
window.unonload = function () { };