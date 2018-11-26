Ext.define('UI.store.forms.CtrForm', {
    extend: 'Ext.data.Store',    
    alias: 'store.ctrForm',         
    model: 'UI.model.forms.CtrForm',
    pageSize: 25,
    autoLoad: false,
    remoteSort:true,
    remoteFilter:true,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/ctrforms',
        reader: {
            type: 'json',
            rootProperty: 'records',
            totalProperty: 'totalRecords'
        }
    }
});