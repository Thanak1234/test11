Ext.define('UI.model.forms.Transaction', {
    extend: 'Ext.data.Model',
    idProperty: 'id',    
    fields: [
        { name: 'id', type: 'string' },
        { name: 'playerId', type: 'string' },
        { name: 'playerName', type: 'string' },
        { name: 'driverLicense', type: 'string' },
        { name: 'gercCardNo', type: 'string' },
        { name: 'idCard', type: 'string' },
        { name: 'passport', type: 'string' },
        { name: 'playerCountry', type: 'string' },
        { name: 'categoryName', type: 'string' },
        { name: 'totalAmount', type: 'float' },
        { name: 'noTrans', type: 'int' },
        { name: 'transType', type: 'string' },
        { name: 'frmIds', type: 'string' },
        { name: 'exclude', type: 'bool' }
    ]
});