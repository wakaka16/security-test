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