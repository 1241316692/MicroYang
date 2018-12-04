function changeSelectBoxAll(item) {
    $(item).is(":checked") ? $(item).closest("table").find(".select-box").prop('checked',true) : $(item).closest("table").find(".select-box").prop('checked',false);
}
function changeSelectBox(item) {
    var table = $(item).closest("table");
    table.find(".select-box").size() == table.find(".select-box:checked").size() ? table.find(".select-box-all").prop('checked', true) : table.find(".select-box-all").prop('checked', false);
}
function initPage(item, data, func) {
    if (data.pages > 1) {
        item.show();
        item.pagination({
            current: data.page_num,
            pageCount: data.pages,
            count: 2,
            jump: true,
            coping: true,
            prevContent: '上页',
            nextContent: '下一页',
            callback: function (api) {
                func(api.getCurrent());
            }
        });
    } else {
        item.hide();
    }
}

function inputMoney(item) {
    $(item).unbind('input propertychange').bind('input propertychange', function() {
        var $this = $(this);
        var val = $this.val();
        var reg = /^(\d*)(\.?)(\d{0,2})/g;
        val.replace(reg,function(rs,$1,$2,$3){
            val = $1 + $2 + $3;
        });
        $this.val(val);
    });
}

function inputYear(item) {
    $(item).unbind('input propertychange').bind('input propertychange', function() {
        var $this = $(this);
        var val = $this.val();
        var year = parseInt(val);
        var reg = /^(\d{0,4})/g;
        val.replace(reg,function(rs,$1){
            val = $1;
        });
        $this.val(val);
    });
}

function checkMoney(item, defVal) {
    var reg = /^(\d*)(\.?)(\d{0,2})/g;
    $(item).val().replace(reg,function(rs,$1,$2,$3){
        var money = 0;
        if (defVal != undefined) money = defVal;
        if ($2) $3 = $3.length == 0 ? '00' : ($3.length == 1 ? ($3 + '0') : $3);
        if ($1) money = $1 + $2 + $3;
        $(item).val(money);
    });
}

function inputNum(item) {
    $(item).unbind('input propertychange').bind('input propertychange', function() {
        var $this = $(this);
        var val = $this.val();
        $this.val(val.replace(/\D/g,''));
    });
}

function checkDate(item) {
    var isDate = true;

    var reg=/^(\d+)-(\d{1,2})-(\d{1,2})$/;
    var r=$(item).val().match(reg);
    if(r==null) {
        isDate = false;
    } else {
        r[2]=r[2]-1;
        var d= new Date(r[1],r[2],r[3]);
        if(d.getFullYear()!=r[1]) isDate = false;
        if(d.getMonth()!=r[2]) isDate = false;
        if(d.getDate()!=r[3]) isDate = false;
    }

    if (!isDate) $(item).val("");
}

function checkYear(item) {
    var year = $(item).val();
    var reg=/^(1949|19[5-9]\d|2\d{3})$/;
    if (!reg.test(year)) $(item).val('');
}
function val(a) {
    if (typeof(a) === "undefined" || a == null) return "";
    return a;
}

function valHtml(a) {
    if (typeof(a) === "undefined" || a == null) return "";
    return a.replace(/\n/g,'<br/>').replace(/\t/g,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;').replace(/\s/g,'&nbsp;');
}

function searchAreaIndex(selectedUnitId) {
    for (x in areas) {
        for (y in areas[x].units) {
            if (areas[x].units[y].id == selectedUnitId) {
                return x;
            }
        }
    }
}

function initArea(selectedUnitId) {
    var selectedAreaIndex = searchAreaIndex(selectedUnitId);

    var selects = '<input id="unit-input" type="hidden" name="unit_id" value="' + selectedUnitId + '">'
        + '<select onchange="changeUnitSelect(this)">'
        + '<option value="">请选择</option>';

    for (x in areas) {
        selects += '<option value="' + x + '"' + (x==selectedAreaIndex? ' selected=true' : '') + '>' + areas[x].name + '</option>';
    }

    selects += '</select>'
        + '<select id="unit-select" onchange="$(\'#unit-input\').val($(this).val())">' + initUnitOption(areas[selectedAreaIndex] ? areas[selectedAreaIndex].units : null, selectedUnitId) + '</select>';
    $("#unit-select-box").html(selects);
}

//设置cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}
function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}

