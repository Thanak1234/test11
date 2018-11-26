
Ext.define("UI.view.common.fileUpload.FileUpload",{
    extend: "Ext.grid.Panel",
    xtype: 'form-fileupload',
    requires: [
        "UI.view.common.fileUpload.FileUploadController",
        'Ext.grid.column.Action'
    ],
    controller: "common-fileupload-fileupload",
    viewModel: {
        type: "common-fileupload-fileupload"
    },
    iconCls : 'fa fa-upload',
    title: 'File Attachment',
    stateful: false,
    collapsible: true,
    headerBorders: false,
    editable: false,     
    viewConfig: {
        enableTextSelection: true
    },
    listeners: {
        rowdblclick: 'viewFile'
    },
    store:{
       type:'fileUpload'
    },
    guid: new Ext.data.identifier.Uuid().generate(),
    initComponent: function () {
        var me = this;
        
        me.bbar= ['->', {
                    text: 'Add file',
                    iconCls: 'fa fa-plus',
                    reference: 'addBt',
                    handler: 'addNewFile',
                    hidden: !me.editable
                }];
        
        me.columns = [
            {
                text        : 'Name',
                flex        : 1,
                sortable    : true,
                dataIndex   : 'name'
            },{
                text        : 'Description',
                width       : 200,
                sortable    : true,
                dataIndex   : 'description'
            },{
                text        : 'File Name',
                width       : 200,
                sortable    : true,
                dataIndex   : 'fileName'
            },{
                text        : 'Upload By',
                width       : 180,
                sortable    : true,
                dataIndex: 'uploadedBy'
            }, {
                xtype: 'datecolumn',
                text: 'Upload Date',
                width: 180,
                sortable: true,
                format: 'Y-m-d H:i:s',
                dataIndex: 'uploadedDate'
            }, {                
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn', 
                align: 'center',
                width: 30,
                items: [{
                        iconCls: 'fa fa-download',
                        tooltip: 'Dowload',
                        handler: 'dowloadFile'
                }]
            }, {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                align: 'center',
                hidden: !me.editable,
                width: 30,
                items: [{
                    iconCls: 'fa fa-trash-o',
                    tooltip: 'Remove',
                    handler: 'removeFile'                    
                }]
            }
        ];
        

        me.callParent(arguments);
    },
    getData: function () {
        var me              = this, 
            store = me.getStore();
        return {
            newItems: me.getNewRecords(store.getData()),
            removedItems: me.getArrayRemovedItems(store.getRemovedRecords()),
            allItems: me.getArrayItems(store.getRange())
        };
    },
    getNewRecords: function(data){
        var newItems = [];
        var items = (data != undefined && data.length > 0) ? data.items : [];
        if (items && items != undefined && items.length > 0) {
            for (var i = 0; i < items.length; i++) {
                if (items[i].data.isTemp == true) {
                    newItems.push(items[i].data);
                }
            }
        }
        return newItems;
    },
    getArrayItems: function(items){
        var newItems=[]
        if (items && items != undefined && items.length > 0) {
            for (var i = 0; i < items.length; i++) {
                newItems.push(items[i].data);
            }
        }
        return newItems;
    },
    getArrayRemovedItems: function(items){
        var newItems=[]
        if (items && items != undefined && items.length > 0) {
            for (var i = 0; i < items.length; i++) {
                var removedRec = items[i].data;
                if (removedRec.isTemp) {
                    removedRec.id = 0;
                }
                newItems.push(removedRec);
            }
        }
        return newItems;
    }
});
