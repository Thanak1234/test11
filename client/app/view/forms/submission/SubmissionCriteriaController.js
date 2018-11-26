Ext.define('UI.view.form.submission.SubmissionCriteriaController', {
    extend: 'UI.view.commons.AbstractWindowController',
    alias: 'controller.forms-submission-criteria',
    onFormTypeChanged: function(el, value) {
        var me = this,
        viewmodel = me.getViewModel();
        if(value == 'CTR') {
            viewmodel.set('criteria.multiAmount', 5000);
            viewmodel.set('criteria.singleAmount', 10000);
            viewmodel.set('disableMultiAmount', false);
            viewmodel.set('disableSingleAmount', false);
        } else {
            viewmodel.set('criteria.multiAmount', 0);
            viewmodel.set('criteria.singleAmount', 0);
            viewmodel.set('disableMultiAmount', true);
            viewmodel.set('disableSingleAmount', true);
        }
        
    },
    onSaveClicked: function(el) {
        var me = this,
        view = me.getView(),
        viewmodel = me.getViewModel(),
        refs = me.getReferences();

        var multiRecords = refs.multiTransactionStepThree.getRecords();
        var singleRecords = refs.singleTransactionStepThree.getRecords();

        var records = Ext.Array.merge(multiRecords, singleRecords);
        var criteria = viewmodel.get('criteria');
        var data = {
            criteria: criteria,
            transactions: records
        }

        Ext.Ajax.request({
            method: 'POST',
            url: Configuration.baseUrl + 'api/v1/submission/save',
            jsonData: data,
            success: function(response, request) {

            },
            failure: function(response, request) {

            }
        });

        // view.close();
    },
    onNextClicked: function () {
        this.doCardNavigation(1);
    },
    onPreviousClicked: function (btn) {
        this.doCardNavigation(-1);
    },
    doCardNavigation: function (incr) {
        var me = this,
        view = me.getView(),
        viewmodel = view.getViewModel(),
        refs = me.getReferences(),
        criteriaForm = refs.criteriaForm.getForm();

        var layout = view.getLayout();
        var index = layout.activeItem.id.split('card-')[1];
        var next = parseInt(index, 10) + incr; 
        
        var multiTransactionStepTwoStore = refs.multiTransactionStepTwo.getStore();
        var singleTransactionStepTwoStore = refs.singleTransactionStepTwo.getStore();

        var multiTransactionStepThreeStore = refs.multiTransactionStepThree.getStore();
        var singleTransactionStepThreeStore = refs.singleTransactionStepThree.getStore();

        switch(index) {
            case '0': {
                if(criteriaForm.isValid()) {
                    var criteria = viewmodel.get('criteria');                    
                    var singleParams = {
                        amount: criteria.singleAmount,
                        dateFrom: criteria.transDate.getTime(),
                        excludeCountry: criteria.exclusiveCountry
                    };

                    var multiParams = {
                        amount: criteria.multiAmount,
                        dateFrom: criteria.transDate.getTime(),
                        excludeCountry: criteria.exclusiveCountry
                    };
                    
                    Ext.Ajax.request({
                        method: 'GET',
                        url: Configuration.baseUrl + 'api/v1/ctrforms/transactions/single',
                        async: false,
                        params: singleParams,
                        success: function(response, request) {                            
                            var records = JSON.parse(response.responseText);
                            singleTransactionStepTwoStore.removeAll();
                            singleTransactionStepTwoStore.add(records);
                        },
                        failure: function(response, request) {
                            console.log(response);
                        }
                    });

                    Ext.Ajax.request({
                        method: 'GET',
                        url: Configuration.baseUrl + 'api/v1/ctrforms/transactions/multi',
                        async: false,
                        params: multiParams,
                        success: function(response, request) {                            
                            var records = JSON.parse(response.responseText);
                            multiTransactionStepTwoStore.removeAll();
                            multiTransactionStepTwoStore.add(records);
                        },
                        failure: function(response, request) {
                            console.log(response);
                        }
                    });

                    me.navigation(next);
                }
                break;
            }
            case '1': {
                var multiTransactionStepRecords = multiTransactionStepTwoStore.queryRecords('exclude', false);
                var singleTransactionStepRecords = singleTransactionStepTwoStore.queryRecords('exclude', false);

                multiTransactionStepThreeStore.removeAll();
                multiTransactionStepThreeStore.add(multiTransactionStepRecords);

                singleTransactionStepThreeStore.removeAll();
                singleTransactionStepThreeStore.add(singleTransactionStepRecords);
                me.navigation(next);
                break;
            }

            case '2': {
                
                me.navigation(next);
                break;
            }
        }
        
    },
    navigation: function(index) {
        var me = this,
        view = me.getView(),
        layout = view.getLayout();

        layout.setActiveItem(index)
        view.down('#card-prev').setDisabled(index===0);
        view.down('#card-next').setDisabled(index===2);
        view.down('#card-save').setDisabled(!(index===2));
    }
});