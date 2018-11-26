Ext.application({
    name: 'UI',

    extend: 'UI.Application',

    requires: [
        'UI.view.main.Main'
    ],
    defaultToken : 'dashboard',
});

Ext.Ajax.setWithCredentials(true);
Ext.Ajax.on({
    beforerequest: function(conn, options) {
        options.headers = options.headers || {};
        options.headers['X-Auth-Token'] = Configuration.token;
    },
    requestexception: function(conn, response, options) {
        
    }
});
