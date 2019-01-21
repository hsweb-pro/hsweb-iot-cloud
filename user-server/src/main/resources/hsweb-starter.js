//组件信息
var info = {
    groupId: "org.hswebframework.iot",
    artifactId: "user-server",
    version: "1.0",
    website: "iot-cloud.hsweb.pro",
    author: "admin@hsweb.me",
    comment: "用户服务"
};
var menus = [
    {
        "id": "e9dc96d6b677cbae865670e6813f5e8b",
        "name": "系统设置",
        "parentId": "-1",
        "permissionId": "",
        "path": "sOrB",
        "sortIndex": 1,
        "describe": " ",
        "url": "",
        "icon": "fa fa-cogs",
        "status": 1
    },
    {
        "id": "f84b8dacb3ea07ecd811cfb869137b90",
        "name": "首页设置",
        "parentId": "e9dc96d6b677cbae865670e6813f5e8b",
        "permissionId": "dashboard",
        "path": "sOrB-JOQv",
        "sortIndex": 101,
        "describe": null,
        "url": "admin/dashboard/list.html",
        "icon": "fa fa-dashboard",
        "status": 1
    },
    {
        "id": "8db17b9ba28dd949c926b329af477a08",
        "name": "菜单管理",
        "parentId": "e9dc96d6b677cbae865670e6813f5e8b",
        "permissionId": "menu",
        "path": "sOrB-i2ea",
        "sortIndex": 102,
        "describe": null,
        "url": "admin/menu/list.html",
        "icon": "fa fa-navicon",
        "status": 1
    },
    {
        "id": "a52df62b69e21fd756523faf8f0bd986",
        "name": "权限管理",
        "parentId": "e9dc96d6b677cbae865670e6813f5e8b",
        "permissionId": "permission,autz-setting",
        "path": "sOrB-X27v",
        "sortIndex": 103,
        "describe": null,
        "url": "admin/permission/list.html",
        "icon": "fa fa-briefcase",
        "status": 1
    },
    {
        "id": "42fc4f83d12cc172e4690937eb15752a",
        "name": "角色管理",
        "parentId": "e9dc96d6b677cbae865670e6813f5e8b",
        "permissionId": "role",
        "path": "sOrB-4ofL",
        "sortIndex": 104,
        "describe": null,
        "url": "admin/role/list.html",
        "icon": "fa fa-users",
        "status": 1
    },
    {
        "id": "58eba1a4371dd3c0da24fac5da8cadc2",
        "name": "用户管理",
        "parentId": "e9dc96d6b677cbae865670e6813f5e8b",
        "permissionId": "user",
        "path": "sOrB-Dz7b",
        "sortIndex": 105,
        "describe": null,
        "url": "admin/user/list.html",
        "icon": "fa fa-user",
        "status": 1
    },
    {
        "id": "org-01",
        "name": "组织架构",
        "parentId": "-1",
        "permissionId": "",
        "path": "a2o0",
        "sortIndex": 2,
        "describe": " ",
        "url": "",
        "icon": "fa fa-sitemap",
        "status": 1
    },
    {
        "id": "org-01-01",
        "name": "机构管理",
        "parentId": "org-01",
        "permissionId": "organizational",
        "path": "a2o0-iL0F",
        "sortIndex": 201,
        "describe": null,
        "url": "admin/org/list.html",
        "icon": "fa fa-leaf",
        "status": 1
    },
    {
        "id": "org-01-02",
        "name": "综合设置",
        "parentId": "org-01",
        "permissionId": "organizational,department,position,person",
        "path": "a2o0-A12e",
        "sortIndex": 202,
        "describe": null,
        "url": "admin/org/manager/index.html",
        "icon": "fa fa-sitemap",
        "status": 1
    },
    {
        "id": "dev-01",
        "name": "开发人员工具",
        "parentId": "-1",
        "permissionId": "",
        "path": "d010",
        "sortIndex": 3,
        "describe": " ",
        "url": "",
        "icon": "fa fa-th-list",
        "status": 1
    },
    {
        "id": "code-gen",
        "name": "代码生成器",
        "parentId": "dev-01",
        "permissionId": "file,database-manager,datasource",
        "path": "d010-jG1V",
        "sortIndex": 301,
        "describe": null,
        "url": "admin/code-generator/index.html",
        "icon": "fa fa-desktop",
        "status": 1
    },
    {
        "id": "d9b7a672a63d214a1f063bbbacab89ee",
        "name": "人员管理",
        "parentId": "org-01",
        "permissionId": "person",
        "path": "a2o0-vfXJ",
        "sortIndex": 203,
        "describe": null,
        "url": "admin/org/person/list.html",
        "icon": "fa fa-vcard",
        "status": 1
    }
];
var user = [
    {
        "id": org.hswebframework.web.id.IDGenerator.MD5.generate(),
        "name": "超级管理员",
        "username": "admin",
        "password": "ba7a97be0609c22fa1d300691dfcd790",
        "salt": "HX8Hr5Yd",
        "status": 1,
        "lastLoginIp": null,
        "lastLoginTime": null,
        "creatorId": "admin",
        "createTime": 1497160610259
    }
];

var autz_setting = [
    {
        "id": org.hswebframework.web.id.IDGenerator.MD5.generate(),
        "type": "user",
        "settingFor": user[0].id,
        "describe": null,
        "status": 1
    }
];
var autz_menu = [];
menus.forEach(function (menu) {
    autz_menu.push({
        id: org.hswebframework.web.id.IDGenerator.MD5.generate(),
        parentId: "-1",
        menid: menu.id,
        status: 1,
        "settingId": autz_setting[0].id,
        "path": "-"
    });
});
//版本更新信息
var versions = [
    // {
    //     version: "3.0.0",
    //     upgrade: function (context) {
    //         java.lang.System.out.println("更新到3.0.2了");
    //     }
    // }
];
var JDBCType = java.sql.JDBCType;

function install(context) {
    var database = context.database;

}

function initialize(context) {
    var database = context.database;
    database.getTable("s_menu").createInsert().values(menus).exec();
    database.getTable("s_autz_setting").createInsert().values(autz_setting).exec();
    database.getTable("s_autz_menu").createInsert().values(autz_menu).exec();
    database.getTable("s_user").createInsert().values(user).exec();
}

//设置依赖
dependency.setup(info)
    .onInstall(install)
    .onUpgrade(function (context) { //更新时执行
        var upgrader = context.upgrader;
        upgrader.filter(versions)
            .upgrade(function (newVer) {
                newVer.upgrade(context);
            });
    })
    .onUninstall(function (context) { //卸载时执行

    }).onInitialize(initialize);