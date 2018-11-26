Ext.define('UI.view.xComponents.LookupField', {
    extend: 'Ext.form.field.ComboBox',
    alias: ['widget.lookupField'],
    allowBlank: false,
    editable: true,
    queryMode: 'local',
    forceSelection: true,
    initComponent: function () {
        var me = this;
        me.buildStore();
        me.callParent(arguments);
        me.initialize();  
    },
    initialize: function() {
        var me = this;                
    },
    buildStore: function() {
        var me = this;
        this.store = {
            autoLoad: true,
            proxy: {
                type: 'rest',
                url: Configuration.baseUrl + me.url,
                reader: {
                    type: 'json'
                }
            }
        };                  
    },
    listeners: {
        beforerender: function (elm, eOpts) {
            var me = this;
            var store = this.getStore();
            if (me.params) {
                store.getProxy().extraParams = me.params;
            };
            store.load();
        }
    }
});