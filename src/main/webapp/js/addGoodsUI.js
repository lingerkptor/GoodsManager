window.onload = function () {
    const gName = document.getElementById("gName");
    const L = document.getElementById("L");
    const W = document.getElementById("W");
    const H = document.getElementById("H");
    const price = document.getElementById("price");
    const classId = document.getElementById("classId");
    const getClassIdList = document.getElementById("getClassIdList");
    const tags = document.getElementById("tags");
    const addTag = document.getElementById("addTag");
    const removeTag = document.getElementById("removeTag");
    const pictureFiles = document.getElementById("pictureFiles");
    const addGoods = document.getElementById("addGoods");

    getClassIdList.addEventListener("click", function (e) {
        let prototype = classId.getElementsByClassName("prototype")[0].cloneNode(true);
        addGoodsPage.getClassIdList({
            update: function (list) {
                classId.innerHTML = '';
                classId.appendChild(prototype);
                list.array.forEach(classObj => {
                    newNode = prototype.cloneNode(true);
                    newNode.classList.remove('prototype');
                    newNode.value = classObj.id;
                    newNode.innerText = classObj.name;
                    classId.appendChild(newNode);
                });
            }
        });
    });

    let tagList = [];
    addTag.addEventListener('click', function (e) {
        let tagName = prompt("標籤名稱");
        tagList.push(tagName);
        console.log(tagList);
        let context = '';
        tagList.forEach(tag =>
            context += tag + ' '
        );
        tags.value = context;
    });
    removeTag.addEventListener('click', function (e) {
        let tagName = prompt("標籤名稱");
        let context = '';
        for (i = 0; i < tagList.length; i++) {
            if (tagList[i] == tagName) {
                tagList.splice(i, 1);
                continue;
            }
            context += tagList[i] + ' ';
        }
        tags.value = context;
    });

    addGoods.addEventListener('click', function (e) {
        // window.location.replace('/searchGoods.html');
        let formObj = new FormData();
        formObj.append('gName', gName.value);
        formObj.append('L', L.value);
        formObj.append('W', W.value);
        formObj.append('H', H.value);
        formObj.append('price', price.value);
        formObj.append('classId', classId.options[classId.selectedIndex].value);
        formObj.append('tags', tags.value);
        for (i = 0; i < pictureFiles.files.length; i++) {
            formObj.append('pic' + i, pictureFiles.files[i])
        };
        addGoodsPage.sendNewGoods(formObj, {
            update: function (responseObj) {
                if (responseObj.addSucess) {
                    alert('上傳成功');
                    window.location.replace('/GoodsManager/searchGoods.html');
                } else {
                    alert('上傳失敗,請看訊息');
                }
            }
        })
    });


};


window.onunload = function () {

};