window.onload = function () {
    const classFilter = document.getElementById('class');
    const tagFilter = document.getElementById('tag');
    const dateFilter = document.getElementById('date');
    const keyword = document.getElementById('keyword');
    const filterBtn = document.getElementById('filterBtn');
    const clearfilterBtn = document.getElementById('clearfilterBtn');
    const GoodsTable = document.getElementById('GoodsTable');
    const GoodsDetail = function () {
        let detail = document.getElementById('GoodsDetail');
        return {
            update: function (goods) {
                //還沒寫完
            }
        }
    };
    const selectedGoods = [];

    searchGoodsPage.getclassList({
        update: function (classArray) {
            classArray.forEach(classObj => {
                let prototype = document.createElement("option");
                prototype.value = classObj.classId;
                prototype.innerText = classObj.className;
                classFilter.appendChild(prototype);
            });
        }
    });
    searchGoodsPage.getTagList({
        update: function (tagArray) {
            tagArray.forEach(tagObj => {
                let prototype = document.createElement("option");
                prototype.value = tagObj.classId;
                prototype.innerText = tagObj.className;
                tagFilter.appendChild(prototype);
            });
        }
    });
    classFilter.addEventListener('change', function (e) {
        if (classFilter.selectedIndex > 0) {
            searchGoodsPage.addFilter('class', classFilter.options[classFilter.selectedIndex].value);
        } else {
            searchGoodsPage.removeFilter('class');
        }
    });
    tagFilter.addEventListener('change', function (e) {
        if (tagFilter.selectedIndex > 0) {
            searchGoodsPage.addFilter('tag', tagFilter.options[tagFilter.selectedIndex].value);
        } else {
            searchGoodsPage.removeFilter('tag');
        }
    });

    dateFilter.addEventListener('change', function (e) {
        if (dateFilter.value.length > 0) {
            searchGoodsPage.addFilter('date', dateFilter.value);
        } else {
            searchGoodsPage.removeFilter('date');
        }

    });
    keyword.addEventListener('change', function (e) {
        if (keyword.value.length > 0) {
            searchGoodsPage.addFilter('keyword', keyword.value);
        } else {
            searchGoodsPage.removeFilter('keyword');
        }
    });


    filterBtn.addEventListener('click', function (e) {
        searchGoodsPage.getGoodsList({
            update: function (array) {
                let prototype = GoodsTable.getElementsByClassName('prototype')[0].cloneNode(true);
                GoodsTable.innerHTML = '';
                GoodsTable.appendChild(prototype);
                array.forEach(goods => {
                    let newGoods = prototype.cloneNode(true);
                    newGoods.setAttribute('goodsId', goods.goodsId);

                    let goodsCheckbox = newGoods.getElementsByName('select')[0];
                    goodsCheckbox.addEventListener('change', function (e) {
                        if (goodsCheckbox.checked == true) {
                            selectedGoods.push(goods.goodsId);
                        } else {
                            selectedGoods.forEach((goodsId, index) => {
                                if (goods.goodsId = goodsId)
                                    selectedGoods.splice(index, 1);
                            });
                        }
                    });

                    let goodName = newGoods.getElementsByName('goodName')[0];
                    goodName.innerText = goods.goodsName;
                    goodName.addEventListener('mousemove', function (e) {
                        GoodsDetail.update(goods);
                    });


                    let modifyGoods = newGoods.getElementsByName('modify')[0];
                    modifyGoods.addEventListener('click', function (e) {
                        //還沒寫完
                    });
                    let deleteGoods = newGoods.getElementsByName('delete')[0];

                    deleteGoods.addEventListener('click', function (e) {
                        //還沒寫完
                    });

                    GoodsTable.appendChild(newGoods);
                });


            }
        });
    });

    clearfilterBtn.addEventListener('click', function (e) {
        classFilter.selectedIndex = 0;
        tagFilter.selectedIndex = 0;
        dateFilter.value = '';
        keyword.value = '';
        searchGoodsPage.removeFilter('class');
        searchGoodsPage.removeFilter('tag');
        searchGoodsPage.removeFilter('date');
        searchGoodsPage.removeFilter('keyword');
    });


};
window.unonload = function () { };