Ext.define('UI.model.common.FrmFileUpload', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        { name: 'id', type: 'string' },
        { name: 'serial', type: 'string' },
        { name: 'name', type: 'string' },
        { name: 'docType', type: 'string' },
        { name: 'identifier', type: 'string' },
        { name: 'docDate', type: 'date' },
        { name: 'preparer', type: 'string' },
        { name: 'fileName', type: 'string' },
        { name: 'isTemp', type: 'boolean', defaultValue: false },
        { name: 'uploadedDate', type: 'date', format: 'm-d-Y' },
        { name: 'uploadedBy', type: 'string' }
    ]    
});
