/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('UI.view.main.MainController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.main',
    listen: {
        controller: {
            '#': {
                unmatchedroute: 'onRouteChange'
            }
        }
    },
    routes: {
        ':node': {
            action: 'onRouteChange',
            before: function (id, action) {
                var me = this;
                me.beforeRouteChange(id, action);
            }
        },
        onProfileView: 'onProfileView',
        onLogout: 'onLogout'
    },
    onSignOutClicked: function(el) {
        deleteCookie('token');
        window.location.reload();
    },
    onNavigationTreeSelectionChange: function (tree, record) {
        if (record) {
            RouteInfo.title = record.get('text');
            RouteInfo.cls = record.get('formCls');
            RouteInfo.routeId = record.get("routeId");
            this.redirectTo(RouteInfo.routeId);
        }
    },
    beforeRouteChange: function (id, action) {
        var me = this;        
        action.resume();
        return true;
    },
    onContentTabSelectionChange: function (tabPanel, panel, oldCard, eOpts) {
        var refs = this.getReferences(),
            navigationList = refs.navigation,
            store = navigationList.getStore(),
            hashTag = panel.routeId || null,
            node = hashTag ? store.findNode('routeId', hashTag) : null;
        if (node) {
            navigationList.setSelection(node);
        }  
        this.redirectTo(hashTag);
    },
    onRouteChange: function (id) {        
        var me = this,
        refs = me.getReferences(),
        contentPanel = refs.contentPanel,
        mainLayout = contentPanel.getLayout(),
        viewmodel = this.getView().getViewModel(),
        selection = viewmodel.get('navigation.selection');
        if(id === 'dashboard') {
            me.addTab(function() {
                var form = Ext.create('UI.view.forms.dashboard.Dashboard', {
                    closable: false,
                    routeId: 'dashboard'
                });
                return form;                
            }, id);
        } else {
            if(RouteInfo && RouteInfo.cls) {
                me.addTab(function() {
                    var form = Ext.create(RouteInfo.cls, {
                        title: RouteInfo.title,
                        closable: true,
                        extraData: RouteInfo.extraData
                    });
                    return form;                
                }, id);  
                RouteInfo.reset();          
            }
        }     
    },
    addTab: function (createFormFn, viewId) {
        var me = this,
            refs = me.getReferences(),
            contentPanel = refs.contentPanel,
            mainLayout = contentPanel.getLayout(),
            viewModel = me.getViewModel(),
            existingItem = contentPanel.child('component[routeId=' + viewId + ']');

        Ext.getBody().mask("Data Processing...");
        
        if (!existingItem) {
            var form = Ext.apply(createFormFn(), {
                routeId: viewId
            });
            var activeTab = contentPanel.add(form); 
            contentPanel.setActiveItem(activeTab);
        } else {
            contentPanel.setActiveItem(existingItem);            
        }
        Ext.getBody().unmask();
    }
});
