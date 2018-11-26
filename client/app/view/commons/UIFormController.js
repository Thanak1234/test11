Ext.define('UI.view.commons.UIFormController', {
    extend: 'Ext.app.ViewController',
    showToast: function(s) {
        Ext.toast({
            html: s,
            closable: false,
            align: 't',
            slideInDuration: 400
        });
    },
    routeTo: function(title, routeId, cls, extraData=undefined) {
        RouteInfo.routeId = routeId;
        RouteInfo.title = title;
        RouteInfo.cls = cls;
        RouteInfo.extraData = extraData;
        this.redirectTo(routeId);
    }
});