Ext.define("UI.view.reports.audit.AuditForm", {
    extend: "UI.view.commons.UIReportForm",
    xtype: 'report-audit',
    controller: 'report-audit',
    viewModel: 'report-audit',
    buildCriteria: function() {
        var items = [{
            xtype: 'combo',
            fieldLabel: 'Form Type',
            store: ['CTR', 'SIR', 'STR'],
            emptyText: 'ALL FORM TYPE',
            bind: {
                value: '{reportInfo.formType}'
            }
        }, {
            xtype: 'datefield',
            fieldLabel: 'Date From',
            bind: {
                value: '{reportInfo.dateFrom}'
            }
        }, {
            xtype: 'datefield',
            fieldLabel: 'Date To',
            bind: {
                value: '{reportInfo.dateTo}'
            }
        }];
        return items;
    },
    buildColumns: function() {
        var columns = [{
            text: 'Form No'
        }, {
            text: 'Player Name'
        }];
        return columns;
    }
});