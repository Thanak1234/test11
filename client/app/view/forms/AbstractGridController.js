Ext.define('UI.view.form.AbstractGridController', {
    extend: 'UI.view.commons.UIFormController',
    alias: 'controller.abstract-grid',
    onRefreshClicked: function(el) {
        var me = this,
        view = me.getView(),
        store = view.getStore();
        store.loadPage(1);
    },
    onRowDoubleClicked: function(el) {
        if(me.onPreviewClicked) me.onPreviewClicked(el);
    },
    onSearchClearClick: function(el) {
        var me = this,
        viewmodel = me.getViewModel();
        viewmodel.set('gridInfo.searchText', '');
        me.onSearchClicked(el);
    },
    onSearchClicked: function(el) {
        var me = this,
        view = me.getView(),
        viewmodel = me.getViewModel(),
        store = view.getStore(),
        searchText = viewmodel.get('gridInfo.searchText');
        store.getProxy().extraParams = {
            query: searchText
        }
        store.load();
    },
    onSearchTextPress: function(el, e) {
        var me = this;        
        if (e.keyCode == e.ENTER) {
            me.onSearchClicked();
            e.stopEvent();
        }
    },
    onClearFilters: function(el) {
        var me = this,
        view = me.getView();
        view.filters.clearFilters();
    },
    onExportPDFClicked: function(el) {
        var me = this,
        view = me.getView();

        view.mask('PDF is generating...');
        Ext.Ajax.request({
            url: me.actionUrl + '/exports/pdf',
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            binary: true,     
            success: function(res, req) {
                me.createFileDownload(res, 'pdf');
                view.unmask();
            },                                    
            failure: function(res) { 
                console.log('error = ', res);
                view.unmask();
            }
        });
    },
    onExportExcelClicked: function(el) {
        var me = this,
        view = me.getView();
        view.mask('Excel is generating...');
        Ext.Ajax.request({
            url: me.actionUrl + '/exports/excel',
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            binary: true,  
            success: function(res, req) {
                me.createFileDownload(res, 'excel');
                view.unmask();
            },                                    
            failure: function(res) { 
                console.log('error = ', res);
                view.unmask();
            }
        });
    },
    createFileDownload: function(res, exportType) {
        var byteArray = new Uint8Array(res.responseBytes);
        var blob = new Blob([byteArray], {type: "application/octet-stream"});
        var link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);        
        var fileName = this.getFormattedTime();
        if(exportType == 'pdf')
            fileName = fileName + '.pdf';
        else
            fileName = fileName + '.xls';        
        link.download = fileName;
        link.click();
    },
    getFormattedTime: function() {
        var today = new Date();
        var y = today.getFullYear();
        var m = today.getMonth();
        var d = today.getDate();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        return y + '-' + m + '-' + d + '-' + h + '-' + m + '-' + s;
    }
});