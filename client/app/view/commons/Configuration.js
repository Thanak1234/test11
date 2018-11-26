Ext.define('Configuration', {
    statics: {
        baseUrl: 'http://localhost:9000/',
        token: getCookie('token')
    }
});