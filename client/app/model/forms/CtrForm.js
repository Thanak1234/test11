Ext.define('UI.model.forms.CtrForm', {
    extend: 'Ext.data.Model',
    idProperty: 'id',    
    fields: [
        { name: 'id', type: 'string' },
        { name: 'formNo', type: 'string' },
        { name: 'typeOf', type: 'string' },
        { name: 'transaction', type: 'string' },
        { name: 'location', type: 'string' },
        { name: 'area', type: 'string' },
        { name: 'playerId', type: 'string' },
        { name: 'playerName', type: 'string' },
        { name: 'playerNationality', type: 'string' },
        { name: 'playerCardType', type: 'string' },
        { name: 'driverLicense', type: 'string' },
        { name: 'gercCardNo', type: 'string' },
        { name: 'idCard', type: 'string' },
        { name: 'passport', type: 'string' },
        { name: 'other', type: 'string' },
        { name: 'noCard', type: 'string' },
        { name: 'dob', type: 'date' },
        { name: 'tranDate', type: 'date' },
        { name: 'submission', type: 'bool' },
        { name: 'void', type: 'bool' },
        { name: 'remark', type: 'string' }        
    ]
});