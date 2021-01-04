const GoodsView = function (GoodsModel, ClassModel) {
    let good = GoodsModel;
    /** 宣告UI元件 Start */
    const gName = document.getElementById('gName');//商品名稱
    const L = document.getElementById('L');//商品長度
    const W = document.getElementById('W');//商品寬度
    const H = document.getElementById('H');//商品厚度(高度)
    const price = document.getElementById('price');//價格

    //分類清單(動態)
    const classNameList = function () { document.getElementsByClassName("classList"); };

    const tags = document.getElementById('tags');// 分類清單

    const addTag = document.getElementById("addTag");// 增加綁定標籤
    const removeTag = document.getElementById("removeTag");//移除綁定標籤

    const pictureArea = document.getElementById("pictureArea");// 圖片顯示區塊
    const uploadPicBtn = document.getElementById("uploadPictureBtn");//上傳圖片
    const addGoodsBtn = document.getElementById("addGoodsBtn");//新增商品按鈕
    const modifyGoodsBtn = document.getElementById("modifyGoodsBtn");//修改商品按鈕

    /** 宣告UI元件 END  */

    uploadPicBtn.addEventListener('change', function (e) {
        console.log(good);
        good.addPicture(uploadPicBtn.files);
    });




    /**  建立分類節點清單
     * @param {String} className 
     * @returns ElementNode
     */
    function classNameNodeFactory(className) {
        // 建立(複製) classNameNode(Element) ProtoType 
        let prototype = classNameList()[0].cloneNode(true);
        ClassModel.getClassList(className).forEach(subClassName => {
            // 建立(複製) option的 ProtoType
            let option = prototype.children[0].children[0].cloneNode(true);
            option.value = subClassName;
            prototype.children[0].appendChild(option);
        });
        prototype.children[0].addEventListener('click', function (e) {
            let element = e.currentTarget;
            //移除後面的子分類
            for (i = 1; i + 1 < classNameList().length;) {
                if (classNameList()[i].children[0] === element) {
                    element.parentElement.parentElement.removeChild(classNameList()[i + 1]);
                } else
                    i++;
            }
            GoodsModel.className(element.value);
        });

        // 移除 class 屬性的 prototype 
        prototype.classList.remove("prototype");
        return prototype;
    }

    /**
     * 
     */


    return {
        setGoodsNameView: function (value) {

        },
        setLView: function (value) {

        },
        setWView: function (value) {

        },
        setHView: function (value) {

        },
        setPriceView: function (value) {

        },
        /** 更新分類清單
         * @param {Array} classChain 
         */
        setClassNameView: function (classChain) {
            let parentElement = classNameList()[0].parentElement;
            for (i = 1; i < classNameList().length; i++) {
                parentElement.removeChild(classNameList()[i]);
            }
            classChain.forEach(subClassName => {
                parentElement.appendChild(classNameNodeFactory(subClassName));
            });
        },
        setTagsView: function (value) {

        },
        /** 更新圖片集
         * @param {Map} values 
         */
        setPicture: function (pictureMap) {
            let protoType = document.querySelector(".prototype.picture").cloneNode(true);
            pictureArea.innerHTML = "";
            pictureArea.appendChild(protoType);
            for (let [key, value] of pictureMap) {
                let newImg = protoType.cloneNode(true);
                newImg.key = key;
                newImg.src = value;
                newImg.classList.remove('prototype');
                pictureArea.appendChild(newImg);
            }
        }

    };
};
