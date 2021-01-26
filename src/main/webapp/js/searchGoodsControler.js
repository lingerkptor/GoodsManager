let GoodsListcontroler = function () {
    let viewModel = searchGoodsView();

    let classModel = ClassModel();
    viewModel.bindClassModel(classModel);
    classModel.registerObserver({
        update: function () {
            viewModel.setClassList();
            // console.log("class update");
        }
    });

    let tagModel = TagModel();
    viewModel.bindTagsModel(tagModel);
    tagModel.registerObserver({
        update: function () {
            viewModel.setTagsList();
            // console.log("tag update");
        }
    });

    let goodsListModel = GoodsListModel();
    viewModel.bindGoodsListModel(goodsListModel);
    goodsListModel.registerObserver({
        update: function () {
            viewModel.setGoodsList(goodsListModel.getGoodsListData());
            viewModel.setPage(goodsListModel.getPage(), goodsListModel.getSumPage());
        }
    });
};
window.onload = function () {
    GoodsListcontroler();
};
