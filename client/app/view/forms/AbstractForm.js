Ext.define("UI.view.forms.AbstractForm", {
    extend: "UI.view.commons.UIForm",
    xtype: 'abstract-form',
    controller: 'abstract-form',
    scrollable: true,
    requires: [
        'UI.view.form.AbstractFormController',
        'UI.view.forms.AbstractFormModel'
    ],
    viewModel: {
        type: 'abstract-form'
    },    
    initComponent: function() {
        var me = this;
        me.buildItems();
        me.buildButtons();
        me.callParent(arguments);
    },
    buildItems: function() {
        var me = this,
        formConfig = Ext.isFunction(me.getFormConfiguration)? me.getFormConfiguration(me): null,
        items = [];
        if(Ext.isFunction(me.buildInfoItems)) {
            var infoItems = me.buildInfoItems(me);
            items.push({
                xtype: 'panel',
                title: 'Info',
                layout: 'column',
                iconCls: 'fa fa-info',
                border: true,
                bodyPadding: 5,         
                defaults: {
                    xtype: 'textfield',
                    columnWidth: 0.333,
                    allowBlank: false,
                    labelWidth: 125,
                    margin: '0 10 5 0'
                },
                items: infoItems
            })
        }

        if(formConfig && formConfig.commentBlock && formConfig.commentBlock.visible) {
            items.push({
                xtype: 'panel',
                title: 'Comment',
                layout: 'fit',
                border: true,
                collapsible: true,
                bodyPadding: 5,
                items: {
                    xtype: 'textarea',
                    maxLength: 2000,
                    fieldLabel: 'Comment',
                    allowBlank: false,
                    bind: {
                        value: '{formInfo.comment}'
                    }
                }
            })
        }

        if(formConfig && formConfig.fileAttachmentBlock && formConfig.fileAttachmentBlock.visible) {
            var editable = formConfig.fileAttachmentBlock.editable;
            items.push({
                xtype: 'form-fileupload',
                title: 'File Attachment',
                reference: 'attachment',
                border: true,
                editable: editable
            });
        }

        if(formConfig && formConfig.formAttachmentBlock && formConfig.formAttachmentBlock.visible) {
            var editable = formConfig.formAttachmentBlock.editable;
            items.push({
                xtype: 'form-frmfileupload',
                title: 'File Attachment',
                reference: 'attachment',
                border: true,
                editable: editable
            });
        }

        if(formConfig && formConfig.activityBlock && formConfig.activityBlock.visible) {
            items.push({
                xtype: 'activity',
                reference: 'activity',
                border: true,                
                title: 'Activity History'
            });
        }
        
        me.items = items;
    },
    buildButtons: function() {
        var me = this,
        buttons = [],
        formConfig = Ext.isFunction(me.getFormConfiguration)? me.getFormConfiguration(me): null,
        state = me.extraData.state;

        if(formConfig && formConfig.exportPdf.visible) {
            buttons.push({
                xtype: 'button',
                text: 'Export PDF',
                iconCls: 'fa fa-file-pdf-o',
                handler: 'onExportClicked'
            });
        }

        if(state == 'edit') {
            buttons.push('->', {
                xtype: 'button',
                text: 'Close',
                iconCls: 'fa fa-close',
                handler: 'onCloseClicked'
            }, {
                xtype: 'button',
                text: 'Save Change',
                iconCls: 'fa fa-save',
                handler: 'onSaveClicked'
            });            
        } else if (state == 'view') {
            buttons.push('->', {
                xtype: 'button',
                text: 'Close',
                iconCls: 'fa fa-close',
                handler: 'onCloseClicked'
            }); 
        } else {
            buttons.push('->', {
                xtype: 'button',
                text: 'Close',
                iconCls: 'fa fa-close',
                handler: 'onCloseClicked'
            }, {
                xtype: 'button',
                text: 'Save',
                iconCls: 'fa fa-save',
                handler: 'onSaveClicked'
            });  
        }

        me.fbar = buttons
    }
});