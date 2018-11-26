Ext.define('UI.view.common.Authorization', {
    authorize: function(component) {
        var me = this;        
        var rights = [];
        me.applyItems(component, rights);
    },
    applyItems: function(base, rights) {
        var me = this;
        base.items.each(function(c, index, count){
            if(c && c.items && c.items.getCount() > 0) {
                me.applyItems(c);
            } else {
                if(Ext.isFunction(c.getXType)) {
                    var grid = c.grid;
                    if(c.getXType() === 'tableview' && grid) {                        
                        if(grid.dockedItems) 
                            me.applyDocks(rights, grid.dockedItems);

                        if(grid.columns)    
                            me.applyGridColumns(rights, grid.columns);

                        me.applyCmp(rights, c);
                    } else if(c.getXType() === 'panel') {
                        
                    } else {
                        me.applyCmp(rights, c);
                    }                    
                }
            }
        });
    },
    applyCmp: function(rights, cmp) {
        switch(cmp.getXType()) {
            case 'textfield': {
                cmp.setReadOnly(false);
                break;
            }
            case 'button': {
                cmp.setDisabled(false);
                break;
            }
            case 'combo': {
                cmp.setReadOnly(false);
                break;
            }
        }        
    },
    applyDocks: function(rights, dockeditems) {
        var me = this;
        dockeditems.each(function(e){
            if(e.getXType() === 'toolbar' && e.items) {
                e.items.each(function(t){
                    var menus = t.getMenu != null ? t.getMenu() : null;
                    if(t.getXType() === 'button' && menus) {
                        me.applyMenus(rights, menus);                       
                    } else {
                        me.applyCmp(rights, t);
                    }
                });
            }
        });
    },
    applyGridColumns: function(rights, columns) {
        Ext.each(columns, function(e, index) {
            e.setDisabled(false);
        });
    },
    applyMenus: function(rights, menus) {
        var me = this;
        menus.items.each(function(el, index, count) {
            var m = el.getMenu != null ? el.getMenu() : null;
            if(m) {
                me.applyMenus(rights, m);
            } else {
                el.setDisabled(false);
            }
        });
    },
    checkPermission: function(rights, cmp) {

    }
});