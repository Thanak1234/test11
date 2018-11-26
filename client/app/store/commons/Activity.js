Ext.define('UI.store.commons.Activity', {
    extend: 'Ext.data.Store',    
    alias: 'store.activity',         
    model: 'UI.model.common.Activity',
    autoLoad: false,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/activities',
        reader: {
            type: 'json'
        }
    }
});