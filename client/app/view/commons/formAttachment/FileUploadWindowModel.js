Ext.define('UI.view.common.formAttachment.FileUploadWindowModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.common-frmfileupload-fileuploadwindow',
    data: {
         action: 'ADD',
         item : null 
    },
    formulas:{
        submitBtText : function(get) { 
            var textLabel = 'Submit', action = get('action');
            if(action==='ADD'){
                textLabel = 'Add';
            }else if (action==='EDIT'){
                textLabel = 'Edit';
            } 
            return textLabel;
        }, 
        submitBtVisible: function(get){
            var action = get('action');
            return action !=='VIEW'
            
        }
    }    

});
