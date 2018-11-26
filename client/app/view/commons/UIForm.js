Ext.define('UI.view.commons.UIForm', {
    extend: 'Ext.form.Panel',
    mixins: {
        authorization: 'UI.view.common.Authorization'
    },  
    requires: [
        'UI.view.commons.UIFormController',
        'UI.view.common.UIFormModel',
        'overrides.form.BasicForm'
    ],    
    layout: {
        type: 'vbox',   
        animation: 'slide',
        align: 'stretch'     
    },
    trackResetOnLoad:true,
    bodyPadding: 10,
    defaults: {
        scrollable: false,
        margin: '0 0 5 0',
        style: {
            borderTop: '2px solid white'
        }
    },
    initComponent: function() {
        var me = this;
        me.buildEvents();
        me.callParent(arguments);        
    },
    changingObserve: function() {
        var me = this,
        viewmodel = me.getViewModel();
        me.getForm().on('dirtychange', function(form, isDirty) {
            if(isDirty && !me.isPreview) {
                me.isChanged = true;
                if(me.title.indexOf('*') === -1) {
                    me.setTitle(Ext.String.format('{0} {1}', me.title, '*'));
                }               
             }
             else {
                me.isChanged = false;
             }
         });
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            beforerender: function (panel) {
                me.mixins.authorization.authorize(panel);
            },
            beforeclose: function (panel, eOpts) {
                if(me.isClosed) {
                    me.isClosed = true;
                    return true;
                }
                if(me.isChanged) {
                    Ext.MessageBox.show({
                        title: 'Confirmation',
                        msg: 'Some field(s) was changed, you should save before closing form. Do you want to close this form?',
                        buttons: Ext.MessageBox.YESNO,
                        scope: this,
                        icon: Ext.MessageBox.WARNING,
                        fn: function (bt) {
                            if (bt == 'yes') {
                                me.isClosed = true;   
                                panel.close();
                            }
                        }
                    });
                    return false;
                } else {
                    return true;
                }
            },
            afterrender: function(el) {                              
                if(Ext.isFunction(me.initData)){
                    me.initData();
                }
                // me.clearDirty();
                // me.changingObserve();  
            }
        };
    }
});