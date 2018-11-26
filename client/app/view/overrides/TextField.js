Ext.define('overrides.form.field.TextField', {
    override: 'Ext.form.field.Text',
    maxLength: 100, 
    enforceMaxLength: true,
    editable: false,
    constructor: function () {
        var me = this; 
        this.callParent(arguments);
        if(me.allowBlank == false && (Ext.isEmpty(me.hiddenAsterik) || me.hiddenAsterik == false)) {
            var label = me.getFieldLabel() + ' <span class="req" style="color:red">*</span>';
            me.setFieldLabel(label);
        }
    },
    validator: function (text) {
        if (this.allowBlank == false && text && (!Ext.isNumber(text)) && Ext.util.Format.trim(text).length == 0)
            return false;
        else
            return true;
    }
});