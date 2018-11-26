Ext.define("UI.view.commons.CTRPickup.CTRPickup", {
    extend: "Ext.form.field.ComboBox",
    xtype: 'ctrpickup',
    controller: "ctrpickup",
    store: {
        type: 'ctrForm'
    },
    displayField: 'formNo',
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
                '<h3><span>{formNo}</span> ({docNo})</h3>',
                '<span>Type: {typeOf}, Transaction: {tran}</span>',
            '</a>'
        ]
    },
    listeners: {
        beforequery: 'onCTRPickupChanged',
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
        }
    },
    init: function(form) {
        var extraData = form.extraData,
        state = extraData.state,
        me = this;

        if(state == 'view' || state == 'edit') {
            var record = extraData.record,
                id = record.get('ctrFormId');
            Ext.Ajax.request({
                url: Configuration.baseUrl + 'api/v1/ctrform/' + id,
                method: 'GET',
                success: function(res, req) {
                    var data = JSON.parse(res.responseText);
                    var model = Ext.create('UI.model.forms.CtrForm', data);
                    me.getStore().setRecords([model]);
                    me.setValue(id);       
                    me.getTrigger('clear').hide();
                    me.fireEvent('change', me);                
                }
            });
        }
    },
    initComponent: function () {
        var me = this;
        this.pickerId = this.getId() + "_picker";
        this.callParent(arguments);
    },   
    onClearClick: function () {
        var me = this;

        if (me.disabled) {
            return;
        }

        me.clearValue();
        me.setRawValue(null);
        me.value = null;
        me.afterClear(me);
        me.updateLayout();
        me.fireEvent('clear', me);
        me.fireEvent('change', me);
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
        me.getTrigger('clear').hide();
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
