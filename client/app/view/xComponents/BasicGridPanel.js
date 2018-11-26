Ext.define('UI.view.xComponents.BasicGridPanel', {
    extend: 'Ext.grid.Panel',
    xtype: 'x-basic-grid',
    closable: true,
    mixins: {
        authorization: 'UI.view.common.Authorization'
    },
    isReport: false,
    isDialog: false,
    rest: Configuration.baseUrl + '',
    config: {
        name: 'gridpanel',
        crudWinSize: {
            width: 1100,
            minHeight: 400
        },
        require: false
    },
    bind: {
        selection: '{selectedRecord}'
    },
    dataDefault: {

    },
    viewModel: {
        data: {
            selectedRecord: null
        }
    },
    keyMapEnabled: true,  
    keyMap: {
        'CmdOrCtrl+Q': {
            handler: 'onNewKeyMapPressed',
            scope: 'this'
        },
        'CmdOrCtrl+W': {
            handler: 'onModifiedKeyMapPressed',
            scope: 'this'
        },
        'CmdOrCtrl+E': {
            handler: 'onPreviewKeyMapPressed',
            scope: 'this'
        },
        'CmdOrCtrl+R': {
            handler: 'onRefreshKeyMapPressed',
            scope: 'this'
        }
    },  
    initComponent: function() {
        var me = this;
        me.buildColumns();
        me.buildButtons();
        me.buildItems();
        me.buildEvents();  
        me.buildStore();
        me.buildKeyMap();
        me.callParent(arguments);  
    },
    buildKeyMap: function() {
        var me = this;        
    },
    onNewKeyMapPressed: function(e) {
        this.onNewClicked(this.down('button[text="New"]'));
    },
    onModifiedKeyMapPressed: function(e) {
        this.onModifiedClicked(this.down('button[text="Modified"]'));
    },
    onPreviewKeyMapPressed: function(e) {
        this.onPreviewClicked(this.down('button[text="Preview"]'));
    },
    onRefreshKeyMapPressed: function(e) {
        
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            beforerender: function (panel){
                me.mixins.authorization.authorize(panel);
            },
            rowdblclick: function (el, record, item, index, e, eOpts) {
                me.onPreviewClicked(el);
            },
            afterrender: function (el, eOpts) {

            },
            refresh: function (dataview) {
                Ext.each(dataview.panel.columns, function (column) {
                    if (column.autoSizeColumn === true)
                        column.autoSize();
                })
            }
        };
    },
    buildButtons: function() {
        var me = this,
        tbar = [];

        if(!me.isReport) {
            tbar.push({ 
                xtype: 'button', 
                iconCls: 'fa fa-plus-circle', 
                text: 'New', 
                handler: function(el) { me.onNewClicked(el); }
            },
            { 
                xtype: 'button', 
                iconCls: 'fa fa-plus-circlefa fa-pencil-square-o', 
                disabled: true, 
                text: 'Edit', 
                handler: function(el) { me.onModifiedClicked(el); },  
                bind: {
                    disabled: '{!selectedRecord}'
                }
            }, { 
                xtype: 'button', 
                iconCls: 'fa fa-eye', 
                text: 'View', 
                disabled: true,
                handler: function(el) { me.onPreviewClicked(el); },
                bind: {
                    disabled: '{!selectedRecord}'
                } 
            }, '|');
        }

        tbar.push(
            { xtype: 'button', iconCls: 'fa fa-refresh', text: 'Refresh' },
            {
                xtype: 'button',
                text: 'Export',
                iconCls: 'fa fa-download',
                menu: [{
                    iconCls: 'fa fa-file-pdf-o',
                    text: 'Export PDF'
                }, {
                    iconCls: 'fa fa-file-excel-o',
                    text: 'Export Excel'
                }]
            },
            '->',
            { xtype: 'textfield', emptyText: 'Search here...', width: 255 },
            { xtype: 'button', iconCls: 'fa fa-search', text: 'Search' }
            );

        me.tbar = tbar;
    },
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true
    },
    buildItems: function() {
        var me = this;
    },
    buildColumns: function() {
        var me = this;        
        me.columns = [{
            xtype: 'rownumberer'
        }]; 
        if (!Ext.isEmpty(me.fields)) {
            me.createRemoveButton();
            Ext.each(me.sortOf(me.fields, 'visibleIndex', 'asc'), function (item, index, array) {
                me.columns.push(me.kindOfColumn(item));
            });            
        }
    },
    onNewClicked: function (el) {
        var me = this,
            store = me.getStore(),
            tabpanel = me.up('tabpanel[tabId="8E908B55-1DDD-47F8-B999-6576ADD0F2B5"]');
        var crudWin = me.buildCrudWinForm(null, 'create', function (record) {
            record.id = null;
            var model = store.add(record);
        });

        me.showForm(crudWin, el);     
    },
    onModifiedClicked: function (el) {
        var me = this,
            viewmodel = me.getViewModel(),
            selectedRecord = viewmodel.get('selectedRecord'),
            tempRecord = Ext.apply({}, selectedRecord.getData());
        var crudWin = me.buildCrudWinForm(tempRecord, 'modification', function (record) {
            selectedRecord.setId(record.id);
            record.phantom = true;
            me.updateRecord(selectedRecord, record);            
        });
        me.showForm(crudWin, el);
    },
    onPreviewClicked: function (el) {
        var me = this,
            viewmodel = me.getViewModel(),
            selectedRecord = viewmodel.get('selectedRecord'),
                record = selectedRecord.getData();
        var crudWin = me.buildCrudWinForm(record, 'preview', function () {
        });
        me.showForm(crudWin, el);
    },
    onRemoveClicked: function (record) {
        var me = this,
            store = me.getStore();
        Ext.MessageBox.show({
            title: 'Warning',
            msg: 'Are you sure to delete this record?',
            buttons: Ext.MessageBox.YESNO,
            icon: Ext.MessageBox.WARNING,
            fn: function (btn) {
                if (btn === 'yes') {
                    store.remove(record);
                }
            }
        });
    },
    /*
    * @private
    */
    showForm: function(form, el) {
        var me = this,
        tabpanel = me.up('tabpanel[tabId="8E908B55-1DDD-47F8-B999-6576ADD0F2B5"]');
        if(!me.isDialog && tabpanel) {
            tabpanel.add(form);
            tabpanel.setActiveItem(form);            
        } else {
            form.show(el);
        }
    },
    updateRecord: function(oRecord, nRecord) {
        Ext.Object.each(nRecord, function (key, value, el) {
            try {
                oRecord.set(key, value);
            } catch (e) {
            }
        });
    },
    /*
    * @private
    */
    createRemoveButton: function () {
        var me = this;
        me.columns.push({
            menuDisabled: true,
            sortable: false,
            width: 50,
            xtype: 'actioncolumn',
            align: 'center',
            items: [{
                iconCls: 'fa fa-trash-o',
                tooltip: 'Remove',
                width: 50,
                handler: function (grid, rowIndex, colIndexe) {
                    var store = me.getStore(),
                    record = store.getAt(rowIndex);
                    me.onRemoveClicked(record);                    
                }
            }]
        });
    },
    /*
    * @private
    */
    getRawRecordData: function (records) {
        var newItems = []
        if (records && records.length > 0) {
            Ext.each(records, function (record) {
                if (record.data) {
                    if (isNaN(parseInt(record.data.id))) {
                        record.data.id = 0;
                    }
                    newItems.push(record.data);
                }
            });
        }
        return newItems;
    },
    buildStore: function() {
        var me = this,
            fields = [];
        if (!Ext.isEmpty(me.fields)) {
            Ext.each(me.sortOf(me.fields, 'visibleIndex', 'asc'), function (item, index, array) {
                fields.push({ name: item.name, type: 'auto' });
            });
        }
        var store = Ext.create('Ext.data.Store', {
            fields: fields
        });
        me.store = store;
    },
    buildCrudWinForm: function (record, operation, fn) {
        var me = this,
            crudWindow = null,
            buttons = [{
                text: 'Close',
                handler: function (el) {
                    if (!Ext.isEmpty(crudWindow))
                        crudWindow.close();
                }
            }];

        if (operation != 'preview') {
            Ext.Array.insert(buttons, 0, [{
                text: operation == 'create' ? 'Save': 'Save Change',
                handler: function (el) {
                    var viewmodel = crudWindow.getViewModel();
                    if (crudWindow.getForm().isValid()) {
                        var formInfo = Ext.apply({}, viewmodel.get('formInfo'));
                        crudWindow.fn(formInfo);
                        crudWindow.getForm().reset();
                        if (operation == 'modification' && !Ext.isEmpty(crudWindow)) {
                            crudWindow.close();
                        }
                    }
                }
            }]);
        }

        if(me.isDialog) {
            crudWindow = Ext.create('Ext.window.Window', {
                maximizable: true,
                viewModel: {
                    data: {
                        formInfo: Ext.isEmpty(record) ? me.dataDefault : record,
                        readOnly: (operation == 'preview')
                    }
                },
                modal: true,
                height: window.innerHeight,
                resizable: true,
                width: me.crudWinSize.width,
                title: Ext.String.format('{0} ({1})', me.title, (operation == 'preview') ? 'Preview': ((operation == 'create') ? 'New Record': 'Modification')),                
                operation: operation, 
                fn: fn,
                autoScroll:true,
                layout: 'fit',
                getForm: function() {
                    var form = crudWindow.down('form');
                    return form;
                },
                items: {
                    xtype: 'form',
                    bodyPadding: 5,
                    layout: 'column',
                    defaults: {
                        columnWidth: 1,
                        xtype: 'textfield',
                        margin: '0 0 5 0',
                        labelWidth: 125
                    },
                    items: me.buildCrudItems(),
                },
                buttons: buttons
            });
        } else {
            crudWindow = Ext.create('Ext.form.Panel', {
                    viewModel: {
                        data: {
                            formInfo: Ext.isEmpty(record) ? me.dataDefault : record,
                            readOnly: (operation == 'preview')
                        }
                    },
                    layout: 'column',
                    bodyPadding: 5,
                    operation: operation, 
                    closable: true,
                    title: Ext.String.format('{0} ({1})', me.title, (operation == 'preview') ? 'Preview': ((operation == 'create') ? 'New Record': 'Modification')),
                    fn: fn,
                    defaults: {
                        columnWidth: 1,
                        xtype: 'textfield',
                        margin: '0 0 5 0',
                        labelWidth: 125
                    },
                    items: me.buildCrudItems(),
                    buttons: buttons
            });
        }

        return crudWindow;
    },
    buildCrudItems: function() {
        var me = this,
            items = [],
            viewmodel = me.getViewModel();
        if (!Ext.isEmpty(me.fields)) {
            Ext.each(me.sortOf(me.fields, 'visibleIndex', 'asc'), function (item, index, array) {
                var field = me.kindOfField(item);
                items.push(field);
            });
        }
        return items;
    },
    isContains: function (text, container) {
        var certain = false;
        if (Ext.isEmpty(container) || container == [])
            return certain;

        Ext.each(container, function (item, index, a) {
            if (text === item) {
                certain = true;
                return;
            }
        });

        return certain;
    },
    sortOf: function (columns, key, direction) {

        return columns;
    },
    kindOfField: function(field) {
        var kind = Ext.apply({}, field);
        switch (field.kind) {
            case 'time': {
                kind = Ext.applyIf(kind, {
                    xtype: 'timefield',
                    format: 'H:i',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }
            case 'datetime': {
                kind = Ext.applyIf(kind, {
                    xtype: 'datetime',
                    format: 'd/m/Y H:i',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name),
                        minValue: Ext.isEmpty(field.ref) ? null : Ext.String.format('{formInfo.{0}}', field.ref.min),
                        maxValue: Ext.isEmpty(field.ref) || Ext.isEmpty(field.ref.max) ? null : Ext.String.format('{formInfo.{0}}', field.ref.max)
                    },
                    listeners: {
                        beforedestroy: function (el) {
                            var me = this;                               
                            return false;
                        }
                    }
                });
                break;
            }
            case 'date': {
                kind = Ext.applyIf(kind, {
                    xtype: 'datefield',
                    format: 'd/m/Y',
                    altFormats: 'c',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name),
                        minValue: Ext.isEmpty(field.ref) ? null : Ext.String.format('{formInfo.{0}}', field.ref.min),
                        maxValue: Ext.isEmpty(field.ref) || Ext.isEmpty(field.ref.max) ? null : Ext.String.format('{formInfo.{0}}', field.ref.max)
                    },
                    listeners: {
                        beforedestroy: function (el) {
                            var me = this;
                            delete me.bind.minValue;
                            delete me.bind.maxValue;                            
                            return true;
                        }
                    }
                });
                break;
            }
            case 'text': {
                kind = Ext.applyIf(kind, {
                    xtype: 'textfield',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }
            case 'lookup': {
                kind = Ext.applyIf(kind, {
                    xtype: 'lookupfield',
                    queryMode: 'local',
                    forceSelection: true,
                    minChars: 1,
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name),
                        disabled: Ext.isEmpty(field.ref) ? null : Ext.String.format('{!{0}.selection}', field.ref.name),
                        filters: Ext.isEmpty(field.ref) ? null : {
                            property: field.ref.filter.property,
                            value: Ext.String.format('{{0}.selection.{1}}', field.ref.name, field.ref.property)
                        }
                    },
                    listeners: {
                        beforedestroy: function(el) {
                            var me = this;
                            delete me.bind.disabled;
                            delete me.bind.filters; 
                            return true;
                        } 
                    }
                });
                break;
            }
            case 'email': {
                kind = Ext.applyIf(kind, {
                    xtype: 'textfield',
                    regex: /^((([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z\s?]{2,5}){1,25})*(\s*?;\s*?)*)*$/,
                    regexText: 'This field must contain single or multiple valid email addresses separated by semicolon (;)',
                    blankText: 'Please enter email address(s)',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }
            case 'phone': {
                kind = Ext.applyIf(kind, {
                    xtype: 'textfield',
                    regex: /^[0-9]*$/,
                    regexText: 'This field must contain number only.',
                    blankText: 'Please enter phone number.',
                    maskRe: /[0-9.]/,
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }
            case 'textarea': {
                kind = Ext.applyIf(kind, {
                    xtype: 'textarea',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }
            case 'numeric': {    
                kind = Ext.applyIf(kind, {
                    xtype: 'numberfield',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', field.name)
                    }
                });
                break;
            }

            case 'currency': {
                kind = Ext.applyIf(kind, {
                    xtype: 'currencyfield',
                    bind: {
                        readOnly: '{readOnly}',
                        value: Ext.String.format('{formInfo.{0}}', column.name)
                    }
                });
                break;
            }
        }
        return kind;
    },
    kindOfColumn: function (column) {
        var me = this,
            kind = Ext.apply({}, column);

        switch (column.kind) {
            case 'time': {
                kind = Ext.applyIf(kind, {
                    dataIndex: column.name,
                    text: column.fieldLabel,
                    renderer: Ext.util.Format.dateRenderer('H:i')
                });
                break;
            }
            case 'datetime': {
                kind = Ext.applyIf(kind, {
                    dataIndex: column.name,
                    text: column.fieldLabel,
                    renderer: Ext.util.Format.dateRenderer('d/m/Y H:i')
                });
                break;
            }
            case 'date': {
                kind = Ext.applyIf(kind, {
                    dataIndex: column.name,
                    text: column.fieldLabel,
                    renderer: Ext.util.Format.dateRenderer('d/m/Y')
                });
                break;
            }
            case 'email': {
                kind = Ext.applyIf(kind, {
                    text: column.fieldLabel,
                    dataIndex: column.name
                });
                break;
            }
            case 'phone':
            case 'lookup':
            case 'text': {
                kind = Ext.applyIf(kind, {
                    text: column.fieldLabel,
                    dataIndex: column.name
                });
                break;
            }
            case 'textarea': {
                kind = Ext.applyIf(kind, {
                    text: column.fieldLabel,
                    dataIndex: column.name
                });
                break;
            }
            case 'numeric': {
                kind = Ext.applyIf(kind, {
                    dataIndex: column.name,
                    text: column.fieldLabel,
                    renderer: me.formatNumber
                });
                break;
            }

            case 'currency': {
                kind = Ext.applyIf(kind, {
                    text: column.fieldLabel,
                    dataIndex: column.name,
                    renderer: me.formatCurrency
                });
                break;
            }
        }

        return kind;
    },
    formatNumber: function (value) {
        var format = '0,000';
        if (this.floating) {
            format = '0,000.00';
        }
        return Ext.util.Format.number(value, format);
    },
    formatCurrency: function (value) {
        var format = Ext.util.Format;
        format.thousandSeparator = ',';
        format.currencySign = Ext.isEmpty(this.currencySign) ? '$' : this.currencySign;
        format.currencyAtEnd = false;
        return format.currency(value);
    },
    reset: function () {
        var me = this,
            store = me.getStore();
        store.removeAll();
        store.setData([]);
    }
});