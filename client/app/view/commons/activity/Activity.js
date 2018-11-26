Ext.define('UI.view.commons.Activity', {
    extend: 'Ext.grid.Panel',
    xtype: 'activity',
    controller: 'activity',
    store: {
        type: 'activity'
    },
    border: true,
    collapsible: true,
    iconCls: 'fa fa-history',
    viewModel: {
        type: 'activity'
    },
    initComponent: function() {
        var me = this;
        me.buildColumns();
        me.callParent(arguments);
    },
    buildColumns: function() {
        var me = this;
        me.columns = [{
            text        : 'Action',
            flex        : 1,
            sortable    : true,
            dataIndex   : 'action'
        }, {
            text        : 'Action Date',
            flex        : 1,
            sortable    : true,
            dataIndex   : 'createdDate',
            renderer: function(value) {
                return value?Ext.Date.format(new Date(value), 'd/m/Y H:i:s'):'';
            }
        }, {
            text        : 'Action By',
            flex        : 1,
            sortable    : true,
            dataIndex   : 'createdBy'
        }, {
            text        : 'Comment',
            flex        : 1,
            sortable    : true,
            dataIndex   : 'comment'
        }];
    }
});