function changeUnitSelect(item) {
    var index = $(item).val()
    $("#unit-input").val("");
    if (index) {
        $("#unit-select").html(initUnitOption(areas[index].units));
    } else {
        $("#unit-select").html(initUnitOption());
    }

}

function initUnitOption(units, selectedId) {
    var option = '<option value="">请选择</option>';
    for (x in units) {
        option += '<option value="' + units[x].id + '"' + (units[x].id==selectedId? ' selected=true' : '') + '>' + units[x].name + '</option>';
    }
    return option;
}

function getPuk(bindform, callback) {
    if (bindform.find("#pwd").length && bindform.find("#pwd").val().trim() == '') return showDialog("密码不能为空");
    if (bindform.find("#pwdR").length && bindform.find("#pwd").val() != bindform.find("#pwdR").val()) return showDialog("两次输入密码不一致");

    $.ajax({
        url: "/api/user/pubkey",
        type: "post",
        dataType: "json",
        cache: false,
        beforeSend: function() {
        },
        complete: function() {
        },
        success: function (result) {
            if (!authcCheck(result)) return;

            if (result.code == 200) {
                var pwd = bindform.find("#pwd").val();
                var modulus = result.data.modulus;
                var exponent = result.data.exponent;

                //加密
                var key = RSAUtils.getKeyPair(exponent, "", modulus);
                var apwd = RSAUtils.encryptedString(key, JSON.stringify({"password":pwd}));

                bindform.find("input[name=password]").val(apwd);
                callback();
            } else {
                alert(result.msg);
            }
        },error: function (result) {
            alert("网络连接失败！");
        }
    });
}

function editPwd(item) {
    $(item).closest("form").append('<input type="hidden" name="password">');
    $(item).parent().html('<input type="password" id="pwd" autocomplete="off">');
}
function logout() {
    alert("token: " + getCookie("authc_token"));
    clearCookie("authc_token");
    // window.location.href = "/";
}
var dialog_callback;
function showDialog(msg,fun) {
    // initCoverDiv(true);
    initDialogDiv(msg,"must");

    dialog_callback = fun;
    $(document).unbind("keyup").keyup(function(event){
        switch(event.keyCode) {
            case 27:
                cancelDialog(true);
                $(document).unbind("keyup");
        }
    });
}
function showConfirm(msg,fun) {
    // initCoverDiv(true);
    initDialogDiv(msg);

    dialog_callback = fun;
    $(document).unbind("keyup").keyup(function(event){
        switch(event.keyCode) {
            case 27:
                cancelDialog();
                $(document).unbind("keyup");
        }
    });
}
function showDialogWithFail() {
    showDialog("网络连接出错！");
}
function cancelDialog(isOk) {
    $("#cover-div").hide();
    $("#dialog-div-box").animate({"margin-top":"0",filter: "Alpha(Opacity=0, Style=0)", opacity: 0},"fast", function() {
        $("#dialog-div").hide();
    });

    if (isOk) {
        if (dialog_callback) {
            dialog_callback();
            dialog_callback = null;
        }
    }

}
function initCoverDiv(isTransform) {
    var cover_div = $("#cover-div");
    if (cover_div.length == 0) {
        $("body").append('<div id="cover-div" style="width: 100%; height: 100%; position: fixed; top: 0; left: 0; z-index: 900; background: #000000; filter: Alpha(Opacity=40, Style=0); opacity: 0.40;"></div>');
        cover_div = $("#cover-div");
    }
    cover_div.show();
    if (isTransform) {
        cover_div.css({filter: "Alpha(Opacity=0, Style=0)", opacity: 0});
    } else {
        cover_div.css({filter: "Alpha(Opacity=40, Style=0)", opacity: 0.40});
    }

    return cover_div;
}

