Ext.define('UI.view.commons.player.PlayerFieldSet', {
    extend: 'Ext.form.FieldSet',
    xtype: 'x-player-fieldset',
    title: 'Player Info',
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },
    bodyPadding: 5,
    stateless: true,
    initComponent: function() {
        var me = this;
        me.buildItems();       
        me.callParent(arguments);
    },
    buildItems: function() {
        var me = this;
        me.items = [{
            xtype: 'container',
            layout: 'column',
            scrollable: false,
            config: {
                playerId: 0
            },
            defaults: {                
                xtype: 'textfield',
                labelWidth: 155,
                columnWidth: 0.333,
                margin: '0 10 5 0',
                editable: false,
                allowBlank: true
            },
            items: [{
                xtype: 'playerPickup', 
                fieldLabel: 'Player',
                allowBlank: false,
                editable: true,
                bind: {
                    selection: '{selectedPlayer}',
                    value: '{formInfo.playerId}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'No Card',
                bind: {
                    value: '{selectedPlayer.playerCardNo}',
                    readOnly: '{readonly}'
                }
            }, {
                xtype: 'x-component-lookup',
                fieldLabel: 'Player With',
                allowBlank: false,
                editable: true,
                key: 'PLAYER_WITH',
                bind: {
                    value: '{formInfo.playerWith}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Driver\'s License',
                allowBlank: true,
                readOnly: true,
                bind: {
                    value: '{selectedPlayer.driverLicense}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'GERC Card No',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.gercCardNo}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'ID Card',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.idCard}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Passport',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.passport}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Others',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.others}',
                    readOnly: '{readonly}'
                }
            }, {
                xtype: 'datefield',
                fieldLabel: 'DOB',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.dob}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Passport Date Issue',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerDateIssue}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Passport Date Expire',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerDateExpired}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Player Name',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerName}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Also Known As',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerNickName}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Gender',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerGender}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Citizenship of Country',
                allowBlank: true,
                bind: {
                    value: '{selectedPlayer.playerCountry}',
                    readOnly: '{readonly}'
                }
            }, {
                fieldLabel: 'Phone No',
                allowBlank: true,
                columnWidth: 0.999,
                bind: {
                    value: '{selectedPlayer.playerPhone}',
                    readOnly: '{readonly}'
                }
            }, {
                xtype: 'textarea',
                fieldLabel: 'Address',
                allowBlank: true,
                columnWidth: 0.999,
                maxLength: 500,
                bind: {
                    value: '{selectedPlayer.playerAddress}',
                    readOnly: '{readonly}'
                }
            }]
        }];
    }
});