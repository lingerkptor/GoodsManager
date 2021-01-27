const GoodsView = function GoodsView() {
    /**
     * @type { {isInited:()=>boolean,
     * register:({update:function}),
     * } }
     */
    let GoodsModel;
    let Controler;
    /** 宣告UI元件 Start */
    const gName = document.getElementById('gName');//商品名稱
    const L = document.getElementById('L');//商品長度
    const W = document.getElementById('W');//商品寬度
    const H = document.getElementById('H');//商品厚度(高度)
    const price = document.getElementById('price');//價格
    const count = document.getElementById('count');//數量

    /**分類清單(動態)
     * 
     */
    const classNameList = document.getElementById('classList').children;

    const tags = document.getElementById('tags');// 分類清單
    const taglistElement = document.querySelector("#tagList");
    const markedElement = document.querySelector("#marked");

    const addTag = document.getElementById("addTag");// 增加綁定標籤
    const removeTag = document.getElementById("removeTag");//移除綁定標籤

    const pictureArea = document.getElementById("pictureArea");// 圖片顯示區塊
    const uploadPicBtn = document.getElementById("uploadPictureBtn");//上傳圖片
    const addGoodsBtn = document.getElementById("addGoodsBtn");//新增商品按鈕
    const modifyGoodsBtn = document.getElementById("modifyGoodsBtn");//修改商品按鈕

    /** 宣告UI元件 END  */




    /**  建立分類節點清單
     * @param {Array} classList
     * @returns ElementNode
     */
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
            let element = e.currentTarget;
            if (typeof GoodsModel !== "undefined")
                GoodsModel.className(element.value);

            if (typeof Controler !== "undefined")
                Controler.update();
        });

        // 移除 class 屬性的 prototype 
        prototype.classList.remove("prototype");
        prototype.classList.add("classNode");
        return prototype;
    }

    /** UI元件 Listener bind*/
    function bindListener() {
        if (typeof GoodsModel != "undefined") {
            gName.addEventListener("change", function (e) { try { GoodsModel.goodsName(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { gName.value = GoodsModel.goodsName(); } });
            L.addEventListener("change", function (e) { try { GoodsModel.L(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { L.value = GoodsModel.L(); } });
            W.addEventListener("change", function (e) { try { GoodsModel.W(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { W.value = GoodsModel.W(); } });
            H.addEventListener("change", function (e) { try { GoodsModel.H(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { H.value = GoodsModel.H(); } });
            price.addEventListener("change", function (e) { try { GoodsModel.price(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { price.value = GoodsModel.price(); } });
            count.addEventListener("change", function (e) { try { GoodsModel.count(e.currentTarget.value); } catch (e) { console.log(e.message); } finally { count.value = GoodsModel.count(); } });
            addTag.addEventListener("click", function () {

                // 1.
                let list = taglistElement.getElementsByTagName("label");
                for (let i = 0; i < list.length;) {
                    let element = list[i];
                    if (element.classList.contains("selected")) {
                        element.classList.remove("selected");
                        markedElement.appendChild(element);
                        GoodsModel.addTag(element.innerText);
                        continue;
                    }
                    i++;
                }
                /** 2.
                 * let selectedTag = [];
                 * for (let element of taglistElement.getElementsByTagName("label")) {
                 *     if (element.classList.contains("selected")) {
                 *         selectedTag.push(element);
                 *     }
                 * }
                 * for (let element of selectedTag) {
                 *     element.classList.remove("selected");
                 *     markedElement.appendChild(element);
                 * }
                 */
            });
            removeTag.addEventListener("click", function () {
                let list = markedElement.getElementsByTagName("label");
                for (let i = 0; i < list.length;) {
                    let element = list[i];
                    if (element.classList.contains("selected")) {
                        element.classList.remove("selected");
                        taglistElement.appendChild(element);
                        GoodsModel.removeTag(element.innerText);
                        continue;
                    }
                    i++;
                }
            });
            uploadPicBtn.addEventListener('change', function () { GoodsModel.addPicture(uploadPicBtn.files); });
            addGoodsBtn.addEventListener("click", function (e) { GoodsModel.increateGoods(); });
            modifyGoodsBtn.addEventListener("click", function (e) { GoodsModel.modifyGoods(); });
        } else {
            console.log("GoodsModel isn't binded.");
        }
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

    /**
     * 開放方法
     */
    return {
        bindGoodsModel: function (model) {
            GoodsModel = model;
            bindListener();
            if (GoodsModel.GID().length > 0) {
                addGoodsBtn.classList.add("prototype");
                modifyGoodsBtn.classList.remove("prototype");
            } else {
                modifyGoodsBtn.classList.add("prototype");
                addGoodsBtn.classList.remove("prototype");
            }
        },
        bindControler: function (_controler) {
            Controler = _controler;
        },
        setGoodsNameView: function (value) {
            gName.value = value;
        },
        setLView: function (value) {
            L.value = value;
        },
        setWView: function (value) {
            W.value = value;
        },
        setHView: function (value) {
            H.value = value;
        },
        setPriceView: function (value) {
            price.value = value;
        },
        setCountView: function (value) {
            count.value = value;
        },
        /** 更新分類清單
         * @param {Array} classChain 分類鏈
         * @param {Function}  getClassListFun 取得分類清單函式 
         */
        setClassNameView: function (classChain, getClassListFun) {
            let parentElement = classNameList[0].parentElement;
            for (let i = 1; i < classNameList.length;) {//移除除了prototype外的分類表
                parentElement.removeChild(classNameList[i]);
            }

            let before = classNameNodeFactory(getClassListFun());//建立母分類表
            parentElement.appendChild(before);
            for (let i = 0; i < classChain.length; i++) {//建立子分類表
                before.children[0].value = classChain[i];
                before = classNameNodeFactory(getClassListFun(classChain[i]));
                parentElement.appendChild(before);
            }
        },
        /**  更新標籤
         * @param {Iterable} values 被標記的標籤
         * @param {Iterable} tagslist 所有的標籤
         */
        setTagsView: function (values, tagslist) {
            //清除所有標籤內容

            let markedTag = [];
            document.querySelector("#marked").innerHTML = "";
            document.querySelector("#tagList").innerHTML = "";
            for (let tag of values) {
                markedTag.push(tag);
            }
            for (let tag of tagslist) {
                let element = tagElementFactory(tag.tagName);
                if (markedTag.includes(tag.tagName)) {
                    markedElement.appendChild(element);
                } else
                    taglistElement.appendChild(element);
            }

        },
        /** 更新圖片集
         * @param {Map} values 
         */
        setPicture: function (pictureMap) {
            let protoType = pictureArea.querySelector(".prototype").cloneNode(true);
            pictureArea.innerHTML = "";
            pictureArea.appendChild(protoType);
            for (let [key, value] of pictureMap) {
                let newImg = protoType.cloneNode(true);
                newImg.key = key;
                newImg.src = value;
                newImg.classList.remove('prototype');
                newImg.classList.add('picture');
                pictureArea.appendChild(newImg);
            }
        }

    };
};
