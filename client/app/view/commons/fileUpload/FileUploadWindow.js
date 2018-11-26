
Ext.define("UI.view.common.fileUpload.FileUploadWindow",{
    extend: "Ext.window.Window",
    controller: "common-fileupload-fileuploadwindow",
    viewModel: {
        type: "common-fileupload-fileuploadwindow"
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
            url: Configuration.baseUrl + 'api/v1/upload',
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
                fieldLabel: 'Description',
                xtype: 'textarea',
                name: 'description',
                allowBlank: true,
                bind: {
                    value: '{item.description}',
                    readOnly: '{!submitBtVisible}'
                }
            }, {
                fieldLabel: 'Upload By',
                xtype: 'textfield',
                name: 'uploadedBy',
                allowBlank: true,
                readOnly: true,
                bind: {
                    value: '{item.uploadedBy}',
                    hidden: '{submitBtVisible}'
                }
            }, {
                fieldLabel: 'Upload Date',
                xtype: 'datefield',
                name: 'uploadDate',
                allowBlank: true,
                format: 'Y-m-d H:i:s',
                readOnly: true,
                bind: {
                    value: '{item.uploadedDate}',
                    hidden: '{submitBtVisible}'
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
