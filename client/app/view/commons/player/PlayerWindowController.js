Ext.define('UI.view.commons.player.PlayerWindowController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.player-window',
    onCloseClicked: function(el) {
        var me = this,
        view = me.getView();

        view.close();
    }
});