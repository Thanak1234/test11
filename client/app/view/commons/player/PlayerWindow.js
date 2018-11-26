Ext.define('UI.view.commons.player.PlayerWindow', {
    extend: 'Ext.window.Window',
    xtype: 'player-window',
    controller: 'player-window',
    title: 'Add Player',
    bodyPadding: 10,
    modal: true,
    maximizable: true,
    width: 1000,
    scrollable: true,
    layout: {
        type: 'vbox',   
        animation: 'slide',
        align: 'stretch'     
    },
    viewModel: {
        type: 'player-window'
    },
    style: {
        borderTop: '2px solid white'
    },
    initComponent: function() {
        var me = this;
        me.buildItems();
        me.buildButtons();
        me.callParent(arguments);
    },
    buildButtons: function() {
        var me = this;
        me.buttons = [{
            text: 'Close',
            iconCls: 'fa fa-close',
            handler: 'onCloseClicked'
        }, {
            text: 'Save',
            iconCls: 'fa fa-save',
            handler: 'onSaveClicked'
        }];
    },
    buildItems: function() {
        var me = this;
        me.items = [{
            xtype: 'fieldset',
            title: 'Player Info',
            layout: 'anchor',
            items: [{
                xtype: 'container',
                layout: 'column',
                anchor: '100%',
                bodyPadding: 5,
                defaults: {
                    xtype: 'textfield',
                    columnWidth: 0.5,
                    margin: '0 10 5 0',
                    allowBlank: false,
                    labelWidth: 150
                },
                items: [{
                    xtype: 'combo',
                    fieldLabel: 'No Card',
                    store: ['No', 'Yes'],
                    value: 'No'
                }, {
                    fieldLabel: 'Driver\'s License'
                }, {
                    fieldLabel: 'GERC Card No'
                }, {
                    fieldLabel: 'ID Card'
                }, {
                    fieldLabel: 'Passport'
                }, {
                    fieldLabel: 'Others'
                }, {
                    xtype: 'datefield',
                    fieldLabel: 'Date of Birth'
                }, {
                    xtype: 'datefield',
                    fieldLabel: 'Passport Date Issue'
                }, {
                    xtype: 'datefield',
                    fieldLabel: 'Passport Date Expire'
                }, {
                    fieldLabel: 'Player Name'
                }, {
                    fieldLabel: 'Also Known As'
                }, {
                    xtype: 'combo',
                    fieldLabel: 'Gender',
                    store: ['Male', 'Female']
                }, {
                    fieldLabel: 'Phone No'
                }, {
                    xtype: 'combo',
                    fieldLabel: 'Country',
                    store: []
                }, {
                    xtype: 'combo',
                    fieldLabel: 'Citizenship Of Country',
                    store: []
                }, {
                    xtype: 'textarea',
                    fieldLabel: 'Address',
                    maxLength: 500,
                    columnWidth: 1
                }, {
                    xtype: 'checkbox',
                    boxLabel: 'Add To ECDD',
                    columnWidth: 1
                }]
            }]
        }, {
            xtype: 'form-fileupload',
            title: 'File Attachment (Passport, Photos,...)',
            border: true
        }];
    }
});