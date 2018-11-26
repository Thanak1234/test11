
Ext.define("UI.view.common.formAttachment.FileUploadWindow",{
    extend: "Ext.window.Window",
    controller: "common-frmfileupload-fileuploadwindow",
    viewModel: {
        type: "common-frmfileupload-fileuploadwindow"
    },
    modal: true,
    resizable: false,   
    title: "File Attachment", 
    width: 800,
    initComponent: function () {
        var me = this;
        var buttons = [];
        me.items = [{
            xtype: 'form',
            bodyPadding: '10 10 0',
            reference: 'form',
            id: 'uploadForm',
            method: 'POST',
            layout: {
                type: 'vbox',   
                animation: 'slide',
                align: 'stretch'     
            },
            url: Configuration.baseUrl + 'api/v1/frmattachment/upload',
            defaultListenerScope: true,
            defaults: {
                flex: 1,
                anchor: '100%',
                allowBlank: false,
                msgTarget: 'side',
                labelWidth: 100,
                layout: 'form',
                xtype: 'container',
                defaultType: 'textfield'
            },
            items: [{
                fieldLabel: 'Name',
                xtype: 'textfield',
                name: 'name',
                bind: {
                    value: '{item.name}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                fieldLabel: 'Type',
                xtype: 'textfield',
                name: 'docType',
                allowBlank: true,
                bind: {
                    value: '{item.docType}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                fieldLabel: 'Identifier',
                xtype: 'textfield',
                name: 'identifier',
                allowBlank: true,
                bind: {
                    value: '{item.identifier}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                fieldLabel: 'Date',
                xtype: 'datefield',
                name: 'docDate',
                allowBlank: true,
                format: 'Y-m-d',
                bind: {
                    value: '{item.docDate}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                fieldLabel: 'Preparer',
                xtype: 'textfield',
                name: 'preparer',
                allowBlank: true,
                bind: {
                    value: '{item.preparer}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                emptyText: 'Select a file...',
                fieldLabel: 'File',
                xtype: 'fileuploadfield',
                name: 'fileName',
                id: 'fileName',
                msgTarget: 'side',
                bind: {
                    value: '{item.fileName}',
                    hidden: '{!submitBtVisible}',
                    readOnly: '{!submitBtVisible}'
                },
                buttonText: 'Browse'
            }, {
                fieldLabel: 'File',
                xtype: 'label',
                bind: {
                    html: '{item.downloadLink}',
                    hidden: '{submitBtVisible}'
                }
            }, {
                margin: '0 0 0 75',
                xtype: 'label',
                bind: {
                    hidden: '{!submitBtVisible}'
                },
                text: 'File must be less than 50 megabytes.'
            }]
        }];

        var action = me.getViewModel().get('action');
        buttons.push({
            text: 'Close',
            iconCls: 'fa fa-close',            
            handler: 'onCloseClicked'
        });

        if(action != 'VIEW') {
           buttons.push({
                text: 'Save',
                iconCls: 'fa fa-save',
                handler: 'onUploadClicked'
            });
        }

        me.buttons = buttons;
        me.callParent(arguments);
    }
    
});
