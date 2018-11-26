/**
 * This global controller manages the login view and ensures that view is created when
 * the application is launched. Once login is complete we then create the main view.
 */
Ext.define('UI.controller.Root', {
    extend: 'Ext.app.Controller',
    
    requires: [
        'UI.view.login.Login',
        'UI.LoginManager',
        'UI.view.main.Main'
    ],
    
    loadingText: 'Loading...',
    
    onLaunch: function () {
        if (Ext.isIE8) {
            Ext.Msg.alert('Not Supported', 'This example is not supported on Internet Explorer 8. Please use a different browser.');
            return;
        }

        var token = getCookie('token');

        if(token && token != '') {
            this.showUI();
        } else {
            this.session = new Ext.data.Session({
                autoDestroy: false
            });            
            this.login = new UI.view.login.Login({
                session: this.session,
                autoShow: true,
                listeners: {
                    scope: this,
                    login: 'onLogin'
                }
            });
        }
    },

    /**
     * Called when the login controller fires the "login" event.
     *
     * @param loginController
     * @param user
     * @param organization
     * @param loginManager
     */
    onLogin: function (loginController, user, organization, loginManager) {
        this.login.destroy();
        this.loginManager = loginManager;
        this.organization = organization;
        this.user = user;
        this.showUI();
    },
    
    showUI: function() {
        this.viewport = new UI.view.main.Main({
            session: this.session,
            viewModel: {
                data: {
                    currentOrg: this.organization,
                    currentUser: this.user
                }
            }
        });
    },
    
    getSession: function() {
        return this.session;
    }
});
