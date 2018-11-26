Ext.define('RouteInfo', {
    statics: {
        title: '',
        cls: '',
        extraData: undefined,
        reset: function() {
            this.title = '';
            this.cls = '';
            this.extraData = undefined;
        }
    }
});