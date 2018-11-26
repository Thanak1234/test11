Ext.define('UI.view.form.submission.SubmissionController', {
    extend: 'UI.view.commons.UIFormController',
    alias: 'controller.forms-submission',
    onNewClicked: function(el) {
        var me = this,
        view = me.getView();
        var win = Ext.create('UI.view.forms.submission.SubmissionCriteria', {
            animateTarget: el, 
            mainCtrl: me,
            mainView: view
        });
        win.show();
    },
    onClearFilters: function(el) {
        var me = this,
        refs = me.getReferences(),
        view = refs.submissionList;
        view.filters.clearFilters();
    },
    onRefreshClicked: function(el) {
        var me = this,
        refs = me.getReferences(),
        view = refs.submissionList;
        store = view.getStore();
        store.loadPage(1);
    },
    onSearchClearClick: function(el) {
        var me = this,
        viewmodel = me.getViewModel();
        viewmodel.set('gridInfo.searchText', '');
        me.onSearchClicked(el);
    },
    onSearchClicked: function(el) {
        var me = this,
        refs = me.getReferences(),
        view = refs.submissionList;
        viewmodel = me.getViewModel(),
        store = view.getStore(),
        searchText = viewmodel.get('gridInfo.searchText');
        store.getProxy().extraParams = {
            query: searchText
        }
        store.load();
    },
    onSearchTextPress: function(el, e) {
        var me = this;        
        if (e.keyCode == e.ENTER) {
            me.onSearchClicked();
            e.stopEvent();
        }
    }
});