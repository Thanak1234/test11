/* global Ext */
Ext.define('UI.view.common.formAttachment.FileUploadController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.common-frmfileupload-fileupload',    
    config: {
        control: {
            '*': {
                onDataClear: 'clearData'
            }      
        }
    },
    clearData: function () {
        this.getView().getStore().removeAll();
        this.getView().getStore().clearData();
    },
    viewFile: function(grid, record, tr, rowIndex, e, eOpts ) {          
        var me = this;        
        var field = record.getData();
		if(field){
			field.downloadLink = '<span style="margin-right:50px;">Download:</span><a href="' + me.getDownloadLink(grid, record) + '">' + field.fileName + '</a>';
			field.uploadDate = new Date(field.uploadDate);
		}
        var window = Ext.create('UI.view.common.formAttachment.FileUploadWindow', 
         {mainView: me,
         viewModel: {
            data: {
                action: 'VIEW',
                item: field
            }
        }, 
        lauchFrom: me.getReferences().editBtn,
        cbFn: function(record) {              
            me.getView().getStore('frmfileUpload').add(record);            
        }});
        
        window.show(me.getReferences().editBtn);
    },
    
    addNewFile: function(){
        var me=this,
            window = Ext.create('UI.view.common.formAttachment.FileUploadWindow',
                {
                    mainView: me,
                    viewModel: {
                        data: {
                            action: 'ADD'
                        }
                    }, 
                    width: 700,
                    lauchFrom: me.getReferences().addBt,
                    cbFn: function (rec) {
                        rec.set('readOnly', false);
                        console.log(rec);
                        me.getView().getStore().add(rec);                  
                    }
            });
        
        window.show(me.getReferences().addBt);
    },
    
    removeFile: function(grid, rowIndex, colIndex) {
        var me = this, store = grid.getStore(), rec = store.getAt(rowIndex);
        if (rec.get('readOnly')) {
            Ext.MessageBox.show({
                title: 'Error',
                msg: 'This record cannot be removed. Since it was uploaded by another activity.',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
            return;
        }
        Ext.MessageBox.show({
            title: 'Alert',
            msg: 'Are you sure to delete this file?',
            buttons: Ext.MessageBox.YESNO,
            scope: this,
            fn: function(bt){
                if(bt==='yes') {
                   store.remove(rec);
                }
            } 
        });
    },
     
    dowloadFile: function(grid, rowIndex, colIndex){
        var me = this;
        var record = grid.getStore().getAt(rowIndex);

        Ext.core.DomHelper.append(document.body, {
            tag: 'iframe',
            id: 'attachment_' + record.get('id'),
            frameBorder: 0,
            width: 0,
            height: 0,
            src: me.getDownloadLink(grid, record)
        });
    },
    getDownloadLink: function (grid, record, view) {
        var id = record.get('id');
        var requestHeaderId = record.get('requestHeaderId');
        var isTemp = record.get('isTemp');
        var attachmentId = [id, requestHeaderId].join('_');
        if (isTemp) {
            attachmentId = id;
        }
        return Configuration.baseUrl + 'api/forms/attachments/download?attachmentId=' + attachmentId + '&isTemp=' + isTemp;
    }
});
