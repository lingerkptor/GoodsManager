let searchGoodsView = function () {
    let goodsListModel;
    let classModel;
    let tagModel;

    /**分類清單(動態)
     * 
     */
    const classNameList = document.getElementById('classList').children;

    const tags = document.getElementById('tags');// 分類清單
    const taglistElement = document.querySelector("#tagList");
    const markedElement = document.querySelector("#marked");

    const addTag = document.getElementById("addTag");// 增加綁定標籤
    const removeTag = document.getElementById("removeTag");//移除綁定標籤

    const dateFilter = document.getElementById('date');
    const keyword = document.getElementById('keyword');
    const filterBtn = document.getElementById('filterBtn');
    const clearfilterBtn = document.getElementById('clearfilterBtn');
    const GoodsTable = document.getElementById('GoodsTable');

    /**  建立分類節點清單
     * @param {Array} classList
     * @returns ElementNode
     * */
    function classNameNodeFactory(classList) {
        // 建立(複製) classNameNode(Element) ProtoType 
        let prototype = classNameList[0].cloneNode(true);
        if (classList.length == 0)
            return prototype;
        classList.forEach(subClassName => {
            // 建立(複製) option的 ProtoType
            let option = prototype.children[0].children[0].cloneNode(true);
            option.value = subClassName;
            option.label = subClassName;
            prototype.children[0].appendChild(option);
        });
        prototype.children[0].addEventListener('change', function (e) {
            let node = e.currentTarget.parentElement;
            let classListNode = document.getElementById('classList');
            for (let i = 0; i < classNameList.length;) {//刪除之前的子分類
                if (classNameList[i] == node && i < (classNameList.length - 1))
                    classListNode.removeChild(classNameList[i + 1]);
                else i++;
            }
            let subNode = classNameNodeFactory(classModel.getClassList(e.currentTarget.value));
            classListNode.appendChild(subNode);//添加修改的子連結
        });

        // 移除 class 屬性的 prototype 
        prototype.classList.remove("prototype");
        prototype.classList.add("classNode");
        return prototype;
    }
    /** 建立標籤元素
     * 
     * @param {String} tagName 
     */
    function tagElementFactory(tagName) {
        let prototype = tags.querySelector(".prototype").cloneNode(true);
        prototype.innerText = tagName;
        prototype.addEventListener('click', function () {
            if (prototype.classList.contains("selected")) {
                prototype.classList.remove("selected");
            } else {
                prototype.classList.add("selected");
            }
        });
        prototype.classList.remove("prototype");
        return prototype;
    }
    /** 商品元素工廠 */
    function goodsRowFactory(goodsObj) {
        let prototype = GoodsTable.querySelector(".prototype").cloneNode(true);
        let date = new Date(goodsObj.date);
        prototype.key = goodsObj.id;
        prototype.querySelector("a[name='datetime']").innerText = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        prototype.querySelector("a[name='GoodsName']").innerText = goodsObj.gName;
        prototype.querySelector("a[name='price']").innerText = goodsObj.price;
        prototype.querySelector("a[name='className']").innerText = goodsObj.className;
        prototype.querySelector("input.modify").addEventListener('click', function () {

        });
        prototype.querySelector("input.delete").addEventListener('click', function () {

        });
        prototype.classList.remove("prototype");
        return prototype;
    }

    /** 重置分類清單 */
    function reloadClassList() {
        for (let i = 0; i < classNameList.length;) {//移除除了prototype的分類清單
            if (classNameList[i].classList.contains('prototype')) {
                i++;
                continue;
            }
            document.getElementById('classList').removeChild(classNameList[i]);
        }
        document.getElementById('classList').appendChild(
            classNameNodeFactory(classModel.getClassList()));
    }
    /** 重置標籤清單 */
    function reloadTagList() {
        taglistElement.innerHTML = "";
        markedElement.innerHTML = "";
        let taglist = tagModel.getTagIterator();
        for (let tag of taglist) {
            let element = tagElementFactory(tag.tagName);
            taglistElement.appendChild(element);
        }
    }



    addTag.addEventListener("click", function () {
        let list = taglistElement.getElementsByTagName("label");
        for (let i = 0; i < list.length;) {
            let element = list[i];
            if (element.classList.contains("selected")) {
                element.classList.remove("selected");
                markedElement.appendChild(element);
                continue;
            }
            i++;
        }
    });
    removeTag.addEventListener("click", function () {
        let list = markedElement.getElementsByTagName("label");
        for (let i = 0; i < list.length;) {
            let element = list[i];
            if (element.classList.contains("selected")) {
                element.classList.remove("selected");
                taglistElement.appendChild(element);
                continue;
            }
            i++;
        }
    });
    filterBtn.addEventListener('click', function () {
        if (typeof goodsListModel == 'undefined')
            return;
        let className;
        for (let element of classNameList) {
            console.log(element.children[0].options);
            let value = element.children[0].options[element.children[0].selectedIndex].value;
            console.log(value);
            if (typeof value != 'undefined' && (value.length > 0))
                className = value;
        }
        console.log(className);
        goodsListModel.setClassFilter(className);
        let taglist = [];
        for (let element of markedElement.children) {
            taglist.push(element.innerText);
        }
        goodsListModel.setTagsFilter(taglist);
        goodsListModel.setDateFilter(dateFilter.value);
        goodsListModel.setKeywordFilter(keyword.value);
        goodsListModel.searchGoodsList();
    });
    clearfilterBtn.addEventListener('click', function () {
        reloadClassList();
        reloadTagList();
        dateFilter.value = "";
        keyword.value = "";
    });

    return {
        bindGoodsListModel: function (model) {
            goodsListModel = model;
        },
        bindClassModel: function (model) {
            classModel = model;
        },
        bindTagsModel: function (model) {
            tagModel = model;
        },
        setClassList: function () {
            reloadClassList();
        },
        setTagsList: function () {
            reloadTagList();
        },
        setGoodsList: function (goodsList) {
            let tbody = document.querySelector("#GoodsTable > tbody");
            for (let i = 0; i < tbody.children.length;) {
                if (tbody.children[i].classList.contains('prototype')) {
                    i++;
                    continue;
                }
                tbody.removeChild(tbody.children[i]);
            }
            goodsList.forEach((goods) => {
                let goodsElement = goodsRowFactory(goods);
                tbody.appendChild(goodsElement);
            });
        }
    };
};