Ext.define("UI.view.forms.dashboard.Dashboard", {
    extend: "UI.view.commons.UIForm",
    xtype: 'form-dashboard',
    controller: 'form-dashboard',    
    viewModel: 'form-dashboard', 
    title: 'Dashboard',
    iconCls: '',
    scrollable: true, 
    closable: false,    
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
    }
});