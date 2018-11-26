Ext.define("UI.view.commons.player.PlayerPickup", {
    extend: "Ext.form.field.ComboBox",
    xtype: 'playerPickup',
    controller: "playerpickup",
    store: {
        type: 'player'
    },
    displayField: 'playerName',
    valueField  : 'id',
    typeAhead   : true,
    employeeEditable: false,
    changeOnly: false,
    queryMode: 'remote',
    minChars: 2,
    pageSize: 20,
    forceSelection: true,
    listConfig  : {
        minWidth: 400,
        resizable: true,
        loadingText: 'Searching...',
        emptyText: 'No matching posts found.',
        itemSelector: '.search-item',

        // Custom rendering template for each item
        itemTpl: [
            '<a class="tpl-list-employee">',
                '<h3><span>{playerName}</span> ({passport})</h3>',
                '<span>GERC: {gercCardNo}, Country: {playerCountry}</span>',
            '</a>'
        ]
    },
    listeners: {
        beforequery: 'onPlayerPickupChanged',
        afterrender: function (combo) {
            var form = combo.up('form');
            combo.init(form);
            combo.getTrigger('clear').hide();            
            combo.updateLayout();
        }
    },
    triggers: {
        clear: {
            weight: 1,
            cls: Ext.baseCSSPrefix + 'form-clear-trigger',
            hidden: true,
            handler: 'onClearClick',
            scope: 'this'
        },
        add: {
            weight: 3,
            cls :'fas fa-user-plus',
            scope: 'this',
            handler: 'onAddClick',
            hidden: false
        }
    },
    init: function(form) {
        var extraData = form.extraData,
        state = extraData.state,
        me = this;

        if(state == 'view' || state == 'edit') {
            var record = extraData.record,
                id = record.get('playerId');
            Ext.Ajax.request({
                url: Configuration.baseUrl + 'api/v1/player/' + id,
                method: 'GET',
                success: function(res, req) {
                    var playerSelection = JSON.parse(res.responseText);
                    var model = Ext.create('UI.model.commons.Player', playerSelection);
                    me.getStore().setRecords([model]);
                    me.setValue(id);
                    var trigger = me.getTrigger('clear');
                    trigger.hide();
                }
            });
        }
    },
    initComponent: function () {
        var me = this;
        this.pickerId = this.getId() + "_picker";
        this.callParent(arguments);
    },
    onAddClick: function(el, eOpts) {
        var win = Ext.create('UI.view.commons.player.PlayerWindow', {

        });

        win.show(el);
    },
    onClearClick: function () {
        var me = this;

        if (me.disabled) {
            return;
        }

        me.clearValue();
        me.setRawValue(null);
        me.getTrigger('clear').hide();
        me.getTrigger('add').show();
        me.value = null;
        me.afterClear(me);
        me.updateLayout();
        me.fireEvent('clear', me);
    },
    afterClear: function(combo){
        
    },
    /* override extjs combo method */
    updateValue: function () {
        var me = this,
            selectedRecords = me.valueCollection.getRange();

        var data = selectedRecords[0] ? selectedRecords[0].data : null;

        if (selectedRecords.length > 0) {
            me.getTrigger('clear').show();
            // me.getTrigger('add').hide();
            me.updateLayout();
            me.callParent();
        }
    },
    setReadOnly: function (value) {
        var me = this,
            old = me.readOnly;

        me.callParent(arguments);
        if (value != old) {
            this.readOnly = value;
            me.updateLayout();
        }

        if (this.readOnly || this.changeOnly) {
            me.getTrigger('add').hide();
        }
        this.getTrigger('clear').hide();
    },
    assertValue: function () {
        var me = this,
            value = me.getRawValue(),
            displayValue = me.getDisplayValue(),
            lastRecords = me.lastSelectedRecords,
            rec;

        if (me.forceSelection) {
            if (me.multiSelect) {
                if (value !== displayValue) {
                    me.setRawValue(displayValue);
                }
            } else {
                rec = me.findRecordByDisplay(value);
                if (rec) {
                    if (me.getDisplayValue([me.getRecordDisplayData(rec)]) !== displayValue) {
                        me.select(rec, true);
                    }
                } else if (lastRecords && (!me.allowBlank || me.rawValue)) {
                } else {
                    if (lastRecords) {
                        delete me.lastSelectedRecords;
                    }
                    me.setRawValue('');
                }
            }
        }
        me.collapse();
    }
});
