Ext.define("UI.view.forms.CTR.CTRForm", {
    extend: "UI.view.forms.AbstractForm",
    xtype: 'form-ctr',
    requires: [
        'UI.view.form.CTR.CTRFormController',
        'UI.view.forms.CTR.CTRFormModel'
    ],
    controller: 'form-ctr',
    scrollable: true,
    viewModel: 'form-ctr',      
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
    },
    initData: function() {
        var me = this,
        viewmodel = me.getViewModel(),
        state = me.extraData.state;

        viewmodel.set('formInfo', {
            formNo: null,
            submission: false,
            typeOf: 'Slot',
            amount:null,
            area: null,
            docNo: null,
            location: null,
            playerId: null,
            remark: null,
            staffDepartment: null,
            staffId: null,
            tran: null,
            tranDate: null,
            playerWith: 'GERC Card Number'
        });

        switch(state) {
            case 'edit': {
                var data = me.extraData.record.getData();
                viewmodel.set('formInfo', data);
                viewmodel.set('readonly', false);    
                me.fireEvent('loadActivty', data.id);       
                me.fireEvent('loadAttachment', data.id);
                break;
            }
            case 'view': {
                var data = me.extraData.record.getData();
                viewmodel.set('formInfo', data);
                viewmodel.set('readonly', true);
                me.fireEvent('loadActivty', data.id);  
                me.fireEvent('loadAttachment', data.id);  
                me.isPreview = true;
            }
        }
    },
    buildInfoItems: function(main) {
        var me = this;
        return [{
            fieldLabel: 'Threshold Recording Form No',
            labelWidth: 190,
            allowBlank: true,
            editable: false,
            emptyText: 'AUTO',            
            bind: {
                value: '{formInfo.formNo}'
            }
        }, {
            xtype: 'x-component-lookup',            
            fieldLabel: 'Type',
            key: 'CTR_TYPE',
            publishes: 'selection',
            reference: 'type',
            listeners: {
                change: 'onTypeChanged'
            },
            bind: {
                value: '{formInfo.typeOf}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            fieldLabel: 'Transaction',
            key: 'CTR_TRANSACTION',
            reference: 'transaction',
            bind: {
                value: '{formInfo.tran}',
                filters: {
                    property: 'parentId',
                    value: '{type.selection.id}'
                },
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'lookupField',
            fieldLabel: 'Location',
            reference: 'location',
            labelWidth: 190,
            params: { locnType: 'TA' },
            url: 'api/v1/locations',
            displayField: 'locnCode',
            valueField: 'locnCode',
            bind: {
                value: '{formInfo.location}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            fieldLabel: 'Area',
            key: 'CTR_AREA',
            reference: 'area',
            bind: {
                value: '{formInfo.area}',
                filters: {
                    property: 'parentId',
                    value: '{type.selection.id}'
                },
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-player-fieldset',
            title: 'Player Info',
            columnWidth: 1
        }, {
            xtype: 'datetime',
            fieldLabel: 'Trans Date/Time',
            bind: {
                value: '{formInfo.tranDate}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'combobox',
            fieldLabel: 'Submission',
            valueField: 'value',
            displayField: 'name',
            value: false,
            store: {
                type: 'store',
                data: [
                    { value: true, name: 'Yes' },
                    { value: false, name: 'No' }
                ]
            },
            readOnly: true,
            allowBlank: true,
            bind: {
                value: '{formInfo.submission}',
                readOnly: '{readonly}'
            }
        }, {
            fieldLabel: 'Document No',
            allowBlank: true,
            bind: {
                value: '{formInfo.docNo}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'currencyfield',
            fieldLabel: 'Amount',
            value: 0.00,
            bind: {
                value: '{formInfo.amount}',
                readOnly: '{readonly}'
            }
        }, {
            fieldLabel: 'Staff ID',
            bind: {
                value: '{formInfo.staffId}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            fieldLabel: 'Staff Department',
            key: 'AML_DEPARTMENT',
            bind: {
                value: '{formInfo.staffDepartment}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'textarea',
            fieldLabel: 'Remark',
            columnWidth: 0.999,
            maxLength: 2000,
            allowBlank: true,
            bind: {
                value: '{formInfo.remark}',
                readOnly: '{readonly}'
            }
        }];
    },
    getFormConfiguration: function(form) {
        var me = this,
        extraData = me.extraData,
        state = extraData.state;

        if(state == 'new') {
            config = {
                commentBlock: {
                    visible: false
                },
                fileAttachmentBlock: {
                    visible: true,
                    editable: true
                },
                activityBlock: {
                    visible: false
                },
                exportPdf: {
                    visible: false
                }
            }
        } else if (state == 'edit') {
            config = {
                commentBlock: {
                    visible: true
                },
                fileAttachmentBlock: {
                    visible: true,
                    editable: true
                },
                activityBlock: {
                    visible: true
                },
                exportPdf: {
                    visible: true
                }
            };
        } else {
            config = {
                commentBlock: {
                    visible: false
                },
                fileAttachmentBlock: {
                    visible: true,
                    editable: false
                },
                activityBlock: {
                    visible: true
                },
                exportPdf: {
                    visible: true
                }
            };
        }

        return config;
    }
});