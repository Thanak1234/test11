Ext.define('UI.view.forms.AbstractGrid', {
    extend: 'Ext.grid.Panel',
    closable: true,
    controller: 'abstract-grid',
    viewModel: 'abstract-grid',    
    emptyText: 'No Records',
    loadMask: true,
    stateful: false,
    mixins: {
        authorization: 'UI.view.common.Authorization'
    },
    bind: {
        selection: '{selectedRecord}'
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
        me.buildEvents();
        me.buildButtons();        
        me.callParent(arguments);  
    },
    buildButtons: function() {
        var me = this,
        tbar = [];

        if(!me.isReport) {
            tbar.push({ 
                xtype: 'button', 
                iconCls: 'fa fa-plus-circle', 
                text: 'New', 
                handler: 'onNewClicked'
            },
            { 
                xtype: 'button', 
                iconCls: 'fa fa-plus-circlefa fa-pencil-square-o', 
                disabled: true, 
                text: 'Edit', 
                handler: 'onModifiedClicked',  
                bind: {
                    disabled: '{!selectedRecord}'
                }
            }, { 
                xtype: 'button', 
                iconCls: 'fa fa-eye', 
                text: 'View', 
                disabled: true,
                handler: 'onPreviewClicked',
                bind: {
                    disabled: '{!selectedRecord}'
                } 
            }, '|');
        }

        tbar.push(
            {
                text: 'Clear Filters',
                tooltip: 'Clear all filters',
                handler: 'onClearFilters'
            },
            { 
                xtype: 'button', 
                iconCls: 'fa fa-refresh', 
                text: 'Refresh',
                handler: 'onRefreshClicked' 
            },
            {
                xtype: 'button',
                text: 'Export',
                iconCls: 'fa fa-download',
                menu: [{
                    iconCls: 'fa fa-file-pdf-o',
                    text: 'Export PDF',
                    handler: 'onExportPDFClicked'
                }, {
                    iconCls: 'fa fa-file-excel-o',
                    text: 'Export Excel',
                    handler: 'onExportExcelClicked'
                }]
            },
            '->',
            { 
                xtype: 'textfield', 
                emptyText: 'Search here...', 
                width: 255,
                bind: {
                    value: '{gridInfo.searchText}'
                },
                listeners: {
                    specialkey: 'onSearchTextPress'
                },
                triggers: {
                    clear: {
                        weight: 1,
                        cls: Ext.baseCSSPrefix + 'form-clear-trigger',
                        hidden: false,
                        handler: 'onSearchClearClick'
                    }
                }
            },
            { 
                xtype: 'button', 
                iconCls: 'fa fa-search', 
                text: 'Search',
                handler: 'onSearchClicked' 
            });

        me.tbar = tbar;
    },
    bbar: {
        xtype: 'pagingtoolbar',
        displayInfo: true
    },
    buildEvents: function() {
        var me = this;
        me.listeners = {
            beforerender: function (panel){
                me.mixins.authorization.authorize(panel);
            },            
            refresh: function (dataview) {
                Ext.each(dataview.panel.columns, function (column) {
                    if (column.autoSizeColumn === true)
                        column.autoSize();
                })
            }
        };
    }    
})