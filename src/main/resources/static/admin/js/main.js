//打开新窗口
openPageInNewWindow = function (url) {
  window.open(url);
}
//在本窗口打开页面
openPageInLocation = function (url) {
  window.location.href=url;
}

//根据key，获得cookie信息（USERNAME、USERID）
getCookie = function(c_name){
  if (document.cookie.length>0)
  {
    c_start=document.cookie.indexOf(c_name + "=")
    if (c_start!=-1)
    {
      c_start=c_start + c_name.length+1
      c_end=document.cookie.indexOf(";",c_start)
      if (c_end==-1) c_end=document.cookie.length
      return unescape(document.cookie.substring(c_start,c_end))
    }
  }
  return ""
};

//验证权限(超级用户根据用户名进行权限验证，普通用户通过此方式进行权限验证)
// "PATCH", '/v1/account/{param}', 'enableResetAccount', $scope
verifyPermission = function(methods, url, tag,scope) {
  if(hasPermission("userPermissions",url+"&"+methods)){
    scope['permission_' + tag]=true;
  }else{

    $.ajax({
      url: '/v1/competence/findCompetencePermissionByUrl',
      type: 'GET',
      headers: {'Content-Type': 'application/x-www-form-urlencoded'},
      params : {
        "methods" : methods,
        "url" : url
      },
      success: function(res){
        console.log(res);
        if(res.code == 200){
          scope['permission_' + tag] = res.data;
          if(res.data){
            setPermissions("userPermissions",url+"&"+methods);
          }
        }else{
          alert(res.errorMsg);
        }
      },
      error: function(err){
        console.log(err);
      }
    })
  }
}

// 到缓存中查看用户的权限
hasPermission=function (key, permission) {
  if ((window.sessionStorage[key]) != "" && (window.sessionStorage[key])
      != null & (window.sessionStorage[key]) != undefined) {
    permissionUsers = (window.sessionStorage[key]).split(",");
  }
  if (permissionUsers.length == 0) {
    return false;
  }
  return $.inArray(permission, permissionUsers) > -1 ? 1 : 0;
}

//设置权限
setPermissions=function (key, permission) {
  var permissionList = [];
  var nopermissionList = [];
  if (key == 'userPermissions') {
    permissionList.push(permission);
    window.sessionStorage[key] = permissionList;
  } else {
    nopermissionList.push(permission);
    window.sessionStorage[key] = nopermissionList;
  }
}