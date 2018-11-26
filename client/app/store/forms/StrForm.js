Ext.define('UI.store.forms.StrForm', {
    extend: 'Ext.data.Store',    
    alias: 'store.strForm',         
    model: 'UI.model.forms.StrForm',
    pageSize: 25,
    autoLoad: true,
    remoteSort:true,
    remoteFilter:true,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/strforms',
        reader: {
            type: 'json',
            rootProperty: 'records',
            totalProperty: 'totalRecords'
        }
    }
});