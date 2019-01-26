<%--
  Created by IntelliJ IDEA.
  User: hasee
  Date: 2017/6/27
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>线条解码页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="col-md-9" style="margin: auto;float: none;">
    <h1 style="margin: 30px;margin-left: 16.66666667%;">线条解码</h1>
    <form class="form-horizontal">
        <div class="form-group">
            <label for="file" class="col-sm-2 control-label">线码图片：</label>
            <div class="col-sm-10">
                <input type="file" name="file" id="file" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label for="markstr" class="col-sm-2 control-label">标志码：</label>
            <div class="col-sm-10">
                <input type="text" name="markstr" id="markstr" class="form-control" value="cmb">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="button" name="submit_button" id="submit_button" class="form-control"
                       style="width: 100px;" value="提交"/>
            </div>
        </div>
        <div class="form-group">
            <label for="preview" class="col-sm-2 control-label">预览：</label>
            <div class="col-sm-10">
                <div id="preview" style="padding-top: 17px;"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="preview" class="col-sm-2 control-label">字符串：</label>
            <div class="col-sm-10">
                <span id="str" style="word-wrap: break-word;"></span>
            </div>
        </div>
    </form>

</div>
</body>

<script type="text/javascript">
    function preview1(file) {
        var img = new Image(), url = img.src = URL.createObjectURL(file);
        var $img = $(img);
        img.onload = function () {
            URL.revokeObjectURL(url);
            $('#preview').empty().append($img);
        }
    }
    function preview2(file) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var $img = $('<img>').attr("src", e.target.result);
            $('#preview').empty().append($img);
        }
        reader.readAsDataURL(file);
    }

    $(function () {
        $('[type=file]').change(function (e) {
            var file = e.target.files[0]
            preview1(file)
        })
    })


    $("#submit_button").click(function () {
        var file = $("#file").val();
        if (file == "") {
            alert("请选择上传图片！");
            return;
        }
        var markstr = $("#markstr").val();
        //判断上传文件的后缀名
        var strExtension = file.substr(file.lastIndexOf('.') + 1);
        if (strExtension != 'jpg' && strExtension != 'gif'
            && strExtension != 'png' && strExtension != 'bmp') {
            alert("请选择图片文件");
            return;
        }

        var str = $("#str");
        var formData = new FormData();
        formData.append("file", $("#file")[0].files[0]);
        formData.append("markStr", markstr);
        $.ajax({
            url: "/decode",
            type: 'POST',
            data: formData,
// 告诉jQuery不要去处理发送的数据
            processData: false,
// 告诉jQuery不要去设置Content-Type请求头
            contentType: false,
            beforeSend: function () {
                console.log("正在进行，请稍候");
            },
            success: function (result) {
                if (result.success == 0) {
                    str.html(result.str);
                    alert("解码成功");
                } else {
                    alert(result.msg);
                }
            },
            error: function (result) {
                alert("输入不合法");
            }
        });

    });
</script>
</html>