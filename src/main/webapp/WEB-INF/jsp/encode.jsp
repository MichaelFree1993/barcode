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
    <title>线条编码页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="col-md-9" style="margin: auto;float: none;">
    <h1 style="margin: 30px;margin-left: 16.66666667%;">线条编码</h1>
    <form class="form-horizontal">
        <div class="form-group">
            <label for="contents" class="col-sm-2 control-label">字符串：</label>
            <div class="col-sm-10">
                <textarea type="text" name="contents" id="contents" class="form-control" placeholder="字符串"></textarea>
                <span id="contentslength" style="float: right;">0</span>
            </div>
        </div>
        <div class="form-group">
            <label for="length" class="col-sm-2 control-label">线码宽度：</label>
            <div class="col-sm-10">
                <input type="text" name="length" id="length" class="form-control" placeholder="线码宽度"/>
            </div>
        </div>
        <div class="form-group">
            <label for="markstr" class="col-sm-2 control-label">标志码：</label>
            <div class="col-sm-10">
                <input type="text" name="markstr" id="markstr" class="form-control" placeholder="标志码" value="cmb"/>
            </div>
        </div>
        <div class="form-group">
            <label for="r" class="col-sm-2 control-label">R值：</label>
            <div class="col-sm-10">
                <input type="text" name="r" id="r" class="form-control" placeholder="R值"/>
            </div>
        </div>
        <div class="form-group">
            <label for="g" class="col-sm-2 control-label">G值：</label>
            <div class="col-sm-10">
                <input type="text" name="g" id="g" class="form-control" placeholder="G值"/>
            </div>
        </div>
        <div class="form-group">
            <label for="b" class="col-sm-2 control-label">B值：</label>
            <div class="col-sm-10">
                <input type="text" name="b" id="b" class="form-control" placeholder="B值"/>
            </div>
        </div>
        <div class="form-group">
            <label for="codeType" class="col-sm-2 control-label">编码方式：</label>
            <div class="col-sm-10">
                <select name="codeType" id="codeType" class="form-control">
                    <option value="1">第一种编码方式</option>
                    <option value="2">第二种编码方式</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="checkType" class="col-sm-2 control-label">校验方式：</label>
            <div class="col-sm-10">
                <select name="checkType" id="checkType" class="form-control">
                    <option value="1">CRC校验</option>
                    <option value="2">求和取模校验</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="imgType" class="col-sm-2 control-label">生成图片方式：</label>
            <div class="col-sm-10">
                <select name="imgType" id="imgType" class="form-control">
                    <option value="1">图片</option>
                    <option value="2">Base64</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="button" name="submit_button" id="submit_button" class="form-control"
                       style="width: 100px;" value="提交"/>
            </div>
        </div>
        <div class="form-group">
            <label for="imgShow" class="col-sm-2 control-label">编码图片：</label>
            <div class="col-sm-offset-2 col-sm-10">
                <img src="" alt="" id="imgShow">
            </div>
        </div>

    </form>
    <a href="/decode" class="form-control" style="width: 100px;top: 0px;position: absolute;right: 0px;">线码解码</a>

</div>
</body>

<script type="text/javascript">
    $("#contents").bind("input propertychange", function () {
        var count = $("#contents").val().length;
        document.getElementById("contentslength").innerHTML = count;
    });

    $("#submit_button").click(function () {
        var contents = $.trim($("#contents").val());
        var length = $.trim($("#length").val());
        var markstr = $.trim($("#markstr").val());
        var r = $.trim($("#r").val());
        var g = $.trim($("#g").val());
        var b = $.trim($("#b").val());
        var codeType = $.trim($("#codeType").val());
        var checkType = $.trim($("#checkType").val());
        var imgType = $.trim($("#imgType").val());
        var imgShow = $("#imgShow");
        var jsonStr = {
            "contents": contents,
            "length": length,
            "markStr": markstr,
            "r": r,
            "g": g,
            "b": b,
            "codeType": codeType,
            "checkType": checkType,
            "imgType": imgType
        };
        $.ajax({
            type: "post",
            url: "/encode",
            data: jsonStr,
            success: function (result) {
                if (result.success == 0) {
                    imgShow.attr('src', result.imgPath);
                    alert("编码成功");
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