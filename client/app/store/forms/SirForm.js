Ext.define('UI.store.forms.SirForm', {
    extend: 'Ext.data.Store',    
    alias: 'store.sirForm',         
    model: 'UI.model.forms.SirForm',
    pageSize: 25,
    autoLoad: true,
    remoteSort:true,
    remoteFilter:true,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/sirforms',
        reader: {
            type: 'json',
            rootProperty: 'records',
            totalProperty: 'totalRecords'
        }
    }
});