Ext.define('UI.store.commons.FrmFileUpload', {
    extend: 'Ext.data.Store',    
    alias: 'store.frmfileUpload',         
    model: 'UI.model.common.FrmFileUpload',
    autoLoad: false,
    proxy: {
        type: 'rest',
        url: Configuration.baseUrl + 'api/v1/frmattachemnt/attachments',
        reader: {
            type: 'json'
        }
    }
});