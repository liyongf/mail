$(function(){
	  if(location.href.indexOf("load=detail")!=-1){
		  $("tr[class='luru']").css("display","none");
		  $("div[class='wz-bt']").css("display","none");
	  } 
	//绑定验证, 检查该月份是否已创建评估
	$("#ssaMonth").attr("ajaxurl","sfMineStandardAssessController.do?assessExist&ssaMineType="+$("#ssaMineType").val()+"&ssaAssessType="+$("#ssaAssessType").val()+"&id="+$("#id").val());

    $('[name^="lack"]').change(function(){
        var itemid=$(this).attr("name").substring(4,$(this).attr("name").length);
        if($(this).val()=="Y"){
            $("input[name=score"+itemid+"]").val(0);
            $("input[name=score"+itemid+"]").attr("readonly","readonly");
        }else{
            $("input[name=score"+itemid+"]").removeAttr("readonly");
        }
        $("input[name=score"+itemid+"]").trigger("blur");
    });
    $("input[name^='score']").each(function(){
       if($(this).val()==null || $(this).val()==''){
           $(this).val("0");
       }
    });
    $("input[name^='ssaSumScore']").each(function(){
        if($(this).val()==null || $(this).val()==''){
            $(this).val("0");
        }
        $(this).attr("readonly", "readonly");
    });
    $("input[name^='score']").on("focus", function(){
        var index = $(this).attr("name").substring(5);
        if($("select[name='lack"+index+"']>option:selected").val()!="Y") {
            $(this).attr("placeholder", $(this).val());
            $(this).val("");
        }
    });
  });


/**
 * 计算页面的总分
 * Author：张赛超
 * */
function calculate(){
    calculateSum("score", "lack", "ssaSumScore");
}

function calculateSum(score, lack, ssaSumScore){
    //计算所有input标签的和
    var inputSum = 0;       //所有input的总和
    var labelSum = 0;       //所有可用label的总和
    var resultNum = 0;       //这个用来记住页面的总得分
    $("input[name*='"+score+"']").each(function(){
        var value = 0;
        if ($(this).attr("placeholder") != null && $(this).attr("placeholder")!='') {
            value = $(this).attr("placeholder");
        }
        if ($(this).val() == "" || $(this).val() == null) {
            $(this).val(value);
        }

        /*******************************************************************/
        //校验：输入数字
        if(isNaN($(this).val()) == true){
            $(this).parent().children("span").text($(this).val()+"不是数字类型.").css("color", "#ff0000");

            $(this).val(value);
            return false;
        }else{
            $(this).parent().children("span").text("");
        }

        var name = $(this).attr("name");
        var index = name.replace("score","");
        var thisName = "lack"+index;
        var ls = $("label[name='"+thisName+"']").text();

        var max = Number(ls);

        //校验：数字范围
        if($(this).val() < 0){
            $(this).parent().children("span").text("分数必须大于0！").css("color", "#ff0000");
            $(this).val(value);
        }else{
            $(this).parent().children("span").text("");
            if($(this).val() > max){
                $(this).parent().children("span").text($(this).val()+"已超过标准分值！").css("color", "#ff0000");
                $(this).val(value);
            }else{
                $(this).parent().children("span").text("");
            }
        }
        /*******************************************************************/

        inputSum = parseFloat(inputSum) + parseFloat($(this).val());
    });

    var selectArray = $("select[name*='"+lack+"']");

    for(var i=1; i<=selectArray.length; i++){
        var thisName = lack+i;
        var thisSelect = $("select[name='"+thisName+"']");
        var thisLable = $("label[name='"+thisName+"']");

        if(thisSelect.val() == "N"){
            labelSum = parseFloat(labelSum) + parseFloat(thisLable.text());
        }else{
            labelSum = parseFloat(labelSum) + 0;
        }
    }

    if(labelSum == 0){        //如果100-B=0         B——缺项标准分数
        resultNum = 0;
    }else{
        resultNum = 100/labelSum*inputSum;
    }
    resultNum = resultNum.toFixed(2);
    $("#"+ssaSumScore+"").val(resultNum);
}

function calculateTotalSum(){
    var totalSum = 0;
    var count = 0;
    $("input[name^='ssaSumScore']").each(function(){
        if ($(this).attr("name")!='ssaSumScore'){
            if($(this).val()!=null&& $(this).val()!=""){
                totalSum = totalSum + parseFloat($(this).val());
            }
            count++;
        }
    });
    var ssaSumScore = 0;
    if (totalSum!=0 && count!=0) {
        ssaSumScore = totalSum/count;
    }
    $("#ssaSumScore").val(ssaSumScore.toFixed(2));
}


//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}