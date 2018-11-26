Ext.define('UI.store.commons.Navigation', {
    extend: 'Ext.data.TreeStore',    
    alias: 'store.navigation',
    model: 'UI.model.commons.Navigation',
    autoLoad: true,
    parentIdProperty: 'parentId',
    proxy: {
    	type: 'rest',        
    	url: Configuration.baseUrl + 'api/v1',
        reader: {
            type: 'json'
        }
    },
    root: {
        text: 'AML',
        id: 'menus',
        expanded: true
    }
});