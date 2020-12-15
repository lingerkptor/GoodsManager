let tagView;
window.onload = function () {
    tagView = tagUIView();
    tagView.setAddTagFunc(tagManageModel.addTag);
    tagView.setModifyTagFunc(tagManageModel.renameTag);
    tagView.setDeleteTagFunc(tagManageModel.removeTag);
    tagView.setUpdateDataSource(tagManageModel.getTagList);
    tagView.updateUI();
};
window.onunload = function () {

};