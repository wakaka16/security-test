//登录
login = function () {
  //获取用户名和密码
  var username = $("#username").val();
  var password = $("#password").val();
  //数据校验
  if (username === '' || password === '') {
    alert("用户名和密码不能为空");
    return false;
  }
  //ajax异步请求
  $.ajax({
    url: '/login',
    type: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    data: {
      username: username,
      password: password
    },
    //将请求参数json转换为params
    function(obj) {
      var str = [];
      for (var s in obj) {
        str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
      }
      return str.join("&");
    },
    success: function(res){
      console.log(res);
      if(res.code == 200){
        //储存用户信息，页面跳转
        setCookie('USERNAME',res.data.name);
        setCookie('USERID',res.data.id);
        openPageInLocation('../../../index.html');
        return;
      }else{
        alert(res.errorMsg);
        return;
      }
    },
    error: function(err){
      console.log(err);
    }
  })
}

// 设置cookie，但是后台也可以进行设置，此处设置来供页面使用
setCookie = function(c_name,value,expiredays){
  var exdate=new Date()
  exdate.setDate(exdate.getDate()+expiredays)
  document.cookie=c_name+ "=" +escape(value)+
      ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
};

/*获取cookie*/
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