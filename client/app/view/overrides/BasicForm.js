Ext.define('overrides.form.BasicForm', {
    override: 'Ext.form.Panel',
    clearDirty: function() {
        var i, it = this.items.items, l = it.length, c;
        for (i = 0; i < l; i++) {
            c = it[i];
            if(c && c.getValue)
                c.originalValue = String(c.getValue());
        }
    }
})