Ext.define('overrides.form.field.TextArea', {
    override: 'Ext.form.field.TextArea',
    annotation: true,
    maxLength: 2000,
    enforceMaxLength: true,
    constructor: function () {
        var me = this;
        this.callParent(arguments);
        if (me.maxLength && me.maxLength > 0 && this.annotation ) {
            me.addCls('max-length-' + me.maxLength);
        }
    }
});