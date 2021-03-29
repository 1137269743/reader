<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>短评管理功能</title>
    <style>
        #dlgEvaluation{
            padding: 10px
        }
    </style>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">

    <script src="/resources/wangEditor.min.js"></script>

</head>
<body>

<div class="layui-container">
    <blockquote class="layui-elem-quote">短评列表</blockquote>
    <!-- 数据表格 -->
    <table id="grdEvaluation" lay-filter="grdEvaluation"></table>
</div>

<!--表单内容-->
<div id="dialog" style="padding: 10px;display: none">
    <form class="layui-form" >
        <div class="layui-form-item">
            <textarea id="reason" style="resize:none;height: 280px"  name="reason" required lay-verify="required" placeholder="请输入禁用原因" autocomplete="off" class="layui-textarea"></textarea>
        </div>
        <div class="layui-form-item" style="text-align: center">
            <!-- 提交按钮 -->
            <button id="submit" class="layui-btn" lay-submit="" lay-filter="btnSubmit">立即提交</button>
        </div>
    </form>
</div>
<script src="/resources/layui/layui.all.js"></script>
<script>
    function createTime(v){
        let date = new Date(v);
        let y = date.getFullYear();
        let m = date.getMonth()+1;
        m = m<10?'0'+m:m;
        let d = date.getDate();
        d = d<10?("0"+d):d;
        let h = date.getHours();
        h = h<10?("0"+h):h;
        let M = date.getMinutes();
        M = M<10?("0"+M):M;
        let s = date.getSeconds();
        s = s<10?("0"+s):s;
        let str = y+"-"+m+"-"+d+"  "+h+":"+M+":"+s;
        return str;
    }

    let table = layui.table; //table数据表格对象
    let $ = layui.$; //jQuery
    let editor = null; //wangEditor富文本编辑器对象
    //初始化图书列表
    table.render({
        elem: '#grdEvaluation'  //指定div
        , id : "evaluationList" //数据表格id
        , toolbar: "#toolbar" //指定工具栏,包含新增添加
        , url: "/management/evaluation/list" //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'createTime', title: '发布时间', sort:true, width: '300', templet:function (d) {
                return createTime(d.createTime);
            }}
            , {field: 'content', title: '短评内容', width: '200'}
            , {field: 'bookName', title: '图书', width: '200', templet : function (d) {
                return d.book.bookName;
            }}, {field: 'username', title: '用户名', width: '200', templet : function (d) {
                return d.member.username;
            }},
            , {type: 'space', title: '操作', width: '200' , templet : function(d){
                    //为每一行表格数据生成"修改"与"删除"按钮,并附加data-id属性代表图书编号
                    if (d.state == "enable") {
                        return "<button class='layui-btn layui-btn-sm btn-disable'  data-id='" + d.evaluationId + "' data-type='disable' onclick='showDisable(this)'>禁用</button>";
                    } else {
                        return "<button class='layui-btn layui-btn-sm layui-btn-disabled btn-disable' title='禁用原因："+d.disableReason+"'>已禁用</button>";
                    }
                }
            }
        ]]
    });

    let id
    function showDisable(obj){
        layui.layer.open({
            id: "dlgEvaluation", //指定div
            title: "禁用原因", //标题
            type: 1,
            content: $('#dialog').html(), //设置对话框内容,复制自dialog DIV
            area: ['820px', '430px'], //设置对话框宽度高度
            resize: false, //是否允许调整尺寸
        })
        id = $(obj).data("id");
    }
    layui.form.on('submit(btnSubmit)', function(data){
        let formData = data.field;
        let reason = formData.reason

        $.post("/management/evaluation/disable/" + id, {
            reason:reason
        }, function (json) {
            if(json.code=="0"){
                layui.layer.closeAll();
                table.reload('evaluationList');
                layui.layer.msg('数据操作成功,评论列表已刷新');
            }else{
                //处理失败,提示错误信息
                layui.layer.msg(json.msg);
            }
        }, "json");
        return false
    })
</script>
</body>
</html>