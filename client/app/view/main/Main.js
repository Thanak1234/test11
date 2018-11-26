Ext.define('UI.view.main.Main', {
    extend: 'Ext.container.Viewport',
    xtype: 'app-main',
    controller: 'main',
    viewModel: 'main',
    title: 'AML (Dev)',
    layout: 'border',
    requires: [
        'overrides.form.field.TextField', 
        'overrides.form.field.TextArea', 
        'Overrides.list.RootTreeItem',
        'UI.view.forms.SIR.SIRForm',
        'UI.view.form.SIR.SIRFormController',
        'UI.view.forms.SIR.SIRFormModel',
        'UI.view.form.SIR.SIRList',
        'UI.view.form.SIR.SIRFormController',
        'UI.view.commons.UIFormController',
        'UI.view.common.UIFormModel',
        'UI.view.commons.UIFormController',
        'UI.view.common.UIFormModel',
        'UI.view.form.AbstractFormController',
        'UI.view.forms.AbstractFormModel',
        'overrides.form.BasicForm'
    ],
    initComponent: function() {
        var me = this;   
        me.initPreventDefaultKey();      
        me.callParent(arguments);
    },    
    initPreventDefaultKey: function() {
        // var me = this;
        // var excludes = ['F1', 'F2', 'F3', 'F4', 'F5', 'F6', 'F7', 'F8', 'F9', 'F10', 'F11', 'F12', 
        // 'Control', 'Alt', 'A', 'E', 'P', 'R', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'W', 'E'];
        // document.body.addEventListener('keydown', event => {
        //     if(event.ctrlKey && me.contain(event.key, excludes)) {
        //         event.preventDefault();
        //     }
        // });
    },
    contain: function(value, sets) {
        var found = false;
        for(var i=0; i<sets.length; i++) {
            if(sets[i].toLowerCase() === value.toLowerCase()) {
                found = true;
                break;
            }
        }
        return found;
    }, 
    items: [{
        xtype: 'panel',
        region: 'north',
        title: 'AML (Dev)',
        header: {
            items: [{
                xtype: 'button',
                text: 'Im Sopheap',
                menu: [{
                    text: 'Sign out',
                    handler: 'onSignOutClicked'
                }]
            }]
        },
    }, {
        region: 'west',
        width: 250,
        split: true,
        title: 'Forms',        
        collapsible: true,
        reference: 'treelistContainer',
        scrollable: true,
        style: {
            borderTop: '2px solid white'
        },
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        scrollable: 'y',
        items: [{
            xtype: 'treelist',
            reference: 'navigation',
            rootVisible: false,
            store: {
                type: 'navigation'
            },
            listeners: {
                selectionchange: 'onNavigationTreeSelectionChange'
            }
        }]
    }, {
        xtype: 'tabpanel',
        region: 'center',
        scrollable: true,
        style: {
            borderTop: '2px solid white'
        },
        tabId: '8E908B55-1DDD-47F8-B999-6576ADD0F2B5',
        reference: 'contentPanel',
        listeners: {
            selectionchange: 'onNavigationTreeSelectionChange'
        },
        listeners: {
            tabchange: 'onContentTabSelectionChange',
            beforeremove: function () {
                window.history.back();
            }
        },
        tabBar: {
            layout: {
                pack: 'left'
            }
        }
    }]
});
