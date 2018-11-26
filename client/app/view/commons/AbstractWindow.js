Ext.define("UI.view.commons.AbstractWindow", {
    extend: "Ext.window.Window",
    xtype: 'common-abstract-window',
    controller: 'common-abstract-window',
    maximizable: true,
    mixins: {
        authorization: 'UI.view.common.Authorization'
    },
    initComponent: function() {
        var me = this;
        me.buildEvents();
        me.callParent(arguments);
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            beforerender: function (panel) {
                me.mixins.authorization.authorize(panel);
            }
        };
    }    
});