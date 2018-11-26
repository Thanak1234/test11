Ext.define('UI.view.form.CTR.CTRListController', {
    extend: 'UI.view.form.AbstractGridController',
    alias: 'controller.form-ctr-list',
    actionUrl: Configuration.baseUrl + 'api/v1/ctrform',
    onNewClicked: function(el) {
        this.routeTo('Threshold Recording (New Form)', 'form-ctr', 'UI.view.forms.CTR.CTRForm', {
            state: 'new',
            record: null
        });
    },
    onModifiedClicked: function(el) {
        var me = this,
        viewmodel = me.getViewModel(),
        selection = viewmodel.get('selectedRecord');
        var formNo = selection.get('formNo');
        this.routeTo(Ext.String.format('Threshold Recording(Edit:{0})', formNo), ('modification-' + formNo.toLowerCase()), 'UI.view.forms.CTR.CTRForm', {
            state: 'edit',
            record: selection,
            readonly: false
        });
    },
    onPreviewClicked: function(el) {
        var me = this,
        viewmodel = me.getViewModel(),
        selection = viewmodel.get('selectedRecord');
        var formNo = selection.get('formNo');
        this.routeTo(Ext.String.format('Threshold Recording(View:{0})', formNo), ('preview-' + formNo.toLowerCase()), 'UI.view.forms.CTR.CTRForm', {
            state: 'view',
            record: selection,
            readonly: true
        });
    },        
    onRowDoubleClicked: function(el) {
        this.onPreviewClicked(el);
    }
});