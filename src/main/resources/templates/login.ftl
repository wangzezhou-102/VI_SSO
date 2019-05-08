<#assign ctxPath=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>登录</title>

    <link rel="shortcut icon" href="${ctxPath}/static/favicon.ico">
    <link href="${ctxPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctxPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctxPath}/static/css/animate.css" rel="stylesheet">
    <link href="${ctxPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${ctxPath}/static/css/login.css" rel="stylesheet">
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
    <script src="${ctxPath}/static/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctxPath}/static/js/bootstrap.min.js?v=3.3.6"></script>
</head>

<body class="gray-bg signin">

<div class="text-center loginscreen animated fadeInDown">
        <#--<div>-->
            <#--&lt;#&ndash;<h1 class="logo-name">UBR</h1>&ndash;&gt;-->
        <#--</div>-->
        <h1>视在城市大脑视频管理配置平台</h1>
        <br/>
        <h4 style="color: red;">${tips!}</h4>
        <form class="m-t login-form" role="form" action="${ctxPath}/login" method="post">
            <div class="form-group">
                <input type="text" name="username" class="form-control login-input" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control login-input" placeholder="密码" required="">
            </div>
            <#if kaptcha.getKaptchaOnOff() == true>
                <div class="form-group" style="float: left;">
                    <div class="col-sm-8" style="padding-left: 0px; padding-right: 0px;">
                        <input class="form-control" type="text" name="kaptcha" placeholder="验证码" required="">
                    </div>
                    <div class="col-sm-4" style="padding-left: 0px; padding-right: 0px;">
                        <img src="${ctxPath}/kaptcha" id="kaptcha" width="100%" height="100%"/>
                    </div>
                </div>
            </#if>
            <div class="form-group" style="float: left;">
                <div class="checkbox" style="text-align: left">
                    <label>
                        <input type="checkbox" name="remember" style="margin-top: 10px;">记住我
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
        </form>

<script>
    $(function () {
        $("#kaptcha").on('click', function () {
            $("#kaptcha").attr('src', '${ctxPath}/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
        });
    });
</script>

</body>

</html>
