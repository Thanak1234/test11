Ext.define("UI.view.forms.submission.SubmissionCriteria", {
    extend: "UI.view.commons.AbstractWindow",
    xtype: 'forms-submission-criteria',
    controller: 'forms-submission-criteria',
    viewModel: 'forms-submission-criteria',
    requires: [
        'Ext.layout.container.Card'
    ],    
    title: 'New Submission',
    modal: true,
    layout: 'card',
    width: 1010,
    height: 600,
    bodyPadding: 5,    
    defaults: {
        border:false
    },
    fbar: [{
            itemId: 'card-prev',
            text: '&laquo; Previous',
            handler: 'onPreviousClicked',
            disabled: true
        }, {
            itemId: 'card-next',
            text: 'Next &raquo;',
            handler: 'onNextClicked'
        }, {
            itemId: 'card-save',
            text: 'Save',
            handler: 'onSaveClicked',
            disabled: true
        }
    ],
    items: [
        {
            xtype: 'form',
            id: 'card-0',
            layout: 'column',
            title: 'Submission Criteria',
            reference: 'criteriaForm',
            defaults: {
                xtype: 'currencyfield',
                columnWidth: 0.5,
                allowBlank: false,
                labelWidth: 180,
                margin: 5

            },
            items: [{
                xtype: 'combo',
                fieldLabel: 'Form Type',
                columnWidth: 1,
                store: ['CTR', 'STR'],
                bind: {
                    value: '{criteria.formType}'
                },
                listeners: {
                    change: 'onFormTypeChanged'
                }
            }, {
                xtype: 'datefield',
                fieldLabel: 'Trans Date',
                columnWidth: 1,
                bind: {
                    value: '{criteria.transDate}'
                }
            }, {
                fieldLabel: 'Multi-Trans Amount From',
                disabled: true,
                bind: {
                    value: '{criteria.multiAmount}',
                    disabled: '{disableMultiAmount}'
                }
            }, {
                fieldLabel: 'Single-Trans Amount From',
                disabled: true,
                bind: {
                    value: '{criteria.singleAmount}',
                    disabled: '{disableSingleAmount}'
                }
            }, {
                xtype: 'lookupField',
                allowBlank: true,
                multiSelect: true,
                url: 'api/v1/countries',
                fieldLabel: 'Exclusive Country',
                columnWidth: 1,
                valueField: 'name',
                displayField: 'name',
                maxLength: 1000,
                bind: {
                    value: '{criteria.exclusiveCountry}'
                }
            }]
        },
        {
            xtype: 'form',
            id: 'card-1',
            layout: 'border',
            defaults: {
                border: true
            },
            items: [{
                xtype: 'submission-transaction',
                region: 'north',
                title: 'Multiple transaction',
                reference: 'multiTransactionStepTwo',
                height: '50%',
                addExclude: true
            }, {
                xtype: 'submission-transaction',
                region: 'center',
                title: 'Single transaction',
                reference: 'singleTransactionStepTwo',
                height: '50%',
                addExclude: true
            }]
        },
        {
            id: 'card-2',
            layout: 'border',
            defaults: {
                border: true
            },
            items: [{
                xtype: 'submission-transaction',
                region: 'north',
                title: 'Multiple transaction',
                reference: 'multiTransactionStepThree',
                height: '50%'
            }, {
                xtype: 'submission-transaction',
                region: 'center',
                title: 'Single transaction',
                reference: 'singleTransactionStepThree',
                height: '50%'
            }]
        }
    ]
});