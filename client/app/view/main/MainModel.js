/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('UI.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'UI',

        loremIpsum: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
    },

    //TODO - add data, formulas and/or methods to support your view
    stores: {
        navItems: {
            type: 'tree',
            root: {
                expanded: true,
                children: [{
                    text: 'AML',
                    iconCls: 'x-fa fa-home',
                    children: [{
                        text: 'Messages',
                        iconCls: 'x-fa fa-inbox',
                        leaf: true
                    },{
                        text: 'Archive',
                        iconCls: 'x-fa fa-database',
                        children: [{
                            text: 'First',
                            iconCls: 'x-fa fa-sliders',
                            leaf: true
                        },{
                            text: 'No Icon',
                            iconCls: null,
                            leaf: true
                        }]
                    },{
                        text: 'Music',
                        iconCls: 'x-fa fa-music',
                        leaf: true
                    },{
                        text: 'Video',
                        iconCls: 'x-fa fa-film',
                        leaf: true
                    }]
                },{
                    text: 'Reports',
                    iconCls: 'x-fa fa-user',
                    children: [{
                        text: 'Tagged',
                        iconCls: 'x-fa fa-tag',
                        leaf: true
                    },{
                        text: 'Inactive',
                        iconCls: 'x-fa fa-trash',
                        leaf: true
                    }]
                },{
                    text: 'Settings',
                    iconCls: 'x-fa fa-wrench',
                    children: [{
                        text: 'Sharing',
                        iconCls: 'x-fa fa-share-alt',
                        leaf: true
                    },{
                        text: 'Notifications',
                        iconCls: 'x-fa fa-flag',
                        leaf: true
                    },{
                        text: 'Network',
                        iconCls: 'x-fa fa-signal',
                        leaf: true
                    }]
                }]
            }
        }
    }
});
