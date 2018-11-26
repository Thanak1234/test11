Ext.define('UI.store.commons.FileUpload', {
    extend: 'Ext.data.Store',    
    alias: 'store.fileUpload',         
    model: 'UI.model.common.FileUpload',
    autoLoad: false,
    proxy: {
        type: 'rest',
        url: Configuration.baseUrl + 'api/v1/attachments',
        reader: {
            type: 'json'
        }
    }
});