function initDialogDiv(msg,type) {
    var dialog_div = $("#dialog-div");
    if (dialog_div.length == 0) {
        $("body").append('<div id="dialog-div" style="width: 100%; height: 100%; position: fixed; top: 0; left: 0; z-index: 999; display: none;">' +
            '<div style="position: absolute; left: 50%; top: 0; transform: translateX(-50%)">' +
            '<div id="dialog-div-box" class="shadow-box" style="width: 500px; background: white; margin-top: 100px; border-radius: 3px; position: relative;">' +
            '<p style="margin: 0; padding: 10px 20px; font-size: 18px;">提示</p>' +
            '<div id="dialog-div-content" style="padding: 0 20px; word-break:break-all; word-wrap:break-word; color: #808080"></div>' +
            '<div style="padding: 10px; text-align: right;">' +
            '<a class="btn btn-cancel" style="padding: 4px 15px;" onclick="cancelDialog()">取消</a>' +
            '<a class="btn btn-sure" style="padding: 4px 15px;" onclick="cancelDialog(true)">确定</a>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>');
        dialog_div = $("#dialog-div");
    }
    dialog_div.find("#dialog-div-content").text(msg);

    if (type == "must") {
        dialog_div.find(".btn-cancel").hide();
    } else {
        dialog_div.find(".btn-cancel").show();
    }

    var dialog_div_box = dialog_div.find("#dialog-div-box");
    if ($("#dialog-div").is(":hidden")) {
        dialog_div_box.css({"margin-top":"0",filter: "Alpha(Opacity=0, Style=0)", opacity: 0});
        dialog_div.show();
    }
    dialog_div_box.stop().animate({"margin-top":"100px", filter: "Alpha(Opacity=100, Style=0)", opacity: 1},"fast");

    return dialog_div;
}

function clickBtnSure(item) {
    $(item).addClass('btn-sure-click');
}
function unclickBtnSure(item) {
    $(item).removeClass('btn-sure-click');
}

function enterBtn(e, fun) {
    // 兼容FF和IE和Opera
    var theEvent = e || window.event;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        if (fun) fun();
    }
}

function authcCheck(result) {
    switch (result.code) {
        case 1001:
        case 1002:
            showDialog(result.msg, function () {
                openUrl("/");
            });
            return false;
    }
    return true;
}

