Ext.define('UI.view.form.AbstractFormController', {
    extend: 'UI.view.commons.UIFormController',
    alias: 'controller.abstract-form',
    config: {
        control: {
            '*': {
                loadActivty: 'loadActivty',
                loadAttachment: 'loadAttachment'
            }
        }
    },
    loadActivty: function(formId) {
        var me = this,        
        refs = me.getReferences();
        activity = refs.activity,
        store = activity.getStore();
        store.getProxy().extraParams = {
            formId: formId
        };
        store.load();
    },
    loadAttachment: function(formId) {
        var me = this,        
        refs = me.getReferences();
        attachment = refs.attachment,
        store = attachment.getStore();
        store.getProxy().extraParams = {
            formId: formId
        };
        store.load();
    },
    onSaveClicked: function(el) {
        var me = this,
        viewmodel = me.getViewModel(),
        formInfo = viewmodel.get('formInfo'),
        view = me.getView(),
        refs = me.getReferences();        
        form = view.getForm();
        if(form.isValid()) {
            Ext.MessageBox.show({
                title: 'Confirmation',
                msg: 'Do you want to save?',
                buttons: Ext.MessageBox.YESNO,
                scope: this,
                icon: Ext.MessageBox.QUESTION,
                fn: function (bt) {
                    if (bt == 'yes') {
                        me.getView().mask("Data processing...");
                        var attachments = refs.attachment.getData();
                        formInfo.newRecords = attachments.newItems;
                        formInfo.removeRecords = attachments.removedItems;

                        Ext.Ajax.request({
                            url: me.actionUrl,
                            method: 'POST',
                            headers: { 'Content-Type': 'application/json' },
                            jsonData: formInfo,
                            success: function (response, operation) {
                                me.getView().unmask();
                                me.clearData(form);
                                me.showToast('Your transaction success.');
                                if(el.getText() == 'Save Change') {
                                    view.isChanged = false;
                                    view.close();
                                } else {
                                    view.body.scrollTo('Top', 0, true);
                                }
                                
                            },
                            failure: function (data, operation) {
                                me.getView().unmask();
                                var response = Ext.decode(data.responseText);
                                Ext.MessageBox.show({
                                    title: 'Error',
                                    msg: response.Message,
                                    buttons: Ext.MessageBox.OK,
                                    icon: Ext.MessageBox.ERROR
                                });
                            }
                        });
                    }
                }
            });
        } else {
            var invalidFields = me.getInvalidFields();            
            if(invalidFields.length > 0) {
                Ext.MessageBox.show({
                    title: 'Invalid Fields',
                    msg: 'The following field(s) are invalid: : <span style="color: red">' + invalidFields.join(', ') + '</span> .',
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                });
                return;
            }
        }                
    },
    onCloseClicked: function(el) {
        var me = this,
        view = me.getView();
        if(view) view.close();
        
    },
    getInvalidFields: function() {
        var me = this,
        form = me.getView(),
        fields = [];
        var invalids = form.query("field{isValid()==false}");
        Ext.each(invalids, function(item, index) {
            if(item.fieldLabel)
                fields.push(item.fieldLabel.replace(' <span class="req" style="color:red">*</span>', ''));
            else if(item.hiddenLabel)
                fields.push(item.hiddenLabel);
        });
        return fields;
    }
});