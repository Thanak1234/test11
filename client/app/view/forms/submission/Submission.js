Ext.define("UI.view.forms.submission.Submission", {
    extend: "UI.view.commons.UIForm",
    xtype: 'forms-submission',
    controller: 'forms-submission',
    bodyPadding: 0,
    layout: 'border',    
    initComponent: function() {
        var me = this;
        me.buildItems();
        me.callParent(arguments);
    },
    buildItems: function() {
        var me = this;

        me.items = [{
            xtype: 'grid',
            region: 'north',
            title: 'Submission List',
            reference: 'submissionList',
            plugins: [ 
                'gridfilters',
            ],
            style: {
                borderTop: '2px solid white'
            },
            height: '50%',
            split: true,
            border: true,
            margin: 0,
            collapsible: true,
            autoScroll: true,
            scrollable: true,
            store: {
                type: 'submission'
            },
            bbar: {
                xtype: 'pagingtoolbar',
                displayInfo: true
            },
            columns: [{
                xtype: 'rownumberer'
            }, {
                text 		: 'Submission Code',
                dataIndex 	: 'submissionCode',
                width 		: 150,
                sortable 	: true,
                menuDisabled: true
            },{
                text 		: 'Form Type',
                dataIndex 	: 'formType', 
                width 		: 150,
                sortable 	: false,
                menuDisabled: true           
            },{
                xtype       : 'datecolumn',
                format      : 'Y-m-d',
                text		: 'Transaction Date',
                dataIndex	: 'transDate',
                width 		: 150,
                sortable 	: false,
                menuDisabled: false,
                filter: {
                    type:'date'  
                }
            },{
                text		: 'Mulit-Trans Amount',
                dataIndex	: 'multiAmount',
                width 		: 150,
                sortable 	: false,
                formatter   : 'usMoney',
                menuDisabled: true
            },{
                text		: 'Single-Trans Amount',
                dataIndex	: 'singleAmount',
                width 		: 150,
                formatter: 'usMoney',
                sortable 	: false,
                menuDisabled: true
            },{
                text		: 'Action Type',
                dataIndex	: 'actionType',
                width 		: 100,
                sortable 	: false,
                menuDisabled: true
            },
            {	
                text 		: 'Status',
                dataIndex 	: 'status',
                width 		: 100, 
                sortable 	: false,
                menuDisabled: true			
            },{	
                xtype       : 'datecolumn',
                format      : 'Y-m-d H:i',
                text 		: 'Save Date',
                dataIndex 	: 'saveDate',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true                              
            },{	                
                text 		: 'Save By',
                dataIndex 	: 'saveBy',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true                
            },{	
                xtype       : 'datecolumn',
                format      : 'Y-m-d H:i',
                text 		: 'Sent Date',
                dataIndex 	: 'sentDate',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true                
            },{	
                text 		: 'Sent By',
                dataIndex 	: 'sentBy',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true
            }, {	
                xtype       : 'datecolumn',
                format      : 'Y-m-d H:i',
                text 		: 'Void Date',
                dataIndex 	: 'voidDate',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true                
            },{	
                text 		: 'Void By',
                dataIndex 	: 'voidBy',
                width 		: 150,
                sortable 	: false,
                menuDisabled: true
            },{	
                text 		: 'Comment',
                dataIndex 	: 'comment',
                width 		: 280,
                sortable 	: false,
                menuDisabled: true
            }],
            tbar: [{ 
                xtype: 'button', 
                iconCls: 'fa fa-plus-circle', 
                text: 'New Submission', 
                handler: 'onNewClicked'
            }, '|', {
                text: 'Clear Filters',
                tooltip: 'Clear all filters',
                handler: 'onClearFilters'
            }, { 
                xtype: 'button', 
                iconCls: 'fa fa-refresh', 
                text: 'Refresh',
                handler: 'onRefreshClicked' 
            }, {
                xtype: 'button',
                text: 'Export',
                iconCls: 'fa fa-download',
                menu: [{
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
            }]
        }, {
            xtype:'tabpanel',
            region: 'center',
            frame: true,
            style: {
                borderTop: '0px solid white'
            },
            margin: 0,
            items: [{
                xtype: 'panel',
                title: 'Submission Details'                
            }, {
                title: 'Submission Logs',
                html: ''
            }]
        }];
    },
    fbar: [{
        text: 'Void',
        disabled: true,
        iconCls: 'fa fa-minus-circle'
    }, '|', {
        text: 'Verification',
        disabled: true,
        iconCls: 'fa fa-check-circle'
    }, {
        text: 'Download',
        disabled: true,
        iconCls: 'fa fa-download'
    }, {
        text: 'Send',
        disabled: true,
        iconCls: 'fa fa-envelope'
    }]
});