(function($){
    $.URL = {
        "common":{
          "rootPath":"http://localhost/"
        },
        "power":{
            "add":"rs/power/add",
            "update":"rs/power/update",
            "delete":"rs/power/delete",
            "list":"rs/power/list"
        },
        "user":{
            "add":"rs/user/add",
            "update":"rs/user/update",
            "delete":"rs/user/delete",
            "list":"rs/user/list",
            "getId":"rs/user/getIdByName" ,
            "currentUserId": "rs/user/currentUserId",
            "currentUserInfo":"rs/user/currentUser"
        },
        "authority":{
             "add":"rs/authority/add",
             "update":"rs/authority/update",
             "delete":"rs/authority/delete",
            "list":"rs/authority/list"
        },
        "userAuthority":{
              "add":"rs/userAuthority/add"
        },
        "deviceData":{
            "getDeviceData":"rs/deviceData/getDeviceData"
        },
        "device":{
            "list":"rs/device/list",
            "add":"rs/device/add",
            "update":"rs/device/update",
            "delete":"rs/device/delete",
            "findByCondition":"rs/device/findByCondition",
            "getDeviceByCondition":"rs/device/getDeviceByCondition"
        },
        "deviceType":{
            "list":"rs/deviceType/list",
            "add":"rs/deviceType/add",
            "update":"rs/deviceType/update",
            "delete":"rs/deviceType/delete"
        },
        "group":{
            "list":"rs/group/list",
            "add":"rs/group/add",
            "update":"rs/group/update",
            "delete":"rs/group/delete"
        },
        "groupUser":{
            "currentGroupDeviceList":"rs/groupUser/currentGroupDeviceList",
            "getUserByGroupId":"rs/groupUser/getUserByGroupId",
            "getListByCondition":"rs/groupUser/getListByCondition"
        },
        "trackRecord":{
            "list":"rs/tarckRecord/list",
            "getMongoDataByCondition":"rs/trackRecord/getMongoDataByCondition",
            "getListByCondition":"rs/trackRecord/getListByCondition"
        },
        "websocket":{
            "register":"ws://localhost:8080/trackSystem/websocket/hello"
        }
    }
})(jQuery);