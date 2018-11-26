Ext.define('UI.view.forms.CTR.CTRFormModel', {
    extend: 'UI.view.forms.AbstractFormModel',
    alias: 'viewmodel.form-ctr',
    data: {
        formInfo: {
            formNo: null,
            submission: false,
            typeOf: 'Slot',
            amount:null,
            area: null,
            docNo: null,
            location: null,
            playerId: null,
            remark: null,
            staffDepartment: null,
            staffId: null,
            tran: null,
            tranDate: null
        }
    }
});