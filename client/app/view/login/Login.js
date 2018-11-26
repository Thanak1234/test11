Ext.define('UI.view.login.Login', {
    extend: 'Ext.window.Window',
    
    requires: [
        'UI.view.login.LoginController',
        'UI.view.login.LoginModel',
        'Ext.form.Panel',
        'Ext.button.Button',
        'Ext.form.field.Text',
        'Ext.form.field.ComboBox'
    ],
    
    viewModel: 'login',
    
    controller: 'login',
    bodyPadding: 10,
    title: 'Login',
    closable: false,
    modal: true,
    iconCls: 'fa fa-key', 
    resizable: false,   
    items: {
        xtype: 'form',
        reference: 'form',
        defaults: {
            width: 400
        },
        items: [{
            xtype: 'lookupField',
            name: 'username',
            url: 'auth/users',
            fieldLabel: 'Username',
            allowBlank: false,
            enableKeyEvents: true,
            displayField: 'fullName',
            valueField: 'loginName',
            listeners: {
                specialKey: 'onSpecialKey'
            }
        }, {
            xtype: 'textfield',
            name: 'password',
            inputType: 'password',
            fieldLabel: 'Password',
            allowBlank: false,
            enableKeyEvents: true,
            cls: 'password',
            listeners: {
                specialKey: 'onSpecialKey'
            }
        }, {
            xtype: 'displayfield',
            hideEmptyLabel: false,
            value: 'Enter any non-blank password',
            cls: 'hint'
        }]
    },

    buttons: [{
        text: 'Login',
        listeners: {
            click: 'onLoginClick'
        }
    }]
});
