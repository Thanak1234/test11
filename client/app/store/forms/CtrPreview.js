Ext.define('UI.store.forms.CtrPreview', {
    extend: 'Ext.data.Store',    
    alias: 'store.ctrPreview',         
    model: 'UI.model.forms.CtrForm',
    pageSize: 100,
    autoLoad: false,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/ctrforms/transactions/preivew'
    }
});