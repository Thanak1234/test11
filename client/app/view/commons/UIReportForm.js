Ext.define('UI.view.commons.UIReportForm', {
    extend: 'Ext.panel.Panel',
    layout: 'border',  
    mixins: {
        authorization: 'UI.view.common.Authorization'
    },  
    requires: [
        'UI.view.commons.UIReportFormController'
    ],
    defaults: {
        scrollable: true,
        style: {
            borderTop: '2px solid white'
        }
    }, 
    keyMapEnabled: true,  
    keyMap: {
        'ENTER': {
            handler: 'onSearchClicked'
        }
    }, 
    config: {
        store: null
    },     
    initComponent: function() {
        var me = this;
        me.buildItems();
        me.buildEvents();
        me.callParent(arguments);
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            beforerender: function (panel){
                me.mixins.authorization.authorize(panel);
            },
            specialkey: function(field, e){
                if (e.getKey() == e.ENTER) {
                    me.fireEvent('onSearchClicked', field, e);
                }
            }
        };
    }, 
    buildItems: function() {
        var me = this,
        criteria = null,
        grid = null,
        items = [];

        if(Ext.isFunction(me.buildCriteria)) {
            criteria = me.buildCriteria();
            items.push({
                xtype: 'form',
                region: 'west',
                title: 'Report Criteria',
                reference: 'criteria',
                width: 350,
                split: true,                 
                collapsible: true,
                border: true,
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                bodyPadding: 5,
                scrollable: 'y',
                keyMapEnabled: true,
                items: criteria,
                fbar: ['->', {
                    xtype: 'button',
                    text: 'Clear',
                    iconCls: 'fa fa-refresh',
                    handler: 'onClearClicked'
                }, {
                    xtype: 'button',
                    text: 'Search',
                    iconCls: 'fa fa-search',
                    handler: 'onSearchClicked'
                }]
            });
        }

        if(Ext.isFunction(me.buildColumns)) {
            items.push({
                xtype: 'grid',
                region: 'center',
                title: 'Report Result',
                reference: 'result',
                closable: false,
                store: me.getStore(),
                columns: me.buildColumns(),
                bbar: {
                    xtype: 'pagingtoolbar',
                    displayInfo: true
                },
                tbar: [
                {
                    xtype: 'button',
                    text: 'Export',
                    iconCls: 'fa fa-download',
                    menu: me.buildExports()
                }]
            });
        }       

        me.items = items;
    }
});