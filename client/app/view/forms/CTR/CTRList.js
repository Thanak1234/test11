Ext.define('UI.view.form.CTR.CTRList', {
    extend: 'UI.view.forms.AbstractGrid',
    xtype: 'form-ctr-list',
    controller: 'form-ctr-list',
    viewModel: 'form-ctr-list',
    requires: ['Ext.grid.filters.Filters'],
    listeners: {
        rowdblclick: 'onRowDoubleClicked'
    },   
    plugins: [ 
        'gridfilters',
    ],
    initComponent: function() {
        var me = this;
        me.initStore();
        me.buildColumns();
        me.callParent(arguments);
    },
    initStore: function() {
        var me = this;
        me.store = {
            type: 'ctrForm'
        }
    },
    buildColumns:function(){ 
		var me = this;
		me.columns = [{
            xtype: 'rownumberer'
        }, {
            width: 110,
            xtype: 'widgetcolumn',
            locked: false,
            menuDisabled: true,
            widget: {
                xtype: 'button',
                text: 'Actions',
                textAlign: 'left',
                handler: function (btn) {
                    var rec = btn.getWidgetRecord();
                    me.setSelection(rec);
                }
            },
            onWidgetAttach: function (column, widget, record) {
                var data = record.getData();
                var menus = [{
                    text: 'Void',
                    iconCls: 'fa fa-warning',
                    handler: 'onVoidClicked'
                }, {
                    text: 'Remove',
                    iconCls: 'fa fa-remove',
                    handler: 'onRemoveClicked'
                }];
                widget.setMenu(menus);
            }
        }, {
            text 		: 'Form No *^',
            dataIndex 	: 'formNo',
            // locked   : true, 
            width 		: 120,
            sortable 	: true,
            menuDisabled: true
        },{
            text 		: 'Doc No',
            dataIndex 	: 'docNo', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true           
        },{
            text		: 'Type *',
            dataIndex	: 'typeOf',
            width 		: 100,
            sortable 	: false,
            menuDisabled: true
        },{
            text		: 'Transaction',
            dataIndex	: 'tran',
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{
            text		: 'Location *',
            dataIndex	: 'location',
            width 		: 100,
            sortable 	: false,
            menuDisabled: true
        },{
            text		: 'Area',
            dataIndex	: 'area',
            width 		: 100,
            sortable 	: false,
            menuDisabled: true
        },
        {	
            text 		: 'Amount',
            dataIndex 	: 'amount',
            width 		: 100, 
            align		: 'left',
            sortable 	: false,
            menuDisabled: true,
            renderer: me.currencyRender					
        },{	
            text 		: 'Staff ID',
            dataIndex 	: 'staffId',
            width 		: 120,
            sortable 	: false,
            menuDisabled: true                
        },{	
            text 		: 'Staff Department',
            dataIndex 	: 'staffDepartment',
            width 		: 120,
            sortable 	: false,
            menuDisabled: true                
        },{	
            text 		: 'Player Name *',
            dataIndex 	: 'playerName',
            width 		: 120,
            sortable 	: false,
            menuDisabled: true                
        },{	
            text 		: 'Player With',
            dataIndex 	: 'playerWith',
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{
            text 		: 'Driver License',
            dataIndex 	: 'driverLicense', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true            
        },{
            text 		: 'Gerc Card No',
            dataIndex 	: 'gercCardNo', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{
            text 		: 'ID Card',
            dataIndex 	: 'idCard', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{
            text 		: 'Passport *',
            dataIndex 	: 'passport', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true            
        },{
            text 		: 'Others',
            dataIndex 	: 'other', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{
            text 		: 'No Card',
            dataIndex 	: 'noCard', 
            width 		: 120,
            sortable 	: false,
            menuDisabled: true
        },{	
            xtype: 'datecolumn',
            text 		: 'DOB',
            dataIndex 	: 'dob',
            width 		: 120, 
            sortable 	: false,
            menuDisabled: true,
            format: 'd/m/Y'
        },{	
            xtype: 'datecolumn',
            text 		: 'Tran Date *#^',
            dataIndex 	: 'tranDate',
            width 		: 150, 
            sortable 	: true,
            menuDisabled: false,
            format: 'd/m/Y H:i:s',
            filter: {
                type:'date'  
            }
        },{
            text		: 'Submission',
            dataIndex	: 'submission',
            width		: 120,
            sortable 	: false,
            menuDisabled: true,
            renderer	: function(value){							
                var val = 'No';
                if(value == true){
                    val = 'Yes';
                }
                return val;
            }
        },{
            text		: 'Void',
            dataIndex	: 'status',
            width		: 60,
            sortable 	: false,
            menuDisabled: true,
            renderer	: function(value){							
                var val = 'No';
                if(value == true){
                    val = 'Yes';
                }
                return val;
            }
        },
        {
            text 		: 'Remark', 
            dataIndex 	: 'remark',
            sortable 	: false,
            menuDisabled: true,
            width		: 120           
        }];
    },
    currencyRender: function (value) {
        var format = Ext.util.Format;
        format.thousandSeparator = ',';
        format.currencySign = Ext.isEmpty(this.currencySign) ? '$' : this.currencySign;
        format.currencyAtEnd = false;
        return format.currency(value);
    }
});