Ext.define('UI.store.forms.Submission', {
    extend: 'Ext.data.Store',    
    alias: 'store.submission',         
    model: 'UI.model.forms.Submission',
    pageSize: 10,
    autoLoad: true,
    remoteFilter: true,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/submissions',
        reader: {
            type: 'json',
            rootProperty: 'records',
            totalProperty: 'totalRecords'
        }
    }
});