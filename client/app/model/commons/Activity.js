Ext.define('UI.model.common.Activity', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        { name: 'id', type: 'string' },
        { name: 'formId', type: 'string' },
        { name: 'action', type: 'string' },
        { name: 'comment', type: 'string' },
        { name: 'createdBy', type: 'string' },
        { name: 'createdDate', type: 'date'}
    ]
});