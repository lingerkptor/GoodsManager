
const controler = function () {
    let viewModel, goodsModel, classModel, tagModel;
    const observer = {
        update: function () {
            viewModel.setGoodsNameView(goodsModel.goodsName());
            viewModel.setLView(goodsModel.L());
            viewModel.setWView(goodsModel.W());
            viewModel.setHView(goodsModel.H());
            viewModel.setPriceView(goodsModel.price());
            viewModel.setCountView(goodsModel.count());
            viewModel.setClassNameView(classModel.getClassChain(goodsModel.className()), classModel.getClassList);
            viewModel.setTagsView(goodsModel.getTags(), tagModel.getTagIterator());
            viewModel.setPicture(goodsModel.getPicturesKey());
        }
    };


    return {
        /**
         * 控制器初始化
         * @param {GoodsView} viewObj 
         * @param {GoodsModel} goodsObj 
         * @param {ClassModel} classObj 
         * @param {TagModel} tagObj 
         */
        init: async function (viewObj, goodsObj, classObj, tagObj) {
            viewModel = viewObj;
            goodsModel = goodsObj;
            classModel = classObj;
            tagModel = tagObj;
            function sleep(ms = 0) {
                return new Promise(r => setTimeout(r, ms));
            }

            while ((!classModel.isInited())
                && (!classModel.isInited())
                && (!tagModel.isInited())) {
                await sleep(1000);
            }
            // while (!classModel.isInited()) {
            //     await sleep(1000);
            // }
            // while (!goodsModel.isInited()) {
            //     await sleep(1000);
            // }
            // while (!tagModel.isInited()) {
            //     console.log("tagsleep");
            //     await sleep(1000);
            // }

            goodsModel.register(observer);
            observer.update();
            viewModel.bindGoodsModel(goodsModel);
            viewModel.bindControler(this);

        },
        update: function () {
            observer.update();
        }
    };
};


window.onload = function () {
    function getParamsMap(href) {
        let paramMap = new Map();
        console.log(href);
        let params = (href).slice((href).indexOf("?") + 1, (href).length).split("&");
        console.log(params);
        params.forEach(paramStr => {
            let param = paramStr.split("=");
            console.log(param);
            paramMap.set(param[0], param[1]);
        });
        return paramMap;
    }

    let paramMap = getParamsMap(window.location.href);



    let goods = GoodsModel(paramMap.get("GID"));

    let classes = ClassModel();

    let tags = TagModel();

    let view = GoodsView();

    controler().init(view, goods, classes, tags);

};


window.onunload = function () {

};