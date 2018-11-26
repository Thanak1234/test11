Ext.define('UI.view.form.SIR.SIRFormController', {
    extend: 'UI.view.form.AbstractFormController',
    alias: 'controller.form-sir',
    actionUrl: Configuration.baseUrl + 'api/v1/sirform/save',
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

        viewmodel.bind('{formInfo.sirMethodVerify}', function() {
            viewmodel.set('formInfo.sirMethodVerifyOthers', '');
        });

        viewmodel.bind('{formInfo.sirCurrentRelationship}', function() {
            viewmodel.set('formInfo.sirCurrentRelationshipOthers', '');
        });

        viewmodel.bind('{formInfo.sirAffiliationRelationship}', function() {
            viewmodel.set('formInfo.sirAffiliationRelationshipOthers', '');
        });

        viewmodel.bind('{formInfo.sirTypeSuspiciousActivities}', function() {
            viewmodel.set('formInfo.sirTypeSuspiciousActivitiesOthers', '');
        });
    },
    clearData: function(form) {
        var me = this,
        view = me.getView(),
        viewmodel = view.getViewModel();
        form.reset();    
    }
});