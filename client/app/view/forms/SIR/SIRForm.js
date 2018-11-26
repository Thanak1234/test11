Ext.define("UI.view.forms.SIR.SIRForm", {
    extend: "UI.view.forms.AbstractForm",
    xtype: 'form-sir',
    requires: [
        'UI.view.form.SIR.SIRFormController',
        'UI.view.forms.SIR.SIRFormModel'
    ],
    controller: 'form-sir',
    scrollable: true,
    viewModel: 'form-sir',  
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
    },
    initData: function() {
        var me = this,
        viewmodel = me.getViewModel(),
        state = me.extraData.state;

        viewmodel.set('formInfo', {
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
    buildInfoItems: function(parent) {
        return [{
            fieldLabel: 'SIR Form No',
            allowBlank: true,
            labelWidth: 190,
            emptyText: 'AUTO',
            editable: false,
            bind: {
                value: '{formInfo.sirNo}'
            }
        }, {
            fieldLabel: 'SIR Doc No',
            allowBlank: true,
            bind: {
                value: '{formInfo.sirDocumentNo}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'datefield',
            fieldLabel: 'Received Date',
            bind: {
                value: '{formInfo.recievedDate}',
                readOnly: '{readonly}'
            }
        }, {
            fieldLabel: 'Received By',
            labelWidth: 190,
            bind: {
                value: '{formInfo.recievedBy}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            key: 'SIR_AREA',
            fieldLabel: 'Area',
            bind: {
                value: '{formInfo.sirArea}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'combo',
            fieldLabel: 'Submission',
            allowBlank: true,
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
            bind: {
                value: '{formInfo.submission}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-player-fieldset',
            title: 'Player Info',
            columnWidth: 1
        }, {
            xtype: 'x-component-lookup',
            key: 'SIR_METHOD',
            labelWidth: 250,
            fieldLabel: 'Method Used to Verify Identify',
            columnWidth: 0.5,
            bind: {
                value: '{formInfo.sirMethodVerify}',
                readOnly: '{readonly}'
            }
        }, {
            allowBlank: false,
            columnWidth: 0.5,
            hiddenLabel: 'Method Used to Verify Identify Others',
            hiddenAsterik: true,
            emptyText: 'Others',
            disabled: true,
            bind: {
                disabled: '{!(formInfo.sirMethodVerify == "Others")}',
                value: '{formInfo.sirMethodVerifyOthers}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            labelWidth: 250,
            fieldLabel: 'Current Relationship',
            key: 'SIR_CURRENT_RELATION',
            columnWidth: 0.5,
            bind: {
                value: '{formInfo.sirCurrentRelationship}',
                readOnly: '{readonly}'
            }
        }, {
            allowBlank: false,
            columnWidth: 0.5,
            hiddenLabel: 'Current Relationship Others',
            emptyText: 'Others',
            hiddenAsterik: true,
            disabled: true,
            bind: {
                disabled: '{!(formInfo.sirCurrentRelationship == "Others")}',
                value: '{formInfo.sirCurrentRelationshipOthers}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            labelWidth: 250,
            fieldLabel: 'Affiliation or Relationship to NagaWorld',
            columnWidth: 0.5,
            key: 'SIR_AFFILIATION',
            columnWidth: 0.5,
            bind: {
                value: '{formInfo.sirAffiliationRelationship}',
                readOnly: '{readonly}'
            }
        }, {
            allowBlank: false,
            hiddenLabel: 'Affiliation or Relationship to NagaWorld Others',
            emptyText: 'Others',
            hiddenAsterik: true,
            columnWidth: 0.5,
            disabled: true,
            bind: {
                disabled: '{!(formInfo.sirAffiliationRelationship == "Others")}',
                value: '{formInfo.sirAffiliationRelationshipOthers}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            labelWidth: 250,
            fieldLabel: 'Type of Suspicious Activities',
            columnWidth: 0.5,
            key: 'SIR_TYPE_SUSPICIOUS',
            bind: {
                value: '{formInfo.sirTypeSuspiciousActivities}',
                readOnly: '{readonly}'
            }
        }, {
            allowBlank: false,
            columnWidth: 0.5,
            disabled: true,
            emptyText: 'Others',
            hiddenLabel: 'Type of Suspicious Activities Others',
            hiddenAsterik: true,
            bind: {
                disabled: '{!(formInfo.sirTypeSuspiciousActivities == "Others")}',
                value: '{formInfo.sirTypeSuspiciousActivitiesOthers}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            labelWidth: 250,
            fieldLabel: 'Transaction Type',
            columnWidth: 1,
            key: 'SIR_TRANSACTION_TYPE',
            bind: {
                value: '{formInfo.sirTransactionType}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'datetime',
            labelWidth: 250,
            columnWidth: 0.5,
            fieldLabel: 'From Trans Date/Time',
            bind: {
                value: '{formInfo.sirTranDateFrom}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'datetime',
            labelWidth: 150,
            columnWidth: 0.5,
            fieldLabel: 'To Trans Date/Time',
            bind: {
                value: '{formInfo.sirTranDateTo}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'ctrpickup',
            fieldLabel: 'CTR Form No',
            allowBlank: true,
            labelWidth: 250,
            columnWidth: 0.5,
            bind: {
                value: '{formInfo.ctrFormId}',
                selection: '{ctrFormSelection}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'currencyfield',
            fieldLabel: 'Amount',
            value: 0.00,
            columnWidth: 0.5,
            labelWidth: 150,
            bind: {
                value: '{formInfo.sirAmount}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'label',
            text: 'Suspicious Incidence Report Narrative:',
            columnWidth: 1,
            margin: '0 0 10 0'
        }, {
            xtype: 'textarea',
            maxLength: 2000,
            columnWidth: 1,
            allowBlank: true,
            height: 120,
            bind: {
                value: '{formInfo.sirSuspiciousIncidence}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'combo',
            labelWidth: 355,
            columnWidth: 0.5,
            fieldLabel: 'Name search checked against internal database watchlist?',
            value: false,
            valueField: 'value',
            displayField: 'name',
            store: {
                type: 'store',
                data: [
                    { value: true, name: 'Yes' },
                    { value: false, name: 'No' }
                ]
            },
            bind: {
                value: '{formInfo.sirWatchlist}',
                readOnly: '{readonly}'
            }
        }, {
            fieldLabel: 'Staff ID',
            columnWidth: 0.2,
            labelWidth: 70,
            bind: {
                value: '{formInfo.sirStaffId}',
                readOnly: '{readonly}'
            }
        }, {
            xtype: 'x-component-lookup',
            columnWidth: 0.3,
            labelWidth: 115,
            key: 'AML_DEPARTMENT',
            fieldLabel: 'Staff Department',
            bind: {
                value: '{formInfo.sirStaffDepartment}',
                readOnly: '{readonly}'
            }
        },];
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
                formAttachmentBlock: {
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
                formAttachmentBlock: {
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
                formAttachmentBlock: {
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