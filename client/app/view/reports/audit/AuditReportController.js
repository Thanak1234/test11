Ext.define('UI.view.reports.audit.AuditReportController', {
    extend: 'UI.view.commons.UIReportFormController',
    alias: 'controller.report-audit',
    actionUrl: Configuration.baseUrl + 'api/v1/reports/audit'
});