var upload_color = "#e5e5e5";
var upload_color_complete = "#3285ff";
function uploadProgress(item, val) {
    val = val < 0 ? 0 : (val > 100 ? 100 : val);
    $(item).find('.circle-mask span').text(val);
    if(val <= 50 ){
        $(item).find('.circle-left').css({'transform':'rotate(0deg)',"background":upload_color});
        $(item).find('.circle-right').css({'transform':'rotate('+(val*3.6)+'deg)',"background":upload_color});
    }else{
        $(item).find('.circle-left').css({'transform':'rotate('+((val-50)*3.6)+'deg)',"background":upload_color});
        $(item).find('.circle-right').css({'transform':'rotate(0deg)', "background":upload_color_complete});
    }
}
function delData(url,id,backUrl) {
    if (confirm("否是删除?")) {
        $.ajax({
            url: url,
            type: "post",
            data: "id=" + id,
            dataType: "json",
            cache: false,
            beforeSend: function () {
            },
            complete: function () {
            },
            success: function (result) {
                if (result.code == 200) {
                    if (backUrl) {
                        window.location.href = backUrl;
                    } else {
                        history.back(-1);
                    }
                } else {
                    alert(result.msg);
                }
            }, error: function (result) {
                alert("网络连接失败");
            }
        });
    }
}
function enterEvent(e,fun){
    var ev = document.all ? window.event : e;
    if(ev.keyCode == 13){
        if (fun) fun();
    }
}
function initEditor(div) {
    var editor = new window.wangEditor(div);
    var editor_div = $(div);
    editor.customConfig.uploadImgServer = '/api/upload/pic';
    editor.customConfig.uploadFileName = 'files';
    editor.customConfig.onfocus = function () {
        editor_div.find(".w-e-toolbar").css({"border-color":"#4e96ed #4e96ed #ccc #4e96ed"});
        editor_div.find(".w-e-text-container").css("border-color", "#4e96ed");
    };
    editor.customConfig.onblur = function (html) {
        editor_div.find(".w-e-toolbar").css("border-color", "#ccc");
        editor_div.find(".w-e-text-container").css("border-color", "#ccc");
    };
    editor.create();
    return editor;
}
function initLeftMenuSelectedItem(href) {
    var items = $("#left-menu ul li a");
    for (var i = 0; i < items.size(); i++) {
        if (items.eq(i).attr("href") == href) {
            items.eq(i).addClass("item-selected");
            break;
        }
    }
}
function val(val, defVal) {
    if (val == undefined) return defVal == undefined ? '' : defVal;
    return val;
}

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) return pair[1];
    }
}
function uploadFile(item) {
    var progress_bt = $(item).siblings(".btn-input-upload");
    if (progress_bt.hasClass("btn-select-only")) return;

    var formData = new FormData();
    formData.append('files', $(item)[0].files[0]);
    thesisUploadXhr = $.ajax({
        url: "/api/upload/img",
        type: "post",
        data: formData,
        dataType: "json",
        cache: false, // 上传文件无需缓存
        processData: false, // 用于对data参数进行序列化处理 这里必须false
        contentType: false, // 必须
        beforeSend: function() {
            progress_bt.addClass("btn-input-uploading btn-select-only");
        },
        complete: function() {
            $(item).val("");
            progress_bt.removeClass("btn-select-only");
        },
        xhr: function(){
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){
                myXhr.upload.addEventListener('progress',function (ev) {
                    var percent = (ev.loaded / ev.total * 100).toFixed(0);
                    // console.log("per: " + percent);
                    progress_bt.text(percent + "%");
                }, false);
            }
            return myXhr;
        },
        success: function (result) {
            $(item).siblings("input[name=pic]").val(result.data.pic);
            $(item).siblings("input[name=small_pic]").val(result.data.small_pic);
            $(item).siblings("img").show().attr("src", result.data.small_pic);
            progress_bt.removeClass("btn-input-uploading").text("重新上传");
        },error: function () {
            progress_bt.text("上传失败");
        }
    });
}

/**
 * 自动将form表单封装成json对象
 */
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
//保留两位小数并且整数部分三位一个逗号分隔符的数字金钱标准表示法：
//这里假设我们即不知道输入数字的整数位数，也不知道小数位数
/*将100000转为100,000.00形式*/
var dealNumber = function(money){
    if(money && money!=null){
        money = String(money);
        var left=money.split('.')[0],right=money.split('.')[1];
        right = right ? (right.length>=2 ? '.'+right.substr(0,2) : '.'+right+'0') : '.00';
        var temp = left.split('').reverse().join('').match(/(\d{1,3})/g);
        return (Number(money)<0?"-":"") + temp.join(',').split('').reverse().join('')+right;
    }else if(money===0){   //注意===在这里的使用，如果传入的money为0,if中会将其判定为boolean类型，故而要另外做===判断
        return '0.00';
    }else{
        return "";
    }
};
/*将100,000.00转为100000形式*/
var undoNubmer = function(money){
    if(money && money!=null){
        money = String(money);
        var group = money.split('.');
        var left = group[0].split(',').join('');
        return Number(left+"."+group[1]);
    }else{
        return "";
    }
};
/**
 * param 将要转为URL参数字符串的对象
 * key URL参数字符串的前缀
 * encode true/false 是否进行URL编码,默认为true
 *
 * return URL参数字符串
 */
var urlEncode = function (param, key, encode) {
    if(param==null) return '';
    var paramStr = '';
    var t = typeof (param);
    if (t == 'string' || t == 'number' || t == 'boolean') {
        paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);
    } else {
        for (var i in param) {
            var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
            paramStr += urlEncode(param[i], k, encode);
        }
    }
    return paramStr;
};