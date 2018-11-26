Ext.define('UI.model.commons.Navigation', {
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
        { name: 'id', type: 'string' },
        { name: 'parentId', type: 'string' },
        { name: 'text', type: 'string', mapping: 'name' },
        { name: 'routeId', type: 'string' },
        { name: 'leaf', type: 'bool' },
        { name: 'status', type: 'string' }        
    ]
});