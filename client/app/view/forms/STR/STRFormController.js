Ext.define('UI.view.form.STR.STRFormController', {
    extend: 'UI.view.form.AbstractFormController',
    alias: 'controller.form-str',
    actionUrl: Configuration.baseUrl + 'api/v1/strform/save',
    init: function() {
        this.bindCtrFormNo();
        this.clearOthers();
    },
    clearData: function(form) {
        var me = this,
        view = me.getView(),
        viewmodel = view.getViewModel();    
    },
    bindCtrFormNo: function() {
        var me = this,
        viewmodel = me.getViewModel();
        viewmodel.bind('{formInfo.ctrFormId}', function() {
            var ctrFormSelection = viewmodel.get('ctrFormSelection');
            if(ctrFormSelection) {
                var formNo = ctrFormSelection.get('formNo');
                viewmodel.set('formInfo.ctrFormNo', formNo);
            } else {
                viewmodel.set('formInfo.ctrFormNo', '');
            }
        });
    },
    clearOthers: function() {
        var me = this,
        viewmodel = me.getViewModel();

        viewmodel.bind('{formInfo.strMethodVerify}', function() {
            viewmodel.set('formInfo.strMethodVerifyOthers', '');
        });

        viewmodel.bind('{formInfo.strCurrentRelationship}', function() {
            viewmodel.set('formInfo.strCurrentRelationshipOthers', '');
        });

        viewmodel.bind('{formInfo.strAffiliationRelationship}', function() {
            viewmodel.set('formInfo.strAffiliationRelationshipOthers', '');
        });

        viewmodel.bind('{formInfo.strTypeSuspiciousActivities}', function() {
            viewmodel.set('formInfo.strTypeSuspiciousActivitiesOthers', '');
        });
    }
});