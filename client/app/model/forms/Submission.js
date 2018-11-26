Ext.define('UI.model.forms.Submission', {
    extend: 'Ext.data.Model',
    idProperty: 'id',    
    fields: [
        { name: 'id', type: 'string' },
        { name: 'submissionCode', type: 'string' },
        { name: 'formType', type: 'string' },
        { name: 'transDate', type: 'date' },
        { name: 'multiAmount', type: 'float' },
        { name: 'singleAmount', type: 'float' },
        { name: 'actionType', type: 'string' },
        { name: 'status', type: 'string' },
        { name: 'saveDate', type: 'string' },
        { name: 'saveBy', type: 'string' },
        { name: 'sentDate', type: 'date' },
        { name: 'sentBy', type: 'string' },
        { name: 'comment', type: 'string' },
        { name: 'voidDate', type: 'date' },
        { name: 'voidBy', type: 'string' }
    ]
});