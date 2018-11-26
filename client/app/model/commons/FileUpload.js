Ext.define('UI.model.common.FileUpload', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        { name: 'id', type: 'string' },
        { name: 'serial', type: 'string' },
        { name: 'name', type: 'string' },
        { name: 'description', type: 'string' },
        { name: 'fileName', type: 'string' },
        { name: 'isTemp', type: 'boolean', defaultValue: false },
        { name: 'uploadedDate', type: 'date' },
        { name: 'uploadedBy', type: 'string' }
    ]    
});
