Ext.define('UI.view.xComponents.ComponentLookupField', {
    extend: 'Ext.form.field.ComboBox',
    alias: ['widget.x-component-lookup'],
    allowBlank: false,
    editable: true,
    queryMode: 'local',
    forceSelection: true,
    displayField: 'value',
    valueField: 'value',
    initComponent: function () {
        var me = this;
        me.buildStore();
        me.callParent(arguments);
        me.initialize();
    },
    initialize: function() {
        var me = this;
        // if(me.selectFirstRow) {
        //     me.getStore().on('load', function(store, filters, options){
        //         var selected = store.getAt(0); 
        //         if(selected) 
        //             me.setValue(selected.get(me.valueField));
        //     });
        // }        
    },
    buildStore: function() {
        var me = this;
        this.store = {
            autoLoad: true,
            proxy: {
                type: 'rest',
                url: Configuration.baseUrl + 'api/v1/components/lookup',
                reader: {
                    type: 'json'
                },
                extraParams: {
                    key: me.key
                }
            }
        };
    }
});