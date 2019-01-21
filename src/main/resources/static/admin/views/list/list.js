//获取数据
getDataList = function () {
  $.ajax({
    url: '/v1/user/',
    type: 'GET',
    dataType: "json",
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    success: function (res) {
      alert(res)
      console.log(res);
      if (res.code == 200) {
        var dataList = res.data;
        //渲染数据
        var html = "";
        for (var i = 0; i < dataList.length; i++) {
          html += " <tr> <th scope=\"row\">"
              +dataList[i].id
              +"</th><th scope=\"row\">"
              + dataList[i].name
              +" </th><th scope=\"row\">"
              + dataList[i].email
              +"</th><th scope=\"row\">"
              + dataList[i].loginTime
              +"</th> </tr>";
        }
        $('#tbody').html(html);
        return;
      } else {
        alert(res.errorMsg);
        return;
      }
    },
    error: function (err) {
      console.log(err);
    }
  })
}