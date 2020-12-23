let tagView;
window.onload = function () {
    tagView = tagUIView();
    tagView.setAddTagFunc(tagManageModel.addTag);
    tagView.setModifyTagFunc(tagManageModel.updateTag);
    tagView.setDeleteTagFunc(tagManageModel.removeTag);
    tagView.setUpdateDataSource(tagManageModel.getTagList);
    tagView.updateUI();
};
window.onunload = function () {

};