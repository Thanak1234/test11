Ext.define('UI.view.form.SIR.SIRListController', {
    extend: 'UI.view.form.AbstractGridController',
    alias: 'controller.form-sir-list',
    actionUrl: Configuration.baseUrl + 'api/v1/sirform',
    onNewClicked: function(el) {
        this.routeTo('SIR Form (New Form)', 'form-sir', 'UI.view.forms.SIR.SIRForm', {
            state: 'new',
            record: null
        });
    },
    onModifiedClicked: function(el) {
        var me = this,
        viewmodel = me.getViewModel(),
        selection = viewmodel.get('selectedRecord');
        var formNo = selection.get('sirNo');
        this.routeTo(Ext.String.format('SIR Form(Edit:{0})', formNo), ('modification-' + formNo.toLowerCase()), 'UI.view.forms.SIR.SIRForm', {
            state: 'edit',
            record: selection,
            readonly: false
        });
    },
    onPreviewClicked: function(el) {
        var me = this,
        viewmodel = me.getViewModel(),
        selection = viewmodel.get('selectedRecord');
        var formNo = selection.get('sirNo');
        this.routeTo(Ext.String.format('SIR Form(View:{0})', formNo), ('preview-' + formNo.toLowerCase()), 'UI.view.forms.SIR.SIRForm', {
            state: 'view',
            record: selection,
            readonly: true
        });
    },        
    onRowDoubleClicked: function(el) {
        this.onPreviewClicked(el);
    }
});