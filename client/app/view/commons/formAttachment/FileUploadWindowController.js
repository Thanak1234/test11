Ext.define('UI.view.common.formAttachment.FileUploadWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.common-frmfileupload-fileuploadwindow',
    onCloseClicked: function() {
        var me = this;
        me.getView().close();
    },
    onUploadClicked: function () {
        var me = this,
            view = me.getView(),
            item = view.getViewModel().get('item'),
            form = view.getReferences().form,
            mainView = view.mainView.getView();
            console.log('item = ', item);
        if (form.isValid()) {
            item.serial = mainView.guid; 
            var formData = new FormData();
            var filefield = view.down('fileuploadfield[id="fileName"]');
            var file = filefield.getEl().down('input[type=file]').dom.files[0];            
            formData.append("uploadInfo", JSON.stringify(item));
            formData.append("files", file);
            me.uploadFile(form.url, formData, function(req) {
                var attachment = Ext.JSON.decode(req.responseText);
                attachment.isTemp = true;
                view.cbFn(Ext.create('UI.model.common.FrmFileUpload', attachment));
                form.reset();
            });
        }
    },
    uploadFile: function(url, formData, callback) {
        var me = this,
            view = me.getView();
        Ext.MessageBox.show({
            msg: 'Uploading your document, please wait...',
            progressText: 'Uploading...',
            width: 350,
            wait: {
                interval: 100
            }
        });
        var request = new XMLHttpRequest();        
        request.onload = function(event) {
            if (request.status == 200) {                
                callback(request);                
            } else {
                console.log('upload file error = ', request.responseText);
            }
            Ext.MessageBox.hide();          
        };
        request.open("POST", url);
        request.setRequestHeader('X-Auth-Token', Configuration.token);
        request.send(formData);
    }     
});
