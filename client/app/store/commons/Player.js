Ext.define('UI.store.commons.Player', {
    extend: 'Ext.data.Store',    
    alias: 'store.player',         
    model: 'UI.model.commons.Player',
    pageSize: 20,
    autoLoad: false,
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1/players',
        reader: {
            type: 'json',
            rootProperty: 'records',
            totalProperty: 'totalRecords'
        }
    }
});