Ext.define('UI.view.commons.UIReportFormController', {
    extend: 'Ext.app.ViewController',
    init: function() {
        var me = this;

        me.callParent(arguments);
    },
    onClearClicked: function(el) {
        var me = this,
        view = me.getView(),
        viewmodel = view.getViewModel(),
        refs = me.getReferences(),
        criteria = refs.reportCriteria;        
        var form = criteria.gerForm();
        form.reset();
    },
    onSearchClicked: function(el) {
        var me = this,
        refs = me.getReferences(),
        view = me.getView(),
        viewmodel = me.getViewModel(),
        criteria = viewmodel.get('criteria'),
        result = refs.result;
        if(refs.criteria.getForm().isValid()) {
            var store = refs.result.getStore();
            store.getProxy().extraParams = criteria;
            store.load();
        }            
    }
});