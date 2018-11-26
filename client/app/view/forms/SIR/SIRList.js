Ext.define('UI.view.form.SIR.SIRList', {
    extend: 'UI.view.forms.AbstractGrid',
    xtype: 'form-sir-list',
    controller: 'form-sir-list',
    viewModel: 'form-sir-list',
    requires: [
        'UI.view.form.SIR.SIRFormController'
    ],
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
            type: 'sirForm'
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
            sortable	: false,
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
                }, {
                    text: 'Clone to STR',
                    iconCls: 'fa fa-copy',
                }];
                widget.setMenu(menus);
            }
        }, {
            text		: 'Status',
            dataIndex	: 'isCloneToSTR',
            menuDisabled: true,
            sortable	: false,
            width		: 60,            
            renderer	: function(value){							
                var val = 'SIR';
                if(value == true){
                    val = 'STR';
                }
                return val;
            }
        }, {
            text 		: 'Form No *^',
            dataIndex 	: 'sirNo', 
            width 		: 120,
            sortable 	: true,
            menuDisabled: false
        }, {
            text 		: 'Recieved Date',
            dataIndex 	: 'recievedDate',
            menuDisabled: true,
            sortable	: false,
            width 		: 120, 
            renderer: function(value) {
                return value?Ext.Date.format(new Date(value), 'd/m/Y'):'';
            }
        }, {	
            text 		: 'Recieved By',
            dataIndex 	: 'recievedBy', 
            menuDisabled: true,
            width 		: 120,
            sortable 	: false
        }, {
            text 		: 'CTR Form No',
            dataIndex 	: 'ctrFormNo', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text		: 'Area',
            dataIndex	: 'sirArea',
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {	
            text 		: 'Amount',
            dataIndex 	: 'sirAmount',
            width 		: 120, 
            align		: 'left',
            menuDisabled: true,
            sortable 	: false
        }, {	
            text 		: 'Player Name',
            dataIndex 	: 'playerName',
            sortable 	: false,
            menuDisabled: true,
            width 		: 120
        }, {	
            text 		: 'Player Card Type',
            dataIndex 	: 'playerCardType',
            sortable 	: false,
            menuDisabled: true,
            width 		: 120
        }, {
            text 		: 'Driver License',
            dataIndex 	: 'driverLicense', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text 		: 'Gerc Card No',
            dataIndex 	: 'gercCardNo', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text 		: 'ID Card',
            dataIndex 	: 'idCard', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text 		: 'Passport',
            dataIndex 	: 'passport', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text 		: 'Others',
            dataIndex 	: 'others', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
        }, {
            text 		: 'No Card',
            dataIndex 	: 'noCard', 
            width 		: 120,
            menuDisabled: true,
            sortable 	: false
            
        }, {	
            text 		: 'DOB',
            dataIndex 	: 'dob',
            width 		: 120,
            menuDisabled: true, 
            sortable 	: false,
            renderer: function(value) {
                return value?Ext.Date.format(new Date(value), 'd/m/Y'):'';
            }
        }, {
            xtype       : 'datecolumn',	
            text 		: 'From Trans Date/Time #',
            dataIndex 	: 'sirTranDateFrom',
            width 		: 170, 
            sortable 	: false,
            menuDisabled: false,
            format: 'd/m/Y H:i:s',
            filter: {
                type:'date'  
            }
        }, {	
            xtype       : 'datecolumn',	
            text 		: 'To Trans Date/Time',
            dataIndex 	: 'sirTranDateTo',
            width 		: 170, 
            menuDisabled: true,
            sortable 	: false,
            format: 'd/m/Y H:i:s'
        }, {	
            text 		: 'Staff Department',
            dataIndex 	: 'sirStaffDepartment',
            width 		: 170,
            menuDisabled: true,
            sortable 	: false 
        }, {
            text		: 'SIR Document No',
            dataIndex	: 'sirDocumentNo',
            width 		: 170,
            menuDisabled: true,
            sortable	: false
        }, {
            text		: 'Submission',
            dataIndex	: 'submission',
            menuDisabled: true,
            sortable	: false,
            renderer	: function(value){							
                var val = 'No';
                if(value == true){
                    val = 'Yes';
                }
                return val;
            }
        }, {
            text		: 'Void',
            dataIndex	: 'status',
            width		: 70,
            menuDisabled: true,
            sortable	: true,
            renderer	: function(value){							
                var val = 'No';
                if(value == true){
                    val = 'Yes';
                }
                return val;
            }
        }, {
            text		: 'Reviewed',
            dataIndex	: 'review',
            width		: 90,
            menuDisabled: true,
            sortable	: true,
            renderer	: function(value){							
                var val = 'No';
                if(value == true){
                    val = 'Yes';
                }
                return val;
            }
        }, {
            text		: 'Transaction Type',
            dataIndex	: 'sirTransactionType',
            width 		: 160,
            menuDisabled: true,
            sortable	: true
        }];
	}
});