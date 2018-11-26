Ext.define('UI.view.commons.player.PlayerPickupController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.playerpickup',
    onPlayerPickupChanged: function (queryPlan, eOpts) {
        var me = this;
        var v = me.getView();
        if (!queryPlan.query) {        
            queryPlan.query = v.rawValue;
        }
    }
});
