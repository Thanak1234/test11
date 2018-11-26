Ext.define("UI.view.forms.submission.Transaction", {
    extend: "Ext.grid.Panel",
    xtype: 'submission-transaction',    
    config: {
        formType: 'CTR'
    },
    selModel: 'cellmodel',
    plugins: {
        ptype: 'cellediting',
        clicksToEdit: 1
    },
    store: {
        type: 'transaction'
    },
    features: [{
        ftype: 'summary',
        dock: 'bottom'
    }],
    initComponent: function() {
        var me = this;
        me.buildColumns();
        me.buildEvents();
        me.callParent(arguments);
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            rowdblclick: function(el, record) {
                me.onRowDoubleClicked(el, record);
            } 
        }
    },
    onRowDoubleClicked: function(el, record) {
        var me = this,        
        formIds = record.get('frmIds');
        console.log('record = ', record);
        if(me.getFormType() === 'CTR') {
            var win = Ext.create('UI.view.forms.submission.CTRTransactionWindow', {
                animateTarget: el                
            });
            win.setFormIds(formIds);
            win.show();
            win.loadData();
        }
    },
    buildColumns: function() {
        var me = this,
        columns = [{
            xtype: 'rownumberer'
        }];
        if(me.addExclude == true) {
            columns.push({
                xtype: 'checkcolumn',
                header: 'Exclude?',          
                headerCheckbox: true,
                width: 90,
                stopSelection: false,
                dataIndex: 'exclude'
            });
        }
        columns.push({
            text: 'Player Name',
            width: 150,
            dataIndex: 'playerName'
        }, {
            text: 'Driver License',
            width: 150,
            dataIndex: 'driverLicense'
        }, {
            text: 'Gerc Card No',
            width: 150,
            dataIndex: 'gercCardNo'
        }, {
            text: 'ID Card',
            width: 150,
            dataIndex: 'idCard'
        }, {
            text: 'Passport',
            width: 150,
            dataIndex: 'passport'
        }, {
            text: 'Country',
            width: 150,
            dataIndex: 'playerCountry'
        }, {
            text: 'Trans Category',
            width: 150,
            dataIndex: 'categoryName'
        }, {
            text: 'No. Trans',
            width: 150,
            dataIndex: 'noTrans'
        }, {
            text: 'Amount',
            width: 150,
            formatter: 'usMoney',
            summaryType: 'sum',
            summaryFormatter: 'usMoney',
            dataIndex: 'totalAmount'
        });

        me.columns = columns;
    },
    getRecords: function() {
        var me = this,
        store = me.getStore();
        return me.getArrayItems(store.getRange());
    },
    getArrayItems: function(items){
        var newItems=[]
        if (items && items != undefined && items.length > 0) {
            for (var i = 0; i < items.length; i++) {
                newItems.push(items[i].data);
            }
        }
        return newItems;
    }
});