/**
 * This View Controller is associated with the Login view.
 */
Ext.define('UI.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    
    loginText: 'Logging in...',

    onSpecialKey: function(field, e) {
        if (e.getKey() === e.ENTER) {
            this.doLogin();
        }
    },
    
    onLoginClick: function() {
        this.doLogin();
    },
    
    doLogin: function() {
        var form = this.lookupReference('form');
        
        if (form.isValid()) {
            this.getView().mask(this.loginText);

            if (!this.loginManager) {
                this.loginManager = new UI.LoginManager({
                    session: this.getView().getSession()
                });
            }

            this.loginManager.login({
                data: form.getValues(),
                scope: this,
                success: 'onLoginSuccess',
                failure: 'onLoginFailure'
            });
        }
    },
    
    onLoginFailure: function() {
        var me = this;   
        me.getView().unmask();             
        Ext.MessageBox.show({
            title: 'Password validation',
            msg: 'User Name or Password is not correct.',
            buttons: Ext.MessageBox.OK,
            fn: function(btn) {
                if(btn == 'ok') {
                    var form = me.getView().down('form');
                    form.reset();
                }
            },
            scope: this,
            icon: Ext.MessageBox.WARNING
        });
    },

    onLoginSuccess: function(user) {
        this.fireViewEvent('login', this.getView(), {}, {}, this.loginManager);
    }
});
