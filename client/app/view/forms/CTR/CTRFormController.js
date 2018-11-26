Ext.define('UI.view.form.CTR.CTRFormController', {
    extend: 'UI.view.form.AbstractFormController',
    alias: 'controller.form-ctr',
    actionUrl: Configuration.baseUrl + 'api/v1/ctrform/save',
    onTypeChanged: function(combobox , newValue , oldValue , eOpts) {
        var me = this,
        view = me.getView(),
        refs = me.getReferences(),
        viewmodel = me.getViewModel(),
        location = refs.location,
        store = location.getStore();
        store.getProxy().extraParams = { locnType: me.getLocationType(newValue) };
        store.load({
            callback: function (r, options, success) {
                if (success === true) {
                    location.setValue(viewmodel.get('formInfo.location'));
                }
            }
        });
    },
    getLocationType: function(s) {
        var type = '';
        if(s == 'Slot') {
            type = 'MA';
        } else if (s == 'Table') {
            type = 'TA';
        } else if (s == 'Treasury') {
            type = 'TR';
        }

        return type;
    },
    clearData: function(form) {
        var me = this,
        view = me.getView(),
        viewmodel = view.getViewModel();
        form.reset();    
    }
});