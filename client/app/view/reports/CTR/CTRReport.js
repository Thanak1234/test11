Ext.define("UI.view.reports.ctr.CTRReport", {
    extend: "UI.view.commons.UIReportForm",
    xtype: 'report-ctr',
    controller: 'report-ctr',
    viewModel: 'report-ctr',
    title: 'Threshold Recording Report',
    store: {
        type: 'ctrForm'
    },
    buildCriteria: function() {
        var items = [{
            xtype: 'datefield',
            fieldLabel: 'Date From',
            allowBlank: false,
            bind: {
                value: '{criteria.dateFrom}'
            }
        }, {
            xtype: 'datefield',
            fieldLabel: 'Date To',
            allowBlank: false,
            bind: {
                value: '{criteria.dateTo}'
            }
        }, {
            xtype: 'currencyfield',
            fieldLabel: 'Amount From',
            bind: {
                value: '{criteria.amountFrom}'
            }
        }, {
            xtype: 'currencyfield',
            fieldLabel: 'Amount To',
            bind: {
                value: '{criteria.amountTo}'
            }
        }];
        return items;
    },

    buildExports: function() {
        return [{
            iconCls: 'fa fa-file-excel-o',
            text: 'Export Summary Excel',
            handler: 'onExportXls'
        }, {
            iconCls: 'fa fa-file-excel-o',
            text: 'Export Transaction Excel',
            handler: 'onExportXls'
        }];
    },
    buildColumns: function() {
        var columns = [{
            xtype: 'rownumberer'
        }, {
            text 		: 'Form No *^',
            dataIndex 	: 'formNo',
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
            menuDisabled: true			
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
        return columns;
    }
});