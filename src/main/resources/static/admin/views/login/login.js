//登录
login = function () {
  //1、获取用户名和密码
  var username = $("#username").val();
  var password = $("#password").val();
  var remember_me = $("#rememberme").val();
  var csrf_token = getCookie("XSRF-TOKEN");
  //数据校验
  if (username === '' || password === '') {
    alert("用户名和密码不能为空");
    return false;
  }
  //2、发起ajax异步请求
  $.ajax({
    url: '/login',
    type: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    data: {
      username: username,
      password: password,
      _csrf: csrf_token,
      remember_me: remember_me
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
        openPageInLocation('../index/index.html');
      }else{
        alert(res.errorMsg);
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

//
rememberme = function(){
  if($("#rememberme").val()=='off'){
    $("#rememberme").val("on");
  }else{
    $("#rememberme").val("off");
  }

}