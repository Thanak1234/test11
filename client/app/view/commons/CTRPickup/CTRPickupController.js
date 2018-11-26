Ext.define('UI.view.commons.CTRPickup.CTRPickupController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.ctrpickup',
    onCTRPickupChanged: function (queryPlan, eOpts) {
        var me = this;
        var v = me.getView();
        if (!queryPlan.query) {        
            queryPlan.query = v.rawValue;
        }
    }